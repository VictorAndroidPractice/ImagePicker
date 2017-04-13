package com.victorai60.imagepicker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.victorai60.imagepicker.R;
import com.victorai60.imagepicker.entity.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Image> mImages;
    private List<Image> mSelectedImages = new ArrayList<Image>();

    public ImageAdapter(Context context, List<Image> images) {
        mContext = context;
        mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object getItem(int position) {
        return mImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ImageViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.imagepicker_item_image, parent, false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.iv_image);
            viewHolder.ivSelect = (ImageView) convertView.findViewById(R.id.iv_select);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ImageViewHolder) convertView.getTag();
        }
        Image image = (Image) getItem(position);
        Glide.with(mContext)
                .load(image.getPath())
                .placeholder(R.drawable.imagepicker_photo)
                .into(viewHolder.ivImage);
        viewHolder.ivSelect.setImageResource(mSelectedImages.contains(image) ? R.drawable
                .imagepicker_select_checked : R.drawable.imagepicker_select_uncheck);
        return convertView;
    }

    static class ImageViewHolder {
        public ImageView ivImage;
        public ImageView ivSelect;
    }

    public List<Image> getSelectedImages() {
        return mSelectedImages;
    }
}
