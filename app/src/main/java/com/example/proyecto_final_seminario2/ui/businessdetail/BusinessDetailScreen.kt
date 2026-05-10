package com.example.proyecto_final_seminario2.ui.businessdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.proyecto_final_seminario2.data.explorer.models.Business

private val DetailBackground = Color(0xFFFBF8FC)
private val PrimaryBlue = Color(0xFF00527F)
private val TextPrimary = Color(0xFF202124)
private val TextMuted = Color(0xFF667085)
private val BorderSoft = Color(0xFFE6EAF0)

@Composable
fun BusinessDetailScreen(
    business: Business?,
    onBackClick: () -> Unit,
    onRateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = DetailBackground,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onRateClick,
                containerColor = PrimaryBlue,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { padding ->
        if (business == null) {
            MissingBusinessContent(
                onBackClick = onBackClick,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { DetailTopBar(onBackClick = onBackClick) }
                item { DetailHero(business = business) }
                item { MetricsGrid(business = business) }
                item { RecommendationCard(business = business) }
                item { HighlightedQualities(tags = business.tags) }
                item { Spacer(modifier = Modifier.height(72.dp)) }
            }
        }
    }
}

@Composable
private fun DetailTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
        }
        Text(
            text = "Detalle de Negocio",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
private fun DetailHero(business: Business) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.7f)
            .clip(RoundedCornerShape(2.dp))
    ) {
        AsyncImage(
            model = business.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0x99000000)
                        )
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(14.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(50),
                color = Color(0xFF67D7CD)
            ) {
                Text(
                    text = business.category.label.dropLastWhile { it == 's' },
                    fontSize = 11.sp,
                    color = Color(0xFF063C3A),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = business.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Av. Principal 123, Centro Historico",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun MetricsGrid(business: Business) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(
                title = "Servicio",
                value = business.rating.toString(),
                suffix = "/5.0",
                icon = Icons.Filled.Star,
                tint = PrimaryBlue,
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Atencion",
                value = "4.5",
                suffix = "/5.0",
                icon = Icons.Filled.ThumbUp,
                tint = Color(0xFF008E8A),
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(
                title = "Precio",
                value = "Q25 - Q45",
                suffix = "",
                icon = Icons.Filled.AttachMoney,
                tint = Color(0xFF7A4B00),
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Tiempo de espera",
                value = "15m promedio",
                suffix = "",
                icon = Icons.Filled.AccessTime,
                tint = Color(0xFFE02020),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    suffix: String,
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(88.dp),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = title, fontSize = 12.sp, color = Color(0xFF344054))
            }
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                if (suffix.isNotBlank()) {
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(text = suffix, fontSize = 11.sp, color = TextMuted)
                }
            }
        }
    }
}

@Composable
private fun RecommendationCard(business: Business) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0066A0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ThumbUp,
                contentDescription = null,
                tint = Color(0xFF8FD0FF),
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Tasa de", color = Color(0xFFB8E1FF), fontSize = 12.sp)
                Text(text = "recomendacion", color = Color(0xFFB8E1FF), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "Basado en 142 valoraciones", color = Color(0xFF8DC7EA), fontSize = 11.sp)
            }
            Text(
                text = "${business.recommendationPercent}%",
                color = Color(0xFF9BDBFF),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun HighlightedQualities(tags: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "Cualidades Destacadas",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            tags.take(3).forEach { tag ->
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderSoft)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFF475467),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = tag.uppercase(),
                            fontSize = 11.sp,
                            color = Color(0xFF475467)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MissingBusinessContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        DetailTopBar(onBackClick = onBackClick)
        Text(text = "No se encontro el negocio.", color = TextPrimary)
    }
}
