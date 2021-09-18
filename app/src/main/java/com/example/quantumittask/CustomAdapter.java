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

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder3>  {

    private Context context;
    private List<App> apps;

    public CustomAdapter(Context context, List<App> apps) {
        this.context = context;
        this.apps = apps;
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder{
        TextView textViewLayout;
        ImageView imageViewLayout;
        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);

            imageViewLayout = itemView.findViewById(R.id.imageView_layoutList);
            textViewLayout = itemView.findViewById(R.id.textVIew_Layoutlist);
        }
    }
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.layout_list_image,parent,false);
        return new CustomAdapter.MyViewHolder3(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {
        App app= apps.get(position);
        holder.textViewLayout.setText(app.getName());
        holder.imageViewLayout.setImageResource(app.getImage());
    }


    @Override
    public int getItemCount() {
        return apps.size();
    }



}
