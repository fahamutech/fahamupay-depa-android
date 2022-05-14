package com.fahamutech.fahamupay.business.services

import android.content.Context
import android.provider.Telephony
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

suspend fun readAll(context: Context){
    withContext(Dispatchers.IO) {
        val projection = arrayOf(
            Telephony.TextBasedSmsColumns.READ,
            Telephony.TextBasedSmsColumns.BODY,
            Telephony.TextBasedSmsColumns.ADDRESS,
            Telephony.TextBasedSmsColumns.THREAD_ID,
        )
        val cursor = context.contentResolver.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            projection,
            "${Telephony.TextBasedSmsColumns.ADDRESS} = ?",
            arrayOf("TIGOPESA"),
            Telephony.Sms.DEFAULT_SORT_ORDER
        )
        when (cursor?.count) {
            null -> {
                Log.e("READ SMS","******ERROR")
            }
            0 -> {
                Log.e("READ SMS","******NOTHING FOUND")
            }
            else -> {
                cursor.apply {
//                val readIndex: Int = getColumnIndex(Telephony.TextBasedSmsColumns.READ)
                    val bodyIndex: Int = getColumnIndex(Telephony.TextBasedSmsColumns.BODY)
                    val addressIndex: Int = getColumnIndex(Telephony.TextBasedSmsColumns.ADDRESS)
//                val threadIndex: Int = getColumnIndex(Telephony.TextBasedSmsColumns.THREAD_ID)
                    while (moveToNext()) {
//                    val read = getString(readIndex)
                        val body = getString(bodyIndex)
                        val address = getString(addressIndex)
                        if (body.lowercase(Locale.ROOT).contains("umepokea")) {
                            Log.e("ROW SMS", "address: $address, body: $body")
                        }
                    }
                }
            }
        }
    }
}

fun readNewInbox(){

}