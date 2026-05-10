package com.example.proyecto_final_seminario2.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.proyecto_final_seminario2.ui.explorer.components.ExplorerTopBar

private val ProfileBackground = Color(0xFFF8F9FE)
private val PrimaryBlue = Color(0xFF00527F)
private val TextPrimary = Color(0xFF202124)
private val TextMuted = Color(0xFF667085)
private val BorderSoft = Color(0xFFE3E8EF)
private const val MockAvatarUrl =
    "https://api.dicebear.com/9.x/lorelei/png?seed=tito%40ejemplo.com&size=128"

private data class ProfileReview(
    val businessName: String,
    val category: String,
    val date: String,
    val rating: Float,
    val priceRange: String,
    val recommendationPercent: Int,
    val tags: List<String>
)

private val mockReviews = listOf(
    ProfileReview(
        businessName = "La Parrilla del Barrio",
        category = "Restaurante",
        date = "01/05/2026",
        rating = 4.5f,
        priceRange = "Q25-Q45",
        recommendationPercent = 95,
        tags = listOf("Precio accesible", "Puntualidad")
    ),
    ProfileReview(
        businessName = "Farmacia El Pueblo",
        category = "Farmacia",
        date = "16/04/2026",
        rating = 4.2f,
        priceRange = "Q10-Q150",
        recommendationPercent = 80,
        tags = listOf("Atencion rapida", "Precio accesible")
    ),
    ProfileReview(
        businessName = "Cafe Central",
        category = "Restaurante",
        date = "12/04/2026",
        rating = 3.5f,
        priceRange = "Q25-Q45",
        recommendationPercent = 75,
        tags = emptyList()
    )
)

@Composable
fun ProfileScreen(
    onExploreClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = ProfileBackground,
        topBar = { ExplorerTopBar(title = "Punto Local") },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = false,
                    onClick = onExploreClick,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Explore,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = "Explorar") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = "Perfil") }
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { ProfileHeader(onLogoutClick = onLogoutClick) }
            item { TotalReviewsCard(total = 42) }
            item {
                Text(
                    text = "Historial",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
            items(mockReviews) { review ->
                HistoryCard(review = review)
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }
}

@Composable
private fun ProfileHeader(onLogoutClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = Color.White,
            border = BorderStroke(1.dp, BorderSoft),
            modifier = Modifier.size(76.dp)
        ) {
            AsyncImage(
                model = MockAvatarUrl,
                contentDescription = "Avatar de Tito Calderon",
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Tito Calderon",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Text(
            text = "tito@ejemplo.com",
            fontSize = 13.sp,
            color = TextMuted
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = onLogoutClick,
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFD92D20)),
            border = BorderStroke(1.dp, Color(0xFFD92D20))
        ) {
            Text(text = "Cerrar sesion", fontSize = 12.sp)
        }
    }
}

@Composable
private fun TotalReviewsCard(total: Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BorderSoft),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = PrimaryBlue,
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "TOTAL VALORACIONES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextMuted
                )
                Text(
                    text = total.toString(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }
        }
    }
}

@Composable
private fun HistoryCard(review: ProfileReview) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, BorderSoft),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = review.businessName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = review.category.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF008C96)
                    )
                }
                Text(
                    text = review.date,
                    fontSize = 10.sp,
                    color = TextMuted
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingBadge(rating = review.rating)
                Text(text = review.priceRange, fontSize = 11.sp, color = TextPrimary)
                Text(
                    text = "${review.recommendationPercent}% recomienda",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
            }
            if (review.tags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    review.tags.take(2).forEach { tag ->
                        QualityPill(text = tag)
                    }
                }
            }
        }
    }
}

@Composable
private fun RatingBadge(rating: Float) {
    Row(
        modifier = Modifier
            .background(PrimaryBlue, RoundedCornerShape(4.dp))
            .padding(horizontal = 7.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = rating.toString(),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun QualityPill(text: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Color(0xFFF3F5F8)
    ) {
        Text(
            text = text.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextMuted,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}
