package com.wxc.mediaplayer.pager;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wxc.mediaplayer.R;
import com.wxc.mediaplayer.activity.SystemVideoPlayer;
import com.wxc.mediaplayer.adapter.VideoPagerAdapter;
import com.wxc.mediaplayer.base.BasePager;
import com.wxc.mediaplayer.domain.MediaItem;

import java.util.ArrayList;

/**
 * Created by wxc on 2017/2/8.
 */

public class VideoPager extends BasePager {
    private ListView listView;
    private TextView tv_nomedia;
    private ProgressBar pb_loading;

    private ArrayList<MediaItem> mediaItems;

    private VideoPagerAdapter videoPagerAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mediaItems != null && mediaItems.size() > 0) {
                videoPagerAdapter = new VideoPagerAdapter(context, mediaItems, true);
                listView.setAdapter(videoPagerAdapter);
                tv_nomedia.setVisibility(View.GONE);
            } else {
                tv_nomedia.setVisibility(View.VISIBLE);
            }
            pb_loading.setVisibility(View.GONE);
        }
    };

    public VideoPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        getDataFromLocal();
    }

    private void getDataFromLocal() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                isGrantExternalRW((Activity) context);
                SystemClock.sleep(2000);
                mediaItems = new ArrayList<MediaItem>();
                //查询系统提供的视频文件
                ContentResolver contentResolver = context.getContentResolver();
                Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] objs = {
                        MediaStore.Video.Media.DISPLAY_NAME,//视频文件在sdcard的名称
                        MediaStore.Video.Media.DURATION,//视频总时长
                        MediaStore.Video.Media.SIZE,//视频的文件大小
                        MediaStore.Video.Media.DATA,//视频的绝对地址
                        MediaStore.Video.Media.ARTIST,//歌曲的演唱者

                };
                Cursor cursor = contentResolver.query(uri, objs, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        MediaItem mediaItem = new MediaItem();
                        mediaItems.add(mediaItem);//写在上面

                        String name = cursor.getString(0);//视频的名称
                        mediaItem.setName(name);

                        long duration = cursor.getLong(1);//视频的时长
                        mediaItem.setDuration(duration);

                        long size = cursor.getLong(2);//视频的文件大小
                        mediaItem.setSize(size);

                        String data = cursor.getString(3);//视频的播放地址
                        mediaItem.setData(data);

                        String artist = cursor.getString(4);//艺术家
                        mediaItem.setArtist(artist);
                    }
                    cursor.close();
                }
                handler.sendEmptyMessage(10);
            }
        }.start();
    }

    private void isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23 && activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
    }

    @Override
    public View initView() {
        View v = View.inflate(context, R.layout.video_pager, null);
        listView = (ListView) v.findViewById(R.id.listview);
        tv_nomedia = (TextView) v.findViewById(R.id.tv_nomeid);
        pb_loading = (ProgressBar) v.findViewById(R.id.pb_loading);
        listView.setOnItemClickListener(new MyOnItemListener());
        return v;
    }

    private class MyOnItemListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(context, SystemVideoPlayer.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("videolist", mediaItems);
            //序列化，视频列表
            intent.putExtras(bundle);
            //当前播放位置
            intent.putExtra("position", position);
            context.startActivity(intent);
        }
    }
}
