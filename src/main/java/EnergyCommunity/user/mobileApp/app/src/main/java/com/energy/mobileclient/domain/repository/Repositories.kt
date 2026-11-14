package com.energy.mobileclient.domain.repository

import com.energy.mobileclient.domain.model.BatteryProposal
import com.energy.mobileclient.domain.model.Forecast
import com.energy.mobileclient.domain.model.GeoPoint
import com.energy.mobileclient.domain.model.HouseConfig
import com.energy.mobileclient.domain.model.HouseStatus
import com.energy.mobileclient.domain.model.ProximityEvent
import com.energy.mobileclient.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun logout()
    fun currentUser(): Flow<User?>
}

/** Citire status casă (telemetrie). */
interface HouseRepository {
    suspend fun getHouseStatus(houseId: String): Result<HouseStatus>
    suspend fun getHouseConfig(houseId: String): Result<HouseConfig>
}

interface ForecastRepository {
    suspend fun getForecast(location: GeoPoint): Result<Forecast>
}

interface ProposalRepository {
    fun observePendingProposals(houseId: String): Flow<List<BatteryProposal>>
    suspend fun accept(proposalId: String): Result<Unit>
    suspend fun reject(proposalId: String): Result<Unit>
}

/** Anunță sistemul că utilizatorul se apropie/pleacă de casă. */
interface ProximityRepository {
    suspend fun notifyProximity(houseId: String, event: ProximityEvent): Result<Unit>
}

/** Operații admin, scrise prin Core Energy Service. */
interface AdminRepository {
    suspend fun listUsers(): Result<List<User>>
    suspend fun listHouses(): Result<List<HouseConfig>>
    suspend fun updateHouse(config: HouseConfig): Result<HouseConfig>
}
