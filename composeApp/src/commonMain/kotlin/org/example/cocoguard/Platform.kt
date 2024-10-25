package org.example.cocoguard

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform