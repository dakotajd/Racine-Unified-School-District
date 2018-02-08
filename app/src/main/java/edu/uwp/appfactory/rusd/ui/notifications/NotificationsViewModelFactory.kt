package edu.uwp.appfactory.rusd.ui.notifications

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import edu.uwp.appfactory.rusd.data.local.NotificationsRepository

/**
 * Created by dakota on 7/4/17.
 */
class NotificationsViewModelFactory(var repo: NotificationsRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return NotificationsViewModel(repo) as T
    }
}