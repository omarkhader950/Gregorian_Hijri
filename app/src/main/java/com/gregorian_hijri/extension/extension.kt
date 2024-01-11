package com.gregorian_hijri.extension

import java.text.SimpleDateFormat

fun String.convertDateToLong(): Long{
    val date = SimpleDateFormat("dd-MM-yyyy").parse(this)
    println(date.time)
    return date.time
}