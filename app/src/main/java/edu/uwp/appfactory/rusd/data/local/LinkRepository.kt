package edu.uwp.appfactory.rusd.data.local

import edu.uwp.appfactory.rusd.data.model.Link
import edu.uwp.appfactory.rusd.data.model.Notification

/**
 * Created by dakota on 7/23/17.
 */
interface LinkRepository {

    fun initLinks(links: List<Link>)
    fun getAllLinks() : List<Link>
    fun updateLinks()
    fun close()
}