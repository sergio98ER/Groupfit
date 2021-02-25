package controladores;



import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.groupfitnuevo.R;
import com.example.groupfitnuevo.cambiarentrenousuario;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String LOGCAT = "MyFirebaseMessagingService";
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    public void onNewToken(String s){
        super.onNewToken(s);

    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        if(remoteMessage.getNotification()!=null){
            String titulo = remoteMessage.getNotification().getTitle();
            String texto = remoteMessage.getNotification().getBody();


            showNotification(titulo, texto);
        }
    }
    public void showNotification(String title, String text){
        Intent intent = new Intent(this, cambiarentrenousuario.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ArrayList<String> mensaje = new ArrayList<>();
        mensaje.add(title);
        mensaje.add(text);

        intent.putExtra(EXTRA_MESSAGE, mensaje);

        PendingIntent pendingintent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.dummbellbar).setContentTitle(title).setContentText(text).setContentIntent(pendingintent).setStyle(new NotificationCompat.BigTextStyle()
                .bigText(text));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

}
