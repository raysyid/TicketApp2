# Ticket Booking App

A simple Android ticket booking application built with **Kotlin** and **Jetpack Compose** as part of the Mobile Application Programming practicum.

## About

This project demonstrates the implementation of:

- **State Hoisting** with state managed by the parent composable.
- Managing ticket price, ticket quantity, and buyer name as UI state.
- **LaunchedEffect** for handling the ticket booking process.
- A 5-second simulated booking process using Kotlin Coroutines.
- Basic input validation when the buyer name is empty.

### Booking Flow

1. User enters the buyer name and selects the number of tickets.
2. If the buyer name is empty, the application displays:
   `Status : Nama Masih Kosong`
3. If the name is valid, the application displays:
   `Status : Memproses pesanan.........`
4. After 5 seconds, the status changes to:
   `Status : Tiket telah dipesan`

## Project Structure

```text
.
├── screenshots/
│   └── ... screenshots
├── Praktikum_....pdf
└── Android project files
