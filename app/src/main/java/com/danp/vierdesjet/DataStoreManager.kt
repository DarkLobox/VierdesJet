package com.danp.vierdesjet

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context){
    companion object{
        private const val DATASTORE_NAME = "data_preferences"
        private val Context.dataStore by preferencesDataStore(
            name = DATASTORE_NAME
        )

        //
        private val EMAIL_KEY = stringPreferencesKey("email_key");
        private val GROUP_KEY = stringPreferencesKey("group_key");
        private val PASSWORD_KEY = stringPreferencesKey("password_key")
        private val NAME_KEY = stringPreferencesKey("name_key")
        private val STATUS_KEY = stringPreferencesKey("status_key")
        private val DATE_CREATION_KEY = stringPreferencesKey("date_creation_key")
        private val ID_KEY = stringPreferencesKey("id_key")
    }

    //
    suspend fun  setData(emailValue: String, passwordValue: String, groupValue: String){
        setGroup(groupValue)
    }
    suspend fun  setUser(
        emailValue: String,
        passwordValue: String,
        nameValue: String,
        groupValue: String,
        dateCreationValue: String,
        statusValue: String,
        idValue: String
        ){

        setEmail(emailValue)
        setPassword(passwordValue)
        setName(nameValue)
        setGroup(groupValue)
        setDateCreation(dateCreationValue)
        setStatus(statusValue)
        setId(idValue)
    }

    suspend fun resetUser(){
        setEmail("")
        setPassword("")
        setName("")
        setGroup("")
        setDateCreation("")
        setStatus("")
        setId("")
    }

    suspend fun setEmail(emailValue: String){
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = emailValue
        }
    }
    suspend fun setGroup(groupValue: String){
        context.dataStore.edit { preferences ->
            preferences[GROUP_KEY] = groupValue
        }
    }
    suspend fun setPassword(passwordValue: String){
        context.dataStore.edit { preferences ->
            preferences[PASSWORD_KEY] = passwordValue
        }
    }
    suspend fun setName(nameValue: String){
        context.dataStore.edit { preferences ->
            preferences[NAME_KEY] = nameValue
        }
    }
    suspend fun setStatus(statusValue: String){
        context.dataStore.edit { preferences ->
            preferences[STATUS_KEY] = statusValue
        }
    }
    suspend fun setDateCreation(dateCreationValue: String){
        context.dataStore.edit { preferences ->
            preferences[DATE_CREATION_KEY] = dateCreationValue
        }
    }
    suspend fun setId(dateCreationValue: String){
        context.dataStore.edit { preferences ->
            preferences[ID_KEY] = dateCreationValue
        }
    }
    // A getter for the counter value flow
    val emailUser: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[EMAIL_KEY] ?: ""
    }
    val groupUser: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[GROUP_KEY] ?: ""
    }
    val passwordUser: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[PASSWORD_KEY] ?: ""
    }
    val nameUser: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[NAME_KEY] ?: ""
    }
    val statusUser: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[STATUS_KEY] ?: ""
    }
    val dateCreationUser: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[DATE_CREATION_KEY] ?: ""
    }
    val idUser: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[ID_KEY] ?: ""
    }
}