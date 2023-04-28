package projetosistemasmoveis.aula08.atividade1;

import android.app.Fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Imagem extends Fragment {
    private int imagens_id[] = {
            R.drawable.mercurio,
            R.drawable.venus,
            R.drawable.terra,
            R.drawable.marte,
            R.drawable.jupter,
            R.drawable.saturno,
            R.drawable.urano,
            R.drawable.neturno
    };

    private ImageView imPlaneta;
    private TextView tvDescricao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedIntanceState) {

        View view = inflater.inflate( R.layout.fragment_imagem, null );
        imPlaneta = (ImageView) view.findViewById(R.id.imPlaneta);
        tvDescricao = (TextView) view.findViewById(R.id.tvDescricao);
        imPlaneta.setScaleType(ImageView.ScaleType.FIT_XY);

        Configuration config = getResources().getConfiguration();
        if (config.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            String descricao = getArguments().getString("descricao");
            int position = getArguments().getInt("pos");
            imPlaneta.setImageResource(imagens_id[position]);
            tvDescricao.setText(descricao);
        }
        return view;
    }//fim do método onCreateView
    public void setConteudo( int pos, String descricao ) {
        imPlaneta.setImageResource( imagens_id[ pos ] );
        tvDescricao.setText( descricao );
    }//fim do método setConteudo
}