package com.example.praktam_2417051008.data.repository
import com.example.praktam_2417051008.data.api.RetrofitClient
import com.example.praktam_2417051008.data.model.Question

class QuestionRepository {
    suspend fun getQuestions(): List<Question> {
        return try {
            RetrofitClient.instance.getQuestions()
        } catch (e: Exception) {
            emptyList()
        }
    }
}