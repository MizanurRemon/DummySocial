package com.example.dummysocial.Helpers

import java.text.SimpleDateFormat

fun changeDateFormat(currentFormat: String, newFormat: String, dateTime: String): String {
    var formattedDate: String = ""

    val input = SimpleDateFormat(currentFormat)
    val output = SimpleDateFormat(newFormat)
    try {
        val getAbbreviate = input.parse(dateTime)    // parse input
        formattedDate = output.format(getAbbreviate)    // format output
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return formattedDate
}