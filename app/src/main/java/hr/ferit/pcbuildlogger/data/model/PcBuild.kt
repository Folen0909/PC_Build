package hr.ferit.pcbuildlogger.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class PcBuild {

    var key: String? = null
    var name: String? = null

    constructor()

    constructor(name: String) {
        this.name = name
    }

}