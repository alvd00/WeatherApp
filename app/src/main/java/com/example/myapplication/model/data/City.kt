package ru.geekbrains.androidwithkotlin.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(
    val city: String,
    val latitude: Double,
    val longitude: Double

    ) : Parcelable
