package com.example.funproject.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.funproject.database.CollectDataBaseHelper;
import com.example.funproject.database.FavoratesDataBaseHelper;
import com.example.funproject.database.UserDataBaseHelper;
import com.example.funproject.database.VideoDataBaseHelper;
import com.example.funproject.dedigned_class.ModifyClass.GridSpacingItemDecoration;
import com.example.funproject.entity.Collects;
import com.example.funproject.entity.Favorites;
import com.example.funproject.entity.User;
import com.example.funproject.entity.Video;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link games#newInstance} factory method to
 * create an instance of this fragment.
 */
public class games extends HomeBaseFragment implements VideoListAdapter.OnLikeClickListener,VideoListAdapter.OnCollectClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  int videoNum=0;
    private VideoDataBaseHelper mVideoHelper;
    private FavoratesDataBaseHelper mFavoriteHelper;
    private CollectDataBaseHelper mCollectHelper;
    private UserDataBaseHelper mUserHelper;
    private volatile List<Video> mVideoes = new ArrayList<>();
    private VideoListAdapter mVideoListAdapter;
    private String categoryId;
    private  RecyclerView recyclerView;
    private SharedPreferences preferences;
    private  User mUser;
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
        //获得数据库帮助器实例
        mVideoHelper = VideoDataBaseHelper.getInstance(getContext());
        mFavoriteHelper = FavoratesDataBaseHelper.getInstance(getContext());
        mCollectHelper = CollectDataBaseHelper.getInstance(getContext());
        mUserHelper = UserDataBaseHelper.getInstance(getContext());
        //打开数据库读写连接
        mVideoHelper.openReadLink();
        mVideoHelper.openWriteLink();
        mFavoriteHelper.openReadLink();
        mFavoriteHelper.openWriteLink();
        mUserHelper.openReadLink();
        mUserHelper.openWriteLink();
        mCollectHelper.openReadLink();
        mCollectHelper.openWriteLink();
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


        @SuppressLint("WrongConstant") GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),1, OrientationHelper.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        int spanCount = 1; // 2 columns
        int spacing = 50; // 50px
        boolean includeEdge = false;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount,spacing,includeEdge));
        return view;
    }

    @Override
    public void onLikeButtonClicked(int position) {

    }

    // 实现 Adapter 中定义的对点赞监听接口
    @Override
    public void onLikeClick(int position) {
       System.out.println(mVideoes.get(position).getFavoritesCount());
        preferences = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
//         使用共享内存中干的aacount,从数据库中获取当前登录用户，用于后续查询
        if(preferences.contains("account")){
            String tempName = preferences.getString("account",null);
            mUser= mUserHelper.querryByUsername(tempName);
        }
       //将点赞的视频保存到数据库中，这里查询点赞的视频是否已保存到数据库，如果已保存则将其删除
        // 更新数据
        //获取视频以及用户对象
        Video tempVideo = mVideoHelper.queryByVideoImageUrl(mVideoes.get(position).getVideoimageUrl());
        Favorites tempFav = new Favorites(0,mUser.getUid(),tempVideo.getVid());
        //视频已点赞，再次点击删除
        if(mFavoriteHelper.queryByVid(tempFav)){
            mVideoes.get(position).setFavoritesCount(mVideoes.get(position).getFavoritesCount() - 1);
            mUser.setFavoratesNum(mUser.getFavoratesNum()-1);
            mUserHelper.updataUser(mUser);
            mFavoriteHelper.deleteByFavorites(tempFav);
        }else
        {
            //将视频加入点赞表中
            mUser.setFavoratesNum(mUser.getFavoratesNum()+1);
            mUserHelper.updataUser(mUser);
            mVideoes.get(position).setFavoritesCount(mVideoes.get(position).getFavoritesCount() + 1);
            mFavoriteHelper.insertToFavorates(tempFav);
        }
        mVideoListAdapter.notifyItemChanged(position);
    }
    // 实现 Adapter 中定义的收藏接口
    @Override
    public void onCollectClick(int position) {
        preferences = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
//         使用共享内存中干的aacount,从数据库中获取当前登录用户，用于后续查询
        if(preferences.contains("account")){
            String tempName = preferences.getString("account",null);
            mUser= mUserHelper.querryByUsername(tempName);
        }
        //将点赞的视频保存到数据库中，这里查询点赞的视频是否已保存到数据库，如果已保存则将其删除
        // 更新数据
        //获取视频以及用户对象
        Video tempVideo = mVideoHelper.queryByVideoImageUrl(mVideoes.get(position).getVideoimageUrl());
        Collects tempCol = new Collects(0,mUser.getUid(),tempVideo.getVid());
        //视频已点赞，再次点击删除
        if(mCollectHelper.queryByVid(tempCol)){
            mVideoes.get(position).setCollectCount(mVideoes.get(position).getCollectCount() - 1);
            mUser.setCollectesNum(mUser.getCollectesNum()-1);
            mUserHelper.updataUser(mUser);
            mCollectHelper.deleteByCollect(tempCol);
        }else
        {
            //将视频加入点赞表中
            mUser.setCollectesNum(mUser.getCollectesNum()+1);
            mUserHelper.updataUser(mUser);
            mVideoes.get(position).setCollectCount(mVideoes.get(position).getCollectCount() + 1);
            mCollectHelper.insertToCollect(tempCol);
        }
        mVideoListAdapter.notifyItemChanged(position);
    }
public  void  loadMoreData(){
        if(mVideoes.size()-videoNum>=3)
        {
            mVideoListAdapter = new VideoListAdapter(mVideoes,videoNum);
            mVideoListAdapter.setOnLikeClickListener(this);
            mVideoListAdapter.setOnCollectClickListener(this);
            recyclerView.setAdapter(mVideoListAdapter);
        }
}

    //new一个异步线程，异步调用，更新UI，向handler中传递消息，由handler响应并返回信息
    @SuppressLint("HandlerLeak")
    private final Handler getInformation = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            Video tempVideo =(Video)data.getSerializable("video");
            mVideoes.add(tempVideo);
         if(!mVideoHelper.ExitByVideoImageUrl(tempVideo.getVideoimageUrl())){
             mVideoHelper.insertToVideo((Video)data.getSerializable("video"));
         }
            loadMoreData();
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
            HashSet<String> videoKey=new HashSet<>();
            videoKey.add("video");
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
                           if(!videoKey.contains(title)){
                               System.out.println("[bvid:" + bvid + ",cid:" + cid + ",tname:" + tname + ",title:" + title + ",desc:" + desc + ",deadline:" + res + ",[pic:" + pic + "]," + "url:" + matcher.group() + "]");
                               Video tempVideo = new Video(0,0,authorName,title,typeName,pic,matcher.group(),description,favoritesCount,CollectCount,play,creatTime, 0);
//                                      mVideoes.add(tempVideo);
//                                    将video加载到message中用于bundle在传递数据到UI线程
                               Bundle bundle = new Bundle();
                               bundle.putSerializable("video",tempVideo);
                               Message mMessage= new Message();
                               mMessage.setData(bundle);
                               getInformation.sendMessage(mMessage);
                               videoKey.add(title);
                           }else{
                               continue;
                           }
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