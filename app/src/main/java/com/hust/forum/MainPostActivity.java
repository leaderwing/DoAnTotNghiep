package com.hust.forum;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.quy2016.doantotnghiep.R;

/**
 * Created by Administrator on 4/27/2016.
 */
public class MainPostActivity extends AppCompatActivity {
    TextView txtname ,txtCreated,txtSchool, txtTitle ,txtContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post_content);
        Intent getIntent = getIntent();
        Bundle getData = getIntent.getBundleExtra("sendData");
        txtname = (TextView) findViewById(R.id.postUser);
        txtCreated = (TextView) findViewById(R.id.createdDatePost);
        txtSchool = (TextView) findViewById(R.id.schoolPost);
        txtTitle = (TextView) findViewById(R.id.titlePost);
        txtContent = (TextView) findViewById(R.id.contentPost);
        txtname.setText(getData.getString("name"));
        txtTitle.setText(getData.getString("title"));
        txtContent.setText(getData.getString("content"));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar ;
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
