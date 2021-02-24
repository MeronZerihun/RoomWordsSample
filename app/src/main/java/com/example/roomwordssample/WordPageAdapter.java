package com.example.roomwordssample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WordPageAdapter extends PagedListAdapter<Word, WordPageAdapter.WordPageViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    int count = 0;

    public WordPageAdapter(Context context){
        super(DIFF_CALLBACK);
        mContext = context;
    }


    @NonNull
    @Override
    public WordPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(mContext);
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        Log.i("Creating card", (count++)+"");
        return new WordPageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WordPageViewHolder holder, int position) {
       Word current = getItem(position);
       holder.wordItemView.setText(current.getWord());


    }


    private static DiffUtil.ItemCallback<Word> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Word>() {

                @Override
                public boolean areItemsTheSame(Word old, Word newWord) {
                    return old.getId() == newWord.getId();
                }

                @Override
                public boolean areContentsTheSame(Word old, Word newWord) {
                    return old.equals(newWord);
                }
            };



    class WordPageViewHolder extends RecyclerView.ViewHolder{
        private final TextView wordItemView;

        public WordPageViewHolder(@NonNull View itemView) {
                super(itemView);
                wordItemView = itemView.findViewById(R.id.textView);
        }
    }


}
