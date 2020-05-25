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

class MainActivity : AppCompatActivity() {
    internal lateinit var mService: MainServices
    lateinit var list: MutableList<Item>
    internal var connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as MainServices.MyBinder
            mService = binder.service
        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        var serviceIntent = Intent(this, MainServices::class.java)
        val mSharedPreferences = this.getSharedPreferences("DATA", Context.MODE_PRIVATE)
        var mSaveData = SaveData(mSharedPreferences)
        list = mutableListOf()
        for (i in 0..mSaveData.n) {
            list.add(
                Item(
                    i,
                    mSaveData.date.get(i),
                    mSaveData.mobile.get(i),
                    mSaveData.wifi.get(i),
                    "Tinh sau"
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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
