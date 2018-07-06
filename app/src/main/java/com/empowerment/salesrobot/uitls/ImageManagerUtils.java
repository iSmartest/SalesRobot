package com.empowerment.salesrobot.uitls;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.empowerment.salesrobot.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by 小火
 * Create time on  2017/9/27
 * My mailbox is 1403241630@qq.com
 */

public class ImageManagerUtils {
    /**
     * 加载图片时使用动画效果
     */
    public static ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    /**
     * 默认图片加载设置
     */
    public static DisplayImageOptions options, options3, headOpions;
    /**
     * 加载图片工具类实例 <li>使用方法:imageLoader.displayImage(ImageURL, ImageView, options,
     * animateFirstListener); <li>如果ListView或者GirdView在滑动的时候感觉很卡，可以这么设置<br>
     * ListView.setOnScrollListener(new
     * PauseOnScrollListener(ImageManager.imageLoader, true, true));
     */
    public static ImageLoader imageLoader;
    /**
     * 可以用以解决ListView或者GirdView在滑动的时候卡顿的问题。使用方式：<br>
     * ListView.setOnScrollListener(new
     * PauseOnScrollListener(ImageManager.imageLoader, true, true));
     */
    public static PauseOnScrollListener scrollListener;
    public static void init() {
        animateFirstListener = new AnimateFirstDisplayListener();
        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.image_fail_empty)
                .showImageForEmptyUri(R.drawable.image_fail_empty)
                .showImageOnFail(R.drawable.image_fail_empty).cacheInMemory(true)
                .cacheOnDisc(true).build();

        options3 = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.image_fail_empty)
                .showImageForEmptyUri(R.drawable.image_fail_empty)
                .showImageOnFail(R.drawable.image_fail_empty).cacheInMemory(true)
                .cacheOnDisc(true).build();
        headOpions = new DisplayImageOptions.Builder().showStubImage(R.drawable.my_head_portrait)
                .showImageForEmptyUri(R.drawable.my_head_portrait)
                .showImageOnFail(R.drawable.my_head_portrait).cacheInMemory(true)
                .cacheOnDisc(true).build();

        scrollListener = new PauseOnScrollListener(imageLoader, true, true);
    }

    public static DisplayImageOptions getOptions(int resourceId){
        return  new DisplayImageOptions.Builder().showStubImage(resourceId)
                .showImageForEmptyUri(resourceId)
                .showImageOnFail(resourceId)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
    }
    public static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {
        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                FadeInBitmapDisplayer.animate(imageView, 500);
            }
        }
    }

}
