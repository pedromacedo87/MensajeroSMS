package com.example.pedro.mensajerosms;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    DatabaseHelper myDb;
    Button btn_claves;
    Button btn_frecuencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        btn_claves = findViewById(R.id.btnClaves);
        btn_frecuencias = findViewById(R.id.btnFrecuencia);

        ActivarPermisos();

        btn_claves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentclaves = new Intent(MainActivity.this,ClavesActivity.class);
                MainActivity.this.startActivity(intentclaves);
            }
        });

        btn_frecuencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentfrecuencias = new Intent(MainActivity.this,FrecuenciasActivity.class);
                MainActivity.this.startActivity(intentfrecuencias);
            }
        });


    }

    private void ActivarPermisos() {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.SEND_SMS))
            permissionsNeeded.add("Enviar mensajes");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("Leer almacenamiento externo");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "Se necesita dar permisos para " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                            }
                        });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
        ejecutar();
    }
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    { switch (requestCode) {
        case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
        { Map<String, Integer> perms = new HashMap<String, Integer>();
            perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            if (perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT) .show();
            }
            else
            {
                Toast.makeText(MainActivity.this, "Se negaron algunos permisos", Toast.LENGTH_SHORT) .show();
            }
        }
        break;
        default:
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void ejecutar()
    {
        //Codigo para evaluar el envio de mensajes y enviarlos
        //enviarMensaje("6864245424", "Prueba tres");
        //Termina el codigo de evaluacion
        time time = new time();
        time.execute();
    }
    public class time extends AsyncTask<Void,Integer,Boolean>
    {
        @Override
        protected Boolean doInBackground(Void... voids) {
            hilo();
            return true;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            Toast.makeText(MainActivity.this,"Ejecutando Hilo",Toast.LENGTH_SHORT).show();
        }
    }
    public void hilo() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void enviarMensaje(String numero, String mensaje){
        try{
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(numero, null,mensaje,null,null);
            Toast.makeText(MainActivity.this,"Mensaje Enviado",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(MainActivity.this,"Mensaje no Enviado",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}