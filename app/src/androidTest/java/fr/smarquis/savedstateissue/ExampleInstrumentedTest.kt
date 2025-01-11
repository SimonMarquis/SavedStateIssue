package fr.smarquis.savedstateissue

import androidx.savedstate.serialization.decodeFromSavedState
import androidx.savedstate.serialization.encodeToSavedState
import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.smarquis.savedstateissue.GeoJson.BoundingBox
import fr.smarquis.savedstateissue.GeoJson.Geometry
import fr.smarquis.savedstateissue.GeoJson.Geometry.Point
import fr.smarquis.savedstateissue.GeoJson.Position
import kotlinx.serialization.json.Json

import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertIs

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun savedState_encoding_decoding() {
        val point: Geometry = Point(position = Position(latitude = 1.2, longitude = 2.1))
        assertEquals(
            expected = point,
            // java.lang.IllegalArgumentException: No saved state was found associated with the key 'bbox'.
            actual = decodeFromSavedState(encodeToSavedState(point)),
        )
    }

    @Test
    fun json_encoding_decoding() {
        val point: Geometry = Point(position = Position(latitude = 1.2, longitude = 2.1))
        assertEquals(
            expected = point,
            actual = Json.decodeFromString(Json.encodeToString(point)),
        )
    }

    @Test
    fun json_decoding_point_with_explicit_bbox() {
        val point = Position(latitude = 1.2, longitude = 2.1)
        val decoded = Json.decodeFromString<Geometry>("""
            {
              "type": "Point",
              "bbox": {
                "ne": {"latitude": 1.2, "longitude": 2.1},
                "sw": {"latitude": 1.2, "longitude": 2.1}
              },
              "coordinates": {"latitude": 1.2,"longitude": 2.1}
            }
            """.trimIndent())
        assertEquals(
            expected = BoundingBox(sw = point, ne = point),
            actual = assertIs<Point>(decoded).boundingBox,
        )
    }

}
