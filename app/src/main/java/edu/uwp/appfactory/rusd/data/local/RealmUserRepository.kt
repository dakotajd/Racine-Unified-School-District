package edu.uwp.appfactory.rusd.data.local

import android.util.Log
import edu.uwp.appfactory.rusd.data.model.User
import io.realm.Realm

/**
 * Created by dakota on 8/11/17.
 */
class RealmUserRepository : UserRepository {

    private val TAG = "User Repository"

    override fun getCurrentUser() : User? {
       return realm?.where(User::class.java)?.findFirst()
    }

    private var realm: Realm? = null

    init {
        realm = Realm.getDefaultInstance()
    }

    override fun update(item: User) {
        if (realm != null) {
            realm?.executeTransaction {
                realm?.delete(User::class.java)
                realm?.copyToRealmOrUpdate(item)
            }

            Log.d(TAG, "User saved: " + item)

        } else {
            Log.e(TAG, "Realm is closed cannot add " + item.toString())
        }
    }

    override fun close() {
        if (realm != null) {
            realm?.close()
            realm = null
        }
    }

//    override fun subscribe() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun saveTopics(result: List<Topic>?) {
//        if (realm != null) {
//            realm?.executeTransaction { realm?.copyToRealmOrUpdate(result) }
//        }
//    }
//
//    // TODO need to check this on main activity start currently only checks on login
//    class UpdateTopics(val userRepository: RealmUserRepository) : AsyncTask<Void, Void, List<Topic>>() {
//
//        private val TAG = "Update topics"
//        var retried = false
//
//        override fun doInBackground(vararg params: Void?): List<Topic>? {
//            Log.d(TAG, "Update of topics started")
//            var topics: List<Topic>? = null
//            val (_, response, result) = "/topics".httpGet().responseJson()
//
//            when (result) {
//                is Result.Failure -> {
//                    //TODO
//                }
//
//                is Result.Success -> {
//                    Log.d(TAG, "SUCCESS: " + result.getAs())
//                    val tokenType = object : TypeToken<List<Topic>>() {}.type
//                    topics = Gson().fromJson<List<Topic>>(result.get().obj().getJSONArray("data").toString(), tokenType)
//                    return topics
//                }
//            }
//            return emptyList()
//        }
//
//        override fun onPostExecute(result: List<Topic>?) {
//            super.onPostExecute(result)
//            userRepository.saveTopics(result)
//            userRepository.setDefaultSubscriptions()
//        }
//
//    }


}