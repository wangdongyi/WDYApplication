package com.base.library.view.upPhotoView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.base.library.R;

import java.util.List;


/**
 * 作者：王东一 on 2015/12/22 09:30
 **/
public class HotPictureViewActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<String>> {
    // 屏幕宽度
    public static int screenWidth;
    // 屏幕高度
    public static int screenHeight;

    private PhotoGallery gallery;
    // private ViewGroup tweetLayout; // 弹层
    private int pos = 0;

    private HotTalkGalleryAdapter mAdapter;

    // private ProgressDialog mProgress;

    public HotTalkGalleryAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setContentView(R.layout.hottalk_picture_view);
        gallery = (PhotoGallery) findViewById(R.id.pic_gallery);
        gallery.setVerticalFadingEdgeEnabled(false);// 取消竖直渐变边框
        gallery.setHorizontalFadingEdgeEnabled(false);// 取消水平渐变边框
        gallery.setDetector(new GestureDetector(this, new MySimpleGesture()));
        mAdapter = new HotTalkGalleryAdapter(this);
        gallery.setAdapter(mAdapter);
        gallery.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                return false;
            }
        });
        getSupportLoaderManager().initLoader(0, null, this);
        initViews();
    }

    private class MySimpleGesture extends GestureDetector.SimpleOnGestureListener {
        // 按两下的第二下Touch down时触发
        public boolean onDoubleTap(MotionEvent e) {

            View view = gallery.getSelectedView();
            if (view instanceof MyImageView) {
                MyImageView imageView = (MyImageView) view;
                if (imageView.getScale() > imageView.getMiniZoom()) {
                    imageView.zoomTo(imageView.getMiniZoom());
                } else {
                    imageView.zoomTo(imageView.getMaxZoom());
                }

            } else {

            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }
    }

    @Override
    public android.support.v4.content.Loader<List<String>> onCreateLoader(int arg0, Bundle arg1) {
        Intent intent = getIntent();
        List<String> array = null;
        if (intent != null) {
            array = intent.getExtras().getStringArrayList(DoPicCapUtil.DATA);
            pos = intent.getIntExtra("pos", 0);
        }
        /*
		 * try { String[] flLists =
		 * PictureViewFra.this.getAssets().list(PICTURE); for (String file :
		 * flLists) { if (file.endsWith(".jpg") || file.endsWith(".png")) {
		 *
		 * } }
		 *
		 * } catch (IOException e) { //  Auto-generated catch block
		 * e.printStackTrace(); }
		 */
        return new PictureLoader(this, array);
    }


    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<String>> arg0, List<String> arg1) {
        // Logger.LOG("this", "onLoadFinished");
        mAdapter.setData(arg1);
        gallery.setSelection(pos);
        // mProgress.dismiss();
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<String>> arg0) {
        // Logger.LOG(this, "onLoaderReset");
        mAdapter.setData(null);
    }

    @SuppressWarnings("deprecation")
    private void initViews() {

        screenWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight();

    }
}
