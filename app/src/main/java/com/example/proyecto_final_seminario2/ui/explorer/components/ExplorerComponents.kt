package com.example.proyecto_final_seminario2.ui.explorer.components

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.proyecto_final_seminario2.data.explorer.models.Business
import com.example.proyecto_final_seminario2.ui.theme.pressScaleClickable

@Composable
fun ExplorerTopBar(title: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxWidth(), color = Color.White, tonalElevation = 2.dp) {
        Column {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = LocalPrimaryDark,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(text = title, fontWeight = FontWeight.SemiBold)
            }
            Divider(color = Color(0xFFE6E9EE), thickness = 1.dp)
        }
    }
}

@Composable
fun ExplorerHeader(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(text = title, fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 6.dp))
        Text(text = subtitle, fontSize = 14.sp, color = Color(0xFF6B7280))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryChips(categories: List<String>, selectedIndex: Int, onSelected: (Int) -> Unit, modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories.size) { index ->
            val label = categories[index]
            FilterChip(
                selected = index == selectedIndex,
                onClick = { onSelected(index) },
                label = { Text(text = label, fontSize = 13.sp) },
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(28.dp), color = Color(0xFFF1F5F9)) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color(0xFF6B7280),
                modifier = Modifier.padding(start = 8.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            androidx.compose.material3.TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = { Text("Buscar negocios o servicios", color = Color(0xFF9CA3AF)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xFF111827),
                    unfocusedTextColor = Color(0xFF111827)
                )
            )
        }
    }
}

@Composable
fun BusinessCard(
    item: Business,
    onClick: (Business) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .pressScaleClickable { onClick(item) }
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(model = item.imageUrl, contentDescription = null, modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item.category.label.uppercase(), fontSize = 11.sp, color = Color(0xFF6B7280))
                    Text(text = item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                tint = Color(0xFFF59E0B),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "${item.rating}", fontSize = 12.sp)
                        }
                        Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFEEF2FF)) { Text(text = " ${item.recommendationPercent}% recomienda ", modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp), fontSize = 12.sp, color = Color(0xFF065F46)) }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item.tags.take(3).forEach { tag ->
                    Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFF3F4F6)) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = qualityIcon(tag),
                                contentDescription = null,
                                tint = Color(0xFF475467),
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = tag, fontSize = 12.sp, color = Color(0xFF374151))
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Ver detalle >", color = Color(0xFF2563EB), fontSize = 13.sp)
            }
        }
    }
}

private fun qualityIcon(tag: String): ImageVector {
    val normalized = tag.lowercase()
    return when {
        "precio" in normalized -> Icons.Filled.AttachMoney
        "puntual" in normalized || "rapida" in normalized || "rapido" in normalized -> Icons.Filled.AccessTime
        else -> Icons.Filled.Star
    }
}

@Composable
fun BusinessList(
    items: List<Business>,
    onBusinessClick: (Business) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(items) { b ->
            BusinessCard(
                item = b,
                onClick = onBusinessClick,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}
