package com.empowerment.salesrobot.okhttp.budiler;


import java.io.IOException;
import java.util.Objects;

import okhttp3.Response;

/**
 * Created by 小火
 * Create time on  2017/3/22
 * My mailbox is 1403241630@qq.com
 */
public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response, int id) throws IOException
    {
        return Objects.requireNonNull(response.body()).string();
    }
}
