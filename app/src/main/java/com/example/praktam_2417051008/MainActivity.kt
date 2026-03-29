package com.example.praktam_2417051008

import Model.Question
import Model.QuestionSource
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.praktam_2417051008.ui.theme.PrakTAM_2417051008Theme

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
    val categories = listOf("Hardware", "Software", "Cyber", "AI", "Internet")
    var score by remember {mutableIntStateOf(0)}

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        contentPadding = PaddingValues(16.dp),
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
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Skor Kamu: $score",
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        item {
            Text(
                text = "Kategori Kuis",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(categories) { category ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Text(
                            text = category,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Daftar Pertanyaan",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Pertanyaan:",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = question.pertanyaan,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
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
                    Text("FAKTA", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                }
                Text("VS", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary)

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
                    Text("HOAX", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                }
            }

            if (userPilihan != null) {
                val isCorrect = userPilihan == question.jawabanBenar

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = if (isCorrect) "✔ Jawaban Kamu Benar!" else "✘ Jawaban Kamu Salah!",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isCorrect) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.error
                )

                Button(
                    onClick = { showPenjelasan = !showPenjelasan },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        if (showPenjelasan) "Tutup Penjelasan" else "Lihat Penjelasan", style = MaterialTheme.typography.labelLarge)
                }
            }

            if (showPenjelasan) {
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                Text(
                    text = question.penjelasan,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(top = 8.dp)
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