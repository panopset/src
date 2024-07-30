package com.panopset.tests.marin

import com.panopset.compat.AppVersion
import com.panopset.marin.bootstrap.PlatformMap
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PlatformMapTest {
    @Test
    fun test() {
        val linuxPlatform = PlatformMap().map["linux"]
        Assertions.assertNotNull(linuxPlatform)
        if (linuxPlatform == null) {
            return
        }
        Assertions.assertEquals("Linux", linuxPlatform.platformName  )
        Assertions.assertEquals("panopset_${AppVersion.getVersion()}_amd64.deb", linuxPlatform.artifactName )
    }
}
