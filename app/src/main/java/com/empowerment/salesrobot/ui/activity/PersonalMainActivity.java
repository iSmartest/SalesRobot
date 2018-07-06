package com.empowerment.salesrobot.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.dialog.LogOutDialog;
import com.empowerment.salesrobot.okhttp.OkHttpUtils;
import com.empowerment.salesrobot.okhttp.budiler.StringCallback;
import com.empowerment.salesrobot.uitls.AppManager;
import com.empowerment.salesrobot.uitls.DataCleanManager;
import com.empowerment.salesrobot.uitls.ImageUtil;
import com.empowerment.salesrobot.uitls.PhotoUtil;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.view.RoundedImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.empowerment.salesrobot.config.BaseUrl.IMAGE;
import static com.empowerment.salesrobot.config.BaseUrl.PHONE_NUMBER;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/5.
 * Description:
 */
public class PersonalMainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.my_iconImgview)
    RoundedImageView mUserIcon;
    @BindView(R.id.personalText)
    TextView mPersonalText;
    @BindView(R.id.linear_my_setting_clear_cache)
    LinearLayout mClearCache;
    @BindView(R.id.text_my_setting_clear_cache_size)
    TextView mCacheSize;
    @BindView(R.id.tv_common_problem)
    TextView mCommonProblem;
    private AlertDialog builder; //底部弹出菜单
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri, cropImageUri;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    ToastUtils.makeText(PersonalMainActivity.this, "清理完成");
                    try {
                        mCacheSize.setText(DataCleanManager.getTotalCacheSize(PersonalMainActivity.this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected int getLauoutId() {
        return R.layout.activity_personal_main;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {
        titleBack.setVisibility(View.VISIBLE);
        try {
            mCacheSize.setText(DataCleanManager.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.my_iconImgview, R.id.personalText, R.id.linear_my_setting_clear_cache, R.id.tv_common_problem, R.id.tv_my_setting_update, R.id.sign_Out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.my_iconImgview://上传头像
                showChoosePicDialog();
                break;
            case R.id.personalText://个人资料
                MyApplication.openActivity(context, ModificationActivity.class);
                break;
            case R.id.linear_my_setting_clear_cache:
                new Thread(new clearCache()).start();
                break;
            case R.id.tv_common_problem://常见问题
//                MyApplication.openActivity(context, UpdateActivity.class);
                break;
            case R.id.tv_my_setting_update://版本更新
                MyApplication.openActivity(context, UpdateActivity.class);
                break;
            case R.id.sign_Out:
                LogOutDialog dialog = new LogOutDialog(PersonalMainActivity.this, R.string.log_out, new LogOutDialog.OnSureBtnClickListener() {
                    @Override
                    public void sure() {
                        SPUtil.putString(context, SALE_ID, "");//用户ID
                        SPUtil.putString(context, PHONE_NUMBER, "");//手机号码
                        ToastUtils.makeText(context, "已安全退出账号");
                        AppManager.finishAllActivity();
                        MyApplication.openActivity(context, LoginActivity.class);
                    }
                });
                dialog.show();
                break;
        }
    }

    private void showChoosePicDialog() {
        builder = new AlertDialog.Builder(context, R.style.Dialog).create(); // 先得到构造器
        builder.show();
        LayoutInflater factory = LayoutInflater.from(context);
        View view = factory.inflate(R.layout.dialog_photo_upload, null);
        builder.getWindow().setContentView(view);
        view.findViewById(R.id.tv_album).setOnClickListener(this);
        view.findViewById(R.id.tv_photograph).setOnClickListener(this);
        view.findViewById(R.id.tv_cancel).setOnClickListener(this);
        Window dialogWindow = builder.getWindow();
        dialogWindow.setWindowAnimations(R.style.Dialog);
        dialogWindow.setGravity(Gravity.BOTTOM);//显示在底部
        WindowManager m = getWindowManager();
        Display display = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        Point size = new Point();
        display.getSize(size);
        p.width = size.x;
        dialogWindow.setAttributes(p);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_album://相册
                autoObtainStoragePermission();
                builder.dismiss();
                break;
            case R.id.tv_photograph://拍照
                autoObtainCameraPermission();
                builder.dismiss();
                break;
            case R.id.tv_cancel://取消
                builder.dismiss();
                break;
        }
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ToastUtils.makeText(this, "您已经拒绝过一次");

            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(PersonalMainActivity.this, "com.empowerment.salesrobot.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtil.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                ToastUtils.makeText(this, "设备没有SD卡！");
            }
        }
    }

    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtil.openPic(this, CODE_GALLERY_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(PersonalMainActivity.this, "com.empowerment.salesrobot.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtil.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        ToastUtils.makeText(this, "设备没有SD卡！");
                    }
                } else {
                    ToastUtils.makeText(this, "请允许打开相机！！");
                }
                break;
            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtil.openPic(this, CODE_GALLERY_REQUEST);
                } else {
                    ToastUtils.makeText(this, "请允许打操作SDCard！！");
                }
                break;
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    private static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private static final int output_X = 480;
    private static final int output_Y = 480;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtil.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtil.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this, "com.empowerment.salesrobot.fileprovider", new File(newUri.getPath()));
                        PhotoUtil.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else {
                        ToastUtils.makeText(this, "设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtil.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        showImages(bitmap);
                    }
                    break;
            }
        }
    }

    private void showImages(Bitmap bitmap) {
        mUserIcon.setImageBitmap(bitmap);
        String mPicture = ImageUtil.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        setdata(mPicture);
    }

    private void setdata(String mPicture) {
        Map<String, String> params = new HashMap<>();
        params.put(SALE_ID, SPUtil.getString(context, SALE_ID));
        params.put(SALE_ID, "1");
        File file = new File(mPicture);
        dialog.show();
        OkHttpUtils.post().url(Url.FILE_IMG).params(params).addFile(IMAGE, file.getName(), file).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                dialog.dismiss();
                ToastUtils.makeText(context, e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i("TAG", "onResponse: " + response);
                dialog.dismiss();
                ToastUtils.makeText(context, "修改成功！");
            }
        });
    }


    class clearCache implements Runnable {
        @Override
        public void run() {
            try {

                DataCleanManager.clearAllCache(PersonalMainActivity.this);
                Thread.sleep(3000);
                if (DataCleanManager.getTotalCacheSize(PersonalMainActivity.this).startsWith("O")) {
                    handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                return;
            }
        }
    }

}
