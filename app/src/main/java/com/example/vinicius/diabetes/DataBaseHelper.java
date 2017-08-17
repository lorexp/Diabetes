package com.example.vinicius.diabetes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vinicius on 16/08/17.
 */

public class DataBaseHelper extends SQLiteOpenHelper{
    private static final String Banco = "Diabetes";
    private static final int Versao = 1;

    public DataBaseHelper(Context context){
        super(context,Banco,null,Versao);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE diabetes(_id INTEGER PRIMARY KEY, data DATE,hora TIME,valorMedido INTEGER,nph INTEGER,"
        +"acaoRapida INTEGER, observacoes TEXT);");
        }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion){

    }
}
