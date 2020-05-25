package ntp.internet.speed.meter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(var list: MutableList<Item>) : RecyclerView.Adapter<ItemAdapter.MyHolder>() {
    @SuppressLint("ResourceType")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.bin(list[position])
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date = itemView.findViewById<TextView>(R.id.item_date)
        var mobile = itemView.findViewById<TextView>(R.id.item_mobile)
        var wifi = itemView.findViewById<TextView>(R.id.item_wifi)
        var sumData = itemView.findViewById<TextView>(R.id.item_sum_data)
        fun bin(item: Item) {
            date.text = item.dataDate
            mobile.text = item.dataMobile
            wifi.text = item.dataWifi
            sumData.text = item.sumData
        }
    }
}