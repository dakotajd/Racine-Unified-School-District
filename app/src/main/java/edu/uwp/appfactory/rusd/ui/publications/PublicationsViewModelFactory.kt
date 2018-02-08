package edu.uwp.appfactory.rusd.ui.publications

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import edu.uwp.appfactory.rusd.data.local.PublicationsRepository

/**
 * Created by dakota on 7/4/17.
 */
class PublicationsViewModelFactory(var publicationsRepository: PublicationsRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return PublicationsViewModel(publicationsRepository) as T
    }
}