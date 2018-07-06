package com.empowerment.salesrobot.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.empowerment.salesrobot.dialog.ProgressDialog;


/**
 * 所有Fragment都继承此类
 */

public abstract class BaseFragment extends Fragment {
    protected Bundle bundle;
    protected Context context;
    protected Dialog dialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (dialog == null){
            dialog = ProgressDialog.createLoadingDialog(context, "加载中.....");
        }
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
