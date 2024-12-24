package com.panopset.compat

enum class Colors(val rgb: Long) {
    BLUE(parseHex("0000ff")),
    GREEN(parseHex("00ff00"))
}

fun parseHex(hexString: String): Long {
    return hexString.toLong(radix = 16)
}
