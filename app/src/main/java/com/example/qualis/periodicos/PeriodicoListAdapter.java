package com.example.qualis.periodicos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.qualis.R;

import java.util.List;

public class PeriodicoListAdapter extends RecyclerView.Adapter<PeriodicoListAdapter.PeriodicoViewHolder> {

    private final LayoutInflater mInflater;
    private List<Periodico> mPeriodicos; // Cached copy of Periodicos

    PeriodicoListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public PeriodicoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PeriodicoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PeriodicoViewHolder holder, int position) {
        if (mPeriodicos != null) {
            Periodico current = mPeriodicos.get(position);
            holder.issnItemView.setText(current.getIssn());
            holder.nomeItemView.setText(current.getNome());
            holder.extratoItemView.setText(current.getExtratoCapes  ());
        } else {
            // Covers the case of data not being ready yet.
            holder.issnItemView.setText("No Periodico");
            holder.nomeItemView.setText("No Periodico");
            holder.extratoItemView.setText("No Periodico");
        }
    }

    void setPeriodicos(List<Periodico> periodicos){
        mPeriodicos = periodicos;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mPeriodicos has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mPeriodicos != null)
            return mPeriodicos.size();
        else return 0;
    }

    class PeriodicoViewHolder extends RecyclerView.ViewHolder {
        private final TextView issnItemView;
        private final TextView nomeItemView;
        private final TextView extratoItemView;

        private PeriodicoViewHolder(View itemView) {
            super(itemView);
            issnItemView = itemView.findViewById(R.id.textViewIssn);
            nomeItemView = itemView.findViewById(R.id.textViewNome);
            extratoItemView = itemView.findViewById(R.id.textViewExtrato);
        }
    }
}
