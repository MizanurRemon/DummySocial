package com.example.dummysocial.Network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


@ExperimentalCoroutinesApi
fun Context.checkConnection(): Flow<Boolean> = callbackFlow {
    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            trySend(true).isSuccess
        }

        override fun onLost(network: Network) {
            super.onLost(network)

            trySend(false).isSuccess
        }
    }

    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    //register network
    manager.registerNetworkCallback(
        NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
        callback
    )

    //unregister network
    awaitClose {
        manager.unregisterNetworkCallback(callback)
    }
}