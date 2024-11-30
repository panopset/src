package com.panopset.compat

/**
 * See deploy.properties comments for version upgrade procedure.
 *
 * This class is generated from version.tmplt.
 */
object AppVersion {

    fun getVersion(): String {
        return "1.5.1"
    }

    fun getBuildNumber(): String {
        return "202411301157"
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