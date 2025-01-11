package fr.smarquis.savedstateissue


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public sealed class GeoJson {

    public abstract val boundingBox: BoundingBox?

    @Serializable
    public sealed class Geometry : GeoJson() {

        @SerialName("bbox")
        override val boundingBox: BoundingBox? = null

        @Serializable @SerialName("Point")
        public data class Point(@SerialName("coordinates") val position: Position) : Geometry()

        @Serializable @SerialName("MultiPoint")
        public data class MultiPoint(
            @SerialName("coordinates") val positions: List<Position> = emptyList(),
        ) : Geometry(), List<Position> by positions

        /* other types */
    }

    @Serializable
    public data class BoundingBox(val sw: Position, val ne: Position)

    @Serializable
    public data class Position(val latitude: Double, val longitude: Double, val altitude: Double? = null)

}
