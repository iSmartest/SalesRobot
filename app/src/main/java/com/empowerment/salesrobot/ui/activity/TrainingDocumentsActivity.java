package com.empowerment.salesrobot.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.listener.RecyclerItemTouchListener;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.TrainingDocumentsAdapter;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.ui.model.TrainingDocBean;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.example.xrecyclerview.XRecyclerView;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/3.
 * Description:
 */
public class TrainingDocumentsActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycler_doc)
    XRecyclerView xRecyclerView;
    private int nowPage = 1;
    private int rows = 10;
    private ProgressDialog mProgressDialog;
    //下载成功
    private static final int DOWNLAND_SUCCESS = 1;
    //下载失败
    private static final int DOWNLOAD_ERROR = 2;
    private File file;
    private String mUrl;
    private TrainingDocumentsAdapter mAdapter;
    private List<TrainingDocBean.DataBean.TdoctList> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_training_doc;
    }

    @Override
    protected void loadData() {
        Map<String, String> params = new HashMap<>();
        params.put(SALE_ID, SPUtil.getString(context, SALE_ID));
        params.put("page", nowPage + "");
        MyOkhttp.Okhttp(context, Url.TRAINDOCLIST, "加载中...", params, (response, result, resultNote) -> {
            Gson gson = new Gson();
            TrainingDocBean trainingDocBean = gson.fromJson(response, TrainingDocBean.class);
            if (result.equals("1")) {
                ToastUtils.makeText(context, resultNote);
                return;
            }
            List<TrainingDocBean.DataBean.TdoctList> tdoctLists = trainingDocBean.getData().getTdoctList();
            if (tdoctLists != null && !tdoctLists.isEmpty() && tdoctLists.size() > 0) {
                mList.addAll(tdoctLists);
                mAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
            }
            if (tdoctLists.size() < rows) {
                xRecyclerView.noMoreLoading();
            }
        });
    }

    @Override
    protected void initView() {
        title.setText("培训文档");
        titleBack.setVisibility(View.VISIBLE);
        xRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        mAdapter = new TrainingDocumentsAdapter(context, mList);
        xRecyclerView.setAdapter(mAdapter);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                nowPage = 1;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                loadData();
            }

            @Override
            public void onLoadMore() {
                nowPage++;
                loadData();
                mAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
            }
        });
        xRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(xRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition() - 1;
                if (position < 0 | position >= mList.size()) {
                    return;
                }
                mUrl = Url.HTTP + mList.get(position).getAddress();
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                //截取文件最后14位作为文件名
                String path = Environment.getExternalStorageDirectory() + "/download/";
                String[] name = mUrl.split("/");
                path = path + name[name.length - 1];

                file = new File(path);
                new Thread() {
                    @Override
                    public void run() {
                        //获取本地文件
                        File sdFile = new File(file.getAbsolutePath());
                        //判断文件是否存在
                        if (sdFile.exists()) {
                            //有缓存文件，拿到路径，直接打开
                            Message message = Message.obtain();
                            message.obj = sdFile;
                            message.what = DOWNLAND_SUCCESS;
                            handler.sendMessage(message);
                            mProgressDialog.dismiss();
                            return;
                        }
                        //本地没有此文件，则从网上先下载
                        File downloadFile = download(mUrl, mProgressDialog);
                        Message message = Message.obtain();
                        if (downloadFile != null) {
                            message.obj = downloadFile;
                            message.what = DOWNLAND_SUCCESS;
                        } else {
                            message.what = DOWNLOAD_ERROR;
                        }
                        handler.sendMessage(message);
                        mProgressDialog.dismiss();
                    }
                }.start();
            }
        });
    }

    //传入文件URL地址和弹出dialog进行下载文档
    private File download(String mUrl, ProgressDialog mProgressDialog) {
        try {
            URL url = new URL(mUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            //实现连接
            connection.connect();
            if (connection.getResponseCode() == 200) {
                int max = connection.getContentLength();
                mProgressDialog.setMax(max);
                InputStream is = connection.getInputStream();
                //以下为下载操作
                byte[] arr = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(baos);
                int n = is.read(arr);
                int total = 0;
                while (n > 0) {
                    bos.write(arr);
                    n = is.read(arr);
                    total = total + n;
                    mProgressDialog.setProgress(total);
                }
                bos.close();
                String path = Environment.getExternalStorageDirectory() + "/download/";
                String[] name = mUrl.split("/");
                path = path + name[name.length - 1];
                File file = new File(path);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baos.toByteArray());
                fos.close();
                //关闭网络连接
                connection.disconnect();
                return file;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //下载完成，直接打开文件

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLAND_SUCCESS:
                    File file = (File) msg.obj;
                    if (file.exists()) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        //判断是否是AndroidN以及更高的版本
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Uri contentUri = FileProvider.getUriForFile(context, "com.empowerment.salesrobot.fileprovider", file);
                            intent.setDataAndType(contentUri, "application/pdf");
                        } else {
                            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                        startActivity(intent);
                    }
                    break;
                case DOWNLOAD_ERROR:
                    ToastUtils.makeText(context, "文件加载失败");
                    break;
            }
        }
    };

    @OnClick({R.id.title_Back})
    public void onViewClicked() {
        finish();
    }

}
