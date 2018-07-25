package com.empowerment.salesrobot.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.dialog.ProgressDialog;
import com.empowerment.salesrobot.dialog.SelectedTimeDialog;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/12.
 * Description:
 */
public class NewAddAgencyAffairsActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_add_affairs_data)
    TextView mData;
    @BindView(R.id.tv_add_affairs_time)
    TextView mTime;
    @BindView(R.id.et_add_affairs_content)
    EditText mContent;
    @BindView(R.id.tv_add_affairs_save)
    TextView mSave;
    private final int DATE_DIALOG = 1;
    private int mYear, mMonth, mDay;
    private SelectedTimeDialog selectedTimeDialog;
    @Override
    protected int getLauoutId() {
        return R.layout.activity_new_add_agency_affairs;
    }

    @Override
    protected void loadData() {
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm",Locale.CHINA);
        mData.setText(format1.format(date)); //更新时间
        mTime.setText(format2.format(date)); //更新时间
    }

    @Override
    protected void initView() {
        title.setText("新建待办");
        titleBack.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.tv_add_affairs_data, R.id.tv_add_affairs_time,R.id.tv_add_affairs_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.tv_add_affairs_data:
                showDialog(DATE_DIALOG);
                break;
            case R.id.tv_add_affairs_time:
                selectedTimeDialog = new SelectedTimeDialog(context, R.string.start_time,new SelectedTimeDialog.OnSureBtnClickListener() {
                    @Override
                    public void sure(String title) {
                        mTime.setText(title);
                        selectedTimeDialog.dismiss();
                    }
                });
                selectedTimeDialog.show();
                break;
            case R.id.tv_add_affairs_save:
                String content = mContent.getText().toString().trim();
                String time = mData.getText().toString().trim() + " " + mTime.getText().toString().trim();
                if (content.isEmpty()){
                    ToastUtils.makeText(context,"请输入内容");
                    return;
                }
                saveNewAdd(time,content);
                break;
        }
    }
    private void saveNewAdd(String time,String content) {
        Map<String,String> params = new HashMap<>();
        params.put("endDate",time);
        params.put("content",content);
        params.put("sId", SPUtil.getString(context,SALE_ID));
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        MyOkhttp.Okhttp(context, Url.ADD_PERSONAL_AGENCY, "提交中...", params, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                if (result.equals("1")){
                    ToastUtils.makeText(context,resultNote);
                    return;
                }
                ToastUtils.makeText(context,resultNote);
                mContent.setHint("再添加一个待办事宜");
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    public void display() {
        mData.setText(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };
}
