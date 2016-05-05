package com.hust.friend_find;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.example.quy2016.doantotnghiep.R;
import com.model.ProfileUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by QUY2016 on 5/3/2016.
 */
public class ProfileUserFriendFragment extends FragmentActivity {
    TextView txtUsername , txtUseremail , txtUserbirthday , txtUserschool , txtUsercourse , txtUseraddress, txtUserhobby , txtUsercharacter;
    String userId;
    ProfileUser profileUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_view_layout);
        txtUsername = (TextView) findViewById(R.id.tvNameUser);
        txtUseremail = (TextView) findViewById(R.id.tvEmailUser);
        txtUserbirthday = (TextView) findViewById(R.id.tvBirthUser);
        txtUserschool = (TextView) findViewById(R.id.tvSchoolUser);
        txtUsercourse = (TextView) findViewById(R.id.tvCourseUser);
        txtUseraddress = (TextView) findViewById(R.id.tvAddressUser);
        txtUserhobby = (TextView) findViewById(R.id.tvHobbyUser);
        txtUsercharacter = (TextView) findViewById(R.id.tvCharacterUser);
        Intent receiveData = getIntent();
        userId = receiveData.getStringExtra("userDetails");
        //txtUsername = (TextView) view.findViewById(R.id.tvNameUser);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("user_details");
        query.whereEqualTo("objectId",userId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                for (int i = 0;i < objects.size();i++)
                {
                    ParseObject po = objects.get(i);
                    //profileUser = new ProfileUser();
                    try {
                        txtUsername.setText(po.getParseUser("user").fetchIfNeeded().getString("name"));
                        txtUseremail.setText(po.getParseUser("user").fetchIfNeeded().getEmail());
                        txtUserbirthday.setText(po.getString("user_birthday"));
                        txtUserschool.setText(po.getString("user_school"));
                        txtUsercourse.setText(po.getString("course"));
                        txtUseraddress.setText(po.getString("user_address"));
                        txtUserhobby.setText(po.getString("user_hobbies"));
                        txtUsercharacter.setText(po.getString("user_char"));

                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    }

