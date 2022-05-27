package com.example.dijitalgaraj.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dijitalgaraj.databinding.ActivityMainBinding
import com.example.dijitalgaraj.model.Guid
import com.example.dijitalgaraj.util.Helper
import com.example.dijitalgaraj.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding : ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getDataFromApi(Guid(Helper.GUID))


        observeLiveData()


    }

    private fun observeLiveData() {
        viewModel.error.observe(this, Observer {
            if (it){
                binding.tv.text = "Error"
            }
        })
        viewModel.newEmail.observe(this, Observer {
            if (it != null){
                binding.tv.text = it
            }
        })

    }


}