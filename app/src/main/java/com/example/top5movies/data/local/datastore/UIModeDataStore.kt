package com.example.top5movies.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import com.example.top5movies.utils.UI_MODE
import com.example.top5movies.utils.UI_MODE_PREF
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/*
* The class PrefsDataStore
*
* @author  Waleed Ahmed
* @email shkwaleed92@gmail.com
* @version 1.0
* @since 10 Jul 2021
*
*
* PrefsDataStore abstract class is used to create the data store preferences, JetPack @DataStore is the replacement of the SharedPreferences
* as it follows the observable pattern, moreover we could save/retrieve data in background thread.
* @see this https://developer.android.com/topic/libraries/architecture/datastore?gclsrc=ds&gclsrc=ds&gclid=CLCjpfHere8CFVc_GwodJl8L-A link for more details
*/
abstract class PrefsDataStore(context: Context, fileName: String) {

    protected val dataStore: DataStore<Preferences> = context.createDataStore(name = fileName)
}

/*
* UIModeDataStore class implements the interface for both @UIModeMutableStore to implement logic for the data store in
* preferences, @UIModeReadStore to implements the logic for the date retrieve from the preferences. @PrefsDataStore to
*  get instance of already created data store
* @param context of the application
* */
class UIModeDataStore(context: Context) : PrefsDataStore(context, PREF_FILE_UI_MODE),
    UIModeMutableStore, UIModeReadStore {

    override suspend fun saveToDataStore(isNightMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[UI_MODE_KEY] = isNightMode
        }
    }

    override val uiMode: Flow<Boolean> = dataStore.data.map { preferences ->
        val uiMode = preferences[UI_MODE_KEY] ?: false
        uiMode
    }

    companion object {
        private const val PREF_FILE_UI_MODE = UI_MODE_PREF
        private val UI_MODE_KEY = booleanPreferencesKey(UI_MODE)
    }
}

/*
* UiModeMutableStore interface to store the data in the data store.
*/
interface UIModeMutableStore {
    suspend fun saveToDataStore(isNightMode: Boolean)
}

/*
* UIModeReadStore interface to retrieve the data from the data store.
*/
interface UIModeReadStore {
    val uiMode: Flow<Boolean>
}
