package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.uitls.GlideUtils;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.empowerment.salesrobot.view.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.M_ADDRESS;
import static com.empowerment.salesrobot.config.BaseUrl.M_CONTENT;
import static com.empowerment.salesrobot.config.BaseUrl.M_ID;
import static com.empowerment.salesrobot.config.BaseUrl.M_IDCARD;
import static com.empowerment.salesrobot.config.BaseUrl.M_NAME;
import static com.empowerment.salesrobot.config.BaseUrl.M_PHONE;
import static com.empowerment.salesrobot.config.BaseUrl.M_SEX;
import static com.empowerment.salesrobot.config.BaseUrl.M_WORK;
import static com.empowerment.salesrobot.config.Url.HTTP;


/**
 * 客户资料详情----编辑
 */
public class EditActivity extends BaseActivity {

    public Map<String, String> map = new HashMap<>();
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_OK)
    TextView titleOK;
    @BindView(R.id.title_Layout)
    RelativeLayout titleLayout;
    @BindView(R.id.editPic)
    RoundedImageView editPic;
    @BindView(R.id.edit_Name)
    TextView editName;
    @BindView(R.id.rl_data_list)
    RelativeLayout rlDatas;
    @BindView(R.id.edit_Time)
    TextView editTime;
    @BindView(R.id.tv_customer_coming_times)
    TextView tvTimes;
    @BindView(R.id.editNames)
    EditText editNames;
    @BindView(R.id.editAge)
    EditText editAge;
    @BindView(R.id.editSex)
    EditText editSex;
    @BindView(R.id.editWork)
    EditText editWork;
    @BindView(R.id.editAddses)
    EditText editAddses;
    @BindView(R.id.edit_IdCred)
    EditText editIdCred;
    @BindView(R.id.editPhone)
    EditText editPhone;
    @BindView(R.id.edit_Content)
    EditText editContent;
    @BindView(R.id.ll_vip_reason)
    LinearLayout mReason;
    @BindView(R.id.edit_vip_reason)
    EditText editReason;
    private String type;//1、VIP客户2、其他
    private String reason ="";
    private String mAddress,mAge,mContent,mData,mId,mIdCard,mName,mPhone,mPic,mSex,mWork,mCount;
    private ArrayList<Parcelable> datas;
    @Override
    protected int getLauoutId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void initView() {
        titleBack.setVisibility(View.VISIBLE);
        titleOK.setVisibility(View.VISIBLE);
        titleOK.setText("编辑");
        type = getIntent().getStringExtra("type");
        mAddress = getIntent().getStringExtra("mAddress");
        mAge = getIntent().getStringExtra("mAge");
        mContent = getIntent().getStringExtra("mContent");
        mData = getIntent().getStringExtra("mData");
        mId = getIntent().getStringExtra("mId");
        mIdCard = getIntent().getStringExtra("mIdCard");
        mName = getIntent().getStringExtra("mName");
        mPhone = getIntent().getStringExtra("mPhone");
        mPic = getIntent().getStringExtra("mPic");
        mSex = getIntent().getStringExtra("mSex");
        mWork = getIntent().getStringExtra("mWork");
        mCount = getIntent().getStringExtra("mCount");
        datas = getIntent().getParcelableArrayListExtra("datas");
        if (type.equals("1")){
            title.setText("VIP客户");
            mReason.setVisibility(View.VISIBLE);
        }else if (type.equals("2")){
            title.setText("预成交客户");
            mReason.setVisibility(View.GONE);
        }else if (type.equals("3")){
            title.setText("普通客户");
            mReason.setVisibility(View.GONE);
        }
        setEditText(editNames, false);
        setEditText(editAge, false);
        setEditText(editSex, false);
        setEditText(editAddses, false);
        setEditText(editContent, false);
        setEditText(editIdCred, false);
        setEditText(editPhone, false);
        setEditText(editWork, false);
        titleLayout.setBackgroundColor(getResources().getColor(R.color.colorTransParent));
    }

    @Override
    protected void loadData() {
        editName.setText(mName);
        editNames.setText(mName);
        editAge.setText(mAge);
        editWork.setText(mWork);
        editAddses.setText(mAddress);
        editIdCred.setText(mIdCard);
        editPhone.setText(mPhone);
        editContent.setText(mContent);
        editTime.setText(mData);
        tvTimes.setText(mCount);
        GlideUtils.imageLoader(context,HTTP+mPic,editPic);
        if (mSex.equals("0")) {
            editSex.setText("男");
        } else {
            editSex.setText("女");
        }
    }

    //是否可编辑
    private void setEditText(EditText editText, boolean b) {
        editText.setFocusableInTouchMode(b);
        editText.setFocusable(b);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.title_OK,R.id.rl_data_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.title_OK:
                //编辑 保存
                switch (titleOK.getText().toString()) {
                    case "编辑":
                        titleOK.setText("保存");
                        setEditText(editNames, true);
                        setEditText(editAge, true);
                        setEditText(editSex, true);
                        setEditText(editAddses, true);
                        setEditText(editContent, true);
                        setEditText(editIdCred, true);
                        setEditText(editPhone, true);
                        setEditText(editWork, true);
                        break;
                    case "保存":
                        mName = editNames.getText().toString().trim();
                        mPhone = editPhone.getText().toString().trim();
                        mIdCard = editIdCred.getText().toString().trim();
                        mWork = editWork.getText().toString().trim();
                        mAddress = editAddses.getText().toString().trim();
                        mContent = editContent.getText().toString().trim();
                        if (mName.isEmpty()){
                            ToastUtils.makeText(context,"客户姓名不能为空");
                            return;
                        }
                        if (editSex.getText().toString().trim().isEmpty()){
                            ToastUtils.makeText(context,"客户性别不能为空");
                            return;
                        }
//                        if (mWork.isEmpty()){
//                            ToastUtils.makeText(context,"客户工作不能为空");
//                            return;
//                        }
//                        if (mAddress.isEmpty()){
//                            ToastUtils.makeText(context,"客户地址不能为空");
//                            return;
//                        }
//                        if (mIdCard.isEmpty()){
//                            ToastUtils.makeText(context,"客户身份证不能为空");
//                            return;
//                        }
                        if (mPhone.isEmpty()){
                            ToastUtils.makeText(context,"客户手机号不能为空");
                            return;
                        }
                        if (type.equals("1")){
                            if (editReason.getText().toString().trim().isEmpty()){
                                ToastUtils.makeText(context,"修改vip客户的理由不能为空");

                            }else {
                                reason = editReason.getText().toString().trim();
                            }
                        }else {
                            reason = "";
                        }
                        submitModify();
                        break;
                }
                break;
            case R.id.rl_data_list:
                Bundle bundle = new Bundle();
                bundle.putSerializable("datas",datas);
                bundle.putString("mName",mName);
                MyApplication.openActivity(context,CustomerToStoreTimesActivity.class,bundle);
                break;
        }
    }

    private void submitModify() {
        if (editSex.getText().toString().equals("男")) {
            mSex = "0";
        } else {
            mSex = "1";
        }
        map.put(M_SEX, mSex);
        map.put(M_ID, mId);
        map.put(M_NAME, mName);
        map.put(M_PHONE, mPhone);
        map.put(M_IDCARD, mIdCard);
        map.put(M_WORK, mWork);
        map.put(M_ADDRESS, mAddress);
        map.put(M_CONTENT, mContent);
        map.put("reason", reason);
        MyOkhttp.Okhttp(context, Url.EDIT_CUSTOMER, "保存中...", map, (response, result, resultNote) -> {
            switch (result) {
                case "0":
                    ToastUtils.makeText(context, resultNote);
                    titleOK.setText("编辑");
                    setEditText(editNames, false);
                    setEditText(editAge, false);
                    setEditText(editSex, false);
                    setEditText(editAddses, false);
                    setEditText(editContent, false);
                    setEditText(editIdCred, false);
                    setEditText(editPhone, false);
                    setEditText(editWork, false);
                    break;
                case "1":
                    ToastUtils.makeText(context, resultNote);
                    break;
            }
        });
    }
}
