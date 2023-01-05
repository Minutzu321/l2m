package me.minutz.rwmanager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Utils {

    public static void createNotificationChannel(Context ctxt) {
        CharSequence name = "RVWNOTIF";
        String description = "Sistemul de notificari al aplicatiei RiverWolves";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("RVWNOT", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = ctxt.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public static void sendNotification(String titlu, String text, Context context){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "RVWNOT")
                .setSmallIcon(R.mipmap.ic_rvw_foreground)
                .setContentTitle(titlu)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        int x = 1000 + (int)(Math.random() * ((Integer.MAX_VALUE - 1000) + 1));
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(x, builder.build());
    }

}
