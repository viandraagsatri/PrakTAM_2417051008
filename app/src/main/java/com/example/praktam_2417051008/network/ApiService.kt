package com.example.praktam_2417051008.network

import com.example.praktam_2417051008.model.Question
import retrofit2.http.GET

interface ApiService {
    @GET("soal_kuis.json")
    suspend fun getQuestions(): List<Question>
}