package com.example.userasus.asynctask.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by UserAsus on 29/03/2017.
 */
public class Conection extends SQLiteOpenHelper {


    static  String DATABASE_NAME="colores";
    //String query="create table prueba (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT);";
    String query="create table colores " +
            "(color INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "red INTEGER, " +
            "green INTEGER, " +
            "blue INTEGER);";

    public Conection(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase aa) {
        aa.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
    public static void BD_backup() throws IOException {
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        final String inFileName = "/data/data/com.example.userasus.asynctask/databases/"+DATABASE_NAME;
        File dbFile = new File(inFileName);
        FileInputStream fis = null;
        fis = new FileInputStream(dbFile);
        //String directorio = obtenerDirectorioCopias();
        String directorio = "/storage/sdcard0/Download/";
        File d = new File(directorio);
        if (!d.exists()) {
            d.mkdir();
        }
        String outFileName = directorio + "/"+DATABASE_NAME + "_"+timeStamp;
        OutputStream output = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fis.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        fis.close();
    }
}
