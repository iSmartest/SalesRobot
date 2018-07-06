package com.empowerment.salesrobot.okhttp;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;


import com.empowerment.salesrobot.okhttp.budiler.StringCallback;
import com.empowerment.salesrobot.uitls.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/9 0009.
 */

public class MyOkhttp {


    public interface CallBack {
        void onRequestComplete(String response, String result, String resultNote);
    }

    /**
     * @param context  上下文
     * @param params   post参数
     * @param callBack 回调
     */
    public static void Okhttp(final Context context, String url, final Dialog dialog,Map<String, String> params, final CallBack callBack) {
        dialog.show();
        Log.e("TAG", "json1=" + params.get("saleId"));
        Log.e("TAG", "json2=" + params.get("page"));
        OkHttpUtils.post().params(params).url(url).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "e=" + e.getMessage());
                        dialog.dismiss();
                        ToastUtils.makeText(context,e.getMessage());
                    }
                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "response=" + response);
                        String result = "1";
                        String resultNote = "服务器异常";
                        try {
                            JSONObject object = new JSONObject(response);

                            if (object.has("resultCode") && !object.isNull("resultCode")) {
                                result = object.getString("resultCode");
                            }

                            if (object.has("msg") && !object.isNull("msg")) {
                                resultNote = object.getString("msg");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callBack.onRequestComplete(response, result, resultNote);
                        dialog.dismiss();
                    }
                });
    }
}
