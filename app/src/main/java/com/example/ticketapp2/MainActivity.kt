package com.example.ticketapp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ticketapp2.ticket.TicketScreen
import com.example.ticketapp2.ui.theme.TicketApp2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicketApp2Theme {
                TicketScreen()
            }
        }
    }
}