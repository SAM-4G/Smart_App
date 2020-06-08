package com.sam.kadarairkopi.preference;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySing {

    private static VolleySing mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private VolleySing(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleySing getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySing(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }
}
