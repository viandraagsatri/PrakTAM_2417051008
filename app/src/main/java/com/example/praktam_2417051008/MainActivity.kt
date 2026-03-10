package com.example.praktam_2417051008

import Model.Question
import Model.QuestionSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.praktam_2417051008.ui.theme.PrakTAM_2417051008Theme
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM_2417051008Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val allQuestions = QuestionSource.dummyQuestion

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Quiz Teknologi & Keamanan",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF001F3F),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
            }
        }

        items(allQuestions) { item ->
            QuestionCard(question = item)
        }
    }
}

@Composable
fun QuestionCard(question: Question) {
    var showPenjelasan by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(60.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.fakta),
                        contentDescription = "Fakta",
                        modifier = Modifier.size(30.dp)
                    )

                    Text(
                        text = "atau",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF0D47A1),
                        modifier = Modifier.padding(vertical = 0.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.hoax),
                        contentDescription = "Hoax",
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Pertanyaan:",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF0D47A1)
                    )
                    Text(
                        text = question.pertanyaan,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF001F3F),
                        textAlign = TextAlign.Justify
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { showPenjelasan = !showPenjelasan },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF001F3F), contentColor = Color.White),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (showPenjelasan) "Sembunyikan" else "Cek Jawaban",
                    fontWeight = FontWeight.Bold
                )
            }

            if (showPenjelasan) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFFBBDEFB), thickness = 2.dp)
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (question.jawabanBenar) "✔ FAKTA" else "✘ HOAX",
                    fontWeight = FontWeight.Black,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (question.jawabanBenar) Color(0xFF1B5E20) else Color(0xFFB71C1C)
                )
                Text(
                    text = question.penjelasan,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF37474F),
                    textAlign = TextAlign.Justify
                )
            }
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