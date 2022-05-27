package com.example.dijitalgaraj.util
import io.github.cdimascio.dotenv.dotenv


object Helper {
    val dotenv = dotenv {
        directory = "./assets"
        filename = "env"
    }
    val GUID = dotenv["GUID"]
}