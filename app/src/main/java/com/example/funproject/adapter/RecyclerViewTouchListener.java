package com.example.funproject.adapter;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
    // 定义内部接口属性
    private final onItemClickListener onItemClickListener;
    // 构造函数
    public RecyclerViewTouchListener(RecyclerViewTouchListener.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        // 获取当前触摸位置下的 ItemView
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null) {
            //  获取子视图在适配器数据中的位置
            int position = rv.getChildAdapterPosition(child);
            // 获取子视图在 RecyclerView 中位置
            int id = rv.getChildLayoutPosition(child);
            // 调用内部接口的点击方法，通过接口让外部实现具体行为
            onItemClickListener.onItemClick(rv, child, position, id);
            // 也可以获取与子视图对应的 ViewHolder
            // RecyclerView.ViewHolder viewHolder = rv.getChildViewHolder(child);
            return true; // 返回 true 对触摸事件进行拦截
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
    // 定义内部接口
    public interface onItemClickListener {
        // 定义响应点击事件方法，传入所需参数
        // 这里模仿 ListView 传入父视图，子视图，子视图在适配器数据源中的位置，子视图在父视图中的位置 ID
        void onItemClick(RecyclerView recyclerView, View itemView, int position, long id);
    }
}

