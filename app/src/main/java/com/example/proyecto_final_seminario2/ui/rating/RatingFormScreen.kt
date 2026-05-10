package com.example.proyecto_final_seminario2.ui.rating

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_final_seminario2.data.explorer.models.Business
import com.example.proyecto_final_seminario2.data.rating.models.HighlightedQuality
import com.example.proyecto_final_seminario2.data.rating.models.RatingFormData
import com.example.proyecto_final_seminario2.data.rating.models.WaitTimeOption
import com.example.proyecto_final_seminario2.ui.theme.LocalBackground
import com.example.proyecto_final_seminario2.ui.theme.LocalBorder
import com.example.proyecto_final_seminario2.ui.theme.LocalPrimary
import com.example.proyecto_final_seminario2.ui.theme.LocalTextMuted
import com.example.proyecto_final_seminario2.ui.theme.LocalTextPrimary
import com.example.proyecto_final_seminario2.ui.theme.pressScaleClickable

private val RatingBackground = LocalBackground
private val PrimaryBlue = LocalPrimary
private val TextPrimary = LocalTextPrimary
private val TextMuted = LocalTextMuted
private val BorderSoft = LocalBorder

@Composable
fun RatingFormScreen(
    businessId: String,
    business: Business?,
    onBackClick: () -> Unit,
    onSubmitClick: (RatingFormData) -> Unit,
    modifier: Modifier = Modifier
) {
    var paidPrice by remember { mutableStateOf("") }
    var visitDate by remember { mutableStateOf("") }
    var serviceRating by remember { mutableStateOf(4) }
    var attentionRating by remember { mutableStateOf(3) }
    var satisfactionRating by remember { mutableStateOf(4) }
    var waitTime by remember { mutableStateOf(WaitTimeOption.BETWEEN_15_AND_30_MINUTES) }
    var recommends by remember { mutableStateOf(true) }
    val selectedQualities = remember { mutableStateListOf(HighlightedQuality.KIND_TREATMENT) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = RatingBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item { RatingTopBar(onBackClick = onBackClick) }
            item { BusinessHeader(business = business) }
            item { ResponsibleUseCard() }
            item {
                SectionTitle(text = "Detalles de la visita")
                Spacer(modifier = Modifier.height(8.dp))
                CompactTextField(
                    value = paidPrice,
                    onValueChange = { paidPrice = it },
                    label = "Precio pagado o estimado",
                    placeholder = "Q 0.00"
                )
                Spacer(modifier = Modifier.height(10.dp))
                CompactTextField(
                    value = visitDate,
                    onValueChange = { visitDate = it },
                    label = "Fecha de visita",
                    placeholder = "mm/dd/yyyy"
                )
            }
            item {
                SectionTitle(text = "Califica tu experiencia")
                Spacer(modifier = Modifier.height(10.dp))
                RatingSelector(
                    label = "Servicio",
                    value = serviceRating,
                    onValueChange = { serviceRating = it }
                )
                RatingSelector(
                    label = "Atencion",
                    value = attentionRating,
                    onValueChange = { attentionRating = it }
                )
                RatingSelector(
                    label = "Satisfaccion",
                    value = satisfactionRating,
                    onValueChange = { satisfactionRating = it }
                )
            }
            item {
                SectionTitle(text = "Tiempo de espera")
                SegmentedOptions(
                    options = WaitTimeOption.values().toList(),
                    selectedOption = waitTime,
                    onOptionClick = { waitTime = it }
                )
            }
            item {
                SectionTitle(text = "¿Lo recomendarias?")
                RecommendationOptions(
                    recommends = recommends,
                    onRecommendationClick = { recommends = it },
                    icons = true
                )
            }
            item {
                SectionTitle(text = "Cualidades a destacar")
                QualityChips(
                    selectedQualities = selectedQualities,
                    onQualityClick = { quality ->
                        if (selectedQualities.contains(quality)) {
                            selectedQualities.remove(quality)
                        } else {
                            selectedQualities.add(quality)
                        }
                    }
                )
            }
            item {
                Button(
                    onClick = {
                        onSubmitClick(
                            RatingFormData(
                                businessId = businessId,
                                paidPrice = paidPrice.trim(),
                                visitDate = visitDate.trim(),
                                serviceRating = serviceRating,
                                attentionRating = attentionRating,
                                satisfactionRating = satisfactionRating,
                                waitTime = waitTime,
                                recommends = recommends,
                                highlightedQualities = selectedQualities.toList()
                            )
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Enviar valoracion", fontSize = 13.sp)
                }
                Spacer(modifier = Modifier.height(18.dp))
            }
        }
    }
}

@Composable
private fun RatingTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
        }
        Text(
            text = "Valorar",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextPrimary
        )
    }
}

@Composable
private fun BusinessHeader(business: Business?) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(
            text = business?.name ?: "Negocio no encontrado",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Text(
            text = business?.category?.label?.dropLastWhile { it == 's' } ?: "Servicio local",
            fontSize = 12.sp,
            color = TextMuted
        )
    }
}

@Composable
private fun ResponsibleUseCard() {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7D6)),
        border = BorderStroke(1.dp, Color(0xFFFFE8A3)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = null,
                tint = Color(0xFF8A5A00),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Uso responsable",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF5F4200)
                )
                Text(
                    text = "Tu reseña debe ser honesta, objetiva y basada en tu experiencia real.",
                    fontSize = 11.sp,
                    color = Color(0xFF7A5A12)
                )
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryBlue
    )
}

@Composable
private fun CompactTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(text = label, fontSize = 10.sp, color = TextPrimary)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder, fontSize = 12.sp) },
            singleLine = true,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
}

@Composable
private fun RatingSelector(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(text = label, fontSize = 10.sp, color = TextPrimary)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .background(Color.White, RoundedCornerShape(50)),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            (1..5).forEach { number ->
                val selected = number <= value
                val segmentColor by animateColorAsState(
                    targetValue = if (selected) PrimaryBlue else Color.White,
                    label = "ratingSegmentColor"
                )
                val textColor by animateColorAsState(
                    targetValue = if (selected) Color.White else TextPrimary,
                    label = "ratingSegmentTextColor"
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(28.dp)
                        .background(
                            color = segmentColor,
                            shape = RoundedCornerShape(50)
                        )
                        .pressScaleClickable(pressedScale = 0.96f) { onValueChange(number) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number.toString(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = textColor
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
private fun SegmentedOptions(
    options: List<WaitTimeOption>,
    selectedOption: WaitTimeOption,
    onOptionClick: (WaitTimeOption) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .background(Color.White, RoundedCornerShape(50)),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        options.forEach { option ->
            val selected = option == selectedOption
            val segmentColor by animateColorAsState(
                targetValue = if (selected) PrimaryBlue else Color.White,
                label = "waitSegmentColor"
            )
            val textColor by animateColorAsState(
                targetValue = if (selected) Color.White else TextPrimary,
                label = "waitSegmentTextColor"
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(34.dp)
                    .background(
                        color = segmentColor,
                        shape = RoundedCornerShape(50)
                    )
                    .pressScaleClickable(pressedScale = 0.96f) { onOptionClick(option) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option.label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
            }
        }
    }
}

@Composable
private fun RecommendationOptions(
    recommends: Boolean,
    onRecommendationClick: (Boolean) -> Unit,
    icons: Boolean = false
) {
    val options = listOf(true to "Si", false to "No")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .background(Color.White, RoundedCornerShape(50)),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        options.forEach { (option, label) ->
            val selected = option == recommends
            val segmentColor by animateColorAsState(
                targetValue = if (selected) PrimaryBlue else Color.White,
                label = "recommendSegmentColor"
            )
            val contentColor by animateColorAsState(
                targetValue = if (selected) Color.White else TextPrimary,
                label = "recommendSegmentContentColor"
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(34.dp)
                    .background(
                        color = segmentColor,
                        shape = RoundedCornerShape(50)
                    )
                    .pressScaleClickable(pressedScale = 0.96f) { onRecommendationClick(option) },
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (icons) {
                        Icon(
                            imageVector = if (option) Icons.Filled.Check else Icons.Filled.Close,
                            contentDescription = null,
                            tint = contentColor,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = contentColor
                    )
                }
            }
        }
    }
}

@Composable
private fun QualityChips(
    selectedQualities: List<HighlightedQuality>,
    onQualityClick: (HighlightedQuality) -> Unit
) {
    val qualities = HighlightedQuality.values().toList()

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        qualities.chunked(2).forEach { rowQualities ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowQualities.forEach { quality ->
                    QualityChip(
                        text = quality.label,
                        selected = selectedQualities.contains(quality),
                        onClick = { onQualityClick(quality) },
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

@Composable
private fun QualityChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val chipColor by animateColorAsState(
        targetValue = if (selected) PrimaryBlue else Color.White,
        label = "qualityChipColor"
    )
    val borderColor by animateColorAsState(
        targetValue = if (selected) PrimaryBlue else BorderSoft,
        label = "qualityChipBorderColor"
    )
    val textColor by animateColorAsState(
        targetValue = if (selected) Color.White else TextPrimary,
        label = "qualityChipTextColor"
    )

    Surface(
        shape = RoundedCornerShape(50),
        color = chipColor,
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier
            .pressScaleClickable(pressedScale = 0.96f, onClick = onClick)
            .animateContentSize()
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
        }
    }
}
