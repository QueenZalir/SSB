package com.example.ssb.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ssb.dto.Alarma;
import com.example.ssb.dto.User;
import com.example.ssb.helpers.DatabaseHelper;

import java.util.ArrayList;

public class AlarmaDAL {
    private DatabaseHelper dbHelper;
    private Alarma alarma;


    public AlarmaDAL(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.alarma = new Alarma(); //aqui debería ir user y estado pero no sé porque me da error User|int

    }

    public AlarmaDAL(Context context, User user){
        this.dbHelper = new DatabaseHelper(context);
        this.alarma = alarma;
    }

    public boolean insertar(){
        return tryInsert();
    }

    public boolean insertar(User user, int estado){
        this.alarma.setUser(user);
        this.alarma.setEstado(estado);

        return this.tryInsert();
    }

    public boolean tryInsert() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put("user",this.alarma.getUser().getNombre());
        c.put("estado",this.alarma.getEstado());

        try {
            db.insert("alarma", null, c);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public ArrayList<Alarma> seleccionar()
    {
        ArrayList<Alarma> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor consulta = db.rawQuery("SELECT * FROM alarma", null);

        if(consulta.moveToFirst()) {
            do {
                String user = consulta.getString(0);
                int estado = consulta.getInt(1);

                Alarma alarma = new Alarma(user, estado); // String| int
                lista.add(alarma);


            } while(consulta.moveToNext());

        }


        return lista;
    }


    public Alarma getAlarma() {
        return this.alarma;
    }
}
