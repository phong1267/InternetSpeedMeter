package ntp.internet.speed.meter

import org.junit.Before
import org.junit.Test
import java.util.*

internal class MathTest {
    @Before
    fun setUp() {

    }

    @Test
    fun getMCalendar() {
        var mCalenda = Calendar.getInstance()
        var a = ((mCalenda.get(Calendar.YEAR) * 10000)
                + ((mCalenda.get(Calendar.MONTH) + 1) * 100)
                + mCalenda.get(Calendar.DATE))
        println(a)

        println("${a}".substring(6) + "-" +
                "${a}".substring(4,6) + "-" +
                "${a}".substring(0,4)
        )
    }

    @Test
    fun getDate() {
    }

    @Test
    fun getMonth() {
    }

    @Test
    fun getYear() {
    }

    @Test
    fun getHour() {
    }

    @Test
    fun getMinute() {
    }

    @Test
    fun getSecond() {
    }

    @Test
    fun getToDay() {
    }

    @Test
    fun getTimeToDay() {
    }

    @Test
    fun getTotalRxBytes() {
    }

    @Test
    fun getTotalTxBytes() {
    }

    @Test
    fun getSpeedDownload() {
    }

    @Test
    fun getSpeedUpload() {
    }

    @Test
    fun kbToString() {
    }

    @Test
    fun icon() {
    }
}