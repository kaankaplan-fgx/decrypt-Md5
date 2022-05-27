package com.example.dijitalgaraj.service

import com.example.dijitalgaraj.model.Guid
import com.example.dijitalgaraj.model.HashModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.PUT


interface API {


    @PUT("kaan-kaplan-11374")
    fun putHash(@Body GUID : Guid) : Single<HashModel>
}