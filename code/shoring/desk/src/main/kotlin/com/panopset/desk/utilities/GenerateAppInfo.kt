package com.panopset.desk.utilities

import com.panopset.compat.*
import com.panopset.compat.Fileop.checkParent
import com.panopset.marin.bootstrap.PlatformMap
import com.panopset.marin.secure.checksums.ChecksumType
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class GenerateAppInfo {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            GenerateAppInfo().genappinf(args[0])
        }
    }

    fun genappinf(platformPropsFileBaseName: String): String {
        val propsFileName = "${platformPropsFileBaseName}.properties"
        val propsFile = File(propsFileName)
        if (!propsFile.exists()) {
            throw Exception("Props file ${propsFile.canonicalPath} not found!")
        }
        val props = Propop.load(propsFile)
        if (props.isEmpty) {
            throw Exception("Props file ${propsFile.canonicalPath} is empty!")
        }
        generateJsonForJar(props)
        generateJsonForInstaller(props)
        return ""
    }
}

private fun generateJsonForJar(props: Properties) {
    val platformKey = props.getProperty("PLATFORM_KEY")
    val panopsetJarPath = "./legacy/target"
    val jsonFile = File("${panopsetJarPath}${fsp}${props.getProperty("PLATFORM_KEY")}OneJar.json")
    checkParent(jsonFile)
    val checksumPath = "${ush}${fsp}panopset.jar"
    val json = Jsonop<Map<String, String>>().toJson(
        createJsonMap(
            platformKey,
            File(checksumPath)
        )
    )
    Fileop.write(json, jsonFile)
}

private fun generateJsonForInstaller(props: Properties) {
    val platformKey = props.getProperty("PLATFORM_KEY")
    val platformInstallerPath = "./target"
    val platformInstallerDir = File(platformInstallerPath)
    var firstTime = true
    if (platformInstallerDir.exists() && platformInstallerDir.isDirectory) {
        if (platformInstallerDir.listFiles() == null) {
            throw Exception("${platformInstallerDir.canonicalPath} is empty!")
        }
        for (file in platformInstallerDir.listFiles()!!) {
            if (firstTime && file.exists() && !file.isDirectory) {
                val json = Jsonop<Map<String, String>>().toJson(
                    createJsonMap(
                        platformKey,
                        file
                    )
                )
                Fileop.write(json, File("${platformInstallerPath}${fsp}${file.name}.json"))
                firstTime = false
            }
        }
    }
}

private fun generateJsonObsolete(file: File) {
    val name = file.name
    for (platform in PlatformMap().map.values) {
        if (file.extension == File(platform.artifactName).extension) {
            val targetFile = File("/var/www/html/downloads/pci_$name.json")
            val targetFileForOneJar = File("/var/www/html/downloads/${platform.fxArch}OneJar.json")
            checkParent(targetFileForOneJar)
            if (!targetFile.exists()) {
                val json = Jsonop<Map<String, String>>().toJson(createJsonMap(platform.fxArch, file))
                Fileop.write(json, targetFile)
            }
            if (!targetFileForOneJar.exists()) {
                val json = Jsonop<Map<String, String>>().toJson(
                    createJsonMap(
                        platform.fxArch,
                        File("/var/www/html/downloads/${platform.fxArch}/panopset.jar")
                    )
                )
                Fileop.write(json, targetFileForOneJar)
            }
        }
    }
}

fun createJsonMap(platformKey: String, installerFile: File): Map<String, String> {
    val map = HashMap<String, String>()
    map["platformKey"] = platformKey
    map["version"] = AppVersion.getFullVersion()
    map["bytes"] = byteCount(installerFile)
    map[ChecksumType.SHA512.key] = sha512(installerFile)
    map["ifn"] = installerFile.name
    return map
}

val fsp = Stringop.FSP
val ush = Stringop.USH
