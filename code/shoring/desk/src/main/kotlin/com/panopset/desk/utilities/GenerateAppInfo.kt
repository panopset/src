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
            GenerateAppInfo().go(args[0])
        }
    }

    private fun go(platformPropsFileBaseName: String) {
        val propsFileName = "${platformPropsFileBaseName}.properties"
        val propsFile = File(propsFileName)
        if (!propsFile.exists()) {
            throw Exception("Props file ${propsFile.canonicalPath} not found!")
        }
        val props = Propop.load(propsFile)
        val panopsetJarPath = "./legacy/target"
        val platformInstallerPath = "./target"
        val panopsetJarDir = File(panopsetJarPath)
        val platformInstallerDir = File(platformInstallerPath)
        generateJsonForJar(panopsetJarDir, props)
        generateJsonForInstaller(platformInstallerDir, props)
    }
}

private fun generateJsonForJar(file: File, props: Properties) {
    val name = file.name
            val targetFileForOneJar = File("/var/www/html/downloads/${props.getProperty("PLATFORM_KEY")}OneJar.json")
            checkParent(targetFileForOneJar)
            if (!targetFileForOneJar.exists()) {
                val platformKey = props.getProperty("PLATFORM_KEY")
                val json = Jsonop<Map<String, String>>().toJson(createJsonMap(platformKey,
                    File("/var/www/html/downloads/${platformKey}/panopset.jar")))
                Fileop.write(json, targetFileForOneJar)
            }
}

private fun generateJsonForInstaller(file: File, props: Properties) {
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
                val json = Jsonop<Map<String, String>>().toJson(createJsonMap(platform.fxArch,
                    File("/var/www/html/downloads/${platform.fxArch}/panopset.jar")))
                Fileop.write(json, targetFileForOneJar)
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
                val json = Jsonop<Map<String, String>>().toJson(createJsonMap(platform.fxArch,
                    File("/var/www/html/downloads/${platform.fxArch}/panopset.jar")))
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
