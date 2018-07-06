package com.empowerment.salesrobot.okhttp.budiler.budiler;

import android.text.TextUtils;

import com.empowerment.salesrobot.okhttp.OkHttpUtils;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;

/**
 * Created by 小火
 * Create time on  2017/3/22
 * My mailbox is 1403241630@qq.com
 */
public class OtherRequest extends OkHttpRequest
{
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");

    private RequestBody requestBody;
    private String method;
    private String content;

    public OtherRequest(RequestBody requestBody, String content, String method, String url, Object tag, Map<String, String> params, Map<String, String> headers, int id)
    {
        super(url, tag, params, headers,id);
        this.requestBody = requestBody;
        this.method = method;
        this.content = content;

    }

    @Override
    protected RequestBody buildRequestBody()
    {
        if (requestBody == null && TextUtils.isEmpty(content) && HttpMethod.requiresRequestBody(method))
        {
            Exceptions.illegalArgument("requestBody and content can not be null in method:" + method);
        }

        if (requestBody == null && !TextUtils.isEmpty(content))
        {
            requestBody = RequestBody.create(MEDIA_TYPE_PLAIN, content);
        }

        return requestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody)
    {
        switch (method)
        {
            case OkHttpUtils.METHOD.PUT:
                builder.put(requestBody);
                break;
            case OkHttpUtils.METHOD.DELETE:
                if (requestBody == null) builder.delete();
                else builder.delete(requestBody);
                break;
            case OkHttpUtils.METHOD.HEAD:
                builder.head();
                break;
            case OkHttpUtils.METHOD.PATCH:
                builder.patch(requestBody);
                break;
            default:
                break;
        }
        return builder.build();
    }

}
