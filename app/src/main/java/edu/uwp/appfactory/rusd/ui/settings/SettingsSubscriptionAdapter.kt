package edu.uwp.appfactory.rusd.ui.settings

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.*
import com.android.databinding.library.baseAdapters.BR
import edu.uwp.appfactory.rusd.R
import edu.uwp.appfactory.rusd.data.model.Subscription
import edu.uwp.appfactory.rusd.databinding.SettingsSubscriptionItemBinding

/**
 * Created by dakota on 8/25/17.
 */
class SettingsSubscriptionAdapter(val subscriptions: List<Subscription>, val context: Context) : RecyclerView.Adapter<SettingsSubscriptionAdapter.SettingsSubscriptionViewHolder>() {

    override fun onBindViewHolder(holder: SettingsSubscriptionViewHolder, position: Int) {
        holder.bind(subscriptions[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsSubscriptionViewHolder {
        val binding: SettingsSubscriptionItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.settings_subscription_item, parent, false)
        return SettingsSubscriptionViewHolder(binding, context)
    }

    override fun getItemCount(): Int = subscriptions.size

    class SettingsSubscriptionViewHolder(val binding: SettingsSubscriptionItemBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {

        fun bind(subscription: Subscription) {
            binding.setVariable(BR.subscription, subscription)
            binding.root.setOnClickListener { v ->
                val popup = PopupMenu(context, v)
                popup.inflate(R.menu.subscribe_options)
                //TODO - Implement these subscribe_options
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.unsubscribe -> {

                        }

                        R.id.district_wide -> {

                        }

                        R.id.building_only -> {

                        }
                    }
                    false
                }
                popup.show()
            }
            binding.executePendingBindings()
        }
    }
}