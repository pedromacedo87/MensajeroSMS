package com.example.pedro.mensajerosms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by Pedro on 06/02/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MensajeroSMS.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tbmensajes (CLAVE TEXT PRIMARY KEY, MENSAJE TEXT)");
        db.execSQL("create table tbfrecuenciamensajes (CLAVE TEXT, MENSAJE TEXT, FECHA DATE, TELEFONO TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbmensajes");
        db.execSQL("DROP TABLE IF EXISTS tbfrecuenciamensajes");
        onCreate(db);
    }

    /**Tabla tbmensajes*/
    public boolean insertDataMensajes(String clave,String mensaje) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CLAVE",clave);
        contentValues.put("MENSAJE",mensaje);
        long result = db.insert("tbmensajes",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getDataMensajes(String clave) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from tbmensajes where CLAVE ='"+clave+"'",null);
        return res;
    }

    public Cursor getAllDataMensajes() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from tbmensajes",null);
        return res;
    }

    public boolean updateDataMensajes(String clave,String mensaje) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CLAVE",clave);
        contentValues.put("MENSAJE",mensaje);
        db.update("tbmensajes", contentValues, "CLAVE = ?",new String[] { clave });
        return true;
    }

    public Integer deleteDataMensajes (String clave) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tbmensajes", "CLAVE = ?",new String[] {clave});
    }
    /**Tabla tbfrecuenciamensajes*/
    public boolean insertDataFrecuencias(String clave,String mensaje, String fecha, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CLAVE",clave);
        contentValues.put("MENSAJE",mensaje);
        contentValues.put("FECHA",fecha);
        contentValues.put("TELEFONO",telefono);
        long result = db.insert("tbfrecuenciamensajes",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getDataFrecuencias(String clave, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from tbfrecuenciamensajes where CLAVE ='"+clave+"' and TELEFONO ='"+telefono+"'",null);
        return res;
    }

    public Cursor getAllDataFrecuencias() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from tbfrecuenciamensajes",null);
        return res;
    }

    public boolean updateDataFrecuencias(String clave, String mensaje,String fecha, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CLAVE",clave);
        contentValues.put("MENSAJE",mensaje);
        contentValues.put("FECHA",fecha);
        contentValues.put("TELEFONO",telefono);

        db.update("tbfrecuenciamensajes", contentValues, "CLAVE = ? and TELEFONO = ?",new String[] { clave,telefono });
        return true;
    }

    public Integer deleteDataFrecuencias (String clave, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tbfrecuenciamensajes", "CLAVE = ? and TELEFONO = ?",new String[] {clave,telefono});
    }
}