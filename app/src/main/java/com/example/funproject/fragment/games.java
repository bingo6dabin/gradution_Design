package com.example.funproject.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.funproject.R;
import com.example.funproject.adapter.VideoListAdapter;
import com.example.funproject.dataSourse.Analysis;
import com.example.funproject.dedigned_class.ModifyClass.GridSpacingItemDecoration;
import com.example.funproject.entity.Video;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link games#newInstance} factory method to
 * create an instance of this fragment.
 */
public class games extends HomeBaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  int videoNum=0;
    private volatile List<Video> mVideoes = new ArrayList<>();
    private VideoListAdapter mVideoListAdapter;
    private String categoryId;
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public games() {
        // Required empty public constructor
    }
    public  games(String categoryId){
        this.categoryId=categoryId;
    }
    // TODO: Rename and change types and number of parameters
    public static games newInstance(String param1, String param2) {
        games fragment = new games();
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

        View view = inflater.inflate(R.layout.fragment_games, container, false);
        recyclerView = view.findViewById(R.id.ry_games);
        //线程启动
        new Thread(runnable).start();

        //增大网络请求数据量
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        @SuppressLint("WrongConstant") GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),2, OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        int spanCount = 2; // 2 columns
        int spacing = 50; // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount,spacing,includeEdge));
        return view;
    }

    //new一个异步线程，异步调用，更新UI，向handler中传递消息，由handler响应并返回信息
    @SuppressLint("HandlerLeak")
    private final Handler getInformation = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            mVideoes.add((Video)data.getSerializable("video"));
            if(mVideoes.size()-videoNum>5)
            {
                mVideoListAdapter = new VideoListAdapter(mVideoes,videoNum);
                recyclerView.setAdapter(mVideoListAdapter);
                videoNum = mVideoes.size();
            }
        }
    };
    //异步线程
    final Runnable runnable = this::run;
    @Override
    //销毁
    public void onDestroy() {
        super.onDestroy();
    }

    //运行
    private void run() {
        //将json字符串数据转存成json数组
        try {
            //通过regionIdcode[i]自动获取多个分区的json数据
            String jsonString = Analysis.get_hot_video_info(categoryId);
            //json字符串转成对象
            JSONObject parse = JSONObject.parseObject(jsonString);
            int code = (int) parse.get("code");
            String message = (String) parse.get("message");
            System.out.println(code);
            System.out.println(message);
//对象转成数组获取data数据
            JSONArray dataArray = parse.getJSONArray("data");
            //遍历打印对象数组的数据
            for (Object obj : dataArray) {

                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(obj);

                String  authorName = (String)jsonObject.get("author");
                String  title = (String)jsonObject.get("title");
                String  typeName = (String)jsonObject.get("typename");
                String pic = (String) jsonObject.get("pic");
                String bvid = (String) jsonObject.get("bvid");
                String tname = (String) jsonObject.get("tname");
                String description = (String) jsonObject.get("description");
                Integer favoritesCount = (Integer) jsonObject.get("favorites");
                Integer CollectCount= (Integer) jsonObject.get("coins");
                Integer play = (Integer) jsonObject.get("play");
                String creatTime = (String) jsonObject.get("create");
                String desc = (String) jsonObject.get("desc");
                String jsonString1 = Analysis.get_cid(bvid);

                String temptest = jsonObject.toString().trim();
                System.out.println(temptest);

                //将获取的json数据的花括号去掉，保证解析过程中格式正确
                //String data03 = jsonString1.substring(0, jsonString.length() - 1);
                JSONObject parse1 = JSONObject.parseObject(jsonString1);
                //对象转成数组获取data数据
                JSONArray dataArray1 = parse1.getJSONArray("data");
                for(Object obj1 :dataArray1){
                    JSONObject jsonObject1 = (JSONObject) JSONObject.toJSON(obj1);
                    int cid = (int) jsonObject1.get("cid");
                    //通过bvid与cid获取URL数据
                    String S = Analysis.get_video_url(bvid, cid);
                    //将字符串中的u0026去掉
                    String newString = S.replaceAll("\\\\u0026", "&");
                    //System.out.println(newString);
                    String newString1 = S.replaceAll("u0026", "");
                    //通过字符串中的\\截取时间戳deadline，存放在数组中
                    String[] array = newString1.split("\\\\");
                    String deadline = array[3];
                    String result = deadline.substring(9);
                    //将获取的时间戳deadline转换成年月日时分秒
                    String res;
                    TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    long lt = new Long(result);
                    Date date = new Date(lt * 1000);
                    res = simpleDateFormat.format(date);
                    //System.out.println(res);
                    Matcher matcher = Patterns.WEB_URL.matcher(newString);
                    if (matcher.find()) {
                        System.out.println("[bvid:" + bvid + ",cid:" + cid + ",tname:" + tname + ",title:" + title + ",desc:" + desc + ",deadline:" + res + ",[pic:" + pic + "]," + "url:" + matcher.group() + "]");
                        Video tempVideo = new Video(0,0,authorName,title,typeName,pic,matcher.group(),description,favoritesCount,CollectCount,play,creatTime, 0);
//                                      mVideoes.add(tempVideo);
//                                    将video加载到message中用于bundle在传递数据到UI线程
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("video",tempVideo);
                        Message mMessage= new Message();
                        mMessage.setData(bundle);
                        getInformation.sendMessage(mMessage);
                    }
                }
            }
            //100分钟后执行一次
            Thread.sleep(1000*6000);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}