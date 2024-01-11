package com.gregorian_hijri.data.model

data class ConverterPostData  (
    val hijri: Hijri,
    val gregorian: Gregorian
)

data class Gregorian (
    val date: String,
    val format: String,
    val day: String,
    val weekday: GregorianWeekday,
    val month: GregorianMonth,
    val year: String,
    val designation: Designation
)

data class Designation (
    val abbreviated: String,
    val expanded: String
)

data class GregorianMonth (
    val number: Long,
    val en: String
)

data class GregorianWeekday (
    val en: String
)

data class Hijri (
    val date: String,
    val format: String,
    val day: String,
    val weekday: HijriWeekday,
    val month: HijriMonth,
    val year: String,
    val designation: Designation,
    val holidays: List<Any?>
)

data class HijriMonth (
    val number: Long,
    val en: String,
    val ar: String
)

data class HijriWeekday (
    val en: String,
    val ar: String
)
