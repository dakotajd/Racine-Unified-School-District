package edu.uwp.appfactory.rusd.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * Created by dakota on 8/12/17.
 */

open class Location : RealmObject() {

    @SerializedName("_id") @PrimaryKey var id: String = ""
    @SerializedName("__v") var version: Int = 0
    var name: String = ""
    var subscribers: RealmList<User>? = null
    var createdAt: Date = Date()
    var updatedAt: Date = Date()
    var deletedAt: Date? = null

    override fun toString(): String {
        return "Location(id='$id', version=$version, name='$name', subscribers=" + Arrays.toString(subscribers?.toArray()) + ", createdAt=$createdAt, updatedAt=$updatedAt, deletedAt=$deletedAt)"
    }


}