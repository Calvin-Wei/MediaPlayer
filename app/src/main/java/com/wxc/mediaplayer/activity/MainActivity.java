package com.wxc.mediaplayer.activity;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.wxc.mediaplayer.Fragment.PagerFragment;
import com.wxc.mediaplayer.R;
import com.wxc.mediaplayer.base.BasePager;
import com.wxc.mediaplayer.pager.AudioPager;
import com.wxc.mediaplayer.pager.NetAudioPager;
import com.wxc.mediaplayer.pager.NetVideoPager;
import com.wxc.mediaplayer.pager.VideoPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private FrameLayout fl_main_content;
    private RadioGroup rg_bottom_tag;

    public static ArrayList<BasePager> basePagers;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl_main_content = (FrameLayout) findViewById(R.id.fl_main_content);
        rg_bottom_tag = (RadioGroup) findViewById(R.id.rg_bottom_tag);
        rg_bottom_tag.check(R.id.rb_video);
        basePagers = new ArrayList<>();
        //四个页面添加
        basePagers.add(new VideoPager(this));
        basePagers.add(new AudioPager(this));
        basePagers.add(new NetVideoPager(this));
        basePagers.add(new NetAudioPager(this));

        rg_bottom_tag.setOnCheckedChangeListener(new MyOnCheckedChangedListener());
        rg_bottom_tag.check(R.id.rb_video);
    }

    class MyOnCheckedChangedListener implements RadioGroup.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                default:
                    position = 0;
                    break;
                case R.id.rb_audio:
                    position = 1;
                    break;
                case R.id.rb_net_video:
                    position = 2;
                    break;
                case R.id.rb_netaudio:
                    position = 3;
                    break;

            }
            setFragment();
        }
    }

    private void setFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putInt("Loc",position);
        PagerFragment pagerFragment=new PagerFragment();
        pagerFragment.setArguments(bundle);
        //3.替换
        ft.replace(R.id.fl_main_content,pagerFragment);
//                new Fragment(){
//            @Nullable
//            @Override
//            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//                BasePager basePager = getBasePager();
//                if(basePager != null){
//                    //各个页面的视图
//                    return basePager.rootView;
//                }
//                return null;
//            }
//        });
        //4.提交事务
        ft.commit();
    }

    private BasePager getBasePager() {
        BasePager basePager = basePagers.get(position);
        if (basePager != null && basePager.isInitData) {
            basePager.initData();
            basePager.isInitData = true;
        }
        return basePager;
    }

}
