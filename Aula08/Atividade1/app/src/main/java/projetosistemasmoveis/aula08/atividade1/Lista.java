package projetosistemasmoveis.aula08.atividade1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class Lista extends Fragment {

    private ListView lvPlanetas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_lista, null );

        lvPlanetas = (ListView) view.findViewById( R.id.lvPlanetas );

        lvPlanetas.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long id) {

                tratarSelecao( position );
            }
        } );
        return view;
    }



    protected void tratarSelecao( int position ) {
        String descricao = lvPlanetas.getItemAtPosition( position ).toString();

        Configuration config = getResources().getConfiguration();

        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            FragmentManager fm = getFragmentManager();

            Imagem fd = (Imagem) fm.findFragmentById( R.id.ImagemFragment);

            fd.setConteudo( position, descricao );

        } else {
            Imagem fragImagem = new Imagem();

            Bundle args = new Bundle();
            args.putInt("pos", position);
            args.putString( "descricao", descricao );
            fragImagem.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(android.R.id.content, fragImagem);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
    } //fim do método tratarSelecao




}