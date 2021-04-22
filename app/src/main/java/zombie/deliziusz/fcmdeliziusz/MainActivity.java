package zombie.deliziusz.fcmdeliziusz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Definimos los botones a usar
    Button especifico, atopico;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //los relacionamos con los que tenemos en los recursos
        especifico = findViewById(R.id.btn_especifico);
        atopico = findViewById(R.id.btn_atopico);

        especifico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamarespecifico();
            }

        });
        atopico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamaratopico();
            }
        });
    }//fin del onCreate

    private void llamaratopico() {
    }

    private void llamarespecifico() {
        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

        try {
            //Configuración del token
            String token="";
            json.put("to",token);
            JSONObject notificacion = new JSONObject();
            //declaramos valores a recibir
            notificacion.put("titulo","soy un titulo");
            notificacion.put("detalle","soy un detalle");
            json.put("data", notificacion);
            //declaramos la URL de FireBase
            String URL = "https://fcm.googleapis.com/fcm/send";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,json,null,null){
                //Generamos un map

                @Override
                public Map<String, String> getHeaders() {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","aplication/json");
                    header.put("authorization","key=AAAAgP9gN88:APA91bHmddC2LoKlLmWaRKqrsSGSsWchbgIGDewLfb_J7PQ-t89BR96hykzGQSuVMFvnmeyHoh0OMbtSvyBPuM43QheFuEfMc3lRqnyE1Mxiz6e8RgFMzeRz7chgH-neWgOmR6IdmZE3");
                    return header;
                }
            };
            myrequest.add(request);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}