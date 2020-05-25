package ntp.internet.speed.meter.internetspeedmeter

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ntp.internet.speed.meter.Math


class MainServices : Service() {
    lateinit var notifiCationManager: NotificationManagerCompat
    var mMath = Math(this)

    internal var thread = Thread(Runnable {
        mMath.setDefaultTotalRxBytes()
        while (true) {
            try {
                mMath.isMobileInternet = isConnectedMobile()
                mMath.main()

                startForeground(
                    1001,
                    createNotifiCation(
                        mMath.icon((mMath.speedDownLoad / 1000).toInt()),
                        "Tải về: ${mMath.kbToString(mMath.speedDownLoad)}/s, Tải lên: ${mMath.kbToString(
                            mMath.speedUpLoad
                        )}/s",
                        "Wifi: ${mMath.kbToString((mMath.sumDataWifiDay))}, Di động: ${mMath.kbToString(
                            mMath.sumDataMobileInternetDay
                        )}"
                    )
                )
            } catch (e: Exception) {
            }
        }
    })

    fun isConnectedMobile(): Boolean {
        //https://stackoverflow.com/questions/2802472/detect-network-connection-type-on-android
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info: NetworkInfo = cm.getActiveNetworkInfo()
        return info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE
    }

    private fun getChanneId(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("my_service")
        } else {
            ""// Phien ban duoi android O thi khong can su dung
        }
    }

    private fun createNotifiCation(
        ico: Int,
        title: String,
        text: String
    ): Notification {
        //https://developer.android.com/training/notify-user/build-notification
        val notification = NotificationCompat.Builder(this, getChanneId())
            .setOngoing(true)
            .setSmallIcon(ico)
            .setOnlyAlertOnce(true)
            .setShowWhen(false)
            //.setPriority(NotificationCompat.PRIORITY_MAX)//ƯU tiên
            .setCategory(Notification.CATEGORY_SERVICE)
            //.setTicker("Start Service")
            .setContentTitle(title)
            .setContentText(text)
            .build()
        return notification
    }

    @SuppressLint("WrongConstant")
    private fun createNotificationChannel(channelID: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Hiển thị thông báo"
            val descriptionText = "Hello"
            val importance = NotificationManager.IMPORTANCE_MAX
            val mChannel = NotificationChannel(channelID, name, importance)
            mChannel.description = descriptionText
            mChannel.enableVibration(true)
            // Đăng ký kênh với hệ thống; bạn không thể thay đổi tầm quan trọng
            // hoặc các hành vi thông báo khác sau này
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
            return channelID
        }
        return channelID
    }


    internal inner class MyBinder : Binder() {
        val service: MainServices
            get() = this@MainServices
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notifiCationManager = NotificationManagerCompat.from(applicationContext)
        if (!thread.isAlive) thread.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
    }
}