package com.energy.mobileclient.geolocation

import com.energy.mobileclient.domain.model.GeoPoint
import com.energy.mobileclient.domain.model.ProximityEvent
import kotlinx.coroutines.flow.Flow

/** Emite poziția telefonului. Implementare reală: FusedLocationProvider (în `impl`). */
interface LocationProvider {
    fun locationUpdates(): Flow<GeoPoint>
    suspend fun lastKnownLocation(): GeoPoint?
}

/** Înregistrează o zonă (geofence) în jurul casei. */
interface GeofenceManager {
    fun registerHomeGeofence(home: GeoPoint, radiusMeters: Float)
    fun unregisterHomeGeofence()
}

/** Decide când utilizatorul "se apropie de casă" pe baza pozițiilor. */
interface ProximityDetector {
    fun observe(home: GeoPoint, radiusMeters: Double): Flow<ProximityEvent>
}
