package ntp.internet.speed.meter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import ntp.internet.speed.meter.internetspeedmeter.MainServices

class BroadcastReceiverService : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // run khi app bi kill
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(Intent(context, MainServices::class.java))
        } else {
            context!!.startService(Intent(context, MainServices::class.java))
        }
    }
}