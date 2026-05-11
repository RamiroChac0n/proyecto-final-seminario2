package com.example.proyecto_final_seminario2.ui.businessdetail

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.proyecto_final_seminario2.data.places.models.PlaceDetailsItem
import com.example.proyecto_final_seminario2.data.rating.models.LocalRatingSummary
import java.text.Normalizer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale

@Composable
fun BusinessDetailScreen(
    state: BusinessDetailUiState,
    onBackClick: () -> Unit,
    onRateClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val place = state.place

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            if (place != null) {
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

            place == null -> {
                MissingPlaceContent(
                    onBackClick = onBackClick,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }

            else -> {
                PlaceDetailContent(
                    place = place,
                    localRatingSummary = state.localRatingSummary,
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
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )

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
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "No se encontró el lugar.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Regresa a Explorar e intenta seleccionar otro lugar.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun PlaceDetailContent(
    place: PlaceDetailsItem,
    localRatingSummary: LocalRatingSummary,
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
            DetailHeroImage(place = place)
        }

        item {
            PlaceHeaderInfo(place = place)
        }

        item {
            GoogleInfoCard(place = place)
        }

        item {
            LocalRatingSummaryCard(summary = localRatingSummary)
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
private fun DetailHeroImage(place: PlaceDetailsItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.7f)
            .clip(RoundedCornerShape(16.dp))
    ) {
        PlaceHeroImage(
            place = place,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun PlaceHeaderInfo(place: PlaceDetailsItem) {
    Card(
        shape = RoundedCornerShape(12.dp),
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = place.category.label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Text(
                text = place.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (!place.address.isNullOrBlank()) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = place.address,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
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
private fun GoogleInfoCard(place: PlaceDetailsItem) {
    Card(
        shape = RoundedCornerShape(10.dp),
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Información de Google",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            GoogleMetricRow(
                icon = Icons.Filled.Star,
                label = "Rating Google",
                value = place.rating?.let { "$it / 5.0" } ?: "No disponible"
            )

            GoogleMetricRow(
                icon = Icons.Filled.ThumbUp,
                label = "Valoraciones Google",
                value = place.userRatingCount?.toString() ?: "No disponible"
            )
        }
    }
}

@Composable
private fun GoogleMetricRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(34.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun LocalRatingSummaryCard(summary: LocalRatingSummary) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Calificación de la comunidad",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            if (!summary.hasRatings) {
                Text(
                    text = "Aún no hay valoraciones de usuarios de la app para este lugar.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.75f)
                )
                return@Column
            }

            LocalMetricRow(
                label = "Servicio",
                value = summary.averageServiceRating.formatOneDecimal()
            )

            LocalMetricRow(
                label = "Atención",
                value = summary.averageAttentionRating.formatOneDecimal()
            )

            LocalMetricRow(
                label = "Satisfacción",
                value = summary.averageSatisfactionRating.formatOneDecimal()
            )

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Recomendación",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "${summary.recommendationPercent}% recomienda este lugar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            summary.mostCommonWaitTime?.let { waitTime ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Tiempo de espera más común",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = waitTime,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            if (summary.highlightedQualities.isNotEmpty()) {
                HighlightedQualitiesSection(
                    qualities = summary.highlightedQualities
                )
            }
        }
    }
}

@Composable
private fun LocalMetricRow(
    label: String,
    value: String
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "$value / 5.0",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun HighlightedQualitiesSection(
    qualities: List<String>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Cualidades a destacar",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            qualities.chunked(2).forEach { rowQualities ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowQualities.forEach { quality ->
                        QualityChip(
                            text = quality,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (rowQualities.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun QualityChip(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        Text(
            text = text.uppercase(),
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)
        )
    }
}

@Composable
private fun ContactInfoCard(place: PlaceDetailsItem) {
    val clipboardManager = LocalClipboardManager.current

    Card(
        shape = RoundedCornerShape(10.dp),
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Información del lugar",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            InfoRow(
                icon = Icons.Filled.Place,
                label = "Nombre",
                value = place.name,
                copyValue = place.name,
                onCopyClick = { value ->
                    clipboardManager.setText(AnnotatedString(value))
                }
            )

            InfoRow(
                icon = Icons.Filled.LocationOn,
                label = "Dirección",
                value = place.address ?: "No disponible",
                copyValue = place.address,
                onCopyClick = { value ->
                    clipboardManager.setText(AnnotatedString(value))
                }
            )

            InfoRow(
                icon = Icons.Filled.Phone,
                label = "Teléfono",
                value = place.phoneNumber ?: "No disponible",
                copyValue = place.phoneNumber,
                onCopyClick = { value ->
                    clipboardManager.setText(AnnotatedString(value))
                }
            )

            InfoRow(
                icon = Icons.Filled.Language,
                label = "Sitio web",
                value = place.websiteUri ?: "No disponible",
                copyValue = place.websiteUri,
                onCopyClick = { value ->
                    clipboardManager.setText(AnnotatedString(value))
                }
            )

            InfoRow(
                icon = Icons.Filled.Place,
                label = "Google Maps",
                value = place.googleMapsUri ?: "No disponible",
                copyValue = place.googleMapsUri,
                onCopyClick = { value ->
                    clipboardManager.setText(AnnotatedString(value))
                }
            )
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    copyValue: String?,
    onCopyClick: (String) -> Unit
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

        Column(modifier = Modifier.weight(1f)) {
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

        if (!copyValue.isNullOrBlank()) {
            IconButton(
                onClick = {
                    onCopyClick(copyValue)
                },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ContentCopy,
                    contentDescription = "Copiar $label",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun OpeningHoursCard(openingHours: List<String>) {
    val currentDay = LocalDate.now().dayOfWeek
    val todayStatus = getTodayOpeningStatus(openingHours)

    Card(
        shape = RoundedCornerShape(10.dp),
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Horario",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = todayStatus.label,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (todayStatus.isOpenNow == true) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )
                }
            }

            openingHours.forEach { line ->
                val isToday = line.isScheduleForDay(currentDay)

                OpeningHourRow(
                    line = line,
                    isToday = isToday,
                    isOpenNow = if (isToday) todayStatus.isOpenNow else null
                )
            }
        }
    }
}

@Composable
private fun OpeningHourRow(
    line: String,
    isToday: Boolean,
    isOpenNow: Boolean?
) {
    val parts = line.split(":", limit = 2)
    val day = parts.getOrNull(0)?.trim().orEmpty()
    val hours = parts.getOrNull(1)?.trim().orEmpty()

    val containerColor = if (isToday) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val dayColor = if (isToday) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    val hourColor = if (isToday) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = containerColor,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 9.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = day.ifBlank { line },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = dayColor
                    )

                    if (isToday) {
                        Spacer(modifier = Modifier.width(6.dp))

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = if (isOpenNow == true) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.error
                            }
                        ) {
                            Text(
                                text = if (isOpenNow == true) "ABIERTO" else "CERRADO",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isOpenNow == true) {
                                    MaterialTheme.colorScheme.onPrimary
                                } else {
                                    MaterialTheme.colorScheme.onError
                                },
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                if (hours.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = hours,
                        fontSize = 12.sp,
                        color = hourColor
                    )
                }
            }
        }
    }
}

private data class TodayOpeningStatus(
    val label: String,
    val isOpenNow: Boolean?
)

@RequiresApi(Build.VERSION_CODES.O)
private fun getTodayOpeningStatus(
    openingHours: List<String>,
    now: LocalTime = LocalTime.now(),
    currentDay: DayOfWeek = LocalDate.now().dayOfWeek
): TodayOpeningStatus {
    val todayLine = openingHours.firstOrNull { line ->
        line.isScheduleForDay(currentDay)
    } ?: return TodayOpeningStatus(
        label = "Horario disponible",
        isOpenNow = null
    )

    val isOpen = todayLine.isOpenAt(now)

    return TodayOpeningStatus(
        label = when (isOpen) {
            true -> "Abierto ahora"
            false -> "Cerrado ahora"
            null -> "Horario disponible"
        },
        isOpenNow = isOpen
    )
}

@RequiresApi(Build.VERSION_CODES.O)
private fun String.isScheduleForDay(dayOfWeek: DayOfWeek): Boolean {
    val dayText = substringBefore(":")
        .normalizeScheduleText()

    return dayAliases(dayOfWeek).any { alias ->
        dayText == alias || dayText.startsWith(alias)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun dayAliases(dayOfWeek: DayOfWeek): List<String> {
    return when (dayOfWeek) {
        DayOfWeek.MONDAY -> listOf("lunes", "monday", "mon")
        DayOfWeek.TUESDAY -> listOf("martes", "tuesday", "tue")
        DayOfWeek.WEDNESDAY -> listOf("miercoles", "wednesday", "wed")
        DayOfWeek.THURSDAY -> listOf("jueves", "thursday", "thu")
        DayOfWeek.FRIDAY -> listOf("viernes", "friday", "fri")
        DayOfWeek.SATURDAY -> listOf("sabado", "saturday", "sat")
        DayOfWeek.SUNDAY -> listOf("domingo", "sunday", "sun")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun String.isOpenAt(now: LocalTime): Boolean? {
    val normalizedLine = normalizeScheduleText()

    if (
        normalizedLine.contains("cerrado") ||
        normalizedLine.contains("closed")
    ) {
        return false
    }

    if (
        normalizedLine.contains("24 horas") ||
        normalizedLine.contains("24 hours")
    ) {
        return true
    }

    val scheduleText = substringAfter(":", missingDelimiterValue = "")
        .replace("–", "-")
        .replace("—", "-")
        .replace("−", "-")

    if (scheduleText.isBlank()) {
        return null
    }

    val ranges = scheduleText
        .split(",", ";")
        .map { it.trim() }
        .filter { it.isNotBlank() }

    val parsedRanges = ranges.mapNotNull { range ->
        val times = extractTimes(range)

        if (times.size >= 2) {
            times[0] to times[1]
        } else {
            null
        }
    }

    if (parsedRanges.isEmpty()) {
        return null
    }

    return parsedRanges.any { (start, end) ->
        now.isBetweenOpeningRange(start, end)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun extractTimes(text: String): List<LocalTime> {
    val regex = Regex(
        pattern = """(\d{1,2})(?::(\d{2}))?\s*([ap]\.?\s*m\.?|am|pm)?""",
        option = RegexOption.IGNORE_CASE
    )

    return regex.findAll(text)
        .mapNotNull { match ->
            val hour = match.groupValues[1].toIntOrNull()
            val minute = match.groupValues[2].ifBlank { "0" }.toIntOrNull()
            val marker = match.groupValues[3]
                .lowercase(Locale.getDefault())
                .replace(".", "")
                .replace(" ", "")

            if (hour == null || minute == null) {
                null
            } else {
                toLocalTime(
                    rawHour = hour,
                    minute = minute,
                    marker = marker
                )
            }
        }
        .toList()
}

@RequiresApi(Build.VERSION_CODES.O)
private fun toLocalTime(
    rawHour: Int,
    minute: Int,
    marker: String
): LocalTime? {
    if (minute !in 0..59) return null

    var hour = rawHour

    when (marker) {
        "pm" -> {
            if (hour < 12) hour += 12
        }

        "am" -> {
            if (hour == 12) hour = 0
        }
    }

    if (hour == 24) hour = 0
    if (hour !in 0..23) return null

    return LocalTime.of(hour, minute)
}

@RequiresApi(Build.VERSION_CODES.O)
private fun LocalTime.isBetweenOpeningRange(
    start: LocalTime,
    end: LocalTime
): Boolean {
    return if (end.isAfter(start)) {
        !isBefore(start) && isBefore(end)
    } else {
        !isBefore(start) || isBefore(end)
    }
}

private fun String.normalizeScheduleText(): String {
    val normalized = Normalizer.normalize(
        lowercase(Locale.getDefault()).trim(),
        Normalizer.Form.NFD
    )

    return normalized
        .replace("\\p{Mn}+".toRegex(), "")
        .replace(".", "")
        .replace("\u202f", " ")
        .replace("\u00a0", " ")
        .trim()
}

private fun Float.formatOneDecimal(): String {
    return String.format("%.1f", this)
}