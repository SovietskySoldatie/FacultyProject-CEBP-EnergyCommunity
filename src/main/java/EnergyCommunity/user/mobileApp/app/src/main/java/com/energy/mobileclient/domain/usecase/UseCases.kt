package com.energy.mobileclient.domain.usecase

import com.energy.mobileclient.domain.model.BatteryProposal
import com.energy.mobileclient.domain.model.HouseStatus
import com.energy.mobileclient.domain.model.ProximityEvent
import com.energy.mobileclient.domain.repository.HouseRepository
import com.energy.mobileclient.domain.repository.ProposalRepository
import com.energy.mobileclient.domain.repository.ProximityRepository
import kotlinx.coroutines.flow.Flow

class GetHouseStatusUseCase(private val houses: HouseRepository) {
    suspend operator fun invoke(houseId: String): Result<HouseStatus> = houses.getHouseStatus(houseId)
}

class ObservePendingProposalsUseCase(private val proposals: ProposalRepository) {
    operator fun invoke(houseId: String): Flow<List<BatteryProposal>> =
        proposals.observePendingProposals(houseId)
}

class AcceptProposalUseCase(private val proposals: ProposalRepository) {
    suspend operator fun invoke(proposalId: String): Result<Unit> = proposals.accept(proposalId)
}

class RejectProposalUseCase(private val proposals: ProposalRepository) {
    suspend operator fun invoke(proposalId: String): Result<Unit> = proposals.reject(proposalId)
}

class NotifyProximityUseCase(private val proximity: ProximityRepository) {
    suspend operator fun invoke(houseId: String, event: ProximityEvent): Result<Unit> =
        proximity.notifyProximity(houseId, event)
}
