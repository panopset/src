package com.panopset.compat

/**
 * See deploy.properties comments for version upgrade procedure.
 *
 * This class is generated from version.tmplt.
 */
object AppVersion {

    fun getVersion(): String {
        return "1.4.8"
    }

    fun getBuildNumber(): String {
        return "202410201249"
    }

    fun getFullVersion(): String {
        return getVersion() + "." + getBuildNumber()
    }

    @JvmStatic
    fun main(vararg args: String?) {
        if (args.isNotEmpty()) {
            Logz.warn("AppVersion called with $args")
        }
        println(getVersion())
    }
}