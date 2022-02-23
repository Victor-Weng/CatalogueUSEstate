package com.example.catalogueusestate.Data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catalogueusestate.Activities.DetailsActivity;
import com.example.catalogueusestate.Model.Maison;
import com.example.catalogueusestate.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MaisonRecyclerViewAdapter extends RecyclerView.Adapter<MaisonRecyclerViewAdapter.ViewHolder>
{
    private Context context;
    private List<Maison> maisonList;

    public MaisonRecyclerViewAdapter(Context context, List<Maison> maisonList)
    {
        this.context = context;
        this.maisonList = maisonList;
    }

    @NonNull
    @Override
    public MaisonRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.house,parent,false);

        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull MaisonRecyclerViewAdapter.ViewHolder holder, int position)
    {
        Maison maison=maisonList.get(position);
        String Href=maison.getHref();
        holder.List_Price.setText(maison.getList_price());
        holder.Address.setText(maison.getLine());
        holder.Status.setText(maison.getStatus());
        holder.Bed.setText("Beds: " + Integer.toString(maison.getBeds()));
        holder.Bath.setText("Baths: " +Integer.toString(maison.getBaths()));


        Picasso.get()
                .load(Href)
                .fit()
                .placeholder(android.R.drawable.ic_btn_speak_now)
                .into(holder.photo);

    }
    @Override
    public int getItemCount() { return maisonList.size(); }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView List_Price,Address,Status,Bed,Bath;
        ImageView photo;

        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context=ctx;
            photo=itemView.findViewById(R.id.primaryphotoID);
            List_Price=itemView.findViewById((R.id.listpriceID));
            Address=itemView.findViewById((R.id.addressID));
            Status=itemView.findViewById((R.id.statusID));
            Bed=itemView.findViewById((R.id.bedID));
            Bath=itemView.findViewById((R.id.bathID));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Maison maison=maisonList.get(getAdapterPosition());
                    Intent intent=new Intent(context, DetailsActivity.class);
                    intent.putExtra("maison", maison);
                    ctx.startActivity(intent);


                }

            });
        }


        @Override
        public void onClick(View v) {

        }
    }
}
