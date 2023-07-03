package projetosistemasmoveis.rastreador.TelaDispositivos;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import projetosistemasmoveis.rastreador.Objetos.ObjDispositivoStatus;
import projetosistemasmoveis.rastreador.R;

public class DispositivoCardViewAdapter extends RecyclerView.Adapter<DispositivoCardViewAdapter.ViewHolder> {

    private ObjDispositivoStatus[] dispositivos;
    private OnItemClickListener onItemClickListener;
    private OnMenuClickListener onMenuClickListener;

    public DispositivoCardViewAdapter(ObjDispositivoStatus[] dispositivos) {
        this.dispositivos = dispositivos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dispositivo_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjDispositivoStatus dispositivo = dispositivos[position];
        holder.NomeTextView.setText(dispositivo.getNome());

        if (dispositivo.getOnline()) {
            holder.StatusTextView.setText("Online");
        }else{
            holder.StatusTextView.setText("Offline");
        }
        final int itemPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(itemPosition, dispositivo);
                }
            }
        });

        // OnClickListener para o botão no CardView
        holder.BotaoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMenuClickListener != null) {
                    onMenuClickListener.onMenuClick(itemPosition, dispositivo, view);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        if (dispositivos != null) {
            return dispositivos.length;
        } else {
            return 0;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView NomeTextView;
        public TextView StatusTextView;
        public ImageButton BotaoMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NomeTextView = itemView.findViewById(R.id.TextoNome);
            StatusTextView = itemView.findViewById(R.id.TextoStatus);
            BotaoMenu = itemView.findViewById(R.id.BotaoMenu);
        }
    }

    // Define a interface OnItemClickListener
    public interface OnItemClickListener {
        void onItemClick(int position, ObjDispositivoStatus dispositivo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnMenuClickListener {
        void onMenuClick(int position, ObjDispositivoStatus dispositivo, View view);
    }

    public void setOnMenuClickListener(OnMenuClickListener listener) {
        this.onMenuClickListener = listener;
    }
}