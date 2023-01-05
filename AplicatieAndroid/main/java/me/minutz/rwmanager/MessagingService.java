package me.minutz.rwmanager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() <= 0)
            return;
        if(remoteMessage.getData().get("tip").equals("notif")){
            Utils.sendNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("text"), this);
        }
        if(remoteMessage.getData().get("tip").equals("upd")){
            API.updateMembri(this);
        }
    }

}
