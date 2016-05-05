package com.utils;

import com.parse.ParseUser;

/**
 * Created by Administrator on 4/28/2016.
 */
public class Utils {

    public  static ParseUser user;

    public  static void UserStatus(boolean online)
    {
        user.put("online" , online);
        user.saveEventually();
    }

}
