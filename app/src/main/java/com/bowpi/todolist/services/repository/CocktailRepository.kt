package com.bowpi.todolist.services.repository
import com.bowpi.todolist.model.Cocktail
import com.bowpi.todolist.model.CocktailResponse
import com.bowpi.todolist.services.CocktailApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class CocktailRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(CocktailApiService::class.java)

    fun searchCocktails(query: String, callback: (List<Cocktail>) -> Unit) {
        val call = service.searchCocktails(query)
        call.enqueue(object : retrofit2.Callback<CocktailResponse> {
            override fun onResponse(call: retrofit2.Call<CocktailResponse>, response: retrofit2.Response<CocktailResponse>) {
                if (response.isSuccessful) {
                    val cocktails = response.body()?.drinks ?: emptyList()
                    callback(cocktails)
                } else {
                    callback(emptyList())
                }
            }

            override fun onFailure(call: retrofit2.Call<CocktailResponse>, t: Throwable) {
                callback(emptyList())
            }
        })
    }
}