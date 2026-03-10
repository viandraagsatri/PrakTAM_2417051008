package Model

object QuestionSource {
    val dummyQuestion = listOf(
        Question(
            "Menggunakan HTTPS berarti website tersebut pasti terpercaya.",
            false,
            "HTTPS hanya mengenkripsi komunikasi antara client dan server. Website tetap bisa berisi konten berbahaya."),
        Question(
            "Enkripsi simetris memanfaatkan satu kunci identik untuk enkripsi maupun dekripsi.",
            true,
            "Enkripsi simetris menggunakan satu kunci yang sama untuk mengamankan dan membuka data."),
        Question(
            "SQL Injection adalah serangan yang menargetkan kelemahan pada sisi client.",
            false,
            "SQL Injection menyerang sisi server dengan memanipulasi query database melalui input yang tidak divalidasi."),
        Question(
            "Virtual Machine memungkinkan satu sistem operasi berjalan di atas sistem operasi lain.",
            true,
            "Virtual Machine menggunakan hypervisor untuk menjalankan sistem operasi secara virtual di dalam sistem utama."),
        Question(
            "Menambah kapasitas RAM selalu meningkatkan performa CPU secara langsung.",
            false,
            "RAM membantu multitasking, tetapi tidak meningkatkan kecepatan pemrosesan CPU itu sendiri."),
        Question(
            "DNS berfungsi untuk menerjemahkan nama domain menjadi alamat IP.",
            true,
            "DNS (Domain Name System) mengubah domain seperti google.com menjadi alamat IP numerik."),
        Question(
            "Hashing dan enkripsi adalah proses yang dapat dibalik dengan kunci yang benar.",
            false,
            "Enkripsi bisa dibalik dengan kunci, tetapi hashing bersifat satu arah dan tidak dapat dikembalikan ke bentuk asli."),
        Question(
            "REST API biasanya menggunakan protokol HTTP untuk komunikasi data.",
            true,
            "REST API umumnya berjalan di atas HTTP dan menggunakan metode seperti GET, POST, PUT, dan DELETE."),
        Question(
            "Firewall hanya melindungi perangkat dari serangan virus.",
            false,
            "Firewall mengontrol lalu lintas jaringan masuk dan keluar, bukan khusus untuk virus saja."),
        Question(
            "Pada Object-Oriented Programming, inheritance memungkinkan sebuah class mewarisi atribut dan method dari class lain.",
            true,
            "Inheritance memungkinkan reuse kode dengan menurunkan properti dan fungsi dari parent class."
        )
    )
}