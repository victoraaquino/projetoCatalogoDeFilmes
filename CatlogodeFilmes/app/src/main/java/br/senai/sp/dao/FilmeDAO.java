package br.senai.sp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.senai.sp.modelo.Filme;

public class FilmeDAO extends SQLiteOpenHelper{

    public FilmeDAO(Context context) {
        super(context, "db_filme", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE tbl_filme(" +
                "id INTEGER PRIMARY KEY," +
                " titulo TEXT NOT NULL," +
                " diretor TEXT NOT NULL," +
                " genero TEXT NOT NULL," +
                " data_lancamento TEXT NOT NULL," +
                " duracao TEXT NOT NULL," +
                " nota INTEGER NOT NULL)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void salvar(Filme filme) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getContentValues(filme);

        db.insert("tbl_filme", null, dados);
    }

    @NonNull
    private ContentValues getContentValues(Filme filme) {
        ContentValues dados = new ContentValues();

        dados.put("titulo", filme.getTitulo());
        dados.put("diretor", filme.getDiretor());
        dados.put("genero", filme.getGenero());
        dados.put("data_lancamento", filme.getDataLancamento());
        dados.put("duracao", filme.getDuracao());
        dados.put("nota", filme.getNota());
        return dados;
    }

    public void excluir(Filme filme){
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {String.valueOf(filme.getId())};

        db.delete("tbl_filme", "id = ?", params);
    }

    public List<Filme> getFilmes() {

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM tbl_filme";

        Cursor c = db.rawQuery(sql, null);

        List<Filme> filmes = new ArrayList<>();
        while (c.moveToNext()){
            Filme filme = new Filme();
            filme.setId(c.getInt(c.getColumnIndex("id")));
            filme.setTitulo(c.getString(c.getColumnIndex("titulo")));
            filme.setDiretor(c.getString(c.getColumnIndex("diretor")));
            filme.setGenero(c.getString(c.getColumnIndex("genero")));
            filme.setDataLancamento(c.getString(c.getColumnIndex("data_lancamento")));
            filme.setDuracao(c.getString(c.getColumnIndex("duracao")));
            filme.setNota(c.getInt(c.getColumnIndex("nota")));
            filmes.add(filme);
        }
        return  filmes;
    }

    public void atualizar(Filme filme) {

        SQLiteDatabase db = getReadableDatabase();

        String[] params = {String.valueOf(filme.getId())};
        ContentValues dados = getContentValues(filme);

        db.update("tbl_filme", dados, "id = ?", params);

    }
}
