@file:JvmName("Utils")
@file:JvmMultifileClass

package com.mega.demo

import java.util.UUID

fun generateUuid(): String {
    return UUID.randomUUID().toString()
}
