package com.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by QUY2016 on 5/3/2016.
 */
@ParseClassName("User_friends")
public class UserFriends extends ParseObject {
    public  UserFriends()
    {

    }
    public ParseUser getFromUser()
    {
        return getParseUser("from_user");
    }
    public void setFromUser(ParseUser fromUser)
    {
        put("from_user",fromUser);
    }
    public ParseUser getToUser()
    {
        return getParseUser("to_user");
    }
    public void setToUser(ParseUser fromUser)
    {
        put("to_user",fromUser);
    }
    public  String getRelation()
    {
        return getString("relation");
    }
    public void setRelation(String relation)
    {
        put("relation",relation);
    }

}
