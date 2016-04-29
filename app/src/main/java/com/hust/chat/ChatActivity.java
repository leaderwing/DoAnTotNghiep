package com.hust.chat;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.Utils.Const;
import com.example.quy2016.doantotnghiep.R;
import com.model.ProfileUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by QUY2016 on 4/27/2016.
 */
public class ChatActivity extends CustomActivity {
    private ArrayList<Conversation> listCon;
    public  static  boolean hasMsg  = false;
    private ChatAdapter adapter;
    EditText editText;
    private String buddy , currentUser;
    private Handler handler;
    private ParseUser user;
    private  boolean isRunning = false;
    private Date lastMsgDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout_fragment);
        listCon = new ArrayList<Conversation>();
        ListView list = (ListView) findViewById(R.id.listChat);
        adapter = new ChatAdapter();
        list.setAdapter(adapter);
        list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        list.setStackFromBottom(true);
        editText = (EditText) findViewById(R.id.txtChat);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        setTouchNClick(R.id.btSend);
        buddy = getIntent().getStringExtra(Const.EXTRA_DATA_SEND);
        currentUser = ParseUser.getCurrentUser().getEmail();
        handler = new Handler();

    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        LoadChatConversation();


    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.btSend)
        {
            sendMessage();
        }
    }

    private void sendMessage()
    {
        if (editText.length() == 0)
            return;
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        String s = editText.getText().toString();
        final Conversation c = new Conversation();
        c.setMsg(s);
        c.setDate(new Date());
        c.setSender(currentUser);
        c.setStatus(Const.STATUS_SENDING);
        listCon.add(c);
        adapter.notifyDataSetChanged();
        editText.setText(null);

        ParseObject po = ParseObject.create(Conversation.class);
        po.put("sender", currentUser);
        po.put("receiver", buddy);
        // po.put("createdAt", "");
        po.put("message", s);
        po.saveEventually(new SaveCallback() {

            @Override
            public void done(ParseException e)
            {
                if (e == null)
                    c.setStatus(Const.STATUS_SENT);
                else
                    c.setStatus(Const.STATUS_FAILED);
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void LoadChatConversation() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Chat_data");

        if (listCon.size() == 0) {
            ArrayList<String> all = new ArrayList<String>();
            all.add(buddy);
            all.add(currentUser);
            query.whereContainedIn("sender", all);
            query.whereContainedIn("receiver", all);

        }
        else {
            if (lastMsgDate != null) {
                query.whereGreaterThan("createdAt", lastMsgDate);
                query.whereEqualTo("sender", buddy);
                query.whereEqualTo("receiver", currentUser);
            }
            query.orderByDescending("createdAt");
            query.setLimit(30);
            query.findInBackground(new FindCallback<ParseObject>() {

                @Override
                public void done(List<ParseObject> li, ParseException e) {
                    if (li != null && li.size() > 0) {
                        for (int i = li.size() - 1; i >= 0; i--) {
                            ParseObject po = li.get(i);
                            Conversation c = new Conversation();
                            c.setMsg(po.getString("message"));
                            c.setDate(po.getCreatedAt());
                            c.setSender(po.getString("sender"));
                            listCon.add(c);
                            if (lastMsgDate == null
                                    || lastMsgDate.before(c.getDate()))
                                lastMsgDate = c.getDate();
                            adapter.notifyDataSetChanged();
                        }
                    }
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            if (isRunning)
                                LoadChatConversation();
                        }
                    }, 1000);
                }
            });


        }
    }
    private class ChatAdapter extends BaseAdapter
    {

        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount()
        {
            return listCon.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public Conversation getItem(int arg0)
        {
            return listCon.get(arg0);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int arg0)
        {
            return arg0;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int pos, View v, ViewGroup arg2)
        {
            Conversation c = getItem(pos);
            if (c.getSender().equals(currentUser))
                v = getLayoutInflater().inflate(R.layout.chat_item_sent, null);
            else
                v = getLayoutInflater().inflate(R.layout.chat_item_rcv, null);
            TextView lbl1 = (TextView) v.findViewById(R.id.lbl1);
            lbl1.setText(DateUtils.getRelativeDateTimeString(getApplicationContext(), c.getDate().getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.DAY_IN_MILLIS, 0));

            TextView lbl2 = (TextView) v.findViewById(R.id.lbl2);
            lbl2.setText(c.getMsg());

            TextView lbl3 = (TextView) v.findViewById(R.id.lbl3);
            if (c.getSender().equals(currentUser))
            {
                if (c.getStatus() == Const.STATUS_SENT) {
                    hasMsg = true;
                    lbl3.setText("Delivered");

                }
                else if (c.getStatus() == Const.STATUS_SENDING)
                    lbl3.setText("Sending...");
                else
                    lbl3.setText("Failed");
            }
            else
                lbl3.setText("");

            return v;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
