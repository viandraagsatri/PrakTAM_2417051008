package com.example.praktam_2417051008

import com.example.praktam_2417051008.model.Question
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import com.example.praktam_2417051008.ui.theme.PrakTAM_2417051008Theme
import com.example.praktam_2417051008.network.RetrofitClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrakTAM_2417051008Theme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {

    var globalScore by remember { mutableIntStateOf(0) }
    var allQuestions by remember { mutableStateOf<List<Question>>(emptyList()) }
    var isFetchingData by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            allQuestions = RetrofitClient.instance.getQuestions()
            isFetchingData = false
            isError = false
        } catch (e: Exception) {
            isFetchingData = false
            isError = true
        }
    }

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(navController = navController, score = globalScore, isLoading = isFetchingData, isError = isError)
        }

        composable("detail/{namaKategori}") { backStackEntry ->
            val namaKategori = backStackEntry.arguments?.getString("namaKategori") ?: "Kategori"
            DetailKategoriScreen(namaKategori = namaKategori, navController = navController, allQuestions = allQuestions, onScoreAdd = { globalScore += 10 })
        }
    }
}

@Composable
fun HomeScreen(navController: NavController, score: Int, isLoading: Boolean, isError: Boolean, modifier: Modifier = Modifier) {
    val categories = listOf("Hardware", "Software", "Cyber", "AI", "Internet")

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
                text = "Pilih Kategori Kuis",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(12.dp))

            if (isLoading) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Menghubungkan ke Server...")
                }
            } else if (isError) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Gagal Memuat Data",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Pastikan koneksi internet Anda menyala",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(categories) { category ->
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                            modifier = Modifier.clickable {
                                navController.navigate("detail/$category")
                            }
                        ) {
                            Text(
                                text = category,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailKategoriScreen(namaKategori: String, navController: NavController, allQuestions: List<Question>, onScoreAdd: () -> Unit) {
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val filteredQuestions = remember(namaKategori, allQuestions) {
        allQuestions.filter { it.kategori == namaKategori }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Kuis: $namaKategori",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        delay(2000)
                        isLoading = false
                        snackbarHostState.showSnackbar("Proses kategori $namaKategori berhasil disimpan!")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Memproses...", style = MaterialTheme.typography.labelLarge)
                } else {
                    Text("Simpan Progres", style = MaterialTheme.typography.labelLarge)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredQuestions.isEmpty()) {
                Text(
                    text = "Belum ada soal untuk kategori ini di Server.",
                    color = MaterialTheme.colorScheme.outline
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredQuestions) { question ->
                        QuestionCard(
                            question = question,
                            onScoreIncrease = onScoreAdd
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Kembali", style = MaterialTheme.typography.labelLarge)
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
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
            if (!question.imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = question.imageUrl,
                    contentDescription = "Gambar Soal",
                    placeholder = painterResource(id = R.drawable.logo),
                    error = painterResource(id = R.drawable.hoax),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

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