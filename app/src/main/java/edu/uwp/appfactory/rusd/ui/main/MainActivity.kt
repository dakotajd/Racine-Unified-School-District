package edu.uwp.appfactory.rusd.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import edu.uwp.appfactory.rusd.R
import edu.uwp.appfactory.rusd.data.local.RealmLinkRepository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_layout.*
import android.content.Intent
import android.net.Uri
import android.support.v4.view.GravityCompat
import edu.uwp.appfactory.rusd.RUSDApplication
import edu.uwp.appfactory.rusd.data.local.RealmTopicRepository
import edu.uwp.appfactory.rusd.data.local.TopicRepository
import edu.uwp.appfactory.rusd.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var topicRepo: TopicRepository

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle.syncState()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO check if google play services is available and correct version

        // if user is not logged in redirect to login
        val authToken = getSharedPreferences("prefs", 0).getString("authToken", null)
        if (authToken == null) {
            RUSDApplication.logout(this)
        }

        topicRepo = RealmTopicRepository(this)
        topicRepo.updateTopics()

        setContentView(R.layout.activity_main)

        initViewPager()

        supportActionBar?.elevation = 0f
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = resources.getString(R.string.app_name)

        mDrawerToggle = object:ActionBarDrawerToggle(this, main_nav_drawer, R.string.app_name, R.string.rusd_name) {
            override fun onDrawerOpened(drawerView: View?) {
                super.onDrawerOpened(drawerView)
                invalidateOptionsMenu()
            }

            override fun onDrawerStateChanged(newState: Int) {
                super.onDrawerStateChanged(newState)
                Log.d("DrawerState", newState.toString())
            }

            override fun onDrawerClosed(drawerView: View?) {
                super.onDrawerClosed(drawerView)
                invalidateOptionsMenu()
            }
        }
        main_nav_drawer.addDrawerListener(mDrawerToggle)

        val m: Menu = nav_view.menu
        nav_view.itemIconTintList = null
        val menuGroup = m.addSubMenu(0, 0, 0, "Links")
        val repo = RealmLinkRepository(this)
        val links = repo.getAllLinks()
        val i = Intent(Intent.ACTION_VIEW)
        for (link in links) {
            val url = link.url
            menuGroup.add(link.name).setIcon(R.drawable.link).setOnMenuItemClickListener {
            i.data = Uri.parse(url)
                main_nav_drawer.closeDrawers()
            startActivity(i)
            false
            }
        }
        repo.close()
        val settings = m.findItem(R.id.navdrawer_item_settings)
        settings.setOnMenuItemClickListener {
            val i = Intent(this, SettingsActivity::class.java)
            main_nav_drawer.closeDrawers()
            startActivity(i)
            false
        }

    }

    private fun initViewPager() {
        val adapter = MainPagerAdapter(supportFragmentManager)
        main_viewpager.adapter = adapter
        main_viewpager.offscreenPageLimit = 2
        main_tabLayout.setupWithViewPager(main_viewpager)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (main_nav_drawer.isDrawerOpen(GravityCompat.START)) {
            main_nav_drawer.closeDrawers()
        } else {
            // TODO remove this is just for testing and call super method
            RUSDApplication.logout(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        topicRepo.close()
    }
}
