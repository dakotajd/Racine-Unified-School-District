package edu.uwp.appfactory.rusd.ui.publications

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.uwp.appfactory.rusd.R
import edu.uwp.appfactory.rusd.data.model.Publication
import edu.uwp.appfactory.rusd.data.local.RealmPublicationsRepository
import edu.uwp.appfactory.rusd.data.local.PublicationsRepository
import kotlinx.android.synthetic.main.fragment_recycler_view.view.*

/**
 * Created by Marshall on 6/26/2017.
 * Edited by Jeremiah on 7/21/2017.
 */

class PublicationsFragment private constructor() : LifecycleFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PublicationsRecyclerViewAdapter
    private val layoutManager = LinearLayoutManager(context)

    //MVVM related
    private lateinit var publicationsRepo: PublicationsRepository
    private lateinit var viewModel: PublicationsViewModel

    companion object {

        @JvmStatic
        fun newInstance() : PublicationsFragment {
            val publicationsFragment: PublicationsFragment = PublicationsFragment()
            return publicationsFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        publicationsRepo = RealmPublicationsRepository(context)
        viewModel = ViewModelProviders.of(this, PublicationsViewModelFactory(publicationsRepo)).get(PublicationsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        recyclerView = rootView.main_rv
        recyclerView.layoutManager = layoutManager
        adapter = PublicationsRecyclerViewAdapter(viewModel.getPublications().orEmpty())

        recyclerView.adapter = adapter
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        publicationsRepo.close()
    }
}
