package com.example.vinicius.diabetes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by vinicius on 14/08/17.
 */

public class medicao_detalhadaActivity extends Activity {
    private TextView data,hora,valorMedido,nph,acaoRapida,observacoes;
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.medicao_detalhada);
        data = (TextView) findViewById(R.id.datas);
        hora = (TextView) findViewById(R.id.horas);
        valorMedido = (TextView) findViewById(R.id.valorMedidos);
        nph = (TextView) findViewById(R.id.nphs);
        acaoRapida = (TextView) findViewById(R.id.acaoRapidas);
        observacoes = (TextView) findViewById(R.id.observacoess);

        Intent intent = getIntent();
        Medicao medida = (Medicao) intent.getSerializableExtra("medida");
        data.setText(medida.getData());
        hora.setText(medida.getHora());
        valorMedido.setText((String.valueOf(medida.getValorMedido())));
        nph.setText(String.valueOf(medida.getNph()));
        acaoRapida.setText(String.valueOf(medida.getAcaoRapida()));
        observacoes.setText(medida.getObservacoes());

    }
}
