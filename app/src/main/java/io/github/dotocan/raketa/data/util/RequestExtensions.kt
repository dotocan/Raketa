package io.github.dotocan.raketa.data.util

import java.security.MessageDigest
import java.util.*

fun randomId() = UUID.randomUUID().toString()

fun hashPassword(password: String): String {
    val md = MessageDigest.getInstance("SHA-256")
    val passwordDigest = md.digest(password.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })

    return passwordDigest
}