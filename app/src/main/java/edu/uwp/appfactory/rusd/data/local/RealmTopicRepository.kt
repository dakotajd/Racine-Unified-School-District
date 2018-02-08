package edu.uwp.appfactory.rusd.data.local

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.getAs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.uwp.appfactory.rusd.RUSDApplication
import edu.uwp.appfactory.rusd.data.model.Topic
import io.realm.Realm
import io.realm.RealmResults

/**
 * Created by Jeremiah on 8/25/17.
 */
class RealmTopicRepository(val context: Context) : TopicRepository {

    private val TAG = "Topic Repository"
    private var realm: Realm? = null

    init {
        realm = Realm.getDefaultInstance()
    }

    private fun initTopics(topics: List<Topic>) {
        if (realm != null) {
            realm?.executeTransaction {
                realm?.delete(Topic::class.java)
                realm?.copyToRealmOrUpdate(topics)

            }
        } else {
            Log.e(TAG, "Realm is closed cannot init " + topics.toString())
        }
    }

    override fun getTopics() : RealmResults<Topic>? {
        val topics = realm?.where(Topic::class.java)?.findAll()
        return topics
    }

    override fun updateTopics() {
        UpdateTopics(this).execute(context)
    }

    override fun add(item: Topic) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(item: Topic) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(item: Topic) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close() {
        if (realm != null) {
            realm?.close()
            realm = null
        }
    }

    class UpdateTopics(val topicRepository: RealmTopicRepository) : AsyncTask<Context, Void, List<Topic>>() {

        private val TAG = "Update topics"
        var retried = false

        override fun doInBackground(vararg params: Context?): List<Topic>? {
            Log.d(TAG, "Update of topics started")
            var topics: List<Topic>? = null
            val (_, response, result) = "/topics".httpGet().responseJson()

            when (result) {
                is Result.Failure -> {
                    Log.e(TAG, "ERROR: " + result.getAs())
                    Log.e(TAG, " Response Code: " + response.httpStatusCode)
                    if (!retried && response.httpStatusCode == 401 && params[0] != null &&
                            RUSDApplication.shouldRetryRequest(params[0]!!)) {
                        retried = true
                        return doInBackground(params[0])
                    }
                }

                is Result.Success -> {
                    Log.d(TAG, "SUCCESS: " + result.get().obj().toString())
                    val tokenType = object : TypeToken<List<Topic>>() {}.type
                    topics = RUSDApplication.getGson().fromJson<List<Topic>>(result.get().obj().getJSONArray("data").toString(), tokenType)
                    return topics
                }
            }
            return emptyList()
        }

        override fun onPostExecute(result: List<Topic>) {
            super.onPostExecute(result)
            topicRepository.initTopics(result)
        }

    }
}