package com.energy.mobileclient.domain.model

import java.time.Instant

enum class UserRole { USER, ADMIN }

data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole,
    val houseId: String? = null,
)

data class GeoPoint(val latitude: Double, val longitude: Double)

/** Configurația hardware a casei (Core Energy Service). */
data class HouseConfig(
    val houseId: String,
    val ownerId: String,
    val location: GeoPoint,
    val batteryCapacityKwh: Double,
    val solarPanelCount: Int,
)

/** Stare curentă a casei (Telemetry Service). */
data class HouseStatus(
    val houseId: String,
    val batteryLevelPercent: Int,
    val currentConsumptionKw: Double,
    val currentSolarProductionKw: Double,
    val updatedAt: Instant,
)

data class ForecastPoint(
    val time: Instant,
    val solarRadiationWm2: Double,
    val pricePerKwh: Double,
)

data class Forecast(val points: List<ForecastPoint>)

enum class ProposalStatus { PENDING, ACCEPTED, REJECTED, EXPIRED }

/** Plan propus de agentul AI (ex. "vinde 40% din baterie la 19:00"). */
data class BatteryProposal(
    val id: String,
    val houseId: String,
    val description: String,
    val sellPercent: Int,
    val executeAt: Instant,
    val estimatedProfit: Double?,
    val status: ProposalStatus,
)

enum class ProximityEvent { APPROACHING_HOME, LEFT_HOME_AREA }
