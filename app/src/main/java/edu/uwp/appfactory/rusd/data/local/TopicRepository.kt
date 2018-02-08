package edu.uwp.appfactory.rusd.data.local

import edu.uwp.appfactory.rusd.data.model.Topic

/**
 * Created by Jeremiah on 8/25/17.
 */
interface TopicRepository : BaseRepository<Topic> {
    fun updateTopics()
    fun getTopics() : List<Topic>?
}