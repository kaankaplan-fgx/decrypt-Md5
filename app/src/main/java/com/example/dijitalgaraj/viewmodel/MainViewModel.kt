package com.example.dijitalgaraj.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dijitalgaraj.model.Guid
import com.example.dijitalgaraj.model.HashModel
import com.example.dijitalgaraj.service.ApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.math.BigInteger
import java.security.MessageDigest

class MainViewModel : ViewModel() {
    private val apiService = ApiService()
    private val disposable = CompositeDisposable()

    val newEmail = MutableLiveData<String>()
    val error = MutableLiveData<Boolean>()
    val hash = MutableLiveData<String>()

    fun getDataFromApi(body : Guid) {
        disposable.add(apiService.getData(body)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<HashModel>(){
                override fun onSuccess(t: HashModel) {
                    hash.value = t.hash
                    generateEmail(t.hash!!,t.email!!)
                    error.value = false
                }

                override fun onError(e: Throwable) {
                    error.value = true
                }

            })
        )

    }

    private fun generateEmail(hash : String,email : String) {
        val alphabet = arrayOf(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '@', '-', '+', '_'
        )
        val splithash = arrayOfNulls<String>(hash.length / 32)
        for (i in splithash.indices) {
            splithash[i] = hash.substring(i * 32, i * 32 + 32)
        }

        var result: String? = ""
        for (i in splithash.indices) {
            result = getValue(alphabet, splithash[i]!!, result!!, email)
        }

        newEmail.value = result

    }

    fun md5Hash(str: String): String {
        val md = MessageDigest.getInstance("MD5")
        val bigInt = BigInteger(1, md.digest(str.toByteArray(Charsets.UTF_8)))
        return String.format("%032x", bigInt)
    }


    private fun getValue(
        arr: Array<Char>,
        currentHash: String,
        currentValue: String,
        email: String
    ): String? {
        val hashemail = md5Hash(email)

        for (i in arr.indices) {
            for (j in arr.indices) {
                val x = currentValue + arr[i].toString() + arr[j].toString()
                val hashedX = md5Hash(x)
                var result = hashemail + x + hashedX

                result = md5Hash(result)
                if (result == currentHash)
                    return x
            }
        }
        return ""
    }

}