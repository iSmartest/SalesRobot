package com.empowerment.salesrobot.ui.activity;

import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.listener.RecyclerItemTouchListener;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.InformationAdapter;
import com.empowerment.salesrobot.ui.model.InfromationEntity;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.example.liangmutian.mypicker.DatePickerDialog;
import com.example.liangmutian.mypicker.DateUtil;
import com.example.xrecyclerview.XRecyclerView;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.C_TYPE;
import static com.empowerment.salesrobot.config.BaseUrl.KEY_WORD;
import static com.empowerment.salesrobot.config.BaseUrl.PAGE;
import static com.empowerment.salesrobot.config.BaseUrl.ROWS;
import static com.empowerment.salesrobot.config.BaseUrl.TYPE;


/**
 * 客户资料***详情
 */
public class InformationActivity extends BaseActivity {

    private static final String TAG = "InformationActivity";
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
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
    XRecyclerView informationListView;
    @BindView(R.id.infoLayouts)
    LinearLayout infoLayouts;
    private TextView[] mTextView;
    private Map<String, String> cusMap;
    private InformationAdapter infromationAdapter;
    private List<InfromationEntity.DataBean.CustomerListBean> infoList;
    private int PAGR_SIZE = 1;
    private String type = "2";
    private String cType;
    private String keyWord = "";

    @Override
    protected int getLauoutId() {
        return R.layout.activity_information;
    }

    @Override
    protected void loadData() {

        mEditKey.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(mEditKey.getText().toString().trim())) {
                        ToastUtils.makeText(context, "请输入关键词");
                    } else {
                        keyWord = mEditKey.getText().toString().trim();
                        getdata();
                    }
                    return true;
                }
                return false;
            }
        });

        informationListView.addOnItemTouchListener(new RecyclerItemTouchListener(informationListView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition()-1;
                if (position < 0 | position >= infoList.size()) {
                    return;
                }
                Bundle bundle = new Bundle();
                InfromationEntity.DataBean.CustomerListBean listBean = new InfromationEntity.DataBean.CustomerListBean();
                listBean.setAddress(infoList.get(position).getAddress());
                listBean.setAge(infoList.get(position).getAge());
                listBean.setCarType(infoList.get(position).getCarType());
                listBean.setContent(infoList.get(position).getContent());
                listBean.setDate(infoList.get(position).getDate());
                listBean.setHobby(infoList.get(position).getHobby());
                listBean.setId(infoList.get(position).getId());
                listBean.setIdCard(infoList.get(position).getIdCard());
                listBean.setName(infoList.get(position).getName());
                listBean.setPhone(infoList.get(position).getPhone());
                listBean.setPic(infoList.get(position).getPic());
                listBean.setSex(infoList.get(position).getSex());
                listBean.setWork(infoList.get(position).getWork());
                bundle.putString("info", new Gson().toJson(listBean));
                bundle.putString("title", title.getText().toString());
                MyApplication.openActivity(context, EditActivity.class, bundle);

            }
        });
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
        infoList = new ArrayList<>();//实例化标签对象集合
        informationListView.setLayoutManager(new LinearLayoutManager(context));
        infromationAdapter = new InformationAdapter(context, infoList);
        informationListView.setAdapter(infromationAdapter);
        mTextView = new TextView[5];
        mTextView[0] = mData;
        mTextView[1] = mWork;
        mTextView[2] = mName;
        mTextView[3] = mCarStyle;
        mTextView[4] = mAddress;
        if (title.getText().toString().equals("今日客户")) {
            cType = "1";
            mData.setVisibility(View.GONE);
        } else {
            cType = "2";
            mData.setVisibility(View.VISIBLE);
        }
        currentFocus(1);
        getdata();//开启网络请求*往期或者今日客户

        informationListView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                PAGR_SIZE = 1;
                infoList.clear();
                infromationAdapter.notifyDataSetChanged();
                getdata();
            }

            @Override
            public void onLoadMore() {
                PAGR_SIZE++;
                getdata();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back, R.id.tv_data, R.id.tv_work, R.id.tv_name, R.id.tv_car_style, R.id.tv_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.tv_data:
                type = "1";//拿到日期标签索引
                currentFocus(0);
                showDateDialog(DateUtil.getDateForString("1990-01-01"));//输入框赋值
                break;
            case R.id.tv_work:
                type = "2";//拿到日期标签索引
                keyWord = mEditKey.getText().toString().trim();
                currentFocus(1);
                infoList.clear();
                getdata();
                break;
            case R.id.tv_name:
                type = "3";//拿到日期标签索引
                keyWord = mEditKey.getText().toString().trim();
                currentFocus(2);
                infoList.clear();
                getdata();
                break;
            case R.id.tv_car_style:
                type = "4";//拿到日期标签索引
                keyWord = mEditKey.getText().toString().trim();
                currentFocus(3);
                infoList.clear();
                getdata();
                break;
            case R.id.tv_address:
                type = "5";//拿到日期标签索引
                keyWord = mEditKey.getText().toString().trim();
                currentFocus(4);
                infoList.clear();
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


    public void getdata() {
        cusMap = new HashMap<>();
        cusMap.put(TYPE, type);
        cusMap.put(KEY_WORD, keyWord);
        cusMap.put(C_TYPE, cType);
        cusMap.put(ROWS, "10");
        cusMap.put(PAGE, String.valueOf(PAGR_SIZE));
        MyOkhttp.Okhttp(context, Url.CUSTOMER, dialog, cusMap, new MyOkhttp.CallBack() {
            @Override
            public void onRequestComplete(String response, String result, String resultNote) {
                Gson gson = new Gson();
                InfromationEntity infromationEntity = gson.fromJson(response, InfromationEntity.class);
                switch (result) {
                    case "0":
                        if (infromationEntity.getMsg().equals("暂无数据")) {
                            ToastUtils.makeText(context, infromationEntity.getMsg());
                            informationListView.setVisibility(View.GONE);
                            infoLayouts.setVisibility(View.VISIBLE);
                        } else {
                            informationListView.setVisibility(View.VISIBLE);
                            infoLayouts.setVisibility(View.GONE);
                            infoList.addAll(infromationEntity.getData().getCustomerList());
                            infromationAdapter.notifyDataSetChanged();
                        }
                        break;
                    case "1":
                        ToastUtils.makeText(context, infromationEntity.getMsg());
                        informationListView.setVisibility(View.GONE);
                        infoLayouts.setVisibility(View.VISIBLE);
                        break;
                }

                informationListView.refreshComplete();
            }
        });
    }
}
