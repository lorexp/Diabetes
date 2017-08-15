package com.example.vinicius.diabetes;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by vinicius on 14/08/17.
 */

public class lista_medicoesActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private List<Map<String, Object>> medicoes;

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        Map<String, Object> map = medicoes.get(position);
        Medicao medida = new Medicao();
        medida.setData(map.get("data").toString());
        medida.setHora(map.get("hora").toString());
        medida.setValorMedido(Integer.parseInt(map.get("valorMedido").toString()));
        medida.setNph(Integer.parseInt(map.get("nph").toString()));
        medida.setAcaoRapida(Integer.parseInt(map.get("acaoRapida").toString()));
        medida.setObservacoes("Testando");

        Intent intent = new Intent(this,medicao_detalhadaActivity.class);
        intent.putExtra("medida",medida);
        startActivity(intent);
    }

    private List<Map<String, Object>> listarMedicoes() {
        medicoes = new ArrayList<Map<String, Object>>();
        Map<String, Object> item =
                new HashMap<String, Object>();
        item.put("data", "Data: 02/02/2017");
        item.put("hora","Hora: 21:26");
        item.put("valorMedido", "Glicemia: 187");
        item.put("nph", "Insulina NPH: 15");
        item.put("acaoRapida", "Insulina Ação Rápida: 1");
        medicoes.add(item);
        item = new HashMap<String, Object>();
        item.put("data", "02/02/2017");
        item.put("hora","08:26");
        item.put("valorMedido", "84");
        item.put("nph", "30");
        item.put("acaoRapida", "0");
        medicoes.add(item);
        return medicoes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] de = {
                "data", "hora", "valorMedido", "nph", "acaoRapida"
        };
        int[] para = { R.id.data, R.id.hora,
                R.id.valorMedido, R.id.nph, R.id.acaoRapida };
        SimpleAdapter adapter = new SimpleAdapter(this,
                listarMedicoes(), R.layout.lista_medicoes, de, para);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        registerForContextMenu(getListView());
    }
}

