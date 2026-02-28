package Model
import androidx.annotation.DrawableRes
data class Question (
    val pertanyaan: String,
    val jawabanBenar: Boolean,
    val penjelasan: String,
    @DrawableRes val ImageRes1: Int,
    @DrawableRes val ImageRes2: Int
)