package com.wdy.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.base.library.activity.BaseActivity;
import com.base.library.photopicker.utils.PhotoUtils;
import com.base.library.preview.PhotoPreviewUtil;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {


    TextView sampleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hintTitle();
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoUtils.onPhotoResult(resultCode, data, new PhotoUtils.onPhotoBack() {
            @Override
            public void onBack(ArrayList<String> result) {

            }
        });
    }
}
