package edu.uwp.appfactory.rusd.ui.notifications


import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.Toast
import edu.uwp.appfactory.rusd.R
import edu.uwp.appfactory.rusd.data.local.RealmNotificationRepository
import edu.uwp.appfactory.rusd.data.local.NotificationsRepository
import edu.uwp.appfactory.rusd.data.model.Notification
import edu.uwp.appfactory.rusd.services.RUSDFirebaseMessagingService
import kotlinx.android.synthetic.main.fragment_recycler_view.view.*
import edu.uwp.appfactory.rusd.ui.notifications.dialog.FilterDialogFragment
import org.zakariya.stickyheaders.StickyHeaderLayoutManager

/**
 * A simple [Fragment] subclass.
 */
class NotificationsFragment private constructor() : LifecycleFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationsSectionsAdapter
    private val layoutManager = StickyHeaderLayoutManager()

    //MVVM related
    private lateinit var notificationsRepo: NotificationsRepository
    private lateinit var viewModel: NotificationsViewModel

    companion object {

        @JvmStatic
        fun newInstance() : NotificationsFragment {
            val notificationsFragment: NotificationsFragment = NotificationsFragment()
            return notificationsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        notificationsRepo = RealmNotificationRepository(context)
        RUSDFirebaseMessagingService.setNotificationRepository(notificationsRepo)
        viewModel = ViewModelProviders.of(this, NotificationsViewModelFactory(notificationsRepo)).get(NotificationsViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        recyclerView = rootView.main_rv
        adapter = NotificationsSectionsAdapter(viewModel.getNotifications().value.orEmpty())

        subscribeUI(viewModel)

        layoutManager.setHeaderPositionChangedCallback { _, header, _, newPosition ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (newPosition == StickyHeaderLayoutManager.HeaderPosition.STICKY) {

                    header.elevation = 18F

                } else {
                    header.elevation = 8F
                }
            }
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        return rootView
    }

    override fun onResume() {
        super.onResume()
        notificationsRepo.updateNotifications()
    }

    private fun subscribeUI(viewModel: NotificationsViewModel) {
        viewModel.getNotifications().observe(this, Observer<List<Notification>> { notifications ->
            if (notifications != null) {
                adapter.updateNotifications(notifications)
                //Toast.makeText(context, "Notifications Updated", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.filter -> showFilterDialog()
        }
        return super.onOptionsItemSelected(item)
        //TODO - Implement action
    }

    override fun onDestroy() {
        super.onDestroy()
        RUSDFirebaseMessagingService.setNotificationRepository(null)
        notificationsRepo.close()
    }

    private fun showFilterDialog() {
        val fm: FragmentManager = fragmentManager
        val dialog: FilterDialogFragment = FilterDialogFragment.newInstance(notificationsRepo)
        dialog.show(fm, "filter")
    }
}