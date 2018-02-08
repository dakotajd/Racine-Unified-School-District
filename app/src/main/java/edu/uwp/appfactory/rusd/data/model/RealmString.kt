package edu.uwp.appfactory.rusd.data.model

import io.realm.RealmObject

/**
 * Created by dakota on 8/19/17.
 */
open class RealmString() : RealmObject() {

    constructor(string: String) : this() {
        this.string = string
    }

    private var string: String = ""

    override fun toString(): String {
        return string
    }

}