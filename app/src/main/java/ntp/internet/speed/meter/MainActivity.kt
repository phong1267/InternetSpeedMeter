package ntp.internet.speed.meter

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import ntp.internet.speed.meter.internetspeedmeter.MainServices

class MainActivity : AppCompatActivity() {
    internal lateinit var mService: MainServices
    private lateinit var btnAdd:Button
    private lateinit var listView: ListView
    internal var connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as MainServices.MyBinder
            mService = binder.service
        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }
var dem=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        var serviceIntent = Intent(this, MainServices::class.java)
        btnAdd = findViewById<Button>(R.id.btnadd)
        listView = findViewById<ListView>(R.id.listView)
        val mSharedPreferences = this.getSharedPreferences("PREF", Context.MODE_PRIVATE)
        var mSaveData = SaveData(mSharedPreferences)
        var listItems = mSaveData.date as List<String>
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)

        listView.adapter = adapter

        btnAdd.setOnClickListener(View.OnClickListener {
            dem++
            mSaveData.add("$dem","$dem","$dem")
            adapter.notifyDataSetChanged()
            Toast.makeText(this,"Hello",Toast.LENGTH_LONG).show()
        })

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
