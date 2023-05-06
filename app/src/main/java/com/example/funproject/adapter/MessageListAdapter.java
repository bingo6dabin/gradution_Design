package com.example.funproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funproject.R;
import com.example.funproject.entity.NewsEntity;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NewsEntity> mNewsList;
    public  MessageListAdapter(){

    }
    public  MessageListAdapter(List<NewsEntity> newsEntities){
        this.mNewsList = newsEntities;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType==1){
           View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_one,parent,false);
           return new ViewHolderOne(v);
       }else if(viewType ==2){
           View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_two,parent,false);
           return new ViewHolderTwo(v);
        }else{
           View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item_three,parent,false);
           return new ViewHolderThree(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if(type==1){
                ViewHolderOne  vh =(ViewHolderOne) holder;
            }else if(type==2){
                ViewHolderTwo vh =(ViewHolderTwo) holder;
            }else{
                ViewHolderThree vh = ( ViewHolderThree) holder;
            }
    }

    @Override
    public int getItemViewType(int position) {
        int type = mNewsList.get(position).getType();
        return type;
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
    //定义viewHold
    public  class ViewHolderOne extends RecyclerView.ViewHolder{

        public ViewHolderOne(@NonNull  View itemView) {
            super(itemView);

        }
    }
    public  class ViewHolderTwo extends RecyclerView.ViewHolder{

        public ViewHolderTwo(@NonNull  View itemView) {
            super(itemView);

        }
    }
    public  class ViewHolderThree extends RecyclerView.ViewHolder{

        public ViewHolderThree(@NonNull  View itemView) {
            super(itemView);

        }
    }
}
