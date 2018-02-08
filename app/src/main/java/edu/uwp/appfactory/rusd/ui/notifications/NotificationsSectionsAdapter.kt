package edu.uwp.appfactory.rusd.ui.notifications

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import edu.uwp.appfactory.rusd.R
import edu.uwp.appfactory.rusd.data.model.Notification
import edu.uwp.appfactory.rusd.databinding.ListItemNotificationsBinding
import edu.uwp.appfactory.rusd.databinding.SectionHeaderNotificationsBinding
import org.zakariya.stickyheaders.SectioningAdapter

/**
 * Created by Jeremiah on 8/8/17.
 *
 * Sticky Header Section Adaptor for Notifications RecyclerView
 */

class NotificationsSectionsAdapter(var notifications: List<Notification>) : SectioningAdapter() {

    var sections: ArrayList<Section> = ArrayList()

    init {
        updateNotifications(notifications)
    }

    class Section {
        var title: String = ""
        var notifications: ArrayList<Notification> = ArrayList()
    }

    class ItemViewHolder(private val binding: ListItemNotificationsBinding)
        : SectioningAdapter.ItemViewHolder(binding.root) {

        fun bind(notification: Notification) {
            binding.setVariable(BR.notification, notification)
            binding.executePendingBindings()
        }
    }

    class HeaderViewHolder(private val binding: SectionHeaderNotificationsBinding)
        : SectioningAdapter.HeaderViewHolder(binding.root) {

        fun bind(section: Section) {
            binding.setVariable(BR.section, section)
            binding.executePendingBindings()
        }
    }

    // List must already be sorted before getting here, could easily adapt to sort the List here
    // or send it a HashMap instead of a List to create the Section ArrayList used by the library
    fun updateNotifications(notifications: List<Notification>) {
        this.notifications = notifications
        sections.clear()

        // sort notifications into buckets by topic name
        var title: String? = null
        var currentSection: Section? = null

        notifications.forEach { notification ->
            if (notification.topic.name != title) {
                if (currentSection != null) {
                    sections.add(currentSection!!)
                }

                currentSection = Section()
                title = notification.topic.name
                currentSection?.title = title!!
            }

            if (currentSection != null) {
                currentSection?.notifications?.add(notification)
            }
        }

        if (currentSection != null) {
            sections.add(currentSection!!)
        }
        notifyAllSectionsDataSetChanged()
    }

    override fun getNumberOfSections(): Int {
        return sections.size
    }

    override fun getNumberOfItemsInSection(sectionIndex: Int): Int {
        return sections[sectionIndex].notifications.size
    }

    override fun doesSectionHaveHeader(sectionIndex: Int): Boolean {
        return true
    }

    override fun doesSectionHaveFooter(sectionIndex: Int): Boolean {
        return false
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): SectioningAdapter.ItemViewHolder {
        val binding: ListItemNotificationsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.list_item_notifications, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, headerUserType: Int): SectioningAdapter.HeaderViewHolder {
        val binding: SectionHeaderNotificationsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.section_header_notifications, parent, false)
        return HeaderViewHolder(binding)
    }

    override fun onBindItemViewHolder(viewHolder: SectioningAdapter.ItemViewHolder?, sectionIndex: Int, itemIndex: Int, itemUserType: Int) {
        if (viewHolder is ItemViewHolder) {
            viewHolder.bind(sections[sectionIndex].notifications[itemIndex])
        }
    }

    override fun onBindHeaderViewHolder(viewHolder: SectioningAdapter.HeaderViewHolder?, sectionIndex: Int, headerUserType: Int) {
        if (viewHolder is HeaderViewHolder) {
            viewHolder.bind(sections[sectionIndex])
        }
    }

}