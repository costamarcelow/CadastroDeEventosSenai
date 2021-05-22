package br.senai.cadastrodeeventossenai.database.entity;

import android.provider.BaseColumns;

public final class EventoEntity implements BaseColumns {

    public static final String TABLE_NAME = "evento";
    public static final String COLUMN_NAME_DATA = "data";
    public static final String COLUMN_NAME_ID_LOCAL = "idlocal";
    public static String COLUMN_NAME_NOME = "nome";
    private EventoEntity() {
    }

}
