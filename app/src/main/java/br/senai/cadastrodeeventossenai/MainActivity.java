package br.senai.cadastrodeeventossenai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import br.senai.cadastrodeeventossenai.database.EventoDAO;
import br.senai.cadastrodeeventossenai.modelo.Evento;

public class MainActivity extends AppCompatActivity {

    private ListView listaDeEventos;
    private ArrayAdapter<Evento> adapterEventos;
    private final int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Cadastro de Eventos");

        listaDeEventos = findViewById(R.id.ListView_eventos);
        definirOnClickListenerListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDAO.listar());
        listaDeEventos.setAdapter(adapterEventos);

    }


    private void definirOnClickListenerListView() {
        listaDeEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento eventoClicado = adapterEventos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastrarEventoActivity.class);
                intent.putExtra("eventoEdicao", eventoClicado);
                startActivity(intent);
            }
        });
    }

    public void onClickAgendarEvento(View v) {
        Intent intent = new Intent(MainActivity.this, CadastrarEventoActivity.class);
        startActivity(intent);
    }

    public void onClickLocais(View v) {
        Intent intent = new Intent(MainActivity.this, ListarLocalActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickCrescente(View v) {
        EditText textoBuscaNome = findViewById(R.id.editText_buscarNomeEvento);
        String palavra = textoBuscaNome.getText().toString();
        int opcao = 2;

        EventoDAO eventoDAO = new EventoDAO(getBaseContext());

        adapterEventos = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDAO.listarPesquisa(opcao, palavra));
        listaDeEventos.setAdapter(adapterEventos);

    }

    public void onClickDecrescente(View v) {
        EditText textoBuscaNome = findViewById(R.id.editText_buscarNomeEvento);
        String palavra = textoBuscaNome.getText().toString();
        int opcao = 1;

        EventoDAO eventoDAO = new EventoDAO(getBaseContext());

        adapterEventos = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDAO.listarPesquisa(opcao, palavra));
        listaDeEventos.setAdapter(adapterEventos);
    }

    public void onClickBuscarNome(View v) {
        EditText textoBuscaNome = findViewById(R.id.editText_buscarNomeEvento);
        String palavra = textoBuscaNome.getText().toString();
        int opcao = 2;

        EventoDAO eventoDAO = new EventoDAO(getBaseContext());

        adapterEventos = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDAO.listarPesquisa(opcao, palavra));
        listaDeEventos.setAdapter(adapterEventos);

    }


    public void onClickBuscarCidade(View v) {
        EditText textoBuscaNome = findViewById(R.id.editText_buscarNomeEvento);
        String palavra = textoBuscaNome.getText().toString();

        EditText textoBuscaCidade = findViewById(R.id.editText_buscarNomeCidade);
        String palavraCidade = textoBuscaCidade.getText().toString();
        int opcao = 2;

        EventoDAO eventoDAO = new EventoDAO(getBaseContext());

        adapterEventos = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDAO.listarPesquisaCidade(opcao, palavra, palavraCidade));
        listaDeEventos.setAdapter(adapterEventos);


    }


}
