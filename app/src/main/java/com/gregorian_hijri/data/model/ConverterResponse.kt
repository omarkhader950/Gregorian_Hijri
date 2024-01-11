package com.gregorian_hijri.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConverterResponse(
    @SerializedName("code")
    var code: Long = 0,
    @SerializedName("status")
    var status: String = "",
    @SerializedName("data")
    var data: Data = Data()

): Parcelable {

    @Parcelize
    data class Data(
        @SerializedName("hijri")
        var hijri: Hijri = Hijri(),
        @SerializedName("gregorian")
        var gregorian: Gregorian = Gregorian()
    ) : Parcelable

    @Parcelize
    data class Gregorian(
        @SerializedName("date")
        var date: String ="",
        @SerializedName("format")
        var format: String ="",
        @SerializedName("day")
        var day: String="",
        @SerializedName("weekday")
        var weekday: GregorianWeekday = GregorianWeekday(),
        @SerializedName("month")
        var month: GregorianMonth = GregorianMonth(),
        @SerializedName("year")
        var year: String ="",
        @SerializedName("designation")
        var designation: Designation = Designation()
    ) : Parcelable

    @Parcelize
    data class Designation(
        @SerializedName("abbreviated")
        var abbreviated: String = "",
        @SerializedName("expanded")
        var expanded: String = ""
    ) : Parcelable

    @Parcelize
    data class GregorianMonth(
        @SerializedName("number")
        var number: Long = 0,
        @SerializedName("en")
        var en: String = ""
    ) : Parcelable

    @Parcelize
    data class GregorianWeekday(
        @SerializedName("en")
        var en: String = ""
    ) : Parcelable

    @Parcelize
    data class Hijri(
        @SerializedName("date")
        var date: String = "",
        @SerializedName("format")
        var format: String = "",
        @SerializedName("day")
        var day: String = "",
        @SerializedName("weekday")
        var weekday: HijriWeekday = HijriWeekday(),
        @SerializedName("month")
        var month: HijriMonth = HijriMonth(),
        @SerializedName("year")
        var year: String = "",
        @SerializedName("designation")
        var designation: Designation = Designation(),
        @SerializedName("holidays")
        var holidays: ArrayList<String> = arrayListOf<String>()
    ) : Parcelable

    @Parcelize
    data class HijriMonth(
        @SerializedName("number")
        var number: Long = 0,
        @SerializedName("en")
        var en: String = "",
        @SerializedName("ar")
        var ar: String = ""
    ) : Parcelable

    @Parcelize
    data class HijriWeekday(
        @SerializedName("en")

        var en: String = "",
        @SerializedName("ar")

        var ar: String = ""
    ) : Parcelable

}





