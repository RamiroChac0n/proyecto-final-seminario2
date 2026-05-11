package com.example.proyecto_final_seminario2.ui.businessdetail

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.proyecto_final_seminario2.data.places.models.PlaceDetailsItem

@Composable
fun BusinessDetailScreen(
    state: BusinessDetailUiState,
    onBackClick: () -> Unit,
    onRateClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            if (state.place != null) {
                FloatingActionButton(
                    onClick = onRateClick,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                }
            }
        }
    ) { padding ->
        when {
            state.isLoading -> {
                LoadingContent(
                    onBackClick = onBackClick,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }

            state.errorMessage != null -> {
                ErrorContent(
                    message = state.errorMessage,
                    onBackClick = onBackClick,
                    onRetryClick = onRetryClick,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }

            state.place == null -> {
                MissingPlaceContent(
                    onBackClick = onBackClick,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }

            else -> {
                PlaceDetailContent(
                    place = state.place,
                    isUsingCachedData = state.isUsingCachedData,
                    onBackClick = onBackClick,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }
        }
    }
}

@Composable
private fun LoadingContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        DetailTopBar(onBackClick = onBackClick)

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        DetailTopBar(onBackClick = onBackClick)

        Card(
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = onRetryClick) {
                    Text(text = "Reintentar")
                }
            }
        }
    }
}

@Composable
private fun MissingPlaceContent(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        DetailTopBar(onBackClick = onBackClick)

        Text(
            text = "No se encontró el lugar.",
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun PlaceDetailContent(
    place: PlaceDetailsItem,
    isUsingCachedData: Boolean,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            DetailTopBar(onBackClick = onBackClick)
        }

        if (isUsingCachedData) {
            item {
                Text(
                    text = "Mostrando detalles guardados.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }
        }

        item {
            DetailHero(place = place)
        }

        item {
            MetricsGrid(place = place)
        }

        item {
            ContactInfoCard(place = place)
        }

        if (place.openingHours.isNotEmpty()) {
            item {
                OpeningHoursCard(openingHours = place.openingHours)
            }
        }

        item {
            RecommendationCard()
        }

        item {
            HighlightedQualities()
        }

        item {
            Spacer(modifier = Modifier.height(72.dp))
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
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = "Detalle de Negocio",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(48.dp))
    }
}

@Composable
private fun DetailHero(place: PlaceDetailsItem) {
    val heroContentColor = MaterialTheme.colorScheme.inverseOnSurface

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.7f)
            .clip(RoundedCornerShape(2.dp))
    ) {
        PlaceHeroImage(
            place = place,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.scrim.copy(alpha = 0.65f)
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
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = place.category.label,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = place.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = heroContentColor
            )

            if (!place.address.isNullOrBlank()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = heroContentColor,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = place.address,
                        fontSize = 12.sp,
                        color = heroContentColor
                    )
                }
            }
        }
    }
}

@Composable
private fun PlaceHeroImage(
    place: PlaceDetailsItem,
    modifier: Modifier = Modifier
) {
    val initial = place.name
        .trim()
        .firstOrNull()
        ?.uppercaseChar()
        ?.toString()
        ?: "?"

    if (place.photoUri.isNullOrBlank()) {
        PlaceInitialPlaceholder(
            initial = initial,
            modifier = modifier
        )
        return
    }

    SubcomposeAsyncImage(
        model = place.photoUri,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        loading = {
            PlaceInitialPlaceholder(
                initial = initial,
                modifier = Modifier.fillMaxSize()
            )
        },
        error = {
            PlaceInitialPlaceholder(
                initial = initial,
                modifier = Modifier.fillMaxSize()
            )
        },
        success = {
            SubcomposeAsyncImageContent()
        }
    )
}

@Composable
private fun PlaceInitialPlaceholder(
    initial: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.primaryContainer
        ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun MetricsGrid(place: PlaceDetailsItem) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(
                title = "Rating Google",
                value = place.rating?.toString() ?: "N/D",
                suffix = if (place.rating != null) "/5.0" else "",
                icon = Icons.Filled.Star,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )

            MetricCard(
                title = "Valoraciones",
                value = place.userRatingCount?.toString() ?: "N/D",
                suffix = "",
                icon = Icons.Filled.ThumbUp,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(1f)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(
                title = "Dirección",
                value = if (place.address.isNullOrBlank()) "N/D" else "Disponible",
                suffix = "",
                icon = Icons.Filled.Place,
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.weight(1f)
            )

            MetricCard(
                title = "Horario",
                value = if (place.openingHours.isNotEmpty()) "Disponible" else "N/D",
                suffix = "",
                icon = Icons.Filled.AccessTime,
                tint = MaterialTheme.colorScheme.error,
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
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
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

                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (suffix.isNotBlank()) {
                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = suffix,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactInfoCard(place: PlaceDetailsItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Información del lugar",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            InfoRow(
                icon = Icons.Filled.LocationOn,
                label = "Dirección",
                value = place.address ?: "No disponible"
            )

            InfoRow(
                icon = Icons.Filled.Phone,
                label = "Teléfono",
                value = place.phoneNumber ?: "No disponible"
            )

            InfoRow(
                icon = Icons.Filled.Language,
                label = "Sitio web",
                value = place.websiteUri ?: "No disponible"
            )

            InfoRow(
                icon = Icons.Filled.Place,
                label = "Google Maps",
                value = place.googleMapsUri ?: "No disponible"
            )
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.Top) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(30.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun OpeningHoursCard(openingHours: List<String>) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Horario",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            openingHours.forEach { line ->
                Text(
                    text = line,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun RecommendationCard() {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
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
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Recomendación local",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Disponible cuando registremos valoraciones internas.",
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f),
                    fontSize = 11.sp
                )
            }

            Text(
                text = "N/D",
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun HighlightedQualities() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "Cualidades Destacadas",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Surface(
            shape = RoundedCornerShape(50),
            color = MaterialTheme.colorScheme.surface,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
        ) {
            Text(
                text = "SIN VALORACIONES LOCALES",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
        }
    }
}