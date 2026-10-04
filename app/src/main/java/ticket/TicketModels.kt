package com.example.ticketapp2.ticket

enum class OrderStatus(val message: String) {
    IDLE("Silakan pesan tiket"),
    NAME_EMPTY("Nama Masih Kosong"),
    PROCESSING("Memproses pesanan........."),
    SUCCESS("Tiket telah dipesan")
}

fun formatRupiah(amount: Int): String {
    val digits = amount.toString().reversed().chunked(3).joinToString(".").reversed()
    return "Rp$digits"
}