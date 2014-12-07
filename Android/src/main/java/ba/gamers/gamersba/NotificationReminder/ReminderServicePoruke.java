package ba.gamers.gamersba.NotificationReminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import ba.gamers.gamersba.R;
import ba.gamers.gamersba.RealActivity.WebActivity;

/**
 * Created by Amar on 19.11.2014.
 */

public class ReminderServicePoruke extends IntentService {
    private static final int NOTIF_ID = 1;

    public ReminderServicePoruke(){
        super("ReminderServicePoruke");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        long when = System.currentTimeMillis();         // notification time
        Notification notification = new Notification(R.drawable.gba_icon, "reminder", when);
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.flags |= notification.FLAG_AUTO_CANCEL;
        Intent notificationIntent = new Intent(this, WebActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent , 0);
        notification.setLatestEventInfo(getApplicationContext(), "Imas novu poruku!", "Klikni da je vidis!", contentIntent);
        nm.notify(NOTIF_ID, notification);
    }

}
