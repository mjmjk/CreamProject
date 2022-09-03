package com.example.mjk.creamproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by 8305-53 on 2017-11-02.
 */

public class ImageAdapter extends ArrayAdapter<String> {
    ImageAdapter(Context context, String[] items){
        super(context,R.layout.image_layout,items);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater imageInfalter = LayoutInflater.from(getContext());
        View view = imageInfalter.inflate(R.layout.image_layout,parent,false);
        String item = getItem(position);
        ImageView imageView= (ImageView)view.findViewById(R.id.imageView);
        imageView.setImageResource(R.mipmap.medal2);
        return view;

    }

}