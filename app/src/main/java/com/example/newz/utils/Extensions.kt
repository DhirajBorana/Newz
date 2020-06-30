package com.example.newz.utils

fun String.removeSpace() = this.replace("\\s".toRegex(), "")

fun Any?.exhaustive() = Unit