package com.example.quantumittask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CircularAdapter extends RecyclerView.Adapter<CircularAdapter.MyViewHolder2>  {

    private Context context;
    private List<App> apps;

    public CircularAdapter(Context context, List<App> apps) {
        this.context = context;
        this.apps = apps;
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder{
        TextView circularText;
        ImageView circularImageView;
        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);

            circularImageView = itemView.findViewById(R.id.imageViewCircular);
            circularText = itemView.findViewById(R.id.textViewCircular);
        }
    }
    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.circular_image_list,parent,false);
        return new MyViewHolder2(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
            App app= apps.get(position);
            holder.circularText.setText(app.getName());
            holder.circularImageView.setImageResource(app.getImage());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }



}