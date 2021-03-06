package com.empowerment.salesrobot.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.dialog.NoticeTipsDialog;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.uitls.AppManager;
import com.empowerment.salesrobot.uitls.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/27.
 * Description:
 */
public class NoticeTipsActivity extends AppCompatActivity{
    private Context context;
    String content,endDate,finish,id,type,mTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        content = getIntent().getStringExtra("content");
        mTitle = getIntent().getStringExtra("title");
        JSONObject object = null;
        try {
            object = new JSONObject(getIntent().getStringExtra("extra"));
            endDate = object.getString("endDate");
            finish = object.getString("finish");
            id = object.getString("id");
            type = object.getString("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new NoticeTipsDialog(context, mTitle, content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
                JPushInterface.clearAllNotifications(context);
            }
        }).show();
    }

    public void openActivity() {
        Intent detailIntent = null;
        switch (type) {
            case "1": //公司待办
                submitFinish();
                detailIntent = new Intent(context, AgencyAffairsInfoActivity.class);
                detailIntent.putExtra("type", type);
                detailIntent.putExtra("id", id);
                detailIntent.putExtra("isFinish", finish);
                detailIntent.putExtra("content", content);
                detailIntent.putExtra("time", endDate);
                detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(detailIntent);
                finish();
                break;
            case "3": //个人待办
                submitFinish();
                detailIntent = new Intent(context, AgencyAffairsInfoActivity.class);
                detailIntent.putExtra("type", type);
                detailIntent.putExtra("id", id);
                detailIntent.putExtra("isFinish", finish);
                detailIntent.putExtra("content", content);
                detailIntent.putExtra("time", endDate);
                detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(detailIntent);
                finish();
                break;
            case "4": //系统消息
                detailIntent = new Intent(context, MainActivity.class);
                detailIntent.putExtra("content", content);
                detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(detailIntent);
                finish();
                break;
            case "99":
                detailIntent = new Intent(context, AppManager.currentActivity().getClass());
                detailIntent.putExtra("content", content);
                detailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(detailIntent);
                finish();
                break;
            default:
                break;
        }
    }
    private void submitFinish() {
        Map<String,String> params = new HashMap<>();
        params.put("aId",id+"");
        params.put("sId", SPUtil.getString(context,SALE_ID));
        params.put("aType",String.valueOf(type));//待办类型
        params.put(STORE_ID,SPUtil.getString(context,STORE_ID));
        params.put("type", "1");//1为阅读，2为完结
        MyOkhttp.Okhttp(context, Url.READ_OR_FINISH, "加载中...", params, (response, result, resultNote) -> {
            if (result.equals("1")){
                Log.i("", "submitFinish: " + resultNote );
            }else {
                Log.i("", "submitFinish: " + resultNote );
            }
        });
    }
}
