package com.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;


/**
 * Created by Administrator on 4/25/2016.
 */
@ParseClassName("Post_Info")
public class Post_Info extends ParseObject {
    public Post_Info() {
        super();
    }
    public ParseUser getUser()
    {
        return getParseUser("user");
    }
    public void setUser(ParseUser user)
    {
        put("user",user);
    }
    public String getCourse() {
        return getString("Course");
    }

    public void setCourse(String course) {
        put("Course", course);
    }

    public String getCategory() {
        return getString("Category");
    }

    public void setCategory(String category) {
        put("Category", category);
    }

    public String getTitle() {
        return getString("Title");
    }

    public void setTitle(String title) {
        put("Title", title);
    }
    public String getDescribe()
    {
        return getString("Describe");
    }
    public  void setDescribe(String describe)
    {
        put("Describe",describe);
    }
    public int getNumberView() {
        return getInt("Post_Num_View");
    }
    public void setNumberView(int numberView) {
        put("Post_Num_View", numberView);
    }
    public void setNumberPost(int numberPost) {
        put("Post_Num_Comment", numberPost);
    }
    public int getNumberPost() {
        return getInt("Post_Num_Comment");
    }

    public Date getDate() {
        return getCreatedAt();
    }
    public void setDate(Date date)
    {
        put("createdAt",date);
    }


}