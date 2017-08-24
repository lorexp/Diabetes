package com.example.vinicius.diabetes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
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
 * Created by vinicius on 17/08/17.
 */

public class editarActivity extends Activity{
    private int dia,ano,mes,horas,min;
    private Button data;
    private Button Salvar;
    private Button hora;
    private DataBaseHelper helper;
    private EditText valorMedido,nph,acaoRapida,observacoes;
    private Medicao medida;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.editar);
        data = (Button) findViewById(R.id.data);
        hora = (Button) findViewById(R.id.hora);
        valorMedido = (EditText) findViewById(R.id.valorMedidoo);
        nph = (EditText) findViewById(R.id.nphh);
        acaoRapida = (EditText) findViewById(R.id.acaoRapidaa);
        observacoes = (EditText) findViewById(R.id.observacoese);
        Salvar = (Button) findViewById(R.id.salvar);
        helper = new DataBaseHelper(this);

        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        horas = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        Intent intent = getIntent();

        medida = (Medicao) intent.getSerializableExtra("medida");

        data.setText(medida.getData());
        hora.setText(medida.getHora());
        valorMedido.setText((String.valueOf(medida.getValorMedido())));
        nph.setText(String.valueOf(medida.getNph()));
        acaoRapida.setText(String.valueOf(medida.getAcaoRapida()));
        observacoes.setText(medida.getObservacoes());


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
    public void Editar(View view){
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

        String where [] = new String[]{String.valueOf(medida.getId())};

        long result = db.update("diabetes",values,"_id = ?",where);
        if(result > 0 ){
            Toast.makeText(this,"Atualizou",Toast.LENGTH_SHORT);
            finish();
        }else{
            Toast.makeText(this,"Não Atualizou",Toast.LENGTH_SHORT);
        }
    }
}
