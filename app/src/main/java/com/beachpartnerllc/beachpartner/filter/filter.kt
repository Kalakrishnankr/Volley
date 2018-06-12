package com.beachpartnerllc.beachpartner.filter

/**
 * Created by Owner on 6/7/2018.
 */
data class Filter(
        var eventType: String? = "Please Select",
        var subType: String? = "Please Select",
        var year: String? = "Please Select",
        var month: String? = "Please Select",
        var state: String? = "Please Select",
        var region: String? = "Please Select"
)