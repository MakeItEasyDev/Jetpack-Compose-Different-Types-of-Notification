package com.jetpack.notificationexpand

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.jetpack.notificationexpand.ui.theme.NotificationExpandTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificationExpandTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NotificationExpand()
                }
            }
        }
    }
}

@Composable
fun NotificationExpand() {
    val context = LocalContext.current
    val channelId = "MakeitEasy"
    val notificationId = 0
    val myBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.makeiteasy)
    val bigText = "Contrary to popular belief, lorem ipsum is not simply random text. It has roots in a piece of classical latin literature from 45 BC, making it over 2000 year old."

    LaunchedEffect(Unit) {
        createNotificationChannel(channelId, context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Different Types Of Notification",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = {
                        simpleNotification(
                            context,
                            channelId,
                            notificationId,
                            "Simple Notification",
                            "This is a simple notification with default priority."
                        )
                    }
                ) {
                    Text(
                        text = "Simple Notification",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }

                Button(
                    onClick = {
                        simpleNotificationWithTapAction(
                            context,
                            channelId,
                            notificationId,
                            "Simple Notification + Tap Action",
                            "This is a simple notification will open an activity on tap."
                        )
                    }
                ) {
                    Text(
                        text = "Simple Notification + Tap Action",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }

                Button(
                    onClick = {
                        largeTextNotification(
                            context,
                            channelId,
                            notificationId,
                            "Large Text Notification",
                            bigText
                        )
                    }
                ) {
                    Text(
                        text = "Large Text Notification",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }

                Button(
                    onClick = {
                        largeTextWithBigIconNotification(
                            context,
                            channelId,
                            notificationId,
                            "Large Text with Big Icon Notification",
                            "This is a large text notification with a big icon on the right.",
                            myBitmap
                        )
                    }
                ) {
                    Text(
                        text = "Large Text + Big Icon Notification",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }

                Button(
                    onClick = {
                        bigPictureWithThumbnailNotification(
                            context,
                            channelId,
                            notificationId,
                            "Big Picture + Avatar Notification",
                            "This is a notification showing big picture and an auto hiding avatar.",
                            myBitmap
                        )
                    }
                ) {
                    Text(
                        text = "Big Picture + Big Icon Notification",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    )
}

fun createNotificationChannel(channelId: String, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "MakeitEasy"
        val desc = "My Channel MakeitEasy"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = desc
        }
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun simpleNotification(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_outline_notifications_active_24)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setPriority(priority)
    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}

fun simpleNotificationWithTapAction(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val intent = Intent(context, MainActivity::class.java).apply {
       flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_outline_notifications_active_24)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setPriority(priority)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}

fun largeTextNotification(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_outline_notifications_active_24)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(textContent)
        )
        .setPriority(priority)
    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}

fun largeTextWithBigIconNotification(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    largeIcon: Bitmap,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_outline_notifications_active_24)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setLargeIcon(largeIcon)
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText(
                    textContent
                )
        )
        .setPriority(priority)
    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}

fun bigPictureWithThumbnailNotification(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    bigImage: Bitmap,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_outline_notifications_active_24)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setLargeIcon(bigImage)
        .setStyle(
            NotificationCompat.BigPictureStyle()
                .bigPicture(bigImage)
                .bigLargeIcon(null)
        )
        .setPriority(priority)
    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}






















