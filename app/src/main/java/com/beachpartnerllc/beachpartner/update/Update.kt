package com.beachpartnerllc.beachpartner.update

import com.beachpartnerllc.beachpartner.BuildConfig

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 12 Jun 2018 at 10:17 AM
 */
data class Update(
        val deviceType: String = "Android",
        val currentVersion: String = BuildConfig.VERSION_NAME,
        val currentBuild: Int = BuildConfig.VERSION_CODE,
        val status: String? = null,
        val message: String? = null,
        val updateAvailable: Boolean = false,
        val mandatoryUpdate: Boolean = false
)