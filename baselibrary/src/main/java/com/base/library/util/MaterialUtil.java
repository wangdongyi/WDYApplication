package com.base.library.util;

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;
import com.base.library.R;

/**
 * 作者：王东一
 * 创建时间：2017/10/18.
 */

public class MaterialUtil {
    public static void Material(View view) {
        MaterialRippleLayout.on(view)
                .rippleColor(ContextCompat.getColor(view.getContext(), R.color.top_color))
                .rippleAlpha(0.2f)
                .rippleHover(false)
                .create();
    }
}
