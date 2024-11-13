package com.omni.credentialmanagerchecking.domain.repo

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataProvider @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val IS_SIGNED_IN = booleanPreferencesKey("isSignedIn")
    }

    // Set if the user is signed in or not
    suspend fun setSignedIn(flag: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_SIGNED_IN] = flag
        }
    }

    // Get the signed-in status
    val isSignedIn: Flow<Boolean> = dataStore.data
        .catch { exception ->
            // Handle exceptions when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[PreferencesKeys.IS_SIGNED_IN] ?: false
        }
}
