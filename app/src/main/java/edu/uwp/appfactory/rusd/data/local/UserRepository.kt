package edu.uwp.appfactory.rusd.data.local

import edu.uwp.appfactory.rusd.data.model.User

/**
 * Created by dakota on 8/11/17.
 */
interface UserRepository {

    fun update(user: User)
    fun getCurrentUser() : User?
    fun close()

}