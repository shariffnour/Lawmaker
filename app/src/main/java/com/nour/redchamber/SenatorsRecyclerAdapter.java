package com.nour.redchamber;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.nour.redchamber.models.Senators;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SenatorsRecyclerAdapter extends RecyclerView.Adapter<SenatorsRecyclerAdapter.ViewHolder>
        implements Filterable {
    private Context context;
    private List<Senators> senatorsList;
    private List<Senators> senatorsListFiltered;
    private final LayoutInflater layoutInflater;
    OnSenatorCardClickListener cardClickListener;

    public SenatorsRecyclerAdapter(Context context, List<Senators> senatorsList, OnSenatorCardClickListener cardClickListener) {
        this.context = context;
        this.senatorsList = senatorsList;
        this.senatorsListFiltered = senatorsList;
        this.cardClickListener = cardClickListener;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.senator_card, parent, false);
        return new ViewHolder(itemView, cardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Senators senator = senatorsListFiltered.get(position);
        holder.senatorName.setText(senator.getName());
        holder.senatorDistrict.setText(senator.getDistrict());
        Uri uri = Uri.parse(senator.getImage());
        Picasso.get().load(uri).into(holder.senatorImg);
    }

    @Override
    public int getItemCount() {
        return senatorsListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    senatorsListFiltered = senatorsList;
                } else {
                    List<Senators> filteredList = new ArrayList<>();
                    for (Senators row : senatorsList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getDistrict().toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    senatorsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = senatorsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                senatorsListFiltered = (ArrayList<Senators>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView senatorImg;
        TextView senatorName;
        TextView senatorDistrict;
        CardView senatorCard;
        OnSenatorCardClickListener cardClickListener;

        public ViewHolder(@NonNull View itemView, final OnSenatorCardClickListener cardClickListener) {
            super(itemView);
            this.senatorCard = itemView.findViewById(R.id.senator);
            this.senatorImg = itemView.findViewById(R.id.senatorImage);
            this.senatorName = itemView.findViewById(R.id.senatorName);
            this.senatorDistrict = itemView.findViewById(R.id.senatorDistrict);
            this.cardClickListener = cardClickListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardClickListener.onSenatorCardClick(getAdapterPosition());
                }
            });
        }
    }

    public interface OnSenatorCardClickListener{
        void onSenatorCardClick(int position);
    }
}
