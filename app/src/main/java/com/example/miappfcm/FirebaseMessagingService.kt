package com.example.miappfcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.util.Log


class MyFirebaseMessagingService: FirebaseMessagingService() {

    private val CHANNEL_ID = "fcm_default_channel"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Muestra en logcat
        Log.d("FCM_TOKEN", "Nuevo token: $token")
        // (Opcional) aquí podrías enviar este token a tu servidor
    }


    override fun onMessageReceived(message: RemoteMessage) {
        // Si trae payload de notificación:
        val title = message.notification?.title ?: "Nuevo mensaje"
        val body  = message.notification?.body  ?: message.data["body"] ?: ""

        // Construye y dispara la notificación
        val notif = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_notification)  // crea un icono en res/drawable
            .setAutoCancel(true)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        manager.notify(System.currentTimeMillis().toInt(), notif)
    }

    private fun createNotificationChannel() {
        // Para Android O y superiores, registrar canal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name    = "Mensajes FCM"
            val descr   = "Canal para notificaciones de Firebase Cloud Messaging"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descr
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}
