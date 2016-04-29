package com.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Administrator on 4/21/2016.
 */
@ParseClassName("user_details")
public class ProfileUser extends ParseObject {
    public ProfileUser()
    {

    }
    public String getAuthorName()
    {
        return getString("Name");
    }
    public  void setAuthorName(String user)
    {
        put("Name",user);
    }
    public  String getEmail()
    {
        return getString("Email");
    }
    public  void setEmail(String user)
    {
        put("Email",user);
    }
    public String getbjectId()
    {
        return getObjectId();
    }
    public String getCourse()
    {
        return getString("course");
    }
    public  void setCourse(String course)
    {
        put("course",course);
    }
    public String getSchool()
    {
        return getString("user_school");
    }
    public  void setSchool(String user_school)
    {
        put("user_school",user_school);
    }
    public String getBirthday()
    {
        return getString("user_birthday");
    }
    public  void setBirthday(String user_birthday)
    {
        put("user_birthday",user_birthday);
    }
    public String getHobbies()
    {
        return getString("user_hobbies");
    }
    public  void setHobbies(String user_hobbies)
    {
        put("user_hobbies",user_hobbies);
    }
    public String getCharacter()
    {
        return getString("user_char");
    }
    public  void setCharacter(String user_char)
    {
        put("user_char",user_char);
    }
    public String getAddress()
    {
        return getString("user_address");
    }
    public ParseFile getPhotoFile() {
        return getParseFile("user_avatar");
    }
    public void setPhotoFile(ParseFile file) {
        put("user_avatar", file);
    }
}
