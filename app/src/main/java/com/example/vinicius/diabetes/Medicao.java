package com.example.vinicius.diabetes;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by vinicius on 15/08/17.
 */

public class Medicao implements Serializable {
    private String data;
    private String hora;
    private int valorMedido;
    private int nph;
    private int acaoRapida;
    private String observacoes;
    private int id;




    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getValorMedido() {
        return valorMedido;
    }

    public void setValorMedido(int valorMedido) {
        this.valorMedido = valorMedido;
    }

    public int getNph() {
        return nph;
    }

    public void setNph(int nph) {
        this.nph = nph;
    }

    public int getAcaoRapida() {
        return acaoRapida;
    }

    public void setAcaoRapida(int acaoRapida) {
        this.acaoRapida = acaoRapida;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
