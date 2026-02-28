package com.example.praktam_2417051008

import Model.QuestionSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.praktam_2417051008.ui.theme.PrakTAM_2417051008Theme
import androidx.compose.ui.unit.dp
import Model.Question
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM_2417051008Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(

                    )
                }
            }
        }
    }
}

@Composable
fun Greeting() {
    val question = QuestionSource.dummyQuestion[0]

    var hasil by remember { mutableStateOf("") }
    var showPenjelasan by remember {mutableStateOf(false)}

    Column(modifier = Modifier.fillMaxSize().padding(all = 60.dp)) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(text = "Pertanyaan:")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = question.pertanyaan)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = question.ImageRes1),
                contentDescription = "Fakta",
                modifier = Modifier.size(150.dp).clickable {
                    if (!showPenjelasan) {
                        hasil = if (question.jawabanBenar) "Jawaban Benar" else "Jawaban Salah"
                        showPenjelasan = true
                    }
                }
            )

            Image(
                painter = painterResource(id = question.ImageRes2),
                contentDescription = "Hoax",
                modifier = Modifier.size(150.dp).clickable {
                    if (!showPenjelasan) {
                        hasil = if (!question.jawabanBenar) "Jawaban Benar" else "Jawaban Salah"
                        showPenjelasan = true
                    }
                }
            )
        }

        if(showPenjelasan){
            Text(text = hasil)
            Text(text = "Penjelasan:")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = question.penjelasan)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrakTAM_2417051008Theme {
        Greeting()
    }
}