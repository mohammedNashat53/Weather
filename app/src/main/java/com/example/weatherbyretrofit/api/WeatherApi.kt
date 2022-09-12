package com.example.weatherbyretrofit.api

import com.example.weatherbyretrofit.models.Weather
import retrofit2.Response
import retrofit2.http.GET

interface WeatherApi {
@GET("weather?q=Egypt&appid=b871d8406a6b1452e6faaf242b5f9cce&units=metric")
suspend fun getWeather() : Response<Weather>
}