package com.example.trucksharing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList Name_id,PickupDate,DropTime;

    private static OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.orders,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.Name_id.setText(String.valueOf(Name_id.get(position)));
        holder.PickupDate.setText("\n\nPickup Date: "+String.valueOf(PickupDate.get(position)));
        holder.DropTime.setText("Drop Time: "+String.valueOf(DropTime.get(position)));
    }

    public MyAdapter(Context context, ArrayList name_id, ArrayList pickupTime, ArrayList dropTime) {
        this.context = context;

        Name_id = name_id;
        PickupDate=pickupTime;
        DropTime = dropTime;

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {

        return Name_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name_id,PickupDate,DropTime;
        ImageButton imageButton;

        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name_id=itemView.findViewById(R.id.name);
            imageButton=itemView.findViewById(R.id.imageButton);
            PickupDate=itemView.findViewById(R.id.FirstTExt);
            DropTime=itemView.findViewById(R.id.SecondTExt);
            cardView=itemView.findViewById(R.id.cardid);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

}
