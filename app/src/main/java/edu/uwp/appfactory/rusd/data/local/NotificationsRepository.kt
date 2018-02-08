package edu.uwp.appfactory.rusd.data.local

import android.arch.lifecycle.LiveData
import edu.uwp.appfactory.rusd.data.model.Notification

/**
 * Created by dakota on 7/4/17.
 */
interface NotificationsRepository : BaseRepository<Notification> {
    /**
     * Define additional Notification related methods here
     */

    fun getAllNotifications() : LiveData<List<Notification>>
    fun setAllNotifications()
    fun getAllTopicNames() : Array<CharSequence>
    fun updateNotifications()
}