package ntp.internet.speed.meter

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.util.Log

class SaveData(var sharedPreferences: SharedPreferences) {
    private val TAG = "SaveData"
    private val TAG_DATE = "date"
    private val TAG_WIFI = "wifi"
    private val TAG_MOBILE = "mobile"
    private val TAG_SUM_DATA = "sumData"
    var n = 29
    var date = getDataSharedPreferences(TAG_DATE)
    var wifi = getDataSharedPreferences(TAG_WIFI)
    var mobile = getDataSharedPreferences(TAG_MOBILE)
    var sumData = getDataSharedPreferences(TAG_SUM_DATA)


    fun add(date: Long, wifi: Long, mobile: Long, sumData: Long) {
        val tempDate = mutableListOf(date)
        val tempWifi = mutableListOf(wifi)
        val tempMobile = mutableListOf(mobile)
        val tempSumData = mutableListOf(sumData)
        for (i in 0 until n) {
            if (i >= this.date?.size ?: 0) break
            tempDate.add(this.date?.get(i))
            tempWifi.add(this.wifi?.get(i))
            tempMobile.add(this.mobile?.get(i))
            tempSumData.add(this.sumData?.get(i))
        }
        this.date = tempDate
        this.wifi = tempWifi
        this.mobile = tempMobile
        this.sumData = tempSumData
        saveAll()
    }

    @SuppressLint("CommitPrefEdits")
    private fun saveAll() {
        setDataToSharedPreferences(TAG_DATE, date)
        setDataToSharedPreferences(TAG_WIFI, wifi)
        setDataToSharedPreferences(TAG_MOBILE, mobile)
        setDataToSharedPreferences(TAG_SUM_DATA, sumData)
    }

    private fun setDataToSharedPreferences(name: String, list: MutableList<Long>?) {
        if (list == null) return
        val edit = sharedPreferences.edit()
        try {
            for (i in 0..n) {
                edit.putLong("$name$i", list.get(i))
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
        edit.apply()
    }

    private fun getDataSharedPreferences(name: String): MutableList<Long> {
        var list = mutableListOf<Long>()
        for (i in 0..n) {
            sharedPreferences.getLong("$name$i", 0)?.let { list.add(it) }
        }
        return list
    }
}