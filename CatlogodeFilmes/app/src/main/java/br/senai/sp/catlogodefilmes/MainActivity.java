package br.senai.sp.catlogodefilmes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.sax.StartElementListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.senai.sp.adapter.FilmesAdapter;
import br.senai.sp.dao.FilmeDAO;
import br.senai.sp.modelo.Filme;

public class MainActivity extends AppCompatActivity {

    private ListView listaFilmes;
    private ImageButton btnNovoFilme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // *** Associa o objeto ListView do java à View ListView do Layout xml
        listaFilmes = findViewById(R.id.list_filmes);

        btnNovoFilme = findViewById(R.id.bt_novo_filme);

        // *** Ação do botão novo
        btnNovoFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastroFilmes = new Intent(MainActivity.this, CadastroFilmeActivity.class);
                startActivity(cadastroFilmes);
            }
        });

        // *** DEFINIÇÃO DE UM MENU CONTEXTO PARA A LISTVIEW (lista de filmes)
        registerForContextMenu(listaFilmes);

        listaFilmes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Filme filme = (Filme) listaFilmes.getItemAtPosition(position);

                Intent cadastro = new Intent(MainActivity.this, CadastroFilmeActivity.class);
                cadastro.putExtra("filme", filme);
                startActivity(cadastro);
            }
        });


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_context_lista_filmes, menu);

        /*MenuItem deletar = menu.add("Excluir");
        MenuItem editar = menu.add("Editar");
        MenuItem visualizar = menu.add("Visualizar");

        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(MainActivity.this, "Deletar", Toast.LENGTH_LONG).show();
                return false;
            }
        });*/

        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(final MenuItem item) {

        ContextMenu.ContextMenuInfo menuInfo = item.getMenuInfo();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Filme filme = (Filme) listaFilmes.getItemAtPosition(info.position);
        final FilmeDAO dao = new FilmeDAO(MainActivity.this);


        new AlertDialog.Builder(this).setTitle("Apagar filme").setMessage("Deseja apagar " + filme.getTitulo() + " ?").setPositiveButton("sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this, filme.getTitulo() + " excluido", Toast.LENGTH_LONG).show();
                    dao.excluir(filme);
                    dao.close();
                    carregarLista();
            }
        }).setNegativeButton("não", null).show();

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {

        carregarLista();
        super.onResume();

    }

    private void carregarLista(){
        //Abrir o banco de dados
        //Rodar uma query de consulta
        //Retornar um arraylist

        FilmeDAO dao = new FilmeDAO(this);
        List<Filme> filmes = dao.getFilmes();
        dao.close();

        // *** Definimos um adapter pra carregar os dados da ArrayList na ListView
        // *** utilizando um layoutr pronto (simple_list_item_1)
        // *** ArrayAdapter<Filme> listaFilmesAdapter = new ArrayAdapter<Filme>(this, android.R.layout.simple_list_item_1, filmes);

        FilmesAdapter adapter = new FilmesAdapter(this, filmes);
        listaFilmes.setAdapter(adapter);

        // *** Injetamos o adapter no objeto ListView
        // *** listaFilmes.setAdapter(listaFilmesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
