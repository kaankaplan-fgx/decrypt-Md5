package com.example.dijitalgaraj.service

import com.example.dijitalgaraj.model.Guid
import com.example.dijitalgaraj.model.HashModel
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    //BASE http://career.dijitalgaraj.com/
    // hash/kaan-kaplan-11374

    private val BASE_URL = "http://career.dijitalgaraj.com/hash/"

    val callAdapter = RxJava3CallAdapterFactory.create()

    private val api = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(callAdapter)
        .build()
        .create(API::class.java)



    fun getData(GUID : Guid) : Single<HashModel>{
        return api.putHash(GUID)
    }
}