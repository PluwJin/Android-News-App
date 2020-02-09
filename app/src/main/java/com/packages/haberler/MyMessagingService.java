package com.packages.haberler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        FirebaseApp.initializeApp(this);
        super.onMessageReceived(remoteMessage);
        showNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"),Integer.parseInt(remoteMessage.getData().get("nid")));
    }
    public void showNotification(String title , String message,int id){
        System.out.println("Çekilen id :"+id);
        Icerik.Icerikler[Icerik.kategori][Icerik.baslik][0]=Integer.toString(id);
        System.out.println(id);
        Intent intent =new Intent(this,Icerik.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"MyNotifications")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.notifyicon)
                .setAutoCancel(true)
                .setContentText(message)
                .setSound(defaultSoundUri)
                .setGroup("news")
                .setContentIntent(pendingIntent);
        NotificationManager nfmanager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nfmanager.notify(id,builder.build());

    }
}
