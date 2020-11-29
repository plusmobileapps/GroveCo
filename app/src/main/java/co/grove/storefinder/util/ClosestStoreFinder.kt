package co.grove.storefinder.util

import co.grove.storefinder.model.Store
import co.grove.storefinder.model.StoreRepo
import co.grove.storefinder.viewmodel.Units
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class ClosestStoreFinder(private val repo: StoreRepo) {

    fun findClosestStore(location: Pair<Double, Double>, unitType: Units): Pair<Store, Double> {
        val lat = location.first
        val lon = location.second

        var closestStore = repo.getStore(0)
        var closestDistance = Double.MAX_VALUE

        for (idx in 0 until repo.getStoreCount()) {
            val store = repo.getStore(idx)
            val storeLat = store.lat
            val storeLon = store.long
            val distance = calculateDistance(lat, lon, storeLat, storeLon, unitType)

            if (distance < closestDistance) {
                closestDistance = distance
                closestStore = store
            }
        }

        return Pair(closestStore, closestDistance)
    }


    /**
     * Adapted from:
     * https://dzone.com/articles/distance-calculation-using-3
     */
    private fun calculateDistance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double,
        unitType: Units
    ): Double {

        val theta = lon1 - lon2;
        var dist =
            sin(deg2rad(lat1)) * sin(deg2rad(lat2)) + cos(deg2rad(lat1)) * cos(deg2rad(lat2)) * cos(
                deg2rad(theta)
            )

        dist = acos(dist)

        dist = rad2deg(dist)

        dist *= 60 * 1.1515

        if (unitType == Units.KILOMETERS) {
            dist *= 1.609344
        }

        return dist
    }

    private fun deg2rad(deg: Double): Double {
        return (deg * Math.PI / 180.0)
    }

    private fun rad2deg(rad: Double): Double {
        return (rad * 180.0 / Math.PI)
    }



}

