package projetosistemasmoveis.rastreador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DispositivoCardViewAdapter extends RecyclerView.Adapter<DispositivoCardViewAdapter.ViewHolder> {

    private List<String> mNomes;
    private List<String> mStatus;

    public DispositivoCardViewAdapter(List<String> Nomes, List<String> Status) {
        mNomes = Nomes;
        mStatus = Status;
    }

    public DispositivoCardViewAdapter() {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dispositivo_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.NomeTextView.setText(mNomes.get(position));
        holder.StatusTextView.setText(mStatus.get(position));
    }

    @Override
    public int getItemCount() {
        return mNomes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView NomeTextView;
        public TextView StatusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NomeTextView = itemView.findViewById(R.id.TextoNome);
            StatusTextView = itemView.findViewById(R.id.TextoStatus);
        }
    }
}