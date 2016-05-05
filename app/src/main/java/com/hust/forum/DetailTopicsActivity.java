package com.hust.forum;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.utils.DividerItemDecoration;
import com.utils.PostInfoAdapter;
import com.example.quy2016.doantotnghiep.R;
import com.model.Post_Info;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/20/2016.
 */
public class DetailTopicsActivity extends AppCompatActivity {
    private List<Post_Info> post_infos = new ArrayList<>();
    private RecyclerView recyclerView;
    private PostInfoAdapter postInfoAdapter;
    public int newInt;
    private String course;
    public static final int REQUEST_CODE = 111;
    ProgressDialog progress;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout_post);
        Intent i = getIntent();
        if (savedInstanceState == null) {
            Bundle extras = i.getExtras();
            if (extras == null) {
                newInt = -1;
            } else {
                newInt = extras.getInt("position");
            }
        } else {
            newInt = (Integer) savedInstanceState.getSerializable("position");
        }
        switch (newInt) {
            case 0:
                course = "k56";
                break;
            case 1:
                course = "k57";
                break;
            case 2:
                course = "k58";
                break;
            case 3:
                course = "k59";
                break;
            case 4:
                course = "k60";
                break;
            default:
                break;
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.back);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        postInfoAdapter = new PostInfoAdapter(post_infos);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(postInfoAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), MainPostActivity.class);
                String objectId = post_infos.get(position).getObjectId();
                //Toast.makeText(getApplicationContext(),objectId,Toast.LENGTH_SHORT).show();
                intent.putExtra("sendData", objectId);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareDataPost();
    }


    private void prepareDataPost() {
        progress = ProgressDialog.show(this, "",
                "SAVING DATA...", true);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post_Info");
        query.whereEqualTo("Course", course);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                progress.dismiss();
                if (objects != null && objects.size() > 0) {
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject po = objects.get(i);
                        Post_Info post_info = new Post_Info();
                        post_info.setUser(po.getParseUser("user"));
                        post_info.setObjectId(po.getObjectId());
                        post_info.setDescribe(po.getString("Describe"));
                        post_info.setTitle(po.getString("Title"));
                        post_info.setNumberPost(po.getInt("Post_Num_Comment"));
                        post_info.setNumberView(po.getInt("Post_Num_View"));
                        post_infos.add(post_info);
                        postInfoAdapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext() , "Chưa có bài đăng nào" ,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }




    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_post, menu);
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.filter)
//                .getActionView();
//        searchView.setSearchableInfo(searchManager
//                .getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.create_new:
                CreateNewPost();
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void CreateNewPost() {
        Intent data = new Intent(getApplicationContext(), CreateNewPostActivity.class);
        data.putExtra("khoa_hoc", course);
        startActivityForResult(data, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                clearData();
                prepareDataPost();

            }
        }
    }
    public void clearData() {
        int size = this.post_infos.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.post_infos.remove(0);
            }

            postInfoAdapter.notifyDataSetChanged();
        }
    }
}
