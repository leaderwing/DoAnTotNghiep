package com.example.quy2016.doantotnghiep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginDispatchActivity;

/**
 * Created by QUY2016 on 4/17/2016.
 */
public class DispatchActivity extends ParseLoginDispatchActivity {

    @Override
    protected Class<?> getTargetClass() {
        return MainActivity.class;
    }
}
