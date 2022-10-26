package com.stas.easyfood.retrofit

import com.stas.easyfood.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealAPI {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

}