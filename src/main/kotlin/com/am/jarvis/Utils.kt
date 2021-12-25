@file:JvmName("Utils")
@file:JvmMultifileClass

package com.am.jarvis

import java.util.UUID

fun generateUuid(): String {
    return UUID.randomUUID().toString()
}
