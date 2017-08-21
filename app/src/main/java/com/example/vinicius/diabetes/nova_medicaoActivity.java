package com.example.vinicius.diabetes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by vinicius on 13/08/17.
 */

public class nova_medicaoActivity extends Activity {
    private int dia,ano,mes,horas,min;
    private Button data;
    private Button hora;
    private DataBaseHelper helper;
    private EditText valorMedido,nph,acaoRapida,observacoes;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.nova_medicao);
        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        horas = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        data = (Button)findViewById(R.id.data);
        hora = (Button)findViewById(R.id.hora);
        data.setText(dia+"/"+(mes+1)+"/"+ano);
        hora.setText(horas+":"+min);
        valorMedido = (EditText) findViewById(R.id.valorMedido);
        nph = (EditText) findViewById(R.id.nph);
        acaoRapida = (EditText) findViewById(R.id.acaoRapida);
        observacoes = (EditText) findViewById(R.id.observacoes);
        helper = new DataBaseHelper(this);
    }
    public void selecionarOpcao(View v){
        showDialog(v.getId());
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if(R.id.data == id){
            return new DatePickerDialog(this,
                    listener, ano, mes, dia);
        }
        if(R.id.hora == id){
            return new TimePickerDialog(this,timePickerDialog,horas,min,true);
        }
        return null;
    }
    public void Salvar(View v){
        SQLiteDatabase db = helper.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora_format = new SimpleDateFormat("HH:mm");
        long data_banco,hora_banco;
        ContentValues values = new ContentValues();
        try {
            Date date = sdf.parse(data.getText().toString());
            Date hora_long = hora_format.parse(hora.getText().toString());
            data_banco = date.getTime();
            hora_banco = hora_long.getTime();
            values.put("data",data_banco);
            values.put("hora",hora_banco);
        }catch (Exception e){
            e.printStackTrace();
        }
        values.put("valorMedido",Integer.parseInt(valorMedido.getText().toString()));
        values.put("nph",Integer.parseInt(nph.getText().toString()));
        values.put("acaoRapida",Integer.parseInt(acaoRapida.getText().toString()));
        values.put("observacoes",observacoes.getText().toString());

        long resultado = db.insert("Diabetes",null,values);
        if(resultado != -1){
            Toast.makeText(this,"Medição Inserida",Toast.LENGTH_SHORT).show();
            valorMedido.setText("");
            nph.setText("");
            acaoRapida.setText("");
            observacoes.setText("");

        }else{
            Toast.makeText(this,"Não inseriu",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy(){
        helper.close();
        super.onDestroy();
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view,
                              int year, int monthOfYear, int dayOfMonth) {
            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;
            data.setText(dia + "/" + (mes + 1) + "/" + ano);
        }
    };
    protected TimePickerDialog.OnTimeSetListener timePickerDialog = new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay,
                                      int minute) {

                    horas = hourOfDay;
                    min = minute;
                    hora.setText(horas+ ":" +min);
                }
            };
}
