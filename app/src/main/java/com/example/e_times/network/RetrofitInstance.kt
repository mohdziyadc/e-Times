package com.example.e_times.network

import com.example.e_times.Utils.Constants.Companion.BASE_URL
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object{
        val api by lazy{


            /*
            The client is used to log the Retrofit response so as to help
            in debugging.
             */

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsAPI::class.java)

        }
    }

}