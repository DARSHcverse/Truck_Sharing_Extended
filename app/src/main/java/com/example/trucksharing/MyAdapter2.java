package com.example.trucksharing;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {

    private Context context;
    private ArrayList Vechile_id,PickupDate,DropTime,ID;

    private MyAdapter2.OnItemClickListener onItemClickListener;

    public MyAdapter2(Home home, ArrayList<String> vechile_id, ArrayList<String> pickupDate, ArrayList<String> dropTime, ArrayList<Integer> id) {

        this.context=home;

        Vechile_id = vechile_id;
        PickupDate=pickupDate;
        DropTime = dropTime;
        ID=id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.orders,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.Vechile_id.setText(String.valueOf(Vechile_id.get(position)));
        holder.PickupDate.setText("\n\nPickup Date: "+String.valueOf(PickupDate.get(position)));
        holder.DropTime.setText("Drop Time: "+String.valueOf(DropTime.get(position)));

    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MyAdapter2.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return ID.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Vechile_id,PickupDate,DropTime;
        ImageButton imageButton;

        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Vechile_id=itemView.findViewById(R.id.name);
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
