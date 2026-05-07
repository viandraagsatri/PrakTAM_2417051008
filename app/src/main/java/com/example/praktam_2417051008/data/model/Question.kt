package com.example.praktam_2417051008.data.model

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("kategori")
    val kategori: String,

    @SerializedName("pertanyaan")
    val pertanyaan: String,

    @SerializedName("jawabanBenar")
    val jawabanBenar: Boolean,

    @SerializedName("penjelasan")
    val penjelasan: String,

    @SerializedName("imageUrl")
    val imageUrl: String? = null
)