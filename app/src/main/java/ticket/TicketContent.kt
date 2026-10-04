package com.example.ticketapp2.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ticketapp2.ui.theme.*

import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun TicketContent(
    ticketPrice: Int,
    ticketQuantity: Int,
    buyerName: String,
    orderStatus: OrderStatus,
    onBuyerNameChange: (String) -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit,
    onOrderClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalPrice = ticketPrice * ticketQuantity
    val isProcessing = orderStatus == OrderStatus.PROCESSING

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        TicketHeader(title = "Pemesanan Tiket")

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FormCard {
                BuyerNameField(
                    value = buyerName,
                    onValueChange = onBuyerNameChange
                )
                QuantitySelector(
                    quantity = ticketQuantity,
                    onIncrease = onIncreaseQuantity,
                    onDecrease = onDecreaseQuantity
                )
            }

            PriceSummaryCard(
                ticketPrice = ticketPrice,
                ticketQuantity = ticketQuantity,
                totalPrice = totalPrice
            )

            OrderButton(
                enabled = !isProcessing,
                onClick = onOrderClick
            )

            StatusCard(status = orderStatus)
        }
    }
}

// ---------- Header ----------
@Composable
private fun TicketHeader(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = PrimaryBlue,
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
            )
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

// ---------- Kartu pembungkus form (gaya kartu putih membulat pada gambar 1) ----------
@Composable
private fun FormCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            content()
        }
    }
}

// ---------- Input nama pembeli (stateless: value dan callback dari parent) ----------
@Composable
private fun BuyerNameField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Text(
        text = "Nama",
        color = TextDark,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    )
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Masukkan nama Anda", color = TextGray) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PrimaryBlue,
            unfocusedBorderColor = InputBorder,
            focusedTextColor = TextDark,
            unfocusedTextColor = TextDark
        )
    )
}

// ---------- Kontrol jumlah tiket (stateless) ----------
@Composable
private fun QuantitySelector(
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Text(
        text = "Jumlah Tiket",
        color = TextDark,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        QuantityButton(symbol = "-", onClick = onDecrease)
        Text(
            text = "$quantity",
            color = TextDark,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        QuantityButton(symbol = "+", onClick = onIncrease)
    }
}

@Composable
private fun QuantityButton(symbol: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(width = 80.dp, height = 48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = QtyButtonBg,
            contentColor = PrimaryBlue
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(text = symbol, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    }
}

// ---------- Ringkasan harga dan total (stateless) ----------
@Composable
private fun PriceSummaryCard(
    ticketPrice: Int,
    ticketQuantity: Int,
    totalPrice: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PriceRow(label = "Harga Tiket", value = formatRupiah(ticketPrice))
            PriceRow(label = "Jumlah Tiket", value = "$ticketQuantity tiket")
            HorizontalDivider(color = InputBorder)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total Harga",
                    color = TextDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = formatRupiah(totalPrice),
                    color = AccentPeachDark,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun PriceRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextGray, fontSize = 14.sp)
        Text(
            text = value,
            color = TextDark,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ---------- Tombol Pesan Tiket (stateless) ----------
@Composable
private fun OrderButton(enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPeach,
            contentColor = Color.White,
            disabledContainerColor = AccentPeachLight,
            disabledContentColor = Color.White
        )
    ) {
        Text(text = "Pesan Tiket", fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

// ---------- Area status (stateless) ----------
@Composable
private fun StatusCard(status: OrderStatus) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(statusBackground(status), RoundedCornerShape(14.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusIndicator(status = status)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Status : ",
            color = statusTextColor(status),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = status.message,
            color = statusTextColor(status),
            fontSize = 14.sp
        )
    }
}

@Composable
private fun StatusIndicator(status: OrderStatus) {
    when (status) {
        OrderStatus.IDLE -> Unit
        OrderStatus.PROCESSING -> CircularProgressIndicator(
            modifier = Modifier.size(18.dp),
            color = PrimaryBlue,
            strokeWidth = 2.dp
        )
        OrderStatus.NAME_EMPTY -> StatusDot(isSuccess = false, color = ErrorRed)
        OrderStatus.SUCCESS -> StatusDot(isSuccess = true, color = SuccessGreen)
    }
}

@Composable
private fun StatusDot(isSuccess: Boolean, color: Color) {
    Canvas(modifier = Modifier.size(22.dp)) {
        // Lingkaran latar
        drawCircle(color = color)

        val strokeWidth = size.minDimension * 0.12f
        val strokeStyle = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )

        if (isSuccess) {
            // Ikon ceklis
            val checkPath = Path().apply {
                moveTo(size.width * 0.28f, size.height * 0.52f)
                lineTo(size.width * 0.44f, size.height * 0.68f)
                lineTo(size.width * 0.72f, size.height * 0.36f)
            }
            drawPath(path = checkPath, color = Color.White, style = strokeStyle)
        } else {
            // Ikon tanda seru: garis vertikal + titik
            val centerX = size.width / 2f
            drawLine(
                color = Color.White,
                start = Offset(centerX, size.height * 0.26f),
                end = Offset(centerX, size.height * 0.58f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
            drawCircle(
                color = Color.White,
                radius = strokeWidth * 0.6f,
                center = Offset(centerX, size.height * 0.76f)
            )
        }
    }
}

private fun statusBackground(status: OrderStatus): Color = when (status) {
    OrderStatus.IDLE -> NeutralBg
    OrderStatus.NAME_EMPTY -> ErrorBg
    OrderStatus.PROCESSING -> ProcessingBg
    OrderStatus.SUCCESS -> SuccessBg
}

private fun statusTextColor(status: OrderStatus): Color = when (status) {
    OrderStatus.IDLE -> TextGray
    OrderStatus.NAME_EMPTY -> ErrorRed
    OrderStatus.PROCESSING -> PrimaryBlue
    OrderStatus.SUCCESS -> SuccessGreen
}