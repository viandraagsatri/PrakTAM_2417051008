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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

    var score by remember {mutableIntStateOf(0)}

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

                Surface(
                    color = Color(0xFF001F3F),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ){
                    Text(
                        text = "Skor Kamu: $score",
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
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
            QuestionCard(
                question = item,
                onScoreIncrease = {score += 10}
            )
        }
    }
}

@Composable
fun QuestionCard(question: Question, onScoreIncrease: () -> Unit) {
    var userPilihan by remember { mutableStateOf<Boolean?>(null) }
    var showPenjelasan by remember { mutableStateOf(false) }
    var scoreHasIncreased by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
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

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(
                        onClick = {
                            if (userPilihan == null){
                                userPilihan = true
                                if (question.jawabanBenar && !scoreHasIncreased) {
                                    onScoreIncrease()
                                    scoreHasIncreased = true
                                }
                            }
                        },
                        modifier = Modifier.size(60.dp)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.fakta),
                            contentDescription = "Fakta",
                            alpha = if (userPilihan == null || userPilihan == true) 1f else 0.3f
                        )
                    }
                    Text("FAKTA", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                }
                Text("VS", fontWeight = FontWeight.Black, color = Color(0xFF0D47A1))

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    IconButton(
                        onClick = {
                            if (userPilihan == null){
                                userPilihan = false
                                if (!question.jawabanBenar && !scoreHasIncreased) {
                                    onScoreIncrease()
                                    scoreHasIncreased = true
                                }
                            }
                        },
                        modifier = Modifier.size(60.dp)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.hoax),
                            contentDescription = "Hoax",
                            alpha = if (userPilihan == null || userPilihan == false) 1f else 0.3f
                        )
                    }
                    Text("HOAX", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                }
            }

            if (userPilihan != null) {
                val isCorrect = userPilihan == question.jawabanBenar

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (isCorrect) "✔ Jawaban Kamu Benar!" else "✘ Jawaban Kamu Salah!",
                    color = if (isCorrect) Color(0xFF1B5E20) else Color.Red,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = { showPenjelasan = !showPenjelasan },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color(0xFF001F3F),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        if (showPenjelasan) "Tutup Penjelasan" else "Lihat Penjelasan",
                        fontWeight = FontWeight.Bold)
                }
            }

            if (showPenjelasan) {
                HorizontalDivider(color = Color(0xFFBBDEFB))
                Text(
                    text = question.penjelasan,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF37474F),
                    modifier = Modifier.padding(top = 8.dp),
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