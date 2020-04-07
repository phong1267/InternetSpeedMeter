package ntp.internet.speed.meter.internetspeedmeter

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build


class SpeedInternet(var context: Context) {

    @SuppressLint("MissingPermission")
    fun getSpeed(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            var netInfo = cm.activeNetworkInfo
//            //should check null because in airplane mode it will be null
            var nc = cm.getNetworkCapabilities(cm.activeNetwork)
            //var downSpeed = nc!!.linkDownstreamBandwidthKbps
            //var upSpeed = nc!!.linkUpstreamBandwidthKbps
            return nc.getLinkDownstreamBandwidthKbps();
        } else return -1
    }
}