package com.wxc.mediaplayer.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxc.mediaplayer.R;
import com.wxc.mediaplayer.domain.MediaItem;
import com.wxc.mediaplayer.utils.Utils;

import java.util.ArrayList;

/**
 * Created by wxc on 2017/2/9.
 */
public class VideoPagerAdapter extends BaseAdapter {
    private Context context;
    private boolean isVideo;
    private ArrayList<MediaItem> mediaItems;

    private Utils utils;

    public VideoPagerAdapter(Context context, ArrayList<MediaItem> mediaItems, boolean b) {
        this.context = context;
        this.mediaItems = mediaItems;
        this.isVideo = b;
        utils = new Utils();
    }

    @Override
    public int getCount() {
        return mediaItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_video_pager, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MediaItem mediaItem = mediaItems.get(position);
        viewHolder.tv_name.setText(mediaItem.getName());
        viewHolder.tv_time.setText(utils.stringForTime((int) mediaItem.getDuration()));
        viewHolder.tv_size.setText(Formatter.formatFileSize(context, mediaItem.getSize()));

        if (!isVideo) {
            viewHolder.iv_icon.setImageResource(R.drawable.music_default_bg);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_time;
        TextView tv_size;
    }
}
