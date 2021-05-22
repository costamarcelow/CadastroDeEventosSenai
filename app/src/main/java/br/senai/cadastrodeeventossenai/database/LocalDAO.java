package br.senai.cadastrodeeventossenai.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.senai.cadastrodeeventossenai.database.entity.LocalEntity;
import br.senai.cadastrodeeventossenai.modelo.Local;

public class LocalDAO {

    private final String SQL_LISTAR_TODOS = "SELECT * FROM " + LocalEntity.TABLE_NAME;
    private final DBGateway dbGateway;

    public LocalDAO(Context context) {
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Local local) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocalEntity.COLUMN_NAME_DESCRICAO, local.getDescricao());
        contentValues.put(LocalEntity.COLUMN_NAME_BAIRRO, local.getBairro());
        contentValues.put(LocalEntity.COLUMN_NAME_CIDADE, local.getCidade());
        contentValues.put(LocalEntity.COLUMN_NAME_CAPACIDADE, local.getCapacidade());
        if (local.getId() > 0) {
            return dbGateway.getDatabase().update(LocalEntity.TABLE_NAME,
                    contentValues,
                    LocalEntity._ID + "=?",
                    new String[]{String.valueOf(local.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(LocalEntity.TABLE_NAME,
                null, contentValues) > 0;
    }

    public boolean excluir(Local local) {
        return dbGateway.getDatabase().delete(LocalEntity.TABLE_NAME,
                LocalEntity._ID + "=?",
                new String[]{String.valueOf(local.getId())}) > 0;
    }

    public List<Local> listar() {
        List<Local> locais = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(LocalEntity._ID));
            String descricao = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_DESCRICAO));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));
            locais.add(new Local(id, descricao, bairro, cidade, capacidade));
        }
        cursor.close();
        return locais;
    }
}
