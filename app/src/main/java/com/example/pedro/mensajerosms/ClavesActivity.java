package com.example.pedro.mensajerosms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ClavesActivity extends AppCompatActivity {
    Button btn_grabar;
    EditText edt_mensaje;
    Spinner s_spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claves);

        btn_grabar = findViewById(R.id.btnGrabar);
        edt_mensaje = findViewById(R.id.edtMensaje);
        s_spinner = findViewById(R.id.spinner);

        btn_grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
