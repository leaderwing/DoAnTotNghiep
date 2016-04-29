package com.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.quy2016.doantotnghiep.R;
import com.model.ProfileUser;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/**
 * Created by Administrator on 4/21/2016.
 */
public class FriendListAdapter extends ParseQueryAdapter<ProfileUser> {

    public FriendListAdapter(Context context) {
        super(context, new QueryFactory<ProfileUser>() {
            public ParseQuery<ProfileUser> create() {
                // Here we can configure a ParseQuery to display
                // only top-rated meals.
                ParseQuery query = new ParseQuery("user_details");
                //query.whereContainedIn("rating", Arrays.asList("5", "4"));
                //query.orderByDescending("rating");
                return query;
            }
        });
    }

    @Override
    public View getItemView(ProfileUser object, View v, ViewGroup parent) {
        //return super.getItemView(object, v, parent);
        if(v==null)
        {
            v = View.inflate(getContext(), R.layout.list_item_friend_row,null);
        }
        super.getItemView(object,v,parent);
        ParseImageView profile = (ParseImageView) v.findViewById(R.id.avatar);
        ParseFile photoFile = object.getPhotoFile();
        if (photoFile != null) {
            profile.setParseFile(photoFile);
            profile.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }
        TextView userName = (TextView) v.findViewById(R.id.user_name);
        userName.setText(object.getAuthorName());
        TextView school = (TextView) v.findViewById(R.id.user_school);
        school.setText(object.getSchool());
        ImageView status = (ImageView) v.findViewById(R.id.arrow);
        if(object.getBoolean("Online"))
            status.setImageResource(R.drawable.online);
        else
            status.setImageResource(R.drawable.off);

        return v;

    }
}
