package com.empowerment.salesrobot.ui.activity;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.listener.RecyclerItemTouchListener;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.InformationAdapter;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.ui.model.InformationEntity;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.example.liangmutian.mypicker.DatePickerDialog;
import com.example.liangmutian.mypicker.DateUtil;
import com.example.xrecyclerview.XRecyclerView;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.C_TYPE;
import static com.empowerment.salesrobot.config.BaseUrl.KEY_WORD;
import static com.empowerment.salesrobot.config.BaseUrl.PAGE;
import static com.empowerment.salesrobot.config.BaseUrl.ROWS;
import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.TYPE;


/**
 * 客户资料***详情
 */
public class InformationActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_OK)
    TextView titleOK;
    @BindView(R.id.information_a_key_edt_search)
    EditText mEditKey;
    @BindView(R.id.tv_data)
    TextView mData;
    @BindView(R.id.tv_work)
    TextView mWork;
    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.tv_car_style)
    TextView mCarStyle;
    @BindView(R.id.tv_address)
    TextView mAddress;
    @BindView(R.id.ll_search)
    LinearLayout mLLSearch;
    @BindView(R.id.ll_labels)
    LinearLayout mLabels;
    @BindView(R.id.information_ListView)
    XRecyclerView xRecyclerView;
    @BindView(R.id.infoLayouts)
    LinearLayout infoLayouts;
    private TextView[] mTextView;
    private InformationAdapter mAdapter;
    private List<InformationEntity.DataBean.CustomerListBean> infoList = new ArrayList<>();//实例化标签对象集合;
    private int nowPage = 1;
    private int rows = 10;
    private String type = "2";
    private String cType;
    private String keyWord = "";
    private  String dateString;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_information;
    }

    protected void getdata() {
        Map<String, String> cusMap = new HashMap<>();
        cusMap.put(TYPE, type);
        cusMap.put(KEY_WORD, keyWord);
        cusMap.put(C_TYPE, cType);
        cusMap.put(ROWS, rows+"");
        cusMap.put(PAGE, String.valueOf(nowPage));
        cusMap.put(SALE_ID, SPUtil.getString(context,SALE_ID));
        cusMap.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        MyOkhttp.Okhttp(context, Url.CUSTOMER, "加载中...", cusMap, (response, result, resultNote) -> {
            Log.i("", "getdata: "+response);
            Gson gson = new Gson();
            InformationEntity informationEntity = gson.fromJson(response, InformationEntity.class);
            if (result.equals("1")){
                ToastUtils.makeText(context,resultNote);
                xRecyclerView.setVisibility(View.GONE);
                infoLayouts.setVisibility(View.VISIBLE);
                return;
            }
            List<InformationEntity.DataBean.CustomerListBean> customerListBeans = informationEntity.getData().getCustomerList();
            if (customerListBeans != null && !customerListBeans.isEmpty() && customerListBeans.size() > 0){
                xRecyclerView.setVisibility(View.VISIBLE);
                infoLayouts.setVisibility(View.GONE);
                infoList.addAll(customerListBeans);
                mAdapter.notifyDataSetChanged();
            }else {
                ToastUtils.makeText(context, resultNote);
                xRecyclerView.setVisibility(View.GONE);
                infoLayouts.setVisibility(View.VISIBLE);
            }
            if (customerListBeans.size() < rows){
                xRecyclerView.noMoreLoading();
            }
            xRecyclerView.refreshComplete();
        });
    }

    @Override
    protected void loadData() {

    }

    private void showDateDialog(List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(this);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                mEditKey.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])));
                keyWord = mEditKey.getText().toString().trim();
                infoList.clear();
                mAdapter.notifyDataSetChanged();
                dialog.dismiss();
                getdata();//往期根据时间查询
            }

            @Override
            public void onCancel() {
                dialog.dismiss();
            }
        }).setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1);

        builder.setMaxYear(DateUtil.getYear());
        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void initView() {
        title.setText(getIntent().getStringExtra("title"));
        titleBack.setVisibility(View.VISIBLE);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new InformationAdapter(context, infoList);
        xRecyclerView.setAdapter(mAdapter);
        mTextView = new TextView[5];
        mTextView[0] = mData;
        mTextView[1] = mWork;
        mTextView[2] = mName;
        mTextView[3] = mCarStyle;
        mTextView[4] = mAddress;
        if (title.getText().toString().equals("今日客户")) {
            cType = "1";
            mData.setVisibility(View.GONE);
            titleOK.setVisibility(View.VISIBLE);
            titleOK.setText("添加");
        } else {
            cType = "2";
            mData.setVisibility(View.VISIBLE);
            titleOK.setVisibility(View.GONE);
        }
        currentFocus(1);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                nowPage = 1;
                infoList.clear();
                mAdapter.notifyDataSetChanged();
                getdata();
            }

            @Override
            public void onLoadMore() {
                nowPage++;
                getdata();
            }
        });

        mEditKey.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (TextUtils.isEmpty(mEditKey.getText().toString().trim())) {
                    ToastUtils.makeText(context, "请输入关键词");
                } else {
                    keyWord = mEditKey.getText().toString().trim();
                    infoList.clear();
                    mAdapter.notifyDataSetChanged();
                    getdata();
                }
                return true;
            }
            return false;
        });

        xRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(xRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition() - 1;
                if (position < 0 | position >= infoList.size()) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("mAddress",infoList.get(position).getAddress());
                bundle.putString("mAge",infoList.get(position).getAge()+"");
                bundle.putString("mContent",infoList.get(position).getContent());
                bundle.putString("mData",infoList.get(position).getDate());
                bundle.putString("mId",infoList.get(position).getId()+"");
                bundle.putString("mIdCard",infoList.get(position).getIdCard());
                bundle.putString("mName",infoList.get(position).getName());
                bundle.putString("mPhone",infoList.get(position).getPhone());
                bundle.putString("mPic",infoList.get(position).getPic());
                bundle.putString("mSex",infoList.get(position).getSex()+"");
                bundle.putString("mWork",infoList.get(position).getWork());
                bundle.putString("mCount",infoList.get(position).getCount()+"");
                bundle.putSerializable("datas", (Serializable) infoList.get(position).getDates());
                bundle.putString("type",infoList.get(position).getType()+"");

                MyApplication.openActivity(context, EditActivity.class, bundle);
            }
        });

        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        date = calendar.getTime();
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy" + "-" + "MM" + "-" + "dd", Locale.CHINA);
        dateString = formatter2.format(date);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.title_OK, R.id.tv_data, R.id.tv_work, R.id.tv_name, R.id.tv_car_style, R.id.tv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.title_OK:
                Bundle bundle = new Bundle();
                bundle.putString("mStyle","");
                MyApplication.openActivity(context,AddCustomerActivity.class,bundle);
                break;
            case R.id.tv_data:
                type = "1";//拿到日期标签索引
                currentFocus(0);
                showDateDialog(DateUtil.getDateForString(dateString));//输入框赋值
                break;
            case R.id.tv_work:
                type = "2";//拿到工作标签索引
                keyWord = mEditKey.getText().toString().trim();
                currentFocus(1);
                infoList.clear();
                mAdapter.notifyDataSetChanged();
                getdata();
                break;
            case R.id.tv_name:
                type = "3";//拿到姓名标签索引
                keyWord = mEditKey.getText().toString().trim();
                currentFocus(2);
                infoList.clear();
                mAdapter.notifyDataSetChanged();
                getdata();
                break;
            case R.id.tv_car_style:
                type = "4";//拿到车型标签索引
                keyWord = mEditKey.getText().toString().trim();
                currentFocus(3);
                infoList.clear();
                mAdapter.notifyDataSetChanged();
                getdata();
                break;
            case R.id.tv_address:
                type = "5";//拿到区域标签索引
                keyWord = mEditKey.getText().toString().trim();
                currentFocus(4);
                infoList.clear();
                mAdapter.notifyDataSetChanged();
                getdata();
                break;
        }
    }

    private void currentFocus(int position) {
        Resources resource = context.getResources();
        ColorStateList csl1 = resource.getColorStateList(R.color.vipTextColor);
        ColorStateList csl2 = resource.getColorStateList(R.color.yellow);
        for (int i = 0; i < mTextView.length; i++) {
            if (i == position) {
                mTextView[i].setTextColor(csl2);
            } else {
                mTextView[i].setTextColor(csl1);
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        nowPage = 1;
        infoList.clear();
        mAdapter.notifyDataSetChanged();
        getdata();
    }
}
