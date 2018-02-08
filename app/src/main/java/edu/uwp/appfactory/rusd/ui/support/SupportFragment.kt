package edu.uwp.appfactory.rusd.ui.support


import android.arch.lifecycle.*
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.uwp.appfactory.rusd.R
import edu.uwp.appfactory.rusd.data.local.ContactRepository
import edu.uwp.appfactory.rusd.data.local.RealmContactRepository
import edu.uwp.appfactory.rusd.data.model.Contact
import edu.uwp.appfactory.rusd.data.local.PublicationsRepository
import edu.uwp.appfactory.rusd.data.local.RealmPublicationsRepository
import edu.uwp.appfactory.rusd.data.model.Publication
import kotlinx.android.synthetic.main.fragment_support.view.*


/**
 * A simple [Fragment] subclass.
 *
 * Edited by Jeremiah on 7/21/2017.
 */
class SupportFragment private constructor() : LifecycleFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewPager: ViewPager
    private lateinit var dots: TabLayout
    private lateinit var recyclerViewAdapter: SupportRecyclerViewAdapter
    private val layoutManager = LinearLayoutManager(context)
    private lateinit var viewPagerAdapter: SupportViewPagerAdapter

    private lateinit var contactsRepo: ContactRepository
    private lateinit var publicationsRepo: PublicationsRepository
    private lateinit var viewModel: SupportViewModel

    companion object {

        @JvmStatic
        fun newInstance() : SupportFragment {
            val supportFragment: SupportFragment = SupportFragment()
            return supportFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactsRepo = RealmContactRepository(context)
        publicationsRepo = RealmPublicationsRepository(context)
        viewModel = ViewModelProviders.of(this, SupportViewModelFactory(contactsRepo, publicationsRepo)).get(SupportViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_support, container, false)

        recyclerView = rootView.support_recycler_view
        recyclerViewAdapter = SupportRecyclerViewAdapter(viewModel.getContacts().value.orEmpty())
        recyclerView.layoutManager = layoutManager

        subscribeUI(viewModel)

        recyclerView.adapter = recyclerViewAdapter
        viewPager = rootView.support_view_pager
        viewPagerAdapter = SupportViewPagerAdapter(activity.supportFragmentManager, viewModel.getSupportPublications().orEmpty())
        viewPager.adapter = viewPagerAdapter
        dots = rootView.support_dots
        dots.setupWithViewPager(viewPager)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        contactsRepo.updateContacts()
    }

    private fun subscribeUI(viewModel: SupportViewModel) {

        viewModel.getContacts().observe(this, Observer<List<Contact>> { contacts ->
            if (contacts != null) {
                recyclerViewAdapter.setContactsList(contacts)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        contactsRepo.close()
        publicationsRepo.close()
    }
}
