package com.empowerment.salesrobot.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.empowerment.salesrobot.R;
import com.empowerment.salesrobot.dialog.ProgressDialog;
import com.empowerment.salesrobot.ui.fragment.BaseFragment;
import com.empowerment.salesrobot.uitls.AppManager;
import com.empowerment.salesrobot.uitls.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/7/2.
 * Description:
 */
public abstract class BaseActivity extends FragmentActivity{
    protected Context context;
    private BaseFragment lastFragment;
    protected Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(getLauoutId());
        ButterKnife.bind(this);
        dialog = ProgressDialog.createLoadingDialog(context, "加载中.....");
        StatusBarUtil.fullScreen(BaseActivity.this);
        AppManager.addActivity(this);
        initView();//实例化
        loadData();//加载数据
    }

    protected abstract int getLauoutId();

    protected abstract void loadData();

    protected abstract void initView();


    /**
     * 切换Fragment的方法
     *
     * @param fragmentClass 要跳转的Fragment
     * @param containId     容器ID
     * @param isHidden      是否隐藏
     * @param bundle        参数
     * @param isBack        是否添加到回退栈
     * @return
     */
    public BaseFragment changeFragment(Class<? extends BaseFragment> fragmentClass, int containId, boolean isHidden, Bundle bundle, boolean isBack) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //获取Fragment的类名，用类名当做Tag
        String fragmentName = fragmentClass.getSimpleName();
        //根据tag来查找当前Fragment，如果不为null 就代表当前Fragment已经被加载过至少一次
        BaseFragment currentFragment = (BaseFragment) manager.findFragmentByTag(fragmentName);
        if (currentFragment == null) {
            //如果Fragment为null 就创建Fragment对象，添加到FragmentManager中
            try {
                //通过Java动态代理创建的对象
                currentFragment = fragmentClass.newInstance();
                //添加到FragmentManager中
                transaction.add(containId, currentFragment, fragmentName);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (isHidden) {
            //隐藏上一个Fragment
            if (lastFragment != null)
                transaction.hide(lastFragment);
            //显示当前Fragment
            transaction.show(currentFragment);
        } else {
            //替换上一个Fragment
            transaction.replace(containId, currentFragment, fragmentName);
        }
        //传递参数
        if (bundle != null) {
            currentFragment.setBundle(bundle);
        }

        if (isBack) {
            transaction.addToBackStack(fragmentName);
        }

        transaction.commit();

        lastFragment = currentFragment;

        return lastFragment;

    }

}
