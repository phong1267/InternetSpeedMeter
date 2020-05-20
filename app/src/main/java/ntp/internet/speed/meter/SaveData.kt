package ntp.internet.speed.meter

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log

class SaveData(var sharedPreferences: SharedPreferences) {
    var n = 29
    var date = mutableSetToList(getSharedPreferencesDate())
    private var wifi= mutableSetToList(getSharedPreferencesWifi())
    private var mobile= mutableSetToList(getSharedPreferencesMobile())


    fun add(date: String, wifi: String, mobile: String) {
        val tempDate = mutableListOf<String>()
        val tempWifi = mutableListOf<String>()
        val tempMobile = mutableListOf<String>()
        tempDate.add(date)
        tempWifi.add(wifi)
        tempMobile.add(mobile)
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
        val edit = sharedPreferences.edit()
        edit.putStringSet("date", listToMutableSet(date))
        edit.putStringSet("wifi", listToMutableSet(wifi))
        edit.putStringSet("mobile", listToMutableSet(mobile))
        edit.apply()
    }

    private fun mutableSetToList(mutableSet: MutableSet<String>?): MutableList<String>? {
        Log.e("Phong.nt1", "${mutableSet?.size ?: "null"} : $mutableSet")
        var temp = mutableListOf<String>()
        if (mutableSet != null) {
            for (i in mutableSet)
                temp.add(i)
        }
        return temp
    }

    private fun listToMutableSet(mutableSet: MutableList<String>?): MutableSet<String>? {
        var set = HashSet<String>();
        if (mutableSet != null) {
            set.addAll(mutableSet)
        }
        return set
    }

    private fun getSharedPreferencesDate(): MutableSet<String>? = sharedPreferences.getStringSet("date", null)
    private fun getSharedPreferencesWifi(): MutableSet<String>? = sharedPreferences.getStringSet("wifi", null)
    private fun getSharedPreferencesMobile(): MutableSet<String>? = sharedPreferences.getStringSet("mobile", null)
}