package com.wxc.mediaplayer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.wxc.mediaplayer.R;
import com.wxc.mediaplayer.base.BasePager;
import com.wxc.mediaplayer.pager.AudioPager;
import com.wxc.mediaplayer.pager.NetAudioPager;
import com.wxc.mediaplayer.pager.NetVideoPager;
import com.wxc.mediaplayer.pager.VideoPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FrameLayout fl_main_content;
    private RadioGroup rg_bottom_tag;

    private ArrayList<BasePager> basePagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl_main_content = (FrameLayout) findViewById(R.id.fl_main_content);
        rg_bottom_tag = (RadioGroup) findViewById(R.id.rg_bottom_tag);
        rg_bottom_tag.check(R.id.rb_video);
        basePagers=new ArrayList<>();
        //四个页面添加
        basePagers.add(new VideoPager(this));
        basePagers.add(new AudioPager(this));
        basePagers.add(new NetVideoPager(this));
        basePagers.add(new NetAudioPager(this));
    }
}
