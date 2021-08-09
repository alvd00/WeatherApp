package ru.geekbrains.androidwithkotlin.model.data

import android.os.Parcelable
import com.example.myapplication.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val icon: Int = R.drawable.ic_baseline_wb_sunny_24,
    val temperature: Int = 0,
    val feelsLike: Int = 0,
    val pressure: Int = 10
) : Parcelable

fun getDefaultCity() = City("Москва", 55.5578, 37.61729)