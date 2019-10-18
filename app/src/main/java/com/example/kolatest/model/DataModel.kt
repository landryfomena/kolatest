package com.example.kolatest.model

class DataModel(name: String, type: String, version_number: String, feature: String) {

    var name: String
        internal set
    var type: String
        internal set
    var version_number: String
        internal set
    var feature: String
        internal set

    init {
        this.name = name
        this.type = type
        this.version_number = version_number
        this.feature = feature

    }

}