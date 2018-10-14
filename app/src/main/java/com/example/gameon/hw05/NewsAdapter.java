/*
Assignment HW#5
NewsAdapter.java
Jarrod Norris, Andrew Schlesinger
 */
package com.example.gameon.hw05;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        ViewHolder vh;

        if ( convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_display, parent, false);
            vh = new ViewHolder();
            vh.image = convertView.findViewById(R.id.iv_url_to_image);
            vh.author = convertView.findViewById(R.id.tv_author);
            vh.title = convertView.findViewById(R.id.tv_title);
            vh.date = convertView.findViewById(R.id.tv_published);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }


        try {
            Picasso.get().load(news.getUrlToImage()).into(vh.image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        vh.author.setText(news.getAuthor());
        vh.title.setText(news.getTitle());
        vh.date.setText(news.getPublishedAt().substring(0,10));
        return convertView;
    }

    private static class ViewHolder {
        ImageView image;
        TextView author;
        TextView title;
        TextView date;
    }
}
