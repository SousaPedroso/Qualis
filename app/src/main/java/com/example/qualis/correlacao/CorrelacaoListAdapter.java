package com.example.qualis.correlacao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qualis.R;

import java.util.List;

public class CorrelacaoListAdapter extends RecyclerView.Adapter<CorrelacaoListAdapter.CorrelacaoViewHolder> {
    private final LayoutInflater mInflater;
    private List<Correlacao> mCorrelacoes; // Cached copy of Correlacoes

    CorrelacaoListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public CorrelacaoListAdapter.CorrelacaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item_correlacao, parent, false);
        return new CorrelacaoListAdapter.CorrelacaoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CorrelacaoListAdapter.CorrelacaoViewHolder holder, int position) {
        if (mCorrelacoes != null) {
            Correlacao current = mCorrelacoes.get(position);
            holder.issnItemView.setText(current.getIssn());
            holder.areaItemVIew.setText(current.getArea());
            holder.nomePeriodicoItemView.setText(current.getNomePeriodico());
            holder.extratoItemView.setText(current.getExtratoCapes());
        } else {
            // Covers the case of data not being ready yet.
            holder.issnItemView.setText("No Correlacao");
            holder.areaItemVIew.setText("No Correlacao");
            holder.nomePeriodicoItemView.setText("No Correlacao");
            holder.extratoItemView.setText("No Correlacao");
        }
    }

    void setCorrelacoes(List<Correlacao> correlacoes){
        mCorrelacoes = correlacoes;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mCorrelacoes has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCorrelacoes != null)
            return mCorrelacoes.size();
        else return 0;
    }

    class CorrelacaoViewHolder extends RecyclerView.ViewHolder {
        private final TextView issnItemView;
        private final TextView areaItemVIew;
        private final TextView nomePeriodicoItemView;
        private final TextView extratoItemView;

        private CorrelacaoViewHolder(View itemView) {
            super(itemView);
            issnItemView = itemView.findViewById(R.id.textViewIssnCorrelacao);
            areaItemVIew = itemView.findViewById(R.id.textViewArea);
            nomePeriodicoItemView = itemView.findViewById(R.id.textViewNomePeriodico);
            extratoItemView = itemView.findViewById(R.id.textViewExtratoCorrelacao);
        }
    }
}
