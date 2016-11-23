package com.example.application.myfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win8 on 23/11/2016.
 */

public class MyAdapter extends BaseAdapter {
    List<Movies> moviesList = new ArrayList<>();
    Context context;

    public MyAdapter(Context context, List<Movies> mov){
        this.context = context;
        moviesList = mov;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return moviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.movie_list_item, parent , false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Picasso.with(context).load(moviesList.get(position).getMoviePoster()).into(viewHolder.imageView);
        return view;
    }
    public class ViewHolder{
        ImageView imageView;
        public ViewHolder(View view){
            imageView = (ImageView) view.findViewById(R.id.movie_item);
        }
    }
}
