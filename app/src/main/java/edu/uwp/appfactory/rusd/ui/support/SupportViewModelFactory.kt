package edu.uwp.appfactory.rusd.ui.support

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import edu.uwp.appfactory.rusd.data.local.ContactRepository
import edu.uwp.appfactory.rusd.data.local.PublicationsRepository

/**
 * Created by dakota on 7/4/17.
 */
class SupportViewModelFactory(var contactsRepo: ContactRepository, var publicationsRepo: PublicationsRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return SupportViewModel(contactsRepo, publicationsRepo) as T
    }
}