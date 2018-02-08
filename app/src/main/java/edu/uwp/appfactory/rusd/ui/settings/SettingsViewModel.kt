package edu.uwp.appfactory.rusd.ui.settings

import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.widget.TextView
import edu.uwp.appfactory.rusd.data.local.TopicRepository
import edu.uwp.appfactory.rusd.data.local.UserRepository
import edu.uwp.appfactory.rusd.data.model.Subscription

/**
 * Created by dakota on 8/25/17.
 */
class SettingsViewModel(topicsRepository: TopicRepository, userRepository: UserRepository) : ViewModel() {


    companion object {

        @JvmStatic
        @BindingAdapter("subscription")
        fun getSubscriptionStatus(view: TextView, subscription: Subscription) {

            //Did this just to test - it works. I subscribed our test account to School Data districtwide
            if (subscription.topic.subscribers?.map { realmString -> realmString.toString() }?.contains(subscription.currentUser.id)!!) {
                view.text = "Subbed"
            } else {
                view.text = "Unsubbed"
            }
        }
    }
}