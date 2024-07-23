package com.bowpi.todolist.services
import com.bowpi.todolist.model.CocktailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {
    @GET("search.php")
    fun searchCocktails(@Query("s") query: String): Call<CocktailResponse>
}