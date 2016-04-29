package com.Utils;

/**
 * Created by Administrator on 4/21/2016.
 */
import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hust.chat.Conversation;
import com.model.Post_Info;
import com.model.ProfileUser;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(ProfileUser.class);
        ParseObject.registerSubclass(Post_Info.class);
        ParseObject.registerSubclass(Conversation.class);
        Parse.initialize(this, "ZVuYQgGmT79jqb4HpbbabGz8xenRTXaYI30cCTDM", "Zrw7TkZIy1jGGY4JEGl0MAtakRBLXCeIMnF4qjrQ");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
