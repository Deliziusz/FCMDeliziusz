package zombie.deliziusz.fcmdeliziusz;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class Fcm extends FirebaseMessagingService {
    //Para usar el token en el dispositivo
    //con este token podemos envíar una notificación específica
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        //MOSTRAMOS EL VALOR DEL TOKEN
        Log.e("token","mi token es:"+s);
        //metodo para guardar token
        guardartoken(s);
    }

    private void guardartoken(String s) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("token");
        ref.child("deliziusz").setValue(s);
    }

    //Recibe las notificaciones y datos
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //RECIBIENDO INFORMACION DE LOS DATOS
        String from = remoteMessage.getFrom();


        //PARA RECIBIR UNA NOTIFICACION CLAVE VALOR
        if(remoteMessage.getData().size() > 0 ){
            String titulo = remoteMessage.getData().get("Título");
            String detalle = remoteMessage.getData().get("Detalle");

            mayorqueoreo(titulo,detalle);

        }
    }

    private void mayorqueoreo(String titulo, String detalle) {
        String id= "mensaje";
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
        //Para evitar que la app colapse si se utiliza una version menor a oreo
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            nm.createNotificationChannel(nc);
        }
        //Para que cuando toquen la notificación se pueda cancelar
        builder.setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(detalle)
                .setContentIntent(clicknoty())
                .setContentInfo("Nuevo");
        //Creamos un valor de id de manera aleatoria para que no se remplace cuando sea una nueva notificación
        Random random = new Random();
        //creamos variable para numero de la identificación
        int idNotify = random.nextInt(8000);
        //Esta opción es para evitar que sea nulo
        assert nm != null;
        nm.notify(idNotify,builder.build());
    }
    //
    public PendingIntent clicknoty(){
        //
        Intent nf = new Intent(getApplicationContext(), MainActivity.class);
        nf.putExtra("color","rojo");
        //para evitar abrir muchas notificaciones
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //llamamos
        return PendingIntent.getActivity(this, 0,nf,0);
    }
}
