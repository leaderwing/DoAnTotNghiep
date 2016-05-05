package com.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by QUY2016 on 5/2/2016.
 */
@ParseClassName("User_comments")
public class Comments extends ParseObject {
    public  Comments()
    {

    }
    public ParseUser getUser()
    {
        return getParseUser("user");
    }
    public void setUser(ParseUser user)
    {
        put("user",user);
    }
    public String getComment()
    {
        return getString("Comment");
    }
    public  void setComment(String comment)
    {
        put("Comment",comment);
    }
    public ParseObject getPost()
    {
        return getParseObject("post");
    }
    public void setPost(ParseObject post)
    {
        put("post",post);
    }

}
