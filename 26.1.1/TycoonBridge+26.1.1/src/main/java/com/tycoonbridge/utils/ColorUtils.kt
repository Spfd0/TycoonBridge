package com.tycoonbridge.utils

fun String.toColor(): Int = Integer.decode(this)

fun Int.toHexColor(): String = String.format("#%06X", this)
