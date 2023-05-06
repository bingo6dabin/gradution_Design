package com.example.funproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.funproject.R;
import com.example.funproject.adapter.MessageListAdapter;
import com.example.funproject.adapter.VideoListAdapter;
import com.example.funproject.dedigned_class.ModifyClass.GridSpacingItemDecoration;
import com.example.funproject.entity.NewsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private  List<NewsEntity> mNewsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessageListAdapter mMessageListAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MessageFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MessageFragment newInstance(String param1, String param2) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.fragment_message, container, false);
        recyclerView = view.findViewById(R.id.ry_message);
        for(int i=0;i<10;i++){
            int type = i%3+1;
            NewsEntity temp = new NewsEntity();
            temp.setType(type);
            mNewsList.add(temp);
        }
        mMessageListAdapter = new MessageListAdapter(mNewsList);
        recyclerView.setAdapter(mMessageListAdapter);

        @SuppressLint("WrongConstant") GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),1, OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        int spanCount = 1; // 2 columns
        int spacing = 50; // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount,spacing,includeEdge));
         return  view;
    }
}