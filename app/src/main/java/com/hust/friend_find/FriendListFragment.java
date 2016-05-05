package com.hust.friend_find;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.utils.Const;
import com.utils.FriendListAdapter;
import com.example.quy2016.doantotnghiep.R;
import com.hust.chat.ChatActivity;
import com.model.ProfileUser;
import com.parse.ParseException;
import com.parse.ParseQueryAdapter;

import java.util.List;

/**
 * Created by Administrator on 4/21/2016.
 */
public class FriendListFragment extends Fragment {
    private FriendListAdapter friendListAdapter;
    private ParseQueryAdapter<ProfileUser> mainAdapter;
    private List<ProfileUser> users ;
    ListView listFriend;
    LinearLayout noFriendView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.list_item_friends,null);
        listFriend = (ListView) view.findViewById(R.id.listFriend);
        noFriendView = (LinearLayout) view.findViewById(R.id.no_friend_view);
        listFriend.setEmptyView(noFriendView);
        friendListAdapter = new FriendListAdapter(getContext());
        friendListAdapter.loadObjects();
        ListView listFriend = (ListView) view.findViewById(R.id.listFriend);
        listFriend.setAdapter(friendListAdapter);
       // users = new ArrayList<>();
        listFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    startActivity(new Intent(getActivity() ,ChatActivity.class ).putExtra(Const.EXTRA_DATA_SEND ,friendListAdapter.getItem(position).getUser().fetchIfNeeded().getUsername()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getActivity() , "View :" + friendListAdapter.getItem(position).getEmail() , Toast.LENGTH_SHORT ).show();
            }
        });
        return view;
    }

}
