package br.senai.cadastrodeeventossenai.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.senai.cadastrodeeventossenai.database.entity.EventoEntity;
import br.senai.cadastrodeeventossenai.database.entity.LocalEntity;
import br.senai.cadastrodeeventossenai.modelo.Evento;
import br.senai.cadastrodeeventossenai.modelo.Local;

public class EventoDAO {
    private final String SQL_LISTAR_TODOS = "SELECT evento._id, nome, data, idlocal, descricao, bairro, cidade, capacidade FROM "
            + EventoEntity.TABLE_NAME + " INNER JOIN " + LocalEntity.TABLE_NAME + " ON " +
            EventoEntity.COLUMN_NAME_ID_LOCAL + " = " +
            LocalEntity.TABLE_NAME + "." + LocalEntity._ID;
    private final String vazia = "";

    private final DBGateway dbGateway;

    public EventoDAO(Context context) {
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Evento evento) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, evento.getNome());
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, String.valueOf(evento.getData()));
        contentValues.put(EventoEntity.COLUMN_NAME_ID_LOCAL, evento.getLocal().getId());

        if (evento.getId() > 0) {
            return dbGateway.getDatabase().update(EventoEntity.TABLE_NAME,
                    contentValues,
                    EventoEntity._ID + "=?",
                    new String[]{String.valueOf(evento.getId())}) > 0;
        }
        return dbGateway.getDatabase().insert(EventoEntity.TABLE_NAME,
                null, contentValues) > 0;
    }

    public boolean excluir(Evento evento) {
        return dbGateway.getDatabase().delete(EventoEntity.TABLE_NAME,
                EventoEntity._ID + "=?",
                new String[]{String.valueOf(evento.getId())}) > 0;
    }

    public List<Evento> listar() {
        List<Evento> eventos = new ArrayList<>();
        Cursor cursor = dbGateway.getDatabase().rawQuery(SQL_LISTAR_TODOS, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String descricao = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_DESCRICAO));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));

            Local local = new Local(idLocal, descricao, bairro, cidade, capacidade);
            eventos.add(new Evento(id, nome, data, local));
        }
        cursor.close();
        return eventos;
    }


    public String orientacao(int opcao) {
        if (opcao == 1) {
            return "DESC";
        } else if (opcao == 2) {
            return "ASC";
        }
        return "ASC";
    }

    public List<Evento> listarPesquisa(int opcao, String pesquisa) {
        List<Evento> eventosPesquisa = new ArrayList<>();


        String SELECTION = "SELECT evento._id, nome, data, idlocal, descricao, bairro, cidade, capacidade FROM "
                + EventoEntity.TABLE_NAME + " INNER JOIN " + LocalEntity.TABLE_NAME + " ON " +
                EventoEntity.COLUMN_NAME_ID_LOCAL + " = " +
                LocalEntity.TABLE_NAME + "." + LocalEntity._ID +
                " WHERE " + EventoEntity.COLUMN_NAME_NOME + " LIKE " + "'%" + pesquisa + "%'" +
                " ORDER BY " + EventoEntity.COLUMN_NAME_NOME + " " + orientacao(opcao);

        Cursor cursor = dbGateway.getDatabase().rawQuery(
                SELECTION, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String descricao = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_DESCRICAO));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));


            Local local = new Local(idLocal, descricao, bairro, cidade, capacidade);
            eventosPesquisa.add(new Evento(id, nome, data, local));
        }
        cursor.close();
        return eventosPesquisa;

    }

    public List<Evento> listarPesquisaCidade(int opcao, String pesquisa, String pesquisaCidade) {
        List<Evento> eventosPesquisa = new ArrayList<>();


        String SELECTION = "SELECT evento._id, nome, data, idlocal, descricao, bairro, cidade, capacidade FROM "
                + EventoEntity.TABLE_NAME + " INNER JOIN " + LocalEntity.TABLE_NAME + " ON " +
                EventoEntity.COLUMN_NAME_ID_LOCAL + " = " +
                LocalEntity.TABLE_NAME + "." + LocalEntity._ID +
                " WHERE " + EventoEntity.COLUMN_NAME_NOME + " LIKE " + "'%" + pesquisa + "%'" +
                " AND " + LocalEntity.COLUMN_NAME_CIDADE + " LIKE " + "'%" + pesquisaCidade + "%'" +
                " ORDER BY " + EventoEntity.COLUMN_NAME_NOME + " " + orientacao(opcao);

        Cursor cursor = dbGateway.getDatabase().rawQuery(
                SELECTION, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            int idLocal = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String descricao = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_DESCRICAO));
            String bairro = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocalEntity.COLUMN_NAME_CAPACIDADE));


            Local local = new Local(idLocal, descricao, bairro, cidade, capacidade);
            eventosPesquisa.add(new Evento(id, nome, data, local));
        }
        cursor.close();
        return eventosPesquisa;

    }

}
