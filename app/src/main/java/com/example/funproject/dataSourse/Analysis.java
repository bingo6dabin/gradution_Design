package com.example.funproject.dataSourse;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Analysis {
    /**
     * 获取视频的真实链接（可以直接浏览器打开）
     *
     * @return
     */
    public static String get_video_url(String bvid, int cid) {

        String video_url = "https://api.bilibili.com/x/player/playurl?bvid=" + bvid + "&cid=" + cid + "&qn=16&type=mp4&platform=html5&otype=json&fnver=0&fnval=16";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(video_url)
                .get()//默認就是GET請求，可以不写
                .build();
        String result = null;
        try {
            Call call = (Call) okHttpClient.newCall(request);
            Response response = call.execute();
            result = response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果url
        return result;
    }

    /**
     * 获取热榜视频(可以直接浏览器打开)
     *
     * @return
     */
    public static String get_hot_video_info(String regionId) {
        String hot_video_info = "https://api.bilibili.com/x/web-interface/ranking/region?rid="+regionId+"&day=7";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(hot_video_info)
                .get()//默认就是GET請求，可以不写
                .build();
        String result = null;
        try {
            Call call = (Call) okHttpClient.newCall(request);
            Response response = call.execute();
            result = response.body().string();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String get_cid(String bvid){
        String cid_url = "https://api.bilibili.com/x/player/pagelist?bvid=" + bvid + "&jsonp=jsonp";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(cid_url)
                .get()//默認就是GET請求，可以不写
                .build();
        String result = null;
        try {
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}






