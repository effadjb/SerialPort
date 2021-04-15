package world.shanya.serialport.tools

import android.content.Context

object SharedPreferencesUtil {
    fun putString(context: Context, key: String, value: String) {
        val sp = context.getSharedPreferences("SharedPreferencesName", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(context: Context, key: String): String? {
        val sp = context.getSharedPreferences("SharedPreferencesName", Context.MODE_PRIVATE)
        return sp.getString(key, "")
    }

    fun getInt(context: Context, key: String): Int {
        val sp = context.getSharedPreferences("SharedPreferencesName", Context.MODE_PRIVATE)
        return sp.getInt(key, 0)
    }

    fun clearSp(context: Context) {
        val sp = context.getSharedPreferences("SharedPreferencesName", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.clear()
        editor.apply()
    }
}