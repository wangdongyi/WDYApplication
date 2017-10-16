package com.base.library.glide;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;

import com.base.library.util.FileUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.InputStream;

/**
 * 作者：王东一
 * 创建时间：2017/5/16.
 */

public class WDYGlideModule extends AppGlideModule {
    public static DiskCache cache;

    @Override

    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565).disallowHardwareConfig());
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setArrayPoolSize(5)
                .setMemoryCacheScreens(2)
                .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
    }


    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
    }
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
