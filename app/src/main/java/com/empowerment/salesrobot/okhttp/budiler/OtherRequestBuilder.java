package com.empowerment.salesrobot.okhttp.budiler;

import com.empowerment.salesrobot.okhttp.budiler.budiler.OkHttpRequestBuilder;
import com.empowerment.salesrobot.okhttp.budiler.budiler.OtherRequest;
import com.empowerment.salesrobot.okhttp.budiler.budiler.RequestCall;

import okhttp3.RequestBody;


/**
 * Created by 小火
 * Create time on  2017/3/22
 * My mailbox is 1403241630@qq.com
 */
public class OtherRequestBuilder extends OkHttpRequestBuilder<OtherRequestBuilder>
{
    private RequestBody requestBody;
    private String method;
    private String content;

    public OtherRequestBuilder(String method)
    {
        this.method = method;
    }

    @Override
    public RequestCall build()
    {
        return new OtherRequest(requestBody, content, method, url, tag, params, headers,id).build();
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody)
    {
        this.requestBody = requestBody;
        return this;
    }

    public OtherRequestBuilder requestBody(String content)
    {
        this.content = content;
        return this;
    }


}
