package com.example.textify.firebase

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.textify.R
import com.example.textify.activity.ChatActivity
import com.example.textify.models.User
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.textify.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import java.util.Random

class MessagingService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val user: User = User()
        user.id = message.data[Constants.KEY_USER_ID]
        user.name = message.data[Constants.KEY_NAME]
        user.fcmtoken = message.data[Constants.KEY_FCM_TOKEN]
        val notificationId: Int = Random().nextInt()
        val channelId: String = "chat_message"
        val intent: Intent = Intent(this@MessagingService,ChatActivity::class.java)
        intent.putExtra(Constants.KEY_RECEIVER_ID,message.data.getValue(Constants.KEY_USER_ID))
        intent.putExtra(Constants.KEY_SENDER_ID,FirebaseAuth.getInstance().uid)
        intent.putExtra(Constants.KEY_CHAT_ROOM_ID,message.data.getValue(Constants.KEY_CHAT_ROOM_ID))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(Constants.KEY_USER,user)
        val pendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this,channelId)
        builder.setSmallIcon(R.drawable.ic_chat_4)
        builder.setContentTitle(user.name)
        builder.setContentText(message.data[Constants.KEY_MESSAGE])
        builder.setStyle(NotificationCompat.BigTextStyle().bigText(message.data[Constants.KEY_MESSAGE]))
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(true)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            val channelName: CharSequence = "Chat Message"
            val channelDescription: String = "This Notification Channel is used for Chat Message Notification"
            val importance: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel: NotificationChannel = NotificationChannel(channelId,channelName,importance)
            channel.description = channelDescription
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val notificationManagerCompat: NotificationManagerCompat = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManagerCompat.notify(notificationId,builder.build())
    }

}