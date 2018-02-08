package edu.uwp.appfactory.rusd.ui.settings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.settings_subscription_item.*

import edu.uwp.appfactory.rusd.R
import edu.uwp.appfactory.rusd.data.local.RealmTopicRepository
import edu.uwp.appfactory.rusd.data.local.RealmUserRepository
import edu.uwp.appfactory.rusd.data.model.Subscription
import edu.uwp.appfactory.rusd.data.model.Topic

class SettingsActivity : AppCompatActivity() {

    private val userRepository = RealmUserRepository()
    private val topicRepository = RealmTopicRepository(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        subscriptions_recycler_view.layoutManager = LinearLayoutManager(this)
        val currentUser = userRepository.getCurrentUser()
        val subscriptions = mutableListOf<Subscription>()
        topicRepository.getTopics()?.forEach { topic ->
            subscriptions.add(Subscription(topic, currentUser!!))
        }

        val subscriptionAdapter = SettingsSubscriptionAdapter(subscriptions, this)
        subscriptions_recycler_view.adapter = subscriptionAdapter
        registerForContextMenu(subscriptions_recycler_view)
    }

}
