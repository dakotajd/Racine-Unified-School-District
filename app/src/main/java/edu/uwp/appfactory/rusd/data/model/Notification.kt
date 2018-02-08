package edu.uwp.appfactory.rusd.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by dakota on 7/4/17.
 */

/* API SCHEMA

  "_id": ObjectId,
  "__v": Number,
  "title": String,
  "body": String,
  "districtWide": Boolean,
  "location": Location?,
  "sender": String,
  "messageId": Number,
  "createdAt": Date,
  "updatedAt": Date

 */

open class Notification() : RealmObject() {

    @SerializedName("_id") @PrimaryKey var id: String = ""
    @SerializedName("__v") var version: Int = 0
    var title: String = ""
    var body: String = ""
    var districtWide: Boolean = false
    var location: Location? = null
    var topic: Topic = Topic()
    var sender: String = ""
    var messageId: Int = 0
    var createdAt: Date = Date()
    var updatedAt: Date = Date()

    override fun toString(): String {
        return "Notification(id='$id', version=$version, title='$title', body='$body', districtWide = '$districtWide', Location = '$location', sender='$sender', messageId=$messageId, createdAt=$createdAt, updatedAt=$updatedAt)"
    }
}
