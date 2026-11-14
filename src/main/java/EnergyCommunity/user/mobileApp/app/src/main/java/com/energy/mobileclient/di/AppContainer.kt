package com.energy.mobileclient.di

import com.energy.mobileclient.domain.repository.AdminRepository
import com.energy.mobileclient.domain.repository.AuthRepository
import com.energy.mobileclient.domain.repository.ForecastRepository
import com.energy.mobileclient.domain.repository.HouseRepository
import com.energy.mobileclient.domain.repository.ProposalRepository
import com.energy.mobileclient.domain.repository.ProximityRepository
import com.energy.mobileclient.geolocation.LocationProvider
import com.energy.mobileclient.geolocation.ProximityDetector

/**
 * Singurul loc care cunoaște implementările concrete.
 * ViewModel-urile primesc doar interfețe (constructor injection) printr-un ViewModelProvider.Factory.
 * Poate fi înlocuit cu Hilt fără să se schimbe domain/ui.
 */
interface AppContainer {
    val authRepository: AuthRepository
    val houseRepository: HouseRepository
    val forecastRepository: ForecastRepository
    val proposalRepository: ProposalRepository
    val proximityRepository: ProximityRepository
    val adminRepository: AdminRepository
    val locationProvider: LocationProvider
    val proximityDetector: ProximityDetector
}
