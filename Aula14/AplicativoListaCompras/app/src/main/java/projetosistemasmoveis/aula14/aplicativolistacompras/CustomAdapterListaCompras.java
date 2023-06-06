package projetosistemasmoveis.aula14.aplicativolistacompras;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterListaCompras extends ArrayAdapter<ListaCompra> {

    private ArrayList<ListaCompra> listaCompras;

    private int selectedItem = -1; // Índice do item selecionado

    public CustomAdapterListaCompras(Context context, ArrayList<ListaCompra> listaCompras) {
        super(context, 0, listaCompras);
        this.listaCompras = listaCompras;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        ListaCompra listaCompra = listaCompras.get(position);

        TextView textView1 = convertView.findViewById(android.R.id.text1);
        TextView textView2 = convertView.findViewById(android.R.id.text2);

        textView1.setText(listaCompra.getNome());
        textView2.setText("Data : " + listaCompra.getDataCriacao());

        // Define o estado selecionado para o item
        if (position == selectedItem) {
            convertView.setBackgroundColor(getContext().getResources().getColor(android.R.color.holo_green_light));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
        }

        return convertView;
    }

    // Método para definir o item selecionado
    public void setSelectedItem(int position) {
        if(position == selectedItem){
            position = -1;
        }
        selectedItem = position;
        notifyDataSetChanged();
    }

    public int getSelectedItem(){
        return selectedItem;
    }

}