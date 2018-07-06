package com.empowerment.salesrobot.okhttp.budiler;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class Callback<T>
{
    /**
     * UI Thread
     * @param request
     * @param id
     */
    public void onBefore(Request request, int id)
    {
    }

    /**
     * UI Thread
     *
     * @param id
     */
    public void onAfter(int id)
    {
    }

    /**
     * UI Thread
     *
     * @param progress
     * @param total
     * @param id
     */
    public void inProgress(float progress, long total , int id)
    {

    }

    /**
     * if you parse response code in parseNetworkResponse, you should make this method return true.
     *
     * @param response
     * @param id
     * @return
     */
    public boolean validateResponse(Response response, int id)
    {
        return response.isSuccessful();
    }

    /**
     * Thread Pool Thread
     *
     * @param response
     * @param id
     */
    public abstract T parseNetworkResponse(Response response, int id) throws Exception;

    public abstract void onError(Call call, Exception e, int id);

    public abstract void onResponse(T response, int id);


    public static Callback CALLBACK_DEFAULT = new Callback()
    {

        @Override
        public Object parseNetworkResponse(Response response, int id) throws Exception
        {
            return null;
        }

        @Override
        public void onError(Call call, Exception e, int id)
        {

        }

        @Override
        public void onResponse(Object response, int id)
        {

        }
    };

}