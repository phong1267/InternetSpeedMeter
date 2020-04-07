package ntp.internet.speed.meter.internetspeedmeter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task")
data class Data1(
    @ColumnInfo(name = "completed_flag") var completed: Boolean = false
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}