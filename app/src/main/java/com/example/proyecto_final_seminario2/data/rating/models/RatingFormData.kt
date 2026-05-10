package com.example.proyecto_final_seminario2.data.rating.models

/**
 * Payload emitted by the rating screen. Backend can map this shape to the rating endpoint request.
 */
data class RatingFormData(
    val businessId: String,
    val paidPrice: String,
    val visitDate: String,
    val serviceRating: Int,
    val attentionRating: Int,
    val satisfactionRating: Int,
    val waitTime: WaitTimeOption,
    val recommends: Boolean,
    val highlightedQualities: List<HighlightedQuality>
)

enum class WaitTimeOption(val label: String) {
    LESS_THAN_15_MINUTES("< 15m"),
    BETWEEN_15_AND_30_MINUTES("15-30m"),
    MORE_THAN_30_MINUTES("> 30m")
}

enum class HighlightedQuality(val label: String) {
    FAST_ATTENTION("ATENCION RAPIDA"),
    KIND_TREATMENT("TRATO AMABLE"),
    AFFORDABLE_PRICE("PRECIO ACCESIBLE"),
    GOOD_HYGIENE("HIGIENE ADECUADA"),
    PUNCTUALITY("PUNTUALIDAD"),
    RELIABLE_SERVICE("SERVICIO CONFIABLE")
}
