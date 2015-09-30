package com.rcdvl.example.searchabletest;

import com.rcdvl.example.searchabletest.model.Book;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by renan on 9/30/15.
 */
public class MyAdapter extends CursorRecyclerViewAdapter<MyAdapter.ViewHolder> {
    public MyAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        Book book = new Book();
        book.loadFromCursor(cursor);

        viewHolder.bookName.setText(book.name);
        viewHolder.authorName.setText("by " + book.author.firstName + " " + book.author.lastName);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_book, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        TextView authorName;

        public ViewHolder(View itemView) {
            super(itemView);

            bookName = (TextView)itemView.findViewById(R.id.book_name);
            authorName = (TextView)itemView.findViewById(R.id.author_name);
        }
    }
}
