package com.example.vinicius.diabetes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by vinicius on 13/08/17.
 */

public class inicioActivity extends Activity {


    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.inicio);
    }
    public void selecionarOpcao(View view){
        switch (view.getId()){
            case R.id.nova_medicao:
                startActivity(new Intent(this,nova_medicaoActivity.class));
                break;
            case R.id.medicoes:
                startActivity(new Intent(this, lista_medicoesActivity.class));
                break;
        }
    }
}
