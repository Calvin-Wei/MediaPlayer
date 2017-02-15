package com.wxc.mediaplayer.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxc.mediaplayer.base.BasePager;

import static com.wxc.mediaplayer.activity.MainActivity.basePagers;

/**
 * Created by wxc on 2017/2/8.
 */

public class PagerFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BasePager basePager = getBasePager();
        if (basePager != null)
            return basePager.rootView;
        return null;
    }

    private BasePager getBasePager() {
        Bundle bundle = getArguments();
        int position = bundle.getInt("Loc");
        BasePager basePager = basePagers.get(position);
        if (basePager != null && basePager.isInitData) {
            basePager.initData();
            basePager.isInitData = true;
        }
        return basePager;
    }
}
