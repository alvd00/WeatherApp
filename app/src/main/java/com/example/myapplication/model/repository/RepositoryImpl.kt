package com.example.myapplication.model.repository

import ru.geekbrains.androidwithkotlin.model.data.Weather
import ru.geekbrains.androidwithkotlin.model.data.getRussianCities
import ru.geekbrains.androidwithkotlin.model.data.getWorldCities
import ru.geekbrains.androidwithkotlin.model.repository.Repository

class RepositoryImpl : Repository {
    override fun getWeatherFromServer() = Weather()

    override fun getWeatherFromLocalStorageRus() = getRussianCities()

    override fun getWeatherFromLocalStorageWorld() = getWorldCities()

}