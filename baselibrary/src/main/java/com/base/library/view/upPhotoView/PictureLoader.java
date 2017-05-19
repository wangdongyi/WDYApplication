package com.base.library.view.upPhotoView;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * 作者：王东一 on 2015/12/22 09:53
 **/
public class PictureLoader extends AsyncTaskLoader<List<String>> {
    private List<String> dataResult;
    private boolean dataIsReady;
    private static final String PICTURE = "pics";
    List<String> list;

    public PictureLoader(Context context, List<String> list) {
        super(context);
        this.list = list;
        if (dataIsReady) {
            deliverResult(dataResult);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<String> loadInBackground() {
        return list;
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        // 显示加载条
        //Logger.LOG(this, "onStartLoading");
        super.onStartLoading();
    }

    @Override
    protected void onStopLoading() {
        // 隐藏加载条
        //Logger.LOG(this, "onStopLoading");
        super.onStopLoading();
    }

    @Override
    public boolean takeContentChanged() {

        return super.takeContentChanged();
    }

}
