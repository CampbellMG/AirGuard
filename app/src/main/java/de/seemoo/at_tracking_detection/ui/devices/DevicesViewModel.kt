package de.seemoo.at_tracking_detection.ui.devices

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import de.seemoo.at_tracking_detection.database.repository.BeaconRepository
import de.seemoo.at_tracking_detection.database.repository.DeviceRepository
import de.seemoo.at_tracking_detection.database.tables.Beacon
import de.seemoo.at_tracking_detection.database.tables.Device
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DevicesViewModel @Inject constructor(
    private val beaconRepository: BeaconRepository,
    private val deviceRepository: DeviceRepository
) : ViewModel() {

    fun setIgnoreFlag(deviceAddress: String, state: Boolean) = viewModelScope.launch {
        deviceRepository.setIgnoreFlag(deviceAddress, state)
    }

    fun getDeviceBeaconsCount(deviceAddress: String): String =
        beaconRepository.getDeviceBeaconsCount(deviceAddress).toString()

    fun getDevice(deviceAddress: String): Device = deviceRepository.getDevice(deviceAddress)!!

    fun getMarkerLocations(deviceAddress: String): List<Beacon> =
        beaconRepository.getDeviceBeacons(deviceAddress)

    fun update(device: Device) = viewModelScope.launch {
        deviceRepository.update(device)
    }

    val deviceListEmpty = MutableLiveData<Boolean>()

    val ignoredDevices: LiveData<List<Device>> =
        deviceRepository.ignoredDevices.asLiveData()

    val devices: LiveData<List<Device>> = deviceRepository.devices.asLiveData()
}