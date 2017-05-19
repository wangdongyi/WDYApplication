package com.base.library.view.upPhotoView;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：王东一 on 2015/12/22 09:42
 **/
public class HotTalkGalleryAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<MyImageView> imageViews = new ArrayList<MyImageView>();

    private ImageCacheManager imageCache;

    private List<String> mItems;

    public void setData(List<String> data) {
        this.mItems = data;
        notifyDataSetChanged();
    }

    public HotTalkGalleryAdapter(Context context) {
        this.context = context;
        imageCache = ImageCacheManager.getInstance(context);
    }

    @Override
    public int getCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyImageView view = new MyImageView(context);
        view.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        String itemUrl = mItems.get(position);
        if (itemUrl != null) {
            Bitmap bmp;
            try {
                bmp = imageCache.get(itemUrl);
                view.setTag(itemUrl);
                if (bmp != null) {
                    Bitmap bitmap = PictureUtil.getSmallBitmap(itemUrl);
                    view.setImageBitmap(bitmap);
                }
                if (!this.imageViews.contains(view)) {
                    imageViews.add(view);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return view;
    }
}
