package br.senai.cadastrodeeventossenai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.format.DateTimeFormatter;

import br.senai.cadastrodeeventossenai.database.EventoDAO;
import br.senai.cadastrodeeventossenai.database.LocalDAO;
import br.senai.cadastrodeeventossenai.modelo.Evento;
import br.senai.cadastrodeeventossenai.modelo.Local;

public class CadastrarEventoActivity extends AppCompatActivity {

    private int id = 0;
    private Spinner spinnerLocais;
    private ArrayAdapter<Local> locaisAdapter;
    private EditText editTextNome;
    private EditText editTextData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_evento);
        setTitle("Agendamento de Eventos");
        spinnerLocais = findViewById(R.id.spinner_locais);
        editTextNome = findViewById(R.id.editText_nome);
        editTextData = findViewById(R.id.editText_data);
        editTextData.addTextChangedListener(Mask.insert("##/##/####", editTextData));
        carregarLocais();
        carregarEvento();

        Button btn_salvar = findViewById(R.id.btn_salvar);
        btn_salvar.setOnClickListener(new View.OnClickListener() {

            public String validar() {
                String campoVazio = "";

                if (editTextNome.getText().toString().equals("")) {
                    campoVazio = "Campo Nome é obrigatório. ";
                    editTextNome.setError("Este campo é obrigatório");
                }

                if (editTextData.getText().toString().equals("")) {
                    campoVazio = "Campo Data é obrigatório. ";
                    editTextData.setError("Este campo é obrigatório");
                }

                return campoVazio;
            }

            @Override
            public void onClick(View v) {

                String nome = editTextNome.getText().toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String data = String.format(editTextData.getText().toString(), formatter);
                //Local local = (Local) spinnerLocais.getSelectedItem();
                int posicaoLocal = spinnerLocais.getSelectedItemPosition();
                Local local = (Local) locaisAdapter.getItem(posicaoLocal);
                Evento evento = new Evento(id, nome, data, local);

                String campoVazio = validar();
                if (campoVazio.equals("")) {
                    EventoDAO eventoDAO = new EventoDAO(getBaseContext());
                    eventoDAO.salvar(evento);
                    Toast.makeText(CadastrarEventoActivity.this, "Evento Agendado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CadastrarEventoActivity.this, "Erro ao salvar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void carregarLocais() {
        LocalDAO localDAO = new LocalDAO(getBaseContext());
        locaisAdapter = new ArrayAdapter<Local>(CadastrarEventoActivity.this,
                android.R.layout.simple_spinner_item,
                localDAO.listar());
        spinnerLocais.setAdapter(locaisAdapter);
    }

    private void carregarEvento() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().get("eventoEdicao") != null) {
            Evento evento = (Evento) intent.getExtras().get("eventoEdicao");
            editTextNome.setText(evento.getNome());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            editTextData.setText(String.format(evento.getData(), formatter));
            int posicaoLocal = obterPosicaoLocal(evento.getLocal());
            spinnerLocais.setSelection(posicaoLocal);
            id = evento.getId();
        }
    }

    private int obterPosicaoLocal(Local local) {
        for (int posicao = 0; posicao < locaisAdapter.getCount(); posicao++) {
            if (locaisAdapter.getItem(posicao).getId() == local.getId()) {
                return posicao;
            }
        }
        return 0;
    }

    public void onClickVoltar(View v) {
        finish();
    }


    public void onClickExcluir(View v) {
        EditText editTextNome = findViewById(R.id.editText_nome);
        EditText editTextData = findViewById(R.id.editText_data);
        String nome = editTextNome.getText().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = String.format(editTextData.getText().toString(), formatter);
        Local local = (Local) spinnerLocais.getSelectedItem();
        Evento evento = new Evento(id, nome, data, local);
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        boolean excluiu = eventoDAO.excluir(evento);
        if (excluiu) {
            finish();
            Toast.makeText(CadastrarEventoActivity.this, "Agendamento excluído com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(CadastrarEventoActivity.this, "Erro ao excluir", Toast.LENGTH_SHORT).show();
        }
    }
}