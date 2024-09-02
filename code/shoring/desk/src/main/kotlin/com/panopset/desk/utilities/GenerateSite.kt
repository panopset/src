package com.panopset.desk.utilities

import com.panopset.compat.*
import com.panopset.flywheel.FlywheelBuilder
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class GenerateSite {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            GenerateSite().go(args)
        }
    }

    private fun go(args: Array<String>) {
        val panopsetSiteDomainName = DevProps.getSiteDomainName()
        val blurb = if (panopsetSiteDomainName == "panopset.com") {
            ""
        } else {
            "<h1>Prototype</h1>This is a prototype for the next release of <a href=\"https://panopset.com\">panopset.com</a>."
        }
        FlywheelBuilder().file(File(args[0])).targetDirectory(File(args[1]))
            .map("previewBlurb", blurb)
            .map("downloadsTable", GenerateDownloadsTable().createDownloadsTable("/var/www/html/downloads"))
            .map("appVersion", AppVersion.getVersion())
            .map("fullVersion", AppVersion.getFullVersion())
            .map("dashDate", dashDateFormat.format(Date()))
            .map(props2map(Fileop.loadProps(File("deploy.properties"))))
            .construct().exec()
    }

    private val dashDateFormat = SimpleDateFormat("yyyy-MM-dd")
}
