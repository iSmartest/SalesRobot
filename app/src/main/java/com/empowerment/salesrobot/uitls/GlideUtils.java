package com.empowerment.salesrobot.uitls;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.empowerment.salesrobot.R;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/19.
 * Description:
 */
public class GlideUtils {
    public static void imageLoader(Context context, String image, ImageView imageView){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.app_icon);
        requestOptions.error(R.drawable.image_fail_empty);
        Glide.with(context).load(image).apply(requestOptions).into(imageView);
    }
    public static void imageLoader(Context context, Uri image, ImageView imageView){
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.app_icon);
        requestOptions.error(R.drawable.image_fail_empty);
        Glide.with(context).load(image).apply(requestOptions).into(imageView);
    }
}
