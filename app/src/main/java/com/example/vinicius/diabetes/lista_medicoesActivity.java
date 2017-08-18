package com.example.vinicius.diabetes;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by vinicius on 14/08/17.
 */

public class lista_medicoesActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private List<Map<String, Object>> medicoes;
    private DataBaseHelper helper;

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        Map<String, Object> map = medicoes.get(position);
        Medicao medida = new Medicao();
        medida.setId(Integer.parseInt(map.get("id").toString()));
        medida.setData(map.get("data").toString());
        medida.setHora(map.get("hora").toString());
        medida.setValorMedido(Integer.parseInt(map.get("valorMedido").toString()));
        medida.setNph(Integer.parseInt(map.get("nph").toString()));
        medida.setAcaoRapida(Integer.parseInt(map.get("acaoRapida").toString()));
        medida.setObservacoes(map.get("observacoes").toString());

        Intent intent = new Intent(this,medicao_detalhadaActivity.class);
        intent.putExtra("medida",medida);
        startActivity(intent);
    }

    private List<Map<String, Object>> listarMedicoes() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id,data,hora,valorMedido,nph,acaoRapida,observacoes FROM diabetes order by data;",null);
        cursor.moveToFirst();
        medicoes = new ArrayList<Map<String, Object>>();
        for(int i = 0;i<cursor.getCount();i++){
            Map<String, Object> item =
                    new HashMap<String, Object>();
            Date hora,data;
            long hora_long,data_long;

            hora_long = cursor.getLong(2);
            data_long = cursor.getLong(1);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            hora = new Date(hora_long);
            data = new Date(data_long);
            item.put("id",cursor.getInt(0));
            item.put("data",sdf.format(data));
            item.put("hora",simpleDateFormat.format(hora));
            item.put("valorMedido",cursor.getInt(3));
            item.put("nph",cursor.getInt(4));
            item.put("acaoRapida",cursor.getInt(5));
            item.put("observacoes",cursor.getString(6));
            medicoes.add(item);
            cursor.moveToNext();
        }
        cursor.close();
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
        helper = new DataBaseHelper(this);
        SimpleAdapter adapter = new SimpleAdapter(this,
                listarMedicoes(), R.layout.lista_medicoes, de, para);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        registerForContextMenu(getListView());
    }
    @Override
    protected void onResume(){
        super.onResume();
        String[] de = {
                "data", "hora", "valorMedido", "nph", "acaoRapida"
        };
        int[] para = { R.id.data, R.id.hora,
                R.id.valorMedido, R.id.nph, R.id.acaoRapida };
        helper = new DataBaseHelper(this);
        SimpleAdapter adapter = new SimpleAdapter(this,
                listarMedicoes(), R.layout.lista_medicoes, de, para);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        registerForContextMenu(getListView());

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_listar, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.excluir) {
            AdapterView.AdapterContextMenuInfo info =
                    (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Medicao medida = new Medicao();
            Map<String, Object> valor =
                    new HashMap<String, Object>();
            valor = medicoes.get(info.position);
            medicoes.remove(info.position);
            getListView().invalidateViews();
            SQLiteDatabase db = helper.getWritableDatabase();
                String where [] = new String[]{String.valueOf(valor.get("id"))};
                long result = db.delete("diabetes","_id = ?",where);
                if(result != -1) {
                    Toast.makeText(this, "Deletado com Sucesso", Toast.LENGTH_SHORT).show();
                    return true;
                }
        }
        if(item.getItemId() == R.id.editar){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Medicao medida = new Medicao();
            Map<String, Object> valor =
                    new HashMap<String, Object>();
            valor = medicoes.get(info.position);
            medida.setId(Integer.parseInt(valor.get("id").toString()));
            medida.setData(valor.get("data").toString());
            medida.setHora(valor.get("hora").toString());
            medida.setValorMedido(Integer.parseInt(valor.get("valorMedido").toString()));
            medida.setNph(Integer.parseInt(valor.get("nph").toString()));
            medida.setAcaoRapida(Integer.parseInt(valor.get("acaoRapida").toString()));
            medida.setObservacoes(valor.get("observacoes").toString());

            Intent intent = new Intent(this,editarActivity.class);
            intent.putExtra("medida",medida);
            startActivity(intent);

        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onDestroy(){
        helper.close();
        super.onDestroy();
    }
}

