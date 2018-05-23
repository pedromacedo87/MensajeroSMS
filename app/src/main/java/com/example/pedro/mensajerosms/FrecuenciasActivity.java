package com.example.pedro.mensajerosms;

import android.app.Dialog;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FrecuenciasActivity extends AppCompatActivity {
    Button buttonOpenDialog;
    Button buttonUP;
    TextView textFolder;
    TextView textSeleccionado;

    String KEY_TEXTPSS = "TEXTPSS";
    static final int CUSTOM_DIALOG_ID = 0;
    ListView dialog_ListView;

    File root;
    File curFolder;

    private List<String> fileList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frecuencias);

        textSeleccionado = findViewById(R.id.txtSeleccionado);

        buttonOpenDialog = findViewById(R.id.btnSubir);

        buttonOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(CUSTOM_DIALOG_ID);
            }
        });

        root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        curFolder = root;

    }

    @Nullable
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        Dialog dialog = null;
        switch (id){
            case CUSTOM_DIALOG_ID:
                dialog = new Dialog(FrecuenciasActivity.this);
                dialog.setContentView(R.layout.dialoglayout);
                dialog.setTitle("Explorador");
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);

                textFolder = (TextView) dialog.findViewById(R.id.folder);
                buttonUP = (Button) dialog.findViewById(R.id.up);
                buttonUP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ListDir(curFolder.getParentFile());
                    }
                });

                dialog_ListView = (ListView) dialog.findViewById(R.id.dialoglist);
                dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        File selected = new File(fileList.get(position));
                        if (selected.isDirectory()){
                            ListDir(selected);
                        }else {
                            textSeleccionado.setText(selected.toString());
                            Toast.makeText(FrecuenciasActivity.this,selected.toString()+" Seleccionado",Toast.LENGTH_LONG).show();
                            dismissDialog(CUSTOM_DIALOG_ID);
                            try {
                                 FileInputStream fileInputStream=getApplicationContext().openFileInput(textSeleccionado.getText().toString());
                                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"UTF-8");
                                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                StringBuilder stringBuilder = new StringBuilder();
                                String line;
                                Toast.makeText(FrecuenciasActivity.this,"1",Toast.LENGTH_LONG).show();
                                while((line = bufferedReader.readLine()) != null){
                                    Toast.makeText(FrecuenciasActivity.this,"2",Toast.LENGTH_LONG).show();
                                    stringBuilder.append(line).append("\n");
                                }
                                Toast.makeText(FrecuenciasActivity.this,"3",Toast.LENGTH_LONG).show();
                                textSeleccionado.setText(stringBuilder.toString());
                                Toast.makeText(FrecuenciasActivity.this,"4",Toast.LENGTH_LONG).show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e){
                                e.printStackTrace();
                            } catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
                break;
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
        super.onPrepareDialog(id,dialog);
        switch (id){
            case CUSTOM_DIALOG_ID:
                ListDir(curFolder);
                break;
        }
    }

    void ListDir(File f){
        if (f.equals(root)){
            buttonUP.setEnabled(false);
        }else{
            buttonUP.setEnabled(true);
        }
        curFolder = f;
        textFolder.setText(f.getPath());

        File[] files = f.listFiles();
        fileList.clear();

        for(File file : files){
            fileList.add(file.getPath());
        }

        ArrayAdapter<String> directoryList = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,fileList);
        dialog_ListView.setAdapter(directoryList);
    }
}
