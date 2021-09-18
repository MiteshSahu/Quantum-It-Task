package com.example.quantumittask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class MySliderAdapter extends SliderViewAdapter<MySliderAdapter.MyViewHolder> {

    List<Integer> imageList;
    MySliderAdapter(List<Integer> list){
        this.imageList =list;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_image_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
            viewHolder.imageViewSlider.setImageResource(imageList.get(position));
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    class MyViewHolder extends SliderViewAdapter.ViewHolder{

        ImageView imageViewSlider ;
        public MyViewHolder(View itemView) {
            super(itemView);

            imageViewSlider= itemView.findViewById(R.id.imageViewSlider);
        }
    }
}
