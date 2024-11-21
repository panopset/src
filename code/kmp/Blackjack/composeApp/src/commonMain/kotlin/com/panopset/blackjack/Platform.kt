package com.panopset.blackjack

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform