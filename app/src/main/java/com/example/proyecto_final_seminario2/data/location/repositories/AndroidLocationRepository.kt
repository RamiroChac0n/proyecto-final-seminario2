package com.example.proyecto_final_seminario2.data.location.repositories

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.proyecto_final_seminario2.data.location.models.UserLocation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AndroidLocationRepository(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Result<UserLocation> {
        if (!hasLocationPermission()) {
            return Result.failure(Exception("Permiso de ubicación no concedido"))
        }

        return try {
            val lastLocation = fusedLocationClient.lastLocation.awaitTask()

            val location = lastLocation ?: fusedLocationClient
                .getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    CancellationTokenSource().token
                )
                .awaitTask()

            if (location == null) {
                Result.failure(Exception("No se pudo obtener tu ubicación"))
            } else {
                Result.success(
                    UserLocation(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                )
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    private fun hasLocationPermission(): Boolean {
        val finePermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarsePermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return finePermission || coarsePermission
    }

    private suspend fun <T> Task<T>.awaitTask(): T {
        return suspendCancellableCoroutine { continuation ->
            addOnSuccessListener { result ->
                if (continuation.isActive) {
                    continuation.resume(result)
                }
            }

            addOnFailureListener { exception ->
                if (continuation.isActive) {
                    continuation.resumeWithException(exception)
                }
            }

            addOnCanceledListener {
                continuation.cancel()
            }
        }
    }
}