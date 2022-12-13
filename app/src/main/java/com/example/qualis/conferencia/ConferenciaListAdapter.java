package com.example.qualis.conferencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qualis.R;

import java.util.List;

public class ConferenciaListAdapter extends RecyclerView.Adapter<ConferenciaListAdapter.ConferenciaViewHolder> {

    private final LayoutInflater mInflater;
    private List<Conferencia> mConferencias; // Cached copy of Conferencias

    ConferenciaListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public ConferenciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_conferencia_item, parent, false);
        return new ConferenciaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConferenciaViewHolder holder, int position) {
        if (mConferencias != null) {
            Conferencia current = mConferencias.get(position);
            holder.siglaItemView.setText(current.getSigla());
            holder.nomeItemView.setText(current.getNome());
            holder.extratoItemView.setText(current.getExtratoCapes  ());
        } else {
            // Covers the case of data not being ready yet.
            holder.siglaItemView.setText("No Conferencia");
            holder.nomeItemView.setText("No Conferencia");
            holder.extratoItemView.setText("No Conferencia");
        }
    }

    void setConferencias(List<Conferencia> conferencias){
        mConferencias = conferencias;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mConferencias has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mConferencias != null)
            return mConferencias.size();
        else return 0;
    }

    class ConferenciaViewHolder extends RecyclerView.ViewHolder {
        private final TextView siglaItemView;
        private final TextView nomeItemView;
        private final TextView extratoItemView;

        private ConferenciaViewHolder(View itemView) {
            super(itemView);
            siglaItemView = itemView.findViewById(R.id.textViewSigla);
            nomeItemView = itemView.findViewById(R.id.textViewNome);
            extratoItemView = itemView.findViewById(R.id.textViewExtrato);
        }
    }
}
