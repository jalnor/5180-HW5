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

        if ( convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_display, parent, false);
        }

        ImageView image = convertView.findViewById(R.id.iv_url_to_image);
        TextView author = convertView.findViewById(R.id.tv_author);
        TextView title = convertView.findViewById(R.id.tv_title);
        TextView date = convertView.findViewById(R.id.tv_published);

        Picasso.get().load(news.getUrlToImage()).into(image);
        author.setText(news.getAuthor());
        title.setText(news.getTitle());
        date.setText(news.getPublishedAt());
        return convertView;
    }

    private static class ViewHolder {

    }
}
