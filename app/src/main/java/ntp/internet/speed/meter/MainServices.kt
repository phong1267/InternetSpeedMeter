package ntp.internet.speed.meter.internetspeedmeter

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.TrafficStats
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ntp.internet.speed.meter.R


class MainServices : Service() {
    lateinit var notifiCationManager: NotificationManagerCompat
    var tongLuongDung: Long = 0
    var sumWifiDownloadDay: Int = 0

    internal var thread = Thread(Runnable {
        val pref = getSharedPreferences("PREF", MODE_PRIVATE)
        var defaultTrafficStats = TrafficStats.getTotalRxBytes()
        var mDownloadSpeed: Long = 0

        var channelId = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("my_service")
        } else {
            ""// Phien ban duoi android O thi khong can su dung
        }
        while (true) {
            pref.edit().putString("sumWifiDownloadDay", "value").apply()
            mDownloadSpeed =
                TrafficStats.getTotalRxBytes()//Lưu ý rằng các số liệu thống kê được trả về bởi lớp này thiết lập lại và bắt đầu từ số 0 sau mỗi lần khởi động lại.
            if (mDownloadSpeed > defaultTrafficStats) {
                sumWifiDownloadDay = ((mDownloadSpeed - defaultTrafficStats) / 1000).toInt()
            }
            Thread.sleep(1000)
            mDownloadSpeed = TrafficStats.getTotalRxBytes() - mDownloadSpeed
            tongLuongDung += (mDownloadSpeed / 1000)

            startForeground(
                1001,
                createNotifiCation(
                    channelId,
                    icon((mDownloadSpeed / 1000).toInt()),
                    "Tốc độ: " + kbToString(mDownloadSpeed) + "/s",
                    "Wifi: " + kbToString((sumWifiDownloadDay * 1000).toLong())
                )
            )
        }
    })

    private fun createNotifiCation(
        channelId: String,
        ico: Int,
        title: String,
        text: String
    ): Notification {
        //https://developer.android.com/training/notify-user/build-notification
        val notification = NotificationCompat.Builder(this, channelId)
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

    private fun createNotificationChannel(channelID: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Hiển thị thông báo"
            val descriptionText = "Hello"
            val importance = NotificationManager.IMPORTANCE_MAX
            val mChannel = NotificationChannel(channelID, name, importance)
            mChannel.description = descriptionText
            mChannel.enableVibration(false)
            // Đăng ký kênh với hệ thống; bạn không thể thay đổi tầm quan trọng
            // hoặc các hành vi thông báo khác sau này
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
            return channelID
        }
        return channelID
    }


    fun kbToString(kb: Long): String {
        var temp: Float
        if (kb >= 1000000000) {// GB
            temp = kb.toFloat() / 1000000000.toFloat()
            return "%.1f GB".format(temp)
        } else if (kb >= 1000000) {
            temp = kb.toFloat() / 1000000.toFloat()
            return "%.1f MB".format(temp)
        } else if (kb >= 1000) {
            temp = kb.toFloat() / 1000.toFloat()
            return "%.1f kB".format(temp)
        } else {
            return "$kb B"
        }
    }

    fun icon(kb: Int): Int {
        when (kb) {
            0 -> return R.mipmap.ntp000000
            1 -> return R.mipmap.ntp000001
            2 -> return R.mipmap.ntp000002
            3 -> return R.mipmap.ntp000003
            4 -> return R.mipmap.ntp000004
            5 -> return R.mipmap.ntp000005
            6 -> return R.mipmap.ntp000006
            7 -> return R.mipmap.ntp000007
            8 -> return R.mipmap.ntp000008
            9 -> return R.mipmap.ntp000009
            10 -> return R.mipmap.ntp000010
            11 -> return R.mipmap.ntp000011
            12 -> return R.mipmap.ntp000012
            13 -> return R.mipmap.ntp000013
            14 -> return R.mipmap.ntp000014
            15 -> return R.mipmap.ntp000015
            16 -> return R.mipmap.ntp000016
            17 -> return R.mipmap.ntp000017
            18 -> return R.mipmap.ntp000018
            19 -> return R.mipmap.ntp000019
            20 -> return R.mipmap.ntp000020
            21 -> return R.mipmap.ntp000021
            22 -> return R.mipmap.ntp000022
            23 -> return R.mipmap.ntp000023
            24 -> return R.mipmap.ntp000024
            25 -> return R.mipmap.ntp000025
            26 -> return R.mipmap.ntp000026
            27 -> return R.mipmap.ntp000027
            28 -> return R.mipmap.ntp000028
            29 -> return R.mipmap.ntp000029
            30 -> return R.mipmap.ntp000030
            31 -> return R.mipmap.ntp000031
            32 -> return R.mipmap.ntp000032
            33 -> return R.mipmap.ntp000033
            34 -> return R.mipmap.ntp000034
            35 -> return R.mipmap.ntp000035
            36 -> return R.mipmap.ntp000036
            37 -> return R.mipmap.ntp000037
            38 -> return R.mipmap.ntp000038
            39 -> return R.mipmap.ntp000039
            40 -> return R.mipmap.ntp000040
            41 -> return R.mipmap.ntp000041
            42 -> return R.mipmap.ntp000042
            43 -> return R.mipmap.ntp000043
            44 -> return R.mipmap.ntp000044
            45 -> return R.mipmap.ntp000045
            46 -> return R.mipmap.ntp000046
            47 -> return R.mipmap.ntp000047
            48 -> return R.mipmap.ntp000048
            49 -> return R.mipmap.ntp000049
            50 -> return R.mipmap.ntp000050
            51 -> return R.mipmap.ntp000051
            52 -> return R.mipmap.ntp000052
            53 -> return R.mipmap.ntp000053
            54 -> return R.mipmap.ntp000054
            55 -> return R.mipmap.ntp000055
            56 -> return R.mipmap.ntp000056
            57 -> return R.mipmap.ntp000057
            58 -> return R.mipmap.ntp000058
            59 -> return R.mipmap.ntp000059
            60 -> return R.mipmap.ntp000060
            61 -> return R.mipmap.ntp000061
            62 -> return R.mipmap.ntp000062
            63 -> return R.mipmap.ntp000063
            64 -> return R.mipmap.ntp000064
            65 -> return R.mipmap.ntp000065
            66 -> return R.mipmap.ntp000066
            67 -> return R.mipmap.ntp000067
            68 -> return R.mipmap.ntp000068
            69 -> return R.mipmap.ntp000069
            70 -> return R.mipmap.ntp000070
            71 -> return R.mipmap.ntp000071
            72 -> return R.mipmap.ntp000072
            73 -> return R.mipmap.ntp000073
            74 -> return R.mipmap.ntp000074
            75 -> return R.mipmap.ntp000075
            76 -> return R.mipmap.ntp000076
            77 -> return R.mipmap.ntp000077
            78 -> return R.mipmap.ntp000078
            79 -> return R.mipmap.ntp000079
            80 -> return R.mipmap.ntp000080
            81 -> return R.mipmap.ntp000081
            82 -> return R.mipmap.ntp000082
            83 -> return R.mipmap.ntp000083
            84 -> return R.mipmap.ntp000084
            85 -> return R.mipmap.ntp000085
            86 -> return R.mipmap.ntp000086
            87 -> return R.mipmap.ntp000087
            88 -> return R.mipmap.ntp000088
            89 -> return R.mipmap.ntp000089
            90 -> return R.mipmap.ntp000090
            91 -> return R.mipmap.ntp000091
            92 -> return R.mipmap.ntp000092
            93 -> return R.mipmap.ntp000093
            94 -> return R.mipmap.ntp000094
            95 -> return R.mipmap.ntp000095
            96 -> return R.mipmap.ntp000096
            97 -> return R.mipmap.ntp000097
            98 -> return R.mipmap.ntp000098
            99 -> return R.mipmap.ntp000099
            100 -> return R.mipmap.ntp000100
            101 -> return R.mipmap.ntp000101
            102 -> return R.mipmap.ntp000102
            103 -> return R.mipmap.ntp000103
            104 -> return R.mipmap.ntp000104
            105 -> return R.mipmap.ntp000105
            106 -> return R.mipmap.ntp000106
            107 -> return R.mipmap.ntp000107
            108 -> return R.mipmap.ntp000108
            109 -> return R.mipmap.ntp000109
            110 -> return R.mipmap.ntp000110
            111 -> return R.mipmap.ntp000111
            112 -> return R.mipmap.ntp000112
            113 -> return R.mipmap.ntp000113
            114 -> return R.mipmap.ntp000114
            115 -> return R.mipmap.ntp000115
            116 -> return R.mipmap.ntp000116
            117 -> return R.mipmap.ntp000117
            118 -> return R.mipmap.ntp000118
            119 -> return R.mipmap.ntp000119
            120 -> return R.mipmap.ntp000120
            121 -> return R.mipmap.ntp000121
            122 -> return R.mipmap.ntp000122
            123 -> return R.mipmap.ntp000123
            124 -> return R.mipmap.ntp000124
            125 -> return R.mipmap.ntp000125
            126 -> return R.mipmap.ntp000126
            127 -> return R.mipmap.ntp000127
            128 -> return R.mipmap.ntp000128
            129 -> return R.mipmap.ntp000129
            130 -> return R.mipmap.ntp000130
            131 -> return R.mipmap.ntp000131
            132 -> return R.mipmap.ntp000132
            133 -> return R.mipmap.ntp000133
            134 -> return R.mipmap.ntp000134
            135 -> return R.mipmap.ntp000135
            136 -> return R.mipmap.ntp000136
            137 -> return R.mipmap.ntp000137
            138 -> return R.mipmap.ntp000138
            139 -> return R.mipmap.ntp000139
            140 -> return R.mipmap.ntp000140
            141 -> return R.mipmap.ntp000141
            142 -> return R.mipmap.ntp000142
            143 -> return R.mipmap.ntp000143
            144 -> return R.mipmap.ntp000144
            145 -> return R.mipmap.ntp000145
            146 -> return R.mipmap.ntp000146
            147 -> return R.mipmap.ntp000147
            148 -> return R.mipmap.ntp000148
            149 -> return R.mipmap.ntp000149
            150 -> return R.mipmap.ntp000150
            151 -> return R.mipmap.ntp000151
            152 -> return R.mipmap.ntp000152
            153 -> return R.mipmap.ntp000153
            154 -> return R.mipmap.ntp000154
            155 -> return R.mipmap.ntp000155
            156 -> return R.mipmap.ntp000156
            157 -> return R.mipmap.ntp000157
            158 -> return R.mipmap.ntp000158
            159 -> return R.mipmap.ntp000159
            160 -> return R.mipmap.ntp000160
            161 -> return R.mipmap.ntp000161
            162 -> return R.mipmap.ntp000162
            163 -> return R.mipmap.ntp000163
            164 -> return R.mipmap.ntp000164
            165 -> return R.mipmap.ntp000165
            166 -> return R.mipmap.ntp000166
            167 -> return R.mipmap.ntp000167
            168 -> return R.mipmap.ntp000168
            169 -> return R.mipmap.ntp000169
            170 -> return R.mipmap.ntp000170
            171 -> return R.mipmap.ntp000171
            172 -> return R.mipmap.ntp000172
            173 -> return R.mipmap.ntp000173
            174 -> return R.mipmap.ntp000174
            175 -> return R.mipmap.ntp000175
            176 -> return R.mipmap.ntp000176
            177 -> return R.mipmap.ntp000177
            178 -> return R.mipmap.ntp000178
            179 -> return R.mipmap.ntp000179
            180 -> return R.mipmap.ntp000180
            181 -> return R.mipmap.ntp000181
            182 -> return R.mipmap.ntp000182
            183 -> return R.mipmap.ntp000183
            184 -> return R.mipmap.ntp000184
            185 -> return R.mipmap.ntp000185
            186 -> return R.mipmap.ntp000186
            187 -> return R.mipmap.ntp000187
            188 -> return R.mipmap.ntp000188
            189 -> return R.mipmap.ntp000189
            190 -> return R.mipmap.ntp000190
            191 -> return R.mipmap.ntp000191
            192 -> return R.mipmap.ntp000192
            193 -> return R.mipmap.ntp000193
            194 -> return R.mipmap.ntp000194
            195 -> return R.mipmap.ntp000195
            196 -> return R.mipmap.ntp000196
            197 -> return R.mipmap.ntp000197
            198 -> return R.mipmap.ntp000198
            199 -> return R.mipmap.ntp000199
            200 -> return R.mipmap.ntp000200
            201 -> return R.mipmap.ntp000201
            202 -> return R.mipmap.ntp000202
            203 -> return R.mipmap.ntp000203
            204 -> return R.mipmap.ntp000204
            205 -> return R.mipmap.ntp000205
            206 -> return R.mipmap.ntp000206
            207 -> return R.mipmap.ntp000207
            208 -> return R.mipmap.ntp000208
            209 -> return R.mipmap.ntp000209
            210 -> return R.mipmap.ntp000210
            211 -> return R.mipmap.ntp000211
            212 -> return R.mipmap.ntp000212
            213 -> return R.mipmap.ntp000213
            214 -> return R.mipmap.ntp000214
            215 -> return R.mipmap.ntp000215
            216 -> return R.mipmap.ntp000216
            217 -> return R.mipmap.ntp000217
            218 -> return R.mipmap.ntp000218
            219 -> return R.mipmap.ntp000219
            220 -> return R.mipmap.ntp000220
            221 -> return R.mipmap.ntp000221
            222 -> return R.mipmap.ntp000222
            223 -> return R.mipmap.ntp000223
            224 -> return R.mipmap.ntp000224
            225 -> return R.mipmap.ntp000225
            226 -> return R.mipmap.ntp000226
            227 -> return R.mipmap.ntp000227
            228 -> return R.mipmap.ntp000228
            229 -> return R.mipmap.ntp000229
            230 -> return R.mipmap.ntp000230
            231 -> return R.mipmap.ntp000231
            232 -> return R.mipmap.ntp000232
            233 -> return R.mipmap.ntp000233
            234 -> return R.mipmap.ntp000234
            235 -> return R.mipmap.ntp000235
            236 -> return R.mipmap.ntp000236
            237 -> return R.mipmap.ntp000237
            238 -> return R.mipmap.ntp000238
            239 -> return R.mipmap.ntp000239
            240 -> return R.mipmap.ntp000240
            241 -> return R.mipmap.ntp000241
            242 -> return R.mipmap.ntp000242
            243 -> return R.mipmap.ntp000243
            244 -> return R.mipmap.ntp000244
            245 -> return R.mipmap.ntp000245
            246 -> return R.mipmap.ntp000246
            247 -> return R.mipmap.ntp000247
            248 -> return R.mipmap.ntp000248
            249 -> return R.mipmap.ntp000249
            250 -> return R.mipmap.ntp000250
            251 -> return R.mipmap.ntp000251
            252 -> return R.mipmap.ntp000252
            253 -> return R.mipmap.ntp000253
            254 -> return R.mipmap.ntp000254
            255 -> return R.mipmap.ntp000255
            256 -> return R.mipmap.ntp000256
            257 -> return R.mipmap.ntp000257
            258 -> return R.mipmap.ntp000258
            259 -> return R.mipmap.ntp000259
            260 -> return R.mipmap.ntp000260
            261 -> return R.mipmap.ntp000261
            262 -> return R.mipmap.ntp000262
            263 -> return R.mipmap.ntp000263
            264 -> return R.mipmap.ntp000264
            265 -> return R.mipmap.ntp000265
            266 -> return R.mipmap.ntp000266
            267 -> return R.mipmap.ntp000267
            268 -> return R.mipmap.ntp000268
            269 -> return R.mipmap.ntp000269
            270 -> return R.mipmap.ntp000270
            271 -> return R.mipmap.ntp000271
            272 -> return R.mipmap.ntp000272
            273 -> return R.mipmap.ntp000273
            274 -> return R.mipmap.ntp000274
            275 -> return R.mipmap.ntp000275
            276 -> return R.mipmap.ntp000276
            277 -> return R.mipmap.ntp000277
            278 -> return R.mipmap.ntp000278
            279 -> return R.mipmap.ntp000279
            280 -> return R.mipmap.ntp000280
            281 -> return R.mipmap.ntp000281
            282 -> return R.mipmap.ntp000282
            283 -> return R.mipmap.ntp000283
            284 -> return R.mipmap.ntp000284
            285 -> return R.mipmap.ntp000285
            286 -> return R.mipmap.ntp000286
            287 -> return R.mipmap.ntp000287
            288 -> return R.mipmap.ntp000288
            289 -> return R.mipmap.ntp000289
            290 -> return R.mipmap.ntp000290
            291 -> return R.mipmap.ntp000291
            292 -> return R.mipmap.ntp000292
            293 -> return R.mipmap.ntp000293
            294 -> return R.mipmap.ntp000294
            295 -> return R.mipmap.ntp000295
            296 -> return R.mipmap.ntp000296
            297 -> return R.mipmap.ntp000297
            298 -> return R.mipmap.ntp000298
            299 -> return R.mipmap.ntp000299
            300 -> return R.mipmap.ntp000300
            301 -> return R.mipmap.ntp000301
            302 -> return R.mipmap.ntp000302
            303 -> return R.mipmap.ntp000303
            304 -> return R.mipmap.ntp000304
            305 -> return R.mipmap.ntp000305
            306 -> return R.mipmap.ntp000306
            307 -> return R.mipmap.ntp000307
            308 -> return R.mipmap.ntp000308
            309 -> return R.mipmap.ntp000309
            310 -> return R.mipmap.ntp000310
            311 -> return R.mipmap.ntp000311
            312 -> return R.mipmap.ntp000312
            313 -> return R.mipmap.ntp000313
            314 -> return R.mipmap.ntp000314
            315 -> return R.mipmap.ntp000315
            316 -> return R.mipmap.ntp000316
            317 -> return R.mipmap.ntp000317
            318 -> return R.mipmap.ntp000318
            319 -> return R.mipmap.ntp000319
            320 -> return R.mipmap.ntp000320
            321 -> return R.mipmap.ntp000321
            322 -> return R.mipmap.ntp000322
            323 -> return R.mipmap.ntp000323
            324 -> return R.mipmap.ntp000324
            325 -> return R.mipmap.ntp000325
            326 -> return R.mipmap.ntp000326
            327 -> return R.mipmap.ntp000327
            328 -> return R.mipmap.ntp000328
            329 -> return R.mipmap.ntp000329
            330 -> return R.mipmap.ntp000330
            331 -> return R.mipmap.ntp000331
            332 -> return R.mipmap.ntp000332
            333 -> return R.mipmap.ntp000333
            334 -> return R.mipmap.ntp000334
            335 -> return R.mipmap.ntp000335
            336 -> return R.mipmap.ntp000336
            337 -> return R.mipmap.ntp000337
            338 -> return R.mipmap.ntp000338
            339 -> return R.mipmap.ntp000339
            340 -> return R.mipmap.ntp000340
            341 -> return R.mipmap.ntp000341
            342 -> return R.mipmap.ntp000342
            343 -> return R.mipmap.ntp000343
            344 -> return R.mipmap.ntp000344
            345 -> return R.mipmap.ntp000345
            346 -> return R.mipmap.ntp000346
            347 -> return R.mipmap.ntp000347
            348 -> return R.mipmap.ntp000348
            349 -> return R.mipmap.ntp000349
            350 -> return R.mipmap.ntp000350
            351 -> return R.mipmap.ntp000351
            352 -> return R.mipmap.ntp000352
            353 -> return R.mipmap.ntp000353
            354 -> return R.mipmap.ntp000354
            355 -> return R.mipmap.ntp000355
            356 -> return R.mipmap.ntp000356
            357 -> return R.mipmap.ntp000357
            358 -> return R.mipmap.ntp000358
            359 -> return R.mipmap.ntp000359
            360 -> return R.mipmap.ntp000360
            361 -> return R.mipmap.ntp000361
            362 -> return R.mipmap.ntp000362
            363 -> return R.mipmap.ntp000363
            364 -> return R.mipmap.ntp000364
            365 -> return R.mipmap.ntp000365
            366 -> return R.mipmap.ntp000366
            367 -> return R.mipmap.ntp000367
            368 -> return R.mipmap.ntp000368
            369 -> return R.mipmap.ntp000369
            370 -> return R.mipmap.ntp000370
            371 -> return R.mipmap.ntp000371
            372 -> return R.mipmap.ntp000372
            373 -> return R.mipmap.ntp000373
            374 -> return R.mipmap.ntp000374
            375 -> return R.mipmap.ntp000375
            376 -> return R.mipmap.ntp000376
            377 -> return R.mipmap.ntp000377
            378 -> return R.mipmap.ntp000378
            379 -> return R.mipmap.ntp000379
            380 -> return R.mipmap.ntp000380
            381 -> return R.mipmap.ntp000381
            382 -> return R.mipmap.ntp000382
            383 -> return R.mipmap.ntp000383
            384 -> return R.mipmap.ntp000384
            385 -> return R.mipmap.ntp000385
            386 -> return R.mipmap.ntp000386
            387 -> return R.mipmap.ntp000387
            388 -> return R.mipmap.ntp000388
            389 -> return R.mipmap.ntp000389
            390 -> return R.mipmap.ntp000390
            391 -> return R.mipmap.ntp000391
            392 -> return R.mipmap.ntp000392
            393 -> return R.mipmap.ntp000393
            394 -> return R.mipmap.ntp000394
            395 -> return R.mipmap.ntp000395
            396 -> return R.mipmap.ntp000396
            397 -> return R.mipmap.ntp000397
            398 -> return R.mipmap.ntp000398
            399 -> return R.mipmap.ntp000399
            400 -> return R.mipmap.ntp000400
            401 -> return R.mipmap.ntp000401
            402 -> return R.mipmap.ntp000402
            403 -> return R.mipmap.ntp000403
            404 -> return R.mipmap.ntp000404
            405 -> return R.mipmap.ntp000405
            406 -> return R.mipmap.ntp000406
            407 -> return R.mipmap.ntp000407
            408 -> return R.mipmap.ntp000408
            409 -> return R.mipmap.ntp000409
            410 -> return R.mipmap.ntp000410
            411 -> return R.mipmap.ntp000411
            412 -> return R.mipmap.ntp000412
            413 -> return R.mipmap.ntp000413
            414 -> return R.mipmap.ntp000414
            415 -> return R.mipmap.ntp000415
            416 -> return R.mipmap.ntp000416
            417 -> return R.mipmap.ntp000417
            418 -> return R.mipmap.ntp000418
            419 -> return R.mipmap.ntp000419
            420 -> return R.mipmap.ntp000420
            421 -> return R.mipmap.ntp000421
            422 -> return R.mipmap.ntp000422
            423 -> return R.mipmap.ntp000423
            424 -> return R.mipmap.ntp000424
            425 -> return R.mipmap.ntp000425
            426 -> return R.mipmap.ntp000426
            427 -> return R.mipmap.ntp000427
            428 -> return R.mipmap.ntp000428
            429 -> return R.mipmap.ntp000429
            430 -> return R.mipmap.ntp000430
            431 -> return R.mipmap.ntp000431
            432 -> return R.mipmap.ntp000432
            433 -> return R.mipmap.ntp000433
            434 -> return R.mipmap.ntp000434
            435 -> return R.mipmap.ntp000435
            436 -> return R.mipmap.ntp000436
            437 -> return R.mipmap.ntp000437
            438 -> return R.mipmap.ntp000438
            439 -> return R.mipmap.ntp000439
            440 -> return R.mipmap.ntp000440
            441 -> return R.mipmap.ntp000441
            442 -> return R.mipmap.ntp000442
            443 -> return R.mipmap.ntp000443
            444 -> return R.mipmap.ntp000444
            445 -> return R.mipmap.ntp000445
            446 -> return R.mipmap.ntp000446
            447 -> return R.mipmap.ntp000447
            448 -> return R.mipmap.ntp000448
            449 -> return R.mipmap.ntp000449
            450 -> return R.mipmap.ntp000450
            451 -> return R.mipmap.ntp000451
            452 -> return R.mipmap.ntp000452
            453 -> return R.mipmap.ntp000453
            454 -> return R.mipmap.ntp000454
            455 -> return R.mipmap.ntp000455
            456 -> return R.mipmap.ntp000456
            457 -> return R.mipmap.ntp000457
            458 -> return R.mipmap.ntp000458
            459 -> return R.mipmap.ntp000459
            460 -> return R.mipmap.ntp000460
            461 -> return R.mipmap.ntp000461
            462 -> return R.mipmap.ntp000462
            463 -> return R.mipmap.ntp000463
            464 -> return R.mipmap.ntp000464
            465 -> return R.mipmap.ntp000465
            466 -> return R.mipmap.ntp000466
            467 -> return R.mipmap.ntp000467
            468 -> return R.mipmap.ntp000468
            469 -> return R.mipmap.ntp000469
            470 -> return R.mipmap.ntp000470
            471 -> return R.mipmap.ntp000471
            472 -> return R.mipmap.ntp000472
            473 -> return R.mipmap.ntp000473
            474 -> return R.mipmap.ntp000474
            475 -> return R.mipmap.ntp000475
            476 -> return R.mipmap.ntp000476
            477 -> return R.mipmap.ntp000477
            478 -> return R.mipmap.ntp000478
            479 -> return R.mipmap.ntp000479
            480 -> return R.mipmap.ntp000480
            481 -> return R.mipmap.ntp000481
            482 -> return R.mipmap.ntp000482
            483 -> return R.mipmap.ntp000483
            484 -> return R.mipmap.ntp000484
            485 -> return R.mipmap.ntp000485
            486 -> return R.mipmap.ntp000486
            487 -> return R.mipmap.ntp000487
            488 -> return R.mipmap.ntp000488
            489 -> return R.mipmap.ntp000489
            490 -> return R.mipmap.ntp000490
            491 -> return R.mipmap.ntp000491
            492 -> return R.mipmap.ntp000492
            493 -> return R.mipmap.ntp000493
            494 -> return R.mipmap.ntp000494
            495 -> return R.mipmap.ntp000495
            496 -> return R.mipmap.ntp000496
            497 -> return R.mipmap.ntp000497
            498 -> return R.mipmap.ntp000498
            499 -> return R.mipmap.ntp000499
            500 -> return R.mipmap.ntp000500
            501 -> return R.mipmap.ntp000501
            502 -> return R.mipmap.ntp000502
            503 -> return R.mipmap.ntp000503
            504 -> return R.mipmap.ntp000504
            505 -> return R.mipmap.ntp000505
            506 -> return R.mipmap.ntp000506
            507 -> return R.mipmap.ntp000507
            508 -> return R.mipmap.ntp000508
            509 -> return R.mipmap.ntp000509
            510 -> return R.mipmap.ntp000510
            511 -> return R.mipmap.ntp000511
            512 -> return R.mipmap.ntp000512
            513 -> return R.mipmap.ntp000513
            514 -> return R.mipmap.ntp000514
            515 -> return R.mipmap.ntp000515
            516 -> return R.mipmap.ntp000516
            517 -> return R.mipmap.ntp000517
            518 -> return R.mipmap.ntp000518
            519 -> return R.mipmap.ntp000519
            520 -> return R.mipmap.ntp000520
            521 -> return R.mipmap.ntp000521
            522 -> return R.mipmap.ntp000522
            523 -> return R.mipmap.ntp000523
            524 -> return R.mipmap.ntp000524
            525 -> return R.mipmap.ntp000525
            526 -> return R.mipmap.ntp000526
            527 -> return R.mipmap.ntp000527
            528 -> return R.mipmap.ntp000528
            529 -> return R.mipmap.ntp000529
            530 -> return R.mipmap.ntp000530
            531 -> return R.mipmap.ntp000531
            532 -> return R.mipmap.ntp000532
            533 -> return R.mipmap.ntp000533
            534 -> return R.mipmap.ntp000534
            535 -> return R.mipmap.ntp000535
            536 -> return R.mipmap.ntp000536
            537 -> return R.mipmap.ntp000537
            538 -> return R.mipmap.ntp000538
            539 -> return R.mipmap.ntp000539
            540 -> return R.mipmap.ntp000540
            541 -> return R.mipmap.ntp000541
            542 -> return R.mipmap.ntp000542
            543 -> return R.mipmap.ntp000543
            544 -> return R.mipmap.ntp000544
            545 -> return R.mipmap.ntp000545
            546 -> return R.mipmap.ntp000546
            547 -> return R.mipmap.ntp000547
            548 -> return R.mipmap.ntp000548
            549 -> return R.mipmap.ntp000549
            550 -> return R.mipmap.ntp000550
            551 -> return R.mipmap.ntp000551
            552 -> return R.mipmap.ntp000552
            553 -> return R.mipmap.ntp000553
            554 -> return R.mipmap.ntp000554
            555 -> return R.mipmap.ntp000555
            556 -> return R.mipmap.ntp000556
            557 -> return R.mipmap.ntp000557
            558 -> return R.mipmap.ntp000558
            559 -> return R.mipmap.ntp000559
            560 -> return R.mipmap.ntp000560
            561 -> return R.mipmap.ntp000561
            562 -> return R.mipmap.ntp000562
            563 -> return R.mipmap.ntp000563
            564 -> return R.mipmap.ntp000564
            565 -> return R.mipmap.ntp000565
            566 -> return R.mipmap.ntp000566
            567 -> return R.mipmap.ntp000567
            568 -> return R.mipmap.ntp000568
            569 -> return R.mipmap.ntp000569
            570 -> return R.mipmap.ntp000570
            571 -> return R.mipmap.ntp000571
            572 -> return R.mipmap.ntp000572
            573 -> return R.mipmap.ntp000573
            574 -> return R.mipmap.ntp000574
            575 -> return R.mipmap.ntp000575
            576 -> return R.mipmap.ntp000576
            577 -> return R.mipmap.ntp000577
            578 -> return R.mipmap.ntp000578
            579 -> return R.mipmap.ntp000579
            580 -> return R.mipmap.ntp000580
            581 -> return R.mipmap.ntp000581
            582 -> return R.mipmap.ntp000582
            583 -> return R.mipmap.ntp000583
            584 -> return R.mipmap.ntp000584
            585 -> return R.mipmap.ntp000585
            586 -> return R.mipmap.ntp000586
            587 -> return R.mipmap.ntp000587
            588 -> return R.mipmap.ntp000588
            589 -> return R.mipmap.ntp000589
            590 -> return R.mipmap.ntp000590
            591 -> return R.mipmap.ntp000591
            592 -> return R.mipmap.ntp000592
            593 -> return R.mipmap.ntp000593
            594 -> return R.mipmap.ntp000594
            595 -> return R.mipmap.ntp000595
            596 -> return R.mipmap.ntp000596
            597 -> return R.mipmap.ntp000597
            598 -> return R.mipmap.ntp000598
            599 -> return R.mipmap.ntp000599
            600 -> return R.mipmap.ntp000600
            601 -> return R.mipmap.ntp000601
            602 -> return R.mipmap.ntp000602
            603 -> return R.mipmap.ntp000603
            604 -> return R.mipmap.ntp000604
            605 -> return R.mipmap.ntp000605
            606 -> return R.mipmap.ntp000606
            607 -> return R.mipmap.ntp000607
            608 -> return R.mipmap.ntp000608
            609 -> return R.mipmap.ntp000609
            610 -> return R.mipmap.ntp000610
            611 -> return R.mipmap.ntp000611
            612 -> return R.mipmap.ntp000612
            613 -> return R.mipmap.ntp000613
            614 -> return R.mipmap.ntp000614
            615 -> return R.mipmap.ntp000615
            616 -> return R.mipmap.ntp000616
            617 -> return R.mipmap.ntp000617
            618 -> return R.mipmap.ntp000618
            619 -> return R.mipmap.ntp000619
            620 -> return R.mipmap.ntp000620
            621 -> return R.mipmap.ntp000621
            622 -> return R.mipmap.ntp000622
            623 -> return R.mipmap.ntp000623
            624 -> return R.mipmap.ntp000624
            625 -> return R.mipmap.ntp000625
            626 -> return R.mipmap.ntp000626
            627 -> return R.mipmap.ntp000627
            628 -> return R.mipmap.ntp000628
            629 -> return R.mipmap.ntp000629
            630 -> return R.mipmap.ntp000630
            631 -> return R.mipmap.ntp000631
            632 -> return R.mipmap.ntp000632
            633 -> return R.mipmap.ntp000633
            634 -> return R.mipmap.ntp000634
            635 -> return R.mipmap.ntp000635
            636 -> return R.mipmap.ntp000636
            637 -> return R.mipmap.ntp000637
            638 -> return R.mipmap.ntp000638
            639 -> return R.mipmap.ntp000639
            640 -> return R.mipmap.ntp000640
            641 -> return R.mipmap.ntp000641
            642 -> return R.mipmap.ntp000642
            643 -> return R.mipmap.ntp000643
            644 -> return R.mipmap.ntp000644
            645 -> return R.mipmap.ntp000645
            646 -> return R.mipmap.ntp000646
            647 -> return R.mipmap.ntp000647
            648 -> return R.mipmap.ntp000648
            649 -> return R.mipmap.ntp000649
            650 -> return R.mipmap.ntp000650
            651 -> return R.mipmap.ntp000651
            652 -> return R.mipmap.ntp000652
            653 -> return R.mipmap.ntp000653
            654 -> return R.mipmap.ntp000654
            655 -> return R.mipmap.ntp000655
            656 -> return R.mipmap.ntp000656
            657 -> return R.mipmap.ntp000657
            658 -> return R.mipmap.ntp000658
            659 -> return R.mipmap.ntp000659
            660 -> return R.mipmap.ntp000660
            661 -> return R.mipmap.ntp000661
            662 -> return R.mipmap.ntp000662
            663 -> return R.mipmap.ntp000663
            664 -> return R.mipmap.ntp000664
            665 -> return R.mipmap.ntp000665
            666 -> return R.mipmap.ntp000666
            667 -> return R.mipmap.ntp000667
            668 -> return R.mipmap.ntp000668
            669 -> return R.mipmap.ntp000669
            670 -> return R.mipmap.ntp000670
            671 -> return R.mipmap.ntp000671
            672 -> return R.mipmap.ntp000672
            673 -> return R.mipmap.ntp000673
            674 -> return R.mipmap.ntp000674
            675 -> return R.mipmap.ntp000675
            676 -> return R.mipmap.ntp000676
            677 -> return R.mipmap.ntp000677
            678 -> return R.mipmap.ntp000678
            679 -> return R.mipmap.ntp000679
            680 -> return R.mipmap.ntp000680
            681 -> return R.mipmap.ntp000681
            682 -> return R.mipmap.ntp000682
            683 -> return R.mipmap.ntp000683
            684 -> return R.mipmap.ntp000684
            685 -> return R.mipmap.ntp000685
            686 -> return R.mipmap.ntp000686
            687 -> return R.mipmap.ntp000687
            688 -> return R.mipmap.ntp000688
            689 -> return R.mipmap.ntp000689
            690 -> return R.mipmap.ntp000690
            691 -> return R.mipmap.ntp000691
            692 -> return R.mipmap.ntp000692
            693 -> return R.mipmap.ntp000693
            694 -> return R.mipmap.ntp000694
            695 -> return R.mipmap.ntp000695
            696 -> return R.mipmap.ntp000696
            697 -> return R.mipmap.ntp000697
            698 -> return R.mipmap.ntp000698
            699 -> return R.mipmap.ntp000699
            700 -> return R.mipmap.ntp000700
            701 -> return R.mipmap.ntp000701
            702 -> return R.mipmap.ntp000702
            703 -> return R.mipmap.ntp000703
            704 -> return R.mipmap.ntp000704
            705 -> return R.mipmap.ntp000705
            706 -> return R.mipmap.ntp000706
            707 -> return R.mipmap.ntp000707
            708 -> return R.mipmap.ntp000708
            709 -> return R.mipmap.ntp000709
            710 -> return R.mipmap.ntp000710
            711 -> return R.mipmap.ntp000711
            712 -> return R.mipmap.ntp000712
            713 -> return R.mipmap.ntp000713
            714 -> return R.mipmap.ntp000714
            715 -> return R.mipmap.ntp000715
            716 -> return R.mipmap.ntp000716
            717 -> return R.mipmap.ntp000717
            718 -> return R.mipmap.ntp000718
            719 -> return R.mipmap.ntp000719
            720 -> return R.mipmap.ntp000720
            721 -> return R.mipmap.ntp000721
            722 -> return R.mipmap.ntp000722
            723 -> return R.mipmap.ntp000723
            724 -> return R.mipmap.ntp000724
            725 -> return R.mipmap.ntp000725
            726 -> return R.mipmap.ntp000726
            727 -> return R.mipmap.ntp000727
            728 -> return R.mipmap.ntp000728
            729 -> return R.mipmap.ntp000729
            730 -> return R.mipmap.ntp000730
            731 -> return R.mipmap.ntp000731
            732 -> return R.mipmap.ntp000732
            733 -> return R.mipmap.ntp000733
            734 -> return R.mipmap.ntp000734
            735 -> return R.mipmap.ntp000735
            736 -> return R.mipmap.ntp000736
            737 -> return R.mipmap.ntp000737
            738 -> return R.mipmap.ntp000738
            739 -> return R.mipmap.ntp000739
            740 -> return R.mipmap.ntp000740
            741 -> return R.mipmap.ntp000741
            742 -> return R.mipmap.ntp000742
            743 -> return R.mipmap.ntp000743
            744 -> return R.mipmap.ntp000744
            745 -> return R.mipmap.ntp000745
            746 -> return R.mipmap.ntp000746
            747 -> return R.mipmap.ntp000747
            748 -> return R.mipmap.ntp000748
            749 -> return R.mipmap.ntp000749
            750 -> return R.mipmap.ntp000750
            751 -> return R.mipmap.ntp000751
            752 -> return R.mipmap.ntp000752
            753 -> return R.mipmap.ntp000753
            754 -> return R.mipmap.ntp000754
            755 -> return R.mipmap.ntp000755
            756 -> return R.mipmap.ntp000756
            757 -> return R.mipmap.ntp000757
            758 -> return R.mipmap.ntp000758
            759 -> return R.mipmap.ntp000759
            760 -> return R.mipmap.ntp000760
            761 -> return R.mipmap.ntp000761
            762 -> return R.mipmap.ntp000762
            763 -> return R.mipmap.ntp000763
            764 -> return R.mipmap.ntp000764
            765 -> return R.mipmap.ntp000765
            766 -> return R.mipmap.ntp000766
            767 -> return R.mipmap.ntp000767
            768 -> return R.mipmap.ntp000768
            769 -> return R.mipmap.ntp000769
            770 -> return R.mipmap.ntp000770
            771 -> return R.mipmap.ntp000771
            772 -> return R.mipmap.ntp000772
            773 -> return R.mipmap.ntp000773
            774 -> return R.mipmap.ntp000774
            775 -> return R.mipmap.ntp000775
            776 -> return R.mipmap.ntp000776
            777 -> return R.mipmap.ntp000777
            778 -> return R.mipmap.ntp000778
            779 -> return R.mipmap.ntp000779
            780 -> return R.mipmap.ntp000780
            781 -> return R.mipmap.ntp000781
            782 -> return R.mipmap.ntp000782
            783 -> return R.mipmap.ntp000783
            784 -> return R.mipmap.ntp000784
            785 -> return R.mipmap.ntp000785
            786 -> return R.mipmap.ntp000786
            787 -> return R.mipmap.ntp000787
            788 -> return R.mipmap.ntp000788
            789 -> return R.mipmap.ntp000789
            790 -> return R.mipmap.ntp000790
            791 -> return R.mipmap.ntp000791
            792 -> return R.mipmap.ntp000792
            793 -> return R.mipmap.ntp000793
            794 -> return R.mipmap.ntp000794
            795 -> return R.mipmap.ntp000795
            796 -> return R.mipmap.ntp000796
            797 -> return R.mipmap.ntp000797
            798 -> return R.mipmap.ntp000798
            799 -> return R.mipmap.ntp000799
            800 -> return R.mipmap.ntp000800
            801 -> return R.mipmap.ntp000801
            802 -> return R.mipmap.ntp000802
            803 -> return R.mipmap.ntp000803
            804 -> return R.mipmap.ntp000804
            805 -> return R.mipmap.ntp000805
            806 -> return R.mipmap.ntp000806
            807 -> return R.mipmap.ntp000807
            808 -> return R.mipmap.ntp000808
            809 -> return R.mipmap.ntp000809
            810 -> return R.mipmap.ntp000810
            811 -> return R.mipmap.ntp000811
            812 -> return R.mipmap.ntp000812
            813 -> return R.mipmap.ntp000813
            814 -> return R.mipmap.ntp000814
            815 -> return R.mipmap.ntp000815
            816 -> return R.mipmap.ntp000816
            817 -> return R.mipmap.ntp000817
            818 -> return R.mipmap.ntp000818
            819 -> return R.mipmap.ntp000819
            820 -> return R.mipmap.ntp000820
            821 -> return R.mipmap.ntp000821
            822 -> return R.mipmap.ntp000822
            823 -> return R.mipmap.ntp000823
            824 -> return R.mipmap.ntp000824
            825 -> return R.mipmap.ntp000825
            826 -> return R.mipmap.ntp000826
            827 -> return R.mipmap.ntp000827
            828 -> return R.mipmap.ntp000828
            829 -> return R.mipmap.ntp000829
            830 -> return R.mipmap.ntp000830
            831 -> return R.mipmap.ntp000831
            832 -> return R.mipmap.ntp000832
            833 -> return R.mipmap.ntp000833
            834 -> return R.mipmap.ntp000834
            835 -> return R.mipmap.ntp000835
            836 -> return R.mipmap.ntp000836
            837 -> return R.mipmap.ntp000837
            838 -> return R.mipmap.ntp000838
            839 -> return R.mipmap.ntp000839
            840 -> return R.mipmap.ntp000840
            841 -> return R.mipmap.ntp000841
            842 -> return R.mipmap.ntp000842
            843 -> return R.mipmap.ntp000843
            844 -> return R.mipmap.ntp000844
            845 -> return R.mipmap.ntp000845
            846 -> return R.mipmap.ntp000846
            847 -> return R.mipmap.ntp000847
            848 -> return R.mipmap.ntp000848
            849 -> return R.mipmap.ntp000849
            850 -> return R.mipmap.ntp000850
            851 -> return R.mipmap.ntp000851
            852 -> return R.mipmap.ntp000852
            853 -> return R.mipmap.ntp000853
            854 -> return R.mipmap.ntp000854
            855 -> return R.mipmap.ntp000855
            856 -> return R.mipmap.ntp000856
            857 -> return R.mipmap.ntp000857
            858 -> return R.mipmap.ntp000858
            859 -> return R.mipmap.ntp000859
            860 -> return R.mipmap.ntp000860
            861 -> return R.mipmap.ntp000861
            862 -> return R.mipmap.ntp000862
            863 -> return R.mipmap.ntp000863
            864 -> return R.mipmap.ntp000864
            865 -> return R.mipmap.ntp000865
            866 -> return R.mipmap.ntp000866
            867 -> return R.mipmap.ntp000867
            868 -> return R.mipmap.ntp000868
            869 -> return R.mipmap.ntp000869
            870 -> return R.mipmap.ntp000870
            871 -> return R.mipmap.ntp000871
            872 -> return R.mipmap.ntp000872
            873 -> return R.mipmap.ntp000873
            874 -> return R.mipmap.ntp000874
            875 -> return R.mipmap.ntp000875
            876 -> return R.mipmap.ntp000876
            877 -> return R.mipmap.ntp000877
            878 -> return R.mipmap.ntp000878
            879 -> return R.mipmap.ntp000879
            880 -> return R.mipmap.ntp000880
            881 -> return R.mipmap.ntp000881
            882 -> return R.mipmap.ntp000882
            883 -> return R.mipmap.ntp000883
            884 -> return R.mipmap.ntp000884
            885 -> return R.mipmap.ntp000885
            886 -> return R.mipmap.ntp000886
            887 -> return R.mipmap.ntp000887
            888 -> return R.mipmap.ntp000888
            889 -> return R.mipmap.ntp000889
            890 -> return R.mipmap.ntp000890
            891 -> return R.mipmap.ntp000891
            892 -> return R.mipmap.ntp000892
            893 -> return R.mipmap.ntp000893
            894 -> return R.mipmap.ntp000894
            895 -> return R.mipmap.ntp000895
            896 -> return R.mipmap.ntp000896
            897 -> return R.mipmap.ntp000897
            898 -> return R.mipmap.ntp000898
            899 -> return R.mipmap.ntp000899
            900 -> return R.mipmap.ntp000900
            901 -> return R.mipmap.ntp000901
            902 -> return R.mipmap.ntp000902
            903 -> return R.mipmap.ntp000903
            904 -> return R.mipmap.ntp000904
            905 -> return R.mipmap.ntp000905
            906 -> return R.mipmap.ntp000906
            907 -> return R.mipmap.ntp000907
            908 -> return R.mipmap.ntp000908
            909 -> return R.mipmap.ntp000909
            910 -> return R.mipmap.ntp000910
            911 -> return R.mipmap.ntp000911
            912 -> return R.mipmap.ntp000912
            913 -> return R.mipmap.ntp000913
            914 -> return R.mipmap.ntp000914
            915 -> return R.mipmap.ntp000915
            916 -> return R.mipmap.ntp000916
            917 -> return R.mipmap.ntp000917
            918 -> return R.mipmap.ntp000918
            919 -> return R.mipmap.ntp000919
            920 -> return R.mipmap.ntp000920
            921 -> return R.mipmap.ntp000921
            922 -> return R.mipmap.ntp000922
            923 -> return R.mipmap.ntp000923
            924 -> return R.mipmap.ntp000924
            925 -> return R.mipmap.ntp000925
            926 -> return R.mipmap.ntp000926
            927 -> return R.mipmap.ntp000927
            928 -> return R.mipmap.ntp000928
            929 -> return R.mipmap.ntp000929
            930 -> return R.mipmap.ntp000930
            931 -> return R.mipmap.ntp000931
            932 -> return R.mipmap.ntp000932
            933 -> return R.mipmap.ntp000933
            934 -> return R.mipmap.ntp000934
            935 -> return R.mipmap.ntp000935
            936 -> return R.mipmap.ntp000936
            937 -> return R.mipmap.ntp000937
            938 -> return R.mipmap.ntp000938
            939 -> return R.mipmap.ntp000939
            940 -> return R.mipmap.ntp000940
            941 -> return R.mipmap.ntp000941
            942 -> return R.mipmap.ntp000942
            943 -> return R.mipmap.ntp000943
            944 -> return R.mipmap.ntp000944
            945 -> return R.mipmap.ntp000945
            946 -> return R.mipmap.ntp000946
            947 -> return R.mipmap.ntp000947
            948 -> return R.mipmap.ntp000948
            949 -> return R.mipmap.ntp000949
            950 -> return R.mipmap.ntp000950
            951 -> return R.mipmap.ntp000951
            952 -> return R.mipmap.ntp000952
            953 -> return R.mipmap.ntp000953
            954 -> return R.mipmap.ntp000954
            955 -> return R.mipmap.ntp000955
            956 -> return R.mipmap.ntp000956
            957 -> return R.mipmap.ntp000957
            958 -> return R.mipmap.ntp000958
            959 -> return R.mipmap.ntp000959
            960 -> return R.mipmap.ntp000960
            961 -> return R.mipmap.ntp000961
            962 -> return R.mipmap.ntp000962
            963 -> return R.mipmap.ntp000963
            964 -> return R.mipmap.ntp000964
            965 -> return R.mipmap.ntp000965
            966 -> return R.mipmap.ntp000966
            967 -> return R.mipmap.ntp000967
            968 -> return R.mipmap.ntp000968
            969 -> return R.mipmap.ntp000969
            970 -> return R.mipmap.ntp000970
            971 -> return R.mipmap.ntp000971
            972 -> return R.mipmap.ntp000972
            973 -> return R.mipmap.ntp000973
            974 -> return R.mipmap.ntp000974
            975 -> return R.mipmap.ntp000975
            976 -> return R.mipmap.ntp000976
            977 -> return R.mipmap.ntp000977
            978 -> return R.mipmap.ntp000978
            979 -> return R.mipmap.ntp000979
            980 -> return R.mipmap.ntp000980
            981 -> return R.mipmap.ntp000981
            982 -> return R.mipmap.ntp000982
            983 -> return R.mipmap.ntp000983
            984 -> return R.mipmap.ntp000984
            985 -> return R.mipmap.ntp000985
            986 -> return R.mipmap.ntp000986
            987 -> return R.mipmap.ntp000987
            988 -> return R.mipmap.ntp000988
            989 -> return R.mipmap.ntp000989
            990 -> return R.mipmap.ntp000990
            991 -> return R.mipmap.ntp000991
            992 -> return R.mipmap.ntp000992
            993 -> return R.mipmap.ntp000993
            994 -> return R.mipmap.ntp000994
            995 -> return R.mipmap.ntp000995
            996 -> return R.mipmap.ntp000996
            997 -> return R.mipmap.ntp000997
            998 -> return R.mipmap.ntp000998
            999 -> return R.mipmap.ntp000999
            in 1000..1050 -> return R.mipmap.ntp001000
            in 1051..1150 -> return R.mipmap.ntp001100
            in 1151..1250 -> return R.mipmap.ntp001200
            in 1251..1350 -> return R.mipmap.ntp001300
            in 1351..1450 -> return R.mipmap.ntp001400
            in 1451..1550 -> return R.mipmap.ntp001500
            in 1551..1650 -> return R.mipmap.ntp001600
            in 1651..1750 -> return R.mipmap.ntp001700
            in 1751..1850 -> return R.mipmap.ntp001800
            in 1851..1950 -> return R.mipmap.ntp001900
            in 1951..2050 -> return R.mipmap.ntp002000
            in 2051..2150 -> return R.mipmap.ntp002100
            in 2151..2250 -> return R.mipmap.ntp002200
            in 2251..2350 -> return R.mipmap.ntp002300
            in 2351..2450 -> return R.mipmap.ntp002400
            in 2451..2550 -> return R.mipmap.ntp002500
            in 2551..2650 -> return R.mipmap.ntp002600
            in 2651..2750 -> return R.mipmap.ntp002700
            in 2751..2850 -> return R.mipmap.ntp002800
            in 2851..2950 -> return R.mipmap.ntp002900
            in 2951..3050 -> return R.mipmap.ntp003000
            in 3051..3150 -> return R.mipmap.ntp003100
            in 3151..3250 -> return R.mipmap.ntp003200
            in 3251..3350 -> return R.mipmap.ntp003300
            in 3351..3450 -> return R.mipmap.ntp003400
            in 3451..3550 -> return R.mipmap.ntp003500
            in 3551..3650 -> return R.mipmap.ntp003600
            in 3651..3750 -> return R.mipmap.ntp003700
            in 3751..3850 -> return R.mipmap.ntp003800
            in 3851..3950 -> return R.mipmap.ntp003900
            in 3951..4050 -> return R.mipmap.ntp004000
            in 4051..4150 -> return R.mipmap.ntp004100
            in 4151..4250 -> return R.mipmap.ntp004200
            in 4251..4350 -> return R.mipmap.ntp004300
            in 4351..4450 -> return R.mipmap.ntp004400
            in 4451..4550 -> return R.mipmap.ntp004500
            in 4551..4650 -> return R.mipmap.ntp004600
            in 4651..4750 -> return R.mipmap.ntp004700
            in 4751..4850 -> return R.mipmap.ntp004800
            in 4851..4950 -> return R.mipmap.ntp004900
            in 4951..5050 -> return R.mipmap.ntp005000
            in 5051..5150 -> return R.mipmap.ntp005100
            in 5151..5250 -> return R.mipmap.ntp005200
            in 5251..5350 -> return R.mipmap.ntp005300
            in 5351..5450 -> return R.mipmap.ntp005400
            in 5451..5550 -> return R.mipmap.ntp005500
            in 5551..5650 -> return R.mipmap.ntp005600
            in 5651..5750 -> return R.mipmap.ntp005700
            in 5751..5850 -> return R.mipmap.ntp005800
            in 5851..5950 -> return R.mipmap.ntp005900
            in 5951..6050 -> return R.mipmap.ntp006000
            in 6051..6150 -> return R.mipmap.ntp006100
            in 6151..6250 -> return R.mipmap.ntp006200
            in 6251..6350 -> return R.mipmap.ntp006300
            in 6351..6450 -> return R.mipmap.ntp006400
            in 6451..6550 -> return R.mipmap.ntp006500
            in 6551..6650 -> return R.mipmap.ntp006600
            in 6651..6750 -> return R.mipmap.ntp006700
            in 6751..6850 -> return R.mipmap.ntp006800
            in 6851..6950 -> return R.mipmap.ntp006900
            in 6951..7050 -> return R.mipmap.ntp007000
            in 7051..7150 -> return R.mipmap.ntp007100
            in 7151..7250 -> return R.mipmap.ntp007200
            in 7251..7350 -> return R.mipmap.ntp007300
            in 7351..7450 -> return R.mipmap.ntp007400
            in 7451..7550 -> return R.mipmap.ntp007500
            in 7551..7650 -> return R.mipmap.ntp007600
            in 7651..7750 -> return R.mipmap.ntp007700
            in 7751..7850 -> return R.mipmap.ntp007800
            in 7851..7950 -> return R.mipmap.ntp007900
            in 7951..8050 -> return R.mipmap.ntp008000
            in 8051..8150 -> return R.mipmap.ntp008100
            in 8151..8250 -> return R.mipmap.ntp008200
            in 8251..8350 -> return R.mipmap.ntp008300
            in 8351..8450 -> return R.mipmap.ntp008400
            in 8451..8550 -> return R.mipmap.ntp008500
            in 8551..8650 -> return R.mipmap.ntp008600
            in 8651..8750 -> return R.mipmap.ntp008700
            in 8751..8850 -> return R.mipmap.ntp008800
            in 8851..8950 -> return R.mipmap.ntp008900
            in 8951..9050 -> return R.mipmap.ntp009000
            in 9051..9150 -> return R.mipmap.ntp009100
            in 9151..9250 -> return R.mipmap.ntp009200
            in 9251..9350 -> return R.mipmap.ntp009300
            in 9351..9450 -> return R.mipmap.ntp009400
            in 9451..9550 -> return R.mipmap.ntp009500
            in 9551..9650 -> return R.mipmap.ntp009600
            in 9651..9750 -> return R.mipmap.ntp009700
            in 9751..9850 -> return R.mipmap.ntp009800
            in 9851..9950 -> return R.mipmap.ntp009900
            in 9951..10500 -> return R.mipmap.ntp010000
            in 10501..11500 -> return R.mipmap.ntp011000
            in 10501..11500 -> return R.mipmap.ntp011000
            in 11501..12500 -> return R.mipmap.ntp012000
            in 12501..13500 -> return R.mipmap.ntp013000
            in 13501..14500 -> return R.mipmap.ntp014000
            in 14501..15500 -> return R.mipmap.ntp015000
            in 15501..16500 -> return R.mipmap.ntp016000
            in 16501..17500 -> return R.mipmap.ntp017000
            in 17501..18500 -> return R.mipmap.ntp018000
            in 18501..19500 -> return R.mipmap.ntp019000
            in 19501..20500 -> return R.mipmap.ntp020000
            in 20501..21500 -> return R.mipmap.ntp021000
            in 21501..22500 -> return R.mipmap.ntp022000
            in 22501..23500 -> return R.mipmap.ntp023000
            in 23501..24500 -> return R.mipmap.ntp024000
            in 24501..25500 -> return R.mipmap.ntp025000
            in 25501..26500 -> return R.mipmap.ntp026000
            in 26501..27500 -> return R.mipmap.ntp027000
            in 27501..28500 -> return R.mipmap.ntp028000
            in 28501..29500 -> return R.mipmap.ntp029000
            in 29501..30500 -> return R.mipmap.ntp030000
            in 30501..31500 -> return R.mipmap.ntp031000
            in 31501..32500 -> return R.mipmap.ntp032000
            in 32501..33500 -> return R.mipmap.ntp033000
            in 33501..34500 -> return R.mipmap.ntp034000
            in 34501..35500 -> return R.mipmap.ntp035000
            in 35501..36500 -> return R.mipmap.ntp036000
            in 36501..37500 -> return R.mipmap.ntp037000
            in 37501..38500 -> return R.mipmap.ntp038000
            in 38501..39500 -> return R.mipmap.ntp039000
            in 39501..40500 -> return R.mipmap.ntp040000
            in 40501..41500 -> return R.mipmap.ntp041000
            in 41501..42500 -> return R.mipmap.ntp042000
            in 42501..43500 -> return R.mipmap.ntp043000
            in 43501..44500 -> return R.mipmap.ntp044000
            in 44501..45500 -> return R.mipmap.ntp045000
            in 45501..46500 -> return R.mipmap.ntp046000
            in 46501..47500 -> return R.mipmap.ntp047000
            in 47501..48500 -> return R.mipmap.ntp048000
            in 48501..49500 -> return R.mipmap.ntp049000
            in 49501..50500 -> return R.mipmap.ntp050000
            in 50501..51500 -> return R.mipmap.ntp051000
            in 51501..52500 -> return R.mipmap.ntp052000
            in 52501..53500 -> return R.mipmap.ntp053000
            in 53501..54500 -> return R.mipmap.ntp054000
            in 54501..55500 -> return R.mipmap.ntp055000
            in 55501..56500 -> return R.mipmap.ntp056000
            in 56501..57500 -> return R.mipmap.ntp057000
            in 57501..58500 -> return R.mipmap.ntp058000
            in 58501..59500 -> return R.mipmap.ntp059000
            in 59501..60500 -> return R.mipmap.ntp060000
            in 60501..61500 -> return R.mipmap.ntp061000
            in 61501..62500 -> return R.mipmap.ntp062000
            in 62501..63500 -> return R.mipmap.ntp063000
            in 63501..64500 -> return R.mipmap.ntp064000
            in 64501..65500 -> return R.mipmap.ntp065000
            in 65501..66500 -> return R.mipmap.ntp066000
            in 66501..67500 -> return R.mipmap.ntp067000
            in 67501..68500 -> return R.mipmap.ntp068000
            in 68501..69500 -> return R.mipmap.ntp069000
            in 69501..70500 -> return R.mipmap.ntp070000
            in 70501..71500 -> return R.mipmap.ntp071000
            in 71501..72500 -> return R.mipmap.ntp072000
            in 72501..73500 -> return R.mipmap.ntp073000
            in 73501..74500 -> return R.mipmap.ntp074000
            in 74501..75500 -> return R.mipmap.ntp075000
            in 75501..76500 -> return R.mipmap.ntp076000
            in 76501..77500 -> return R.mipmap.ntp077000
            in 77501..78500 -> return R.mipmap.ntp078000
            in 78501..79500 -> return R.mipmap.ntp079000
            in 79501..80500 -> return R.mipmap.ntp080000
            in 80501..81500 -> return R.mipmap.ntp081000
            in 81501..82500 -> return R.mipmap.ntp082000
            in 82501..83500 -> return R.mipmap.ntp083000
            in 83501..84500 -> return R.mipmap.ntp084000
            in 84501..85500 -> return R.mipmap.ntp085000
            in 85501..86500 -> return R.mipmap.ntp086000
            in 86501..87500 -> return R.mipmap.ntp087000
            in 87501..88500 -> return R.mipmap.ntp088000
            in 88501..89500 -> return R.mipmap.ntp089000
            in 89501..90500 -> return R.mipmap.ntp090000
            in 90501..91500 -> return R.mipmap.ntp091000
            in 91501..92500 -> return R.mipmap.ntp092000
            in 92501..93500 -> return R.mipmap.ntp093000
            in 93501..94500 -> return R.mipmap.ntp094000
            in 94501..95500 -> return R.mipmap.ntp095000
            in 95501..96500 -> return R.mipmap.ntp096000
            in 96501..97500 -> return R.mipmap.ntp097000
            in 97501..98500 -> return R.mipmap.ntp098000
            in 98501..99500 -> return R.mipmap.ntp099000
            in 99501..100500 -> return R.mipmap.ntp100000
            in 100501..101500 -> return R.mipmap.ntp101000
            in 101501..102500 -> return R.mipmap.ntp102000
            in 102501..103500 -> return R.mipmap.ntp103000
            in 103501..104500 -> return R.mipmap.ntp104000
            in 104501..105500 -> return R.mipmap.ntp105000
            in 105501..106500 -> return R.mipmap.ntp106000
            in 106501..107500 -> return R.mipmap.ntp107000
            in 107501..108500 -> return R.mipmap.ntp108000
            in 108501..109500 -> return R.mipmap.ntp109000
            in 109501..110500 -> return R.mipmap.ntp110000
            in 110501..111500 -> return R.mipmap.ntp111000
            in 111501..112500 -> return R.mipmap.ntp112000
            in 112501..113500 -> return R.mipmap.ntp113000
            in 113501..114500 -> return R.mipmap.ntp114000
            in 114501..115500 -> return R.mipmap.ntp115000
            in 115501..116500 -> return R.mipmap.ntp116000
            in 116501..117500 -> return R.mipmap.ntp117000
            in 117501..118500 -> return R.mipmap.ntp118000
            in 118501..119500 -> return R.mipmap.ntp119000
            in 119501..120500 -> return R.mipmap.ntp120000
            in 120501..121500 -> return R.mipmap.ntp121000
            in 121501..122500 -> return R.mipmap.ntp122000
            in 122501..123500 -> return R.mipmap.ntp123000
            in 123501..124500 -> return R.mipmap.ntp124000
            in 124501..125500 -> return R.mipmap.ntp125000
            in 125501..126500 -> return R.mipmap.ntp126000
            in 126501..127500 -> return R.mipmap.ntp127000
            in 127501..128500 -> return R.mipmap.ntp128000
            in 128501..129500 -> return R.mipmap.ntp129000
            in 129501..130500 -> return R.mipmap.ntp130000
            in 130501..131500 -> return R.mipmap.ntp131000
            in 131501..132500 -> return R.mipmap.ntp132000
            in 132501..133500 -> return R.mipmap.ntp133000
            in 133501..134500 -> return R.mipmap.ntp134000
            in 134501..135500 -> return R.mipmap.ntp135000
            in 135501..136500 -> return R.mipmap.ntp136000
            in 136501..137500 -> return R.mipmap.ntp137000
            in 137501..138500 -> return R.mipmap.ntp138000
            in 138501..139500 -> return R.mipmap.ntp139000
            in 139501..140500 -> return R.mipmap.ntp140000
            in 140501..141500 -> return R.mipmap.ntp141000
            in 141501..142500 -> return R.mipmap.ntp142000
            in 142501..143500 -> return R.mipmap.ntp143000
            in 143501..144500 -> return R.mipmap.ntp144000
            in 144501..145500 -> return R.mipmap.ntp145000
            in 145501..146500 -> return R.mipmap.ntp146000
            in 146501..147500 -> return R.mipmap.ntp147000
            in 147501..148500 -> return R.mipmap.ntp148000
            in 148501..149500 -> return R.mipmap.ntp149000
            in 149501..150500 -> return R.mipmap.ntp150000
            in 150501..151500 -> return R.mipmap.ntp151000
            in 151501..152500 -> return R.mipmap.ntp152000
            in 152501..153500 -> return R.mipmap.ntp153000
            in 153501..154500 -> return R.mipmap.ntp154000
            in 154501..155500 -> return R.mipmap.ntp155000
            in 155501..156500 -> return R.mipmap.ntp156000
            in 156501..157500 -> return R.mipmap.ntp157000
            in 157501..158500 -> return R.mipmap.ntp158000
            in 158501..159500 -> return R.mipmap.ntp159000
            in 159501..160500 -> return R.mipmap.ntp160000
            in 160501..161500 -> return R.mipmap.ntp161000
            in 161501..162500 -> return R.mipmap.ntp162000
            in 162501..163500 -> return R.mipmap.ntp163000
            in 163501..164500 -> return R.mipmap.ntp164000
            in 164501..165500 -> return R.mipmap.ntp165000
            in 165501..166500 -> return R.mipmap.ntp166000
            in 166501..167500 -> return R.mipmap.ntp167000
            in 167501..168500 -> return R.mipmap.ntp168000
            in 168501..169500 -> return R.mipmap.ntp169000
            in 169501..170500 -> return R.mipmap.ntp170000
            in 170501..171500 -> return R.mipmap.ntp171000
            in 171501..172500 -> return R.mipmap.ntp172000
            in 172501..173500 -> return R.mipmap.ntp173000
            in 173501..174500 -> return R.mipmap.ntp174000
            in 174501..175500 -> return R.mipmap.ntp175000
            in 175501..176500 -> return R.mipmap.ntp176000
            in 176501..177500 -> return R.mipmap.ntp177000
            in 177501..178500 -> return R.mipmap.ntp178000
            in 178501..179500 -> return R.mipmap.ntp179000
            in 179501..180500 -> return R.mipmap.ntp180000
            in 180501..181500 -> return R.mipmap.ntp181000
            in 181501..182500 -> return R.mipmap.ntp182000
            in 182501..183500 -> return R.mipmap.ntp183000
            in 183501..184500 -> return R.mipmap.ntp184000
            in 184501..185500 -> return R.mipmap.ntp185000
            in 185501..186500 -> return R.mipmap.ntp186000
            in 186501..187500 -> return R.mipmap.ntp187000
            in 187501..188500 -> return R.mipmap.ntp188000
            in 188501..189500 -> return R.mipmap.ntp189000
            in 189501..190500 -> return R.mipmap.ntp190000
            in 190501..191500 -> return R.mipmap.ntp191000
            in 191501..192500 -> return R.mipmap.ntp192000
            in 192501..193500 -> return R.mipmap.ntp193000
            in 193501..194500 -> return R.mipmap.ntp194000
            in 194501..195500 -> return R.mipmap.ntp195000
            in 195501..196500 -> return R.mipmap.ntp196000
            in 196501..197500 -> return R.mipmap.ntp197000
            in 197501..198500 -> return R.mipmap.ntp198000
            in 198501..199500 -> return R.mipmap.ntp199000
            in 199501..200500 -> return R.mipmap.ntp200000
            in 200501..9999999 -> return R.mipmap.ntp200001
        }
        return -1
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