package com.example.vinicius.diabetes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by vinicius on 13/08/17.
 */

public class nova_medicaoActivity extends Activity {
    private int dia,ano,mes,horas,min;
    private Button data;
    private Button hora;
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
        data.setText(dia+"/"+mes+"/"+ano);
        hora.setText(horas+":"+min);
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
