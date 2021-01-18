package ntp.internet.speed.meter

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import ntp.internet.speed.meter.internetspeedmeter.MainServices
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    internal lateinit var mService: MainServices
    lateinit var list: MutableList<Item>
    lateinit var serviceIntent:Intent
    internal var connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as MainServices.MyBinder
            mService = binder.service
        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }
    fun kbToString(kb: Long): String {
        var temp: Float
        if (kb >= 1024000000) {// GB
            temp = kb.toFloat() / 1024000000.toFloat()
            return "%.1f GB".format(temp)
        } else if (kb >= 1024000) {
            temp = kb.toFloat() / 1024000.toFloat()
            return "%.1f MB".format(temp)
        } else if (kb >= 1024) {
            temp = kb.toFloat() / 1024.toFloat()
            return "%.1f kB".format(temp)
        } else {
            return "$kb B"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        serviceIntent = Intent(this, MainServices::class.java)
        val mSharedPreferences = this.getSharedPreferences("DATA", Context.MODE_PRIVATE)
        var mSaveData = SaveData(mSharedPreferences)
        list = mutableListOf()
        for (i in 0..mSaveData.n) {
            var temp:String = mSaveData.date.get(i).toString()
            try {
                temp = temp.substring(6) + "-" +
                        temp.substring(4,6) + "-" +
                        temp.substring(1,4)
            }catch (e:Exception){
                temp ="0"
            }

            list.add(
                Item(
                    i,
                    temp,
                    kbToString(mSaveData.mobile.get(i)),
                    kbToString(mSaveData.wifi.get(i)),
                    kbToString(mSaveData.sumData.get(i))
                )
            )
        }

        mainListView.adapter = ItemAdapter(list)

        //serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android")
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_exit -> {
                stopService(serviceIntent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
