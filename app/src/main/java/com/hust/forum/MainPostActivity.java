package com.hust.forum;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.utils.UserCommentAdapter;
import com.example.quy2016.doantotnghiep.R;
import com.model.Comments;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/27/2016.
 */
public class MainPostActivity extends AppCompatActivity {
    TextView txtname ,txtCreated,txtSchool, txtTitle ,txtContent;
    Button btnAddCmt;
    EditText addComment;
    private List<Comments> commentsList = new ArrayList<>();
    public String id,school;
    private RecyclerView recyclerView;
    private UserCommentAdapter commentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post_content);
        Intent getIntent = getIntent();
        id = getIntent.getStringExtra("sendData");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_comment);
        txtname = (TextView) findViewById(R.id.postUser);
        txtCreated = (TextView) findViewById(R.id.createdDatePost);
        txtSchool = (TextView) findViewById(R.id.schoolPost);
        txtTitle = (TextView) findViewById(R.id.titlePost);
        txtContent = (TextView) findViewById(R.id.contentPost);
        addComment = (EditText) findViewById(R.id.add_cmt);
        btnAddCmt = (Button) findViewById(R.id.btSend_cmt);
        addComment.setVisibility(View.INVISIBLE);
        btnAddCmt.setVisibility(View.INVISIBLE);
        commentAdapter = new UserCommentAdapter(commentsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(commentAdapter);
        ActionBar actionBar ;
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        actionBar.setHomeButtonEnabled(true);
        LoadMainPost();
        LoadComments();
        final FloatingActionButton create_cmt = (FloatingActionButton) findViewById(R.id.create_cmt);
        create_cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_cmt.setVisibility(View.INVISIBLE);
            addComment.setVisibility(View.VISIBLE);
            btnAddCmt.setVisibility(View.VISIBLE);
                btnAddCmt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post_Info");
                        query.whereEqualTo("objectId", id);
                        query.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if(addComment.getText().toString().trim().length() == 0)
                                    return;
                                ParseObject newCmt =  ParseObject.create("User_comments");
                                newCmt.put("user", ParseUser.getCurrentUser());
                                newCmt.put("Comment", addComment.getText().toString().trim());
                                newCmt.put("post",object);
                                newCmt.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        addComment.setVisibility(View.INVISIBLE);
                                        btnAddCmt.setVisibility(View.INVISIBLE);
                                        create_cmt.setVisibility(View.VISIBLE);
                                        clearData();
                                        LoadComments();
                                    }
                                });
                            }
                        });

                    }
                });
            }
        });

    }
    private void LoadMainPost()
    {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post_Info");
        query.whereEqualTo("objectId",id);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects != null) {
                    for (ParseObject object : objects) {
                        try {
                            txtname.setText(object.getParseUser("user").fetchIfNeeded().getString("name"));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        txtTitle.setText(object.getString("Title"));
                        txtContent.setText(object.getString("Describe"));

                    }
                }
            }
        });
    }
    private void LoadComments()
    {   ParseQuery<ParseObject> query = ParseQuery.getQuery("Post_Info");
        query.whereEqualTo("objectId",id);
        ParseQuery<ParseObject> newquerry = ParseQuery.getQuery("User_comments");
        newquerry.whereMatchesQuery("post",query);
        newquerry.addDescendingOrder("createdAt");
        newquerry.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() != 0) {
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject po = objects.get(i);
                        Comments comments = new Comments();
                        comments.setUser(po.getParseUser("user"));
                        comments.setComment(po.getString("Comment"));
                        comments.setPost(po.getParseObject("post"));
                        commentsList.add(comments);
                        commentAdapter.notifyDataSetChanged();
                    }
                }
                else
                {

                }

            }
        });

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
    public void clearData() {
        int size = this.commentsList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.commentsList.remove(0);
            }

            commentAdapter.notifyItemRangeRemoved(0,size);
        }
    }
}
