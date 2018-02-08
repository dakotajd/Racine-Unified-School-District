package edu.uwp.appfactory.rusd.ui.notifications

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.widget.TextView
import edu.uwp.appfactory.rusd.data.local.NotificationsRepository
import edu.uwp.appfactory.rusd.data.model.Notification
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by dakota on 7/4/17.
 *
 * Edited by jeremiah on 7/21/17.
 */
class NotificationsViewModel(notificationsRepository: NotificationsRepository) : ViewModel() {

    private var mNotifications: LiveData<List<Notification>> = notificationsRepository.getAllNotifications()

    fun getNotifications() : LiveData<List<Notification>> {
        return mNotifications
    }

    companion object {

        @JvmStatic
        @BindingAdapter("setDate")
        fun setDate(textView: TextView, date: Date) {
            val dateFormat = SimpleDateFormat("MMMM dd, yyyy hh:mm aa", Locale.getDefault())
            textView.text = dateFormat.format(date)
        }
    }
}