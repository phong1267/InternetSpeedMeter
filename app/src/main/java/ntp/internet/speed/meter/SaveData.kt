package ntp.internet.speed.meter

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log

class SaveData(var sharedPreferences: SharedPreferences) {
    val TAG = "SaveData"
    val TAG_DATE = "date"
    val TAG_WIFI = "wifi"
    val TAG_MOBILE = "mobile"
    var n = 29
    var date = getDataSharedPreferences(TAG_DATE)
    var wifi = getDataSharedPreferences(TAG_WIFI)
    var mobile = getDataSharedPreferences(TAG_MOBILE)

    fun add(date: String, wifi: String, mobile: String) {
        val tempDate = mutableListOf(date)
        val tempWifi = mutableListOf(wifi)
        val tempMobile = mutableListOf(mobile)
        for (i in 0 until n) {
            if (i >= this.date?.size ?: 0) break
            tempDate.add(this.date?.get(i).toString())
            tempWifi.add(this.wifi?.get(i).toString())
            tempMobile.add(this.mobile?.get(i).toString())
        }
        this.date = tempDate
        this.wifi = tempWifi
        this.mobile = tempMobile
        saveAll()
    }

    @SuppressLint("CommitPrefEdits")
    fun saveAll() {
        setDataToSharedPreferences(TAG_DATE, date)
        setDataToSharedPreferences(TAG_WIFI, wifi)
        setDataToSharedPreferences(TAG_MOBILE, mobile)
    }

    fun setDataToSharedPreferences(name: String, list: MutableList<String>?) {
        if (list == null) return
        val edit = sharedPreferences.edit()
        try {
            for (i in 0..n) {
                edit.putString("$name$i", list.get(i))
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
        edit.apply()
    }

    fun getDataSharedPreferences(name: String): MutableList<String> {
        var list = mutableListOf<String>()
        for (i in 0..n) {
            sharedPreferences.getString("$name$i", "0")?.let { list.add(it) }
        }
        return list
    }
}