package doit.doittestapplication.data.storage

import android.content.SharedPreferences
import doit.doittestapplication.App
import org.jetbrains.anko.defaultSharedPreferences

object Storage {

    val prefs: SharedPreferences by lazy {
        App.context.defaultSharedPreferences
    }

    fun putToken(value: String) {
        prefs.edit().putString("token", value).apply()
    }

    fun getToken(): String? {
        return prefs.getString("token", "")
    }

    fun clearToken() {
        prefs.edit().clear().apply()
    }

}