package com.example.ticketapp2.ticket

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.ticketapp2.ui.theme.TicketApp2Theme
import kotlinx.coroutines.delay

@Composable
fun TicketScreen() {
    // ================= STATE (dikelola PARENT) =================
    var ticketPrice by remember { mutableIntStateOf(50_000) }
    var ticketQuantity by remember { mutableIntStateOf(1) }
    var buyerName by remember { mutableStateOf("") }
    var orderStatus by remember { mutableStateOf(OrderStatus.IDLE) }
    var orderRequestId by remember { mutableIntStateOf(0) }

    // ================= LAUNCHED EFFECT =================
    LaunchedEffect(orderRequestId) {
        if (orderRequestId == 0) return@LaunchedEffect
        delay(5_000)
        orderStatus = OrderStatus.SUCCESS
    }

    // ================= EVENT HANDLER (dipanggil child lewat callback) =================
    val onOrderClick: () -> Unit = {
        if (buyerName.isBlank()) {
            // CASE 1: nama kosong → langsung tampilkan validasi, tanpa proses
            orderStatus = OrderStatus.NAME_EMPTY
        } else {
            // CASE 2: nama terisi → tampilkan "Memproses", lalu picu LaunchedEffect
            orderStatus = OrderStatus.PROCESSING
            orderRequestId++
        }
    }

    // ================= MENERUSKAN STATE KE CHILD =================
    TicketContent(
        ticketPrice = ticketPrice,
        ticketQuantity = ticketQuantity,
        buyerName = buyerName,
        orderStatus = orderStatus,
        onBuyerNameChange = { newName -> buyerName = newName },
        onIncreaseQuantity = { ticketQuantity++ },
        onDecreaseQuantity = {
            // Jumlah tidak boleh kurang dari 1
            if (ticketQuantity > 1) {
                ticketQuantity--
            }
        },
        onOrderClick = onOrderClick
    )
}

@Preview(showBackground = true)
@Composable
fun TicketScreenPreview() {
    TicketApp2Theme {
        TicketScreen()
    }
}