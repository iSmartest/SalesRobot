package com.empowerment.salesrobot.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.app.MyApplication;
import com.empowerment.salesrobot.config.Url;
import com.empowerment.salesrobot.listener.RecyclerItemTouchListener;
import com.empowerment.salesrobot.okhttp.MyOkhttp;
import com.empowerment.salesrobot.ui.adapter.BrandAdapter;
import com.empowerment.salesrobot.ui.base.BaseActivity;
import com.empowerment.salesrobot.ui.model.ProductSalesBean;
import com.empowerment.salesrobot.uitls.GlideUtils;
import com.empowerment.salesrobot.uitls.SPUtil;
import com.empowerment.salesrobot.uitls.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.empowerment.salesrobot.config.BaseUrl.SALE_ID;
import static com.empowerment.salesrobot.config.BaseUrl.STORE_ID;
import static com.empowerment.salesrobot.config.Url.HTTP;

/**
 * 产品销售
 */
public class ProductSalesActivity extends BaseActivity {
    @BindView(R.id.title_Back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recycler_brand)
    RecyclerView recyclerView;
    @BindView(R.id.mWord_Layout)
    LinearLayout mWordLayout;
    @BindView(R.id.mVido_Layout)
    LinearLayout mVidoLayout;
    @BindView(R.id.mAudi)
    ImageView mAudi;
    private String name,link;
    private BrandAdapter mAdapter;
    private List<ProductSalesBean.DataBean.BList> mList = new ArrayList<>();
    @Override
    protected int getLauoutId() {
        return R.layout.activity_product_sales;
    }

    @Override
    protected void loadData() {
        mList.clear();
        mAdapter.notifyDataSetChanged();
        Map<String,String> params = new HashMap<>();
        params.put(STORE_ID, SPUtil.getString(context,STORE_ID));
        params.put(SALE_ID,SPUtil.getString(context,SALE_ID));
        MyOkhttp.Okhttp(context, Url.PRODUCTSALE, "加载中...", params, (response, result, resultNote) -> {
            Gson gson = new Gson();
            Log.i("TAG", "loadData: " + response);
            ProductSalesBean productSalesBean = gson.fromJson(response,ProductSalesBean.class);
            if (result.equals("1")){
                ToastUtils.makeText(context,resultNote);
                return;
            }
            List<ProductSalesBean.DataBean.BList> brandLists = productSalesBean.getData().getBrandList();
            if (brandLists != null && !brandLists.isEmpty() && brandLists.size() > 0){
                mList.addAll(brandLists);
                mAdapter.notifyDataSetChanged();
            }

            String image = HTTP + productSalesBean.getData().getImage().getAddress();
            name = productSalesBean.getData().getImage().getName();
            link = productSalesBean.getData().getImage().getLink();
            GlideUtils.imageLoader(context,image,mAudi);
        });
    }

    @Override
    protected void initView() {
        title.setText("产品销售");
        titleBack.setVisibility(View.VISIBLE);
        mAdapter = new BrandAdapter(context,mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, OrientationHelper.HORIZONTAL,false));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getLayoutPosition();
                if (position < 0 | position >= mList.size()) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("mTitle", mList.get(position).getName());
                bundle.putString("brandId", mList.get(position).getId());
                MyApplication.openActivity(context,CarBrandActivity.class,bundle);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_Back,R.id.mWord_Layout, R.id.mVido_Layout, R.id.mAudi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_Back:
                finish();
                break;
            case R.id.mWord_Layout:
                MyApplication.openActivity(context,TrainingDocumentsActivity.class);
                break;
            case R.id.mVido_Layout:
                MyApplication.openActivity(context,TrainingVideoActivity.class);
                break;
            case R.id.mAudi:
                Bundle bundle = new Bundle();
                bundle.putString("mTitle",name);
                bundle.putString("mLink", HTTP+link);
                MyApplication.openActivity(context,ContentInfoActivity.class,bundle);
                break;
        }
    }
}
