package br.senai.sp.catlogodefilmes;

import android.widget.EditText;
import android.widget.RatingBar;

import br.senai.sp.modelo.Filme;

public class CadastroFilmeHelper {

    private EditText txtTitulo;
    private EditText txtDiretor;
    private EditText txtDataLancamento;
    private EditText txtDuracao;
    private EditText txtGenero;
    private RatingBar nota;
    private Filme filme;

    public CadastroFilmeHelper(CadastroFilmeActivity activity){

         txtTitulo = activity.findViewById(R.id.txt_titulo);
         txtDiretor = activity.findViewById(R.id.txt_diretor);
         txtDataLancamento = activity.findViewById(R.id.txt_data_lancamento);
         txtDuracao = activity.findViewById(R.id.txt_duracao);
         txtGenero = activity.findViewById(R.id.txt_genero);
         nota = activity.findViewById(R.id.rate_nota);
         filme = new Filme();

    }

    public Filme getFilme(){

        filme.setDataLancamento(txtDataLancamento.getText().toString());
        filme.setDiretor(txtDiretor.getText().toString());
        filme.setDuracao(txtDuracao.getText().toString());
        filme.setGenero(txtGenero.getText().toString());
        filme.setTitulo(txtTitulo.getText().toString());
        filme.setNota(nota.getProgress());

        return filme;

    }

    public void preecherFormulario(Filme filme) {

        txtTitulo.setText(filme.getTitulo());
        txtGenero.setText(filme.getGenero());
        txtDuracao.setText(filme.getDuracao());
        txtDiretor.setText(filme.getDiretor());
        txtDataLancamento.setText(filme.getDataLancamento());
        nota.setProgress(filme.getNota());
        this.filme = filme;
    }
}
