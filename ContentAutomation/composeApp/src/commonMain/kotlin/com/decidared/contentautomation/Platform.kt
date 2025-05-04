package com.decidared.contentautomation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform