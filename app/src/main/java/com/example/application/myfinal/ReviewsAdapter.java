package com.example.application.myfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class ReviewsAdapter extends BaseAdapter {
    List<Movies> moviesList ;
    Context context;

    public ReviewsAdapter(Context context, List<Movies> mov) {
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
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.review_item, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.author.setText(moviesList.get(position).getReviewAuthor());
        viewHolder.content.setText(moviesList.get(position).getReviewContent());
        return view;
    }
    public class ViewHolder {
        TextView author,content;

        public ViewHolder(View view) {
            author = (TextView) view.findViewById(R.id.author);
            content = (TextView) view.findViewById(R.id.content);
        }
    }
}
