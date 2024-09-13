package com.panopset.desk.utilities

import com.google.gson.reflect.TypeToken
import com.panopset.compat.AppVersion
import com.panopset.compat.Fileop
import com.panopset.compat.Jsonop
import com.panopset.compat.Propop
import com.panopset.marin.secure.checksums.ChecksumType
import java.io.File
import java.io.StringWriter
import java.lang.reflect.Type
import java.util.*

class GenerateDownloadsTable {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            GenerateDownloadsTable().createDownloadsTable("/var/www/html/downloads")
        }
    }

    fun createDownloadsTable(path: String): String {
        val platformDownloadMap = createPlatformDownloadMap(path)
        val sw = StringWriter()
        sw.append("<table>")
        for (e in platformDownloadMap.entries) {
            val platformName = e.value.platformName
            val platformPropertiesFileName = "app$platformName.properties"
            val props = Propop.load(File(platformPropertiesFileName))
            val platformFullName = props.getProperty("PLATFORM_NAME").replace("\"", "")
            val launchPath = props.getProperty("LAUNCH_PATH").replace("\"", "")
            val javaCmd = props.getProperty("JAVA_CMD").replace("\"", "")
            sw.append("<tr class=\"menuBar\"><td colspan=\"5\"><b>$platformFullName</b></td></tr>\n")
            sw.append("<tr><th>Type</th><th>Download</th><th>Version</th><th>Bytes</th>")
            sw.append("<th>SHA-512</th></tr>")
            var firstTime = true
            for (platformDownload in e.value.platformDownloads) {
                val artifactType = platformDownload.artifactType
                val artifactName = platformDownload.artifactName
                val relPath = platformDownload.relPath
                val version = AppVersion.getFullVersion()
                val byteCount = platformDownload.byteCount
                val sha512 = platformDownload.sha512

                sw.append("\n\n<tr><td nowrap>\n")
                sw.append(artifactType)
                sw.append("</td><td nowrap>\n")
                sw.append("<a href=\"/downloads/$relPath$artifactName\">$artifactName</a>")
                sw.append("</td><td>\n")
                sw.append(version)
                sw.append("</td><td>\n")
                sw.append(byteCount)
                sw.append("</td><td class=\"dsw99\"><input class=\"output2\" type=\"text\" value=\"")
                sw.append(sha512)
                sw.append("\"></input></td></tr>")
                if (firstTime) {
                    sw.append("<tr><td colspan=\"2\"><pre>Launch path:</pre></td><td colspan=\"3\"><pre>${launchPath}flywheel</pre></td></tr>")
                } else {
                    sw.append("<tr><td colspan=\"2\"><pre>Java command:</pre></td><td colspan=\"3\"><pre>$javaCmd flywheel</pre></td></tr>")
                }
                firstTime = false
            }
            sw.append("<tr><td colspan=\"5\">&nbsp;</td></tr>")
        }
        sw.append("</table>\n\n")
        return sw.toString()
    }

    private fun createPlatformDownloadMap(path: String): Map<String, PlatformDownloadCollection> {
        val platformDownloadMap = Collections.synchronizedSortedMap<String, PlatformDownloadCollection>(TreeMap())
        val tempDirDownloads = File(path)
        for (f in tempDirDownloads.listFiles()!!) {
            if (f.isFile && f.extension == "json") {
                var artifactType: String
                var relPath = ""
                val i = f.name.indexOf("OneJar")
                if (i > -1) {
                    artifactType = "jar"
                    relPath = "${f.name.substring(0, i)}/"
                } else {
                    artifactType = "installer"
                }
                val jsonStr = Fileop.readTextFile(f)
                val mapType: Type = object : TypeToken<HashMap<String, String>>() {}.type
                val rawMap = Jsonop<HashMap<String, String>>().fromJson(jsonStr, mapType)
                val map = Collections.synchronizedSortedMap(TreeMap<String, String>())
                for (e in rawMap) {
                    map[e.key] = e.value
                }
                val ifn = map["ifn"] ?: return platformDownloadMap
                val platformKey = map["platformKey"] ?: return platformDownloadMap
                val archProps = loadPropsFor(platformKey)
                val dspOrd = archProps.getProperty("DSPORD")

                val bytes = map["bytes"] ?: return platformDownloadMap
                val sha512 = map[ChecksumType.SHA512.key] ?: return platformDownloadMap

                addPlatformIfNecessary(platformKey, platformDownloadMap).platformDownloads.add(
                    PlatformDownload(dspOrd, artifactType, ifn, relPath, bytes, sha512)
                )
            }
        }
        return platformDownloadMap
    }

    private fun loadPropsFor(fxArch: String): Properties {
        val propsFile = File("app$fxArch.properties")
        if (!propsFile.exists()) {
            throw RuntimeException(propsFile.canonicalPath)
        }
        return Propop.load(propsFile)
    }
}

private fun addPlatformIfNecessary(
    key: String,
    platformDownloadCollectionMap: MutableMap<String, PlatformDownloadCollection>
): PlatformDownloadCollection {
    if (platformDownloadCollectionMap.containsKey(key)) {
        return platformDownloadCollectionMap[key]!!
    }
    val rtn = PlatformDownloadCollection(key)
    platformDownloadCollectionMap[key] = rtn
    return rtn
}

private data class PlatformDownload(
    val platformOrder: String,
    val artifactType: String,
    val artifactName: String,
    val relPath: String,
    val byteCount: String,
    val sha512: String
) : Comparable<PlatformDownload> {
    override fun compareTo(other: PlatformDownload): Int {
        return "$platformOrder$artifactType".compareTo("${other.platformOrder}${other.artifactType}")
    }
}

private data class PlatformDownloadCollection(val platformName: String) {
    val platformDownloads: SortedSet<PlatformDownload> = Collections.synchronizedSortedSet(TreeSet())
}
