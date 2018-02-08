package edu.uwp.appfactory.rusd.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import edu.uwp.appfactory.rusd.ui.notifications.NotificationsFragment
import edu.uwp.appfactory.rusd.ui.publications.PublicationsFragment
import edu.uwp.appfactory.rusd.ui.support.SupportFragment

/**
 * Created by dakota on 6/10/17.
 */
class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return NotificationsFragment.newInstance()
            1 -> return SupportFragment.newInstance()
            2 -> return PublicationsFragment.newInstance()
            else -> return NotificationsFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        var title = ""
        when (position) {
            0 -> title = "Notifications"
            1 -> title = "Support"
            2 -> title = "Publications"
        }
        return title
    }

}