package com.example.quy2016.doantotnghiep;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.hust.friend_find.FriendListFragment;
import com.hust.friend_find.SearchFriendFragment;
import com.hust.friend_find.SearchGPSFragment;

/**
 * Created by Ratan on 7/29/2015.
 */
public class FriendsFragment extends Fragment implements TabHost.OnTabChangeListener {

    private static final String TAG = "FragmentTabs";
    public static final String TAB_LIST = "list_friends";
    public static final String TAB_FINDS = "find_friends";
    public static final String TAB_FINDS_GPS = "view_friend_gps";

    private View mRoot;
    private TabHost mTabHost;
    private int mCurrentTab;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.tab_friend_fragment, null);
        mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
        setupTabs();
        return mRoot;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(mCurrentTab);
        // manually start loading stuff in the first tab
        updateTab(TAB_LIST, R.id.tab_1);
    }

    private void setupTabs() {
        mTabHost.setup(); // you must call this before adding your tabs!
        mTabHost.addTab(newTab(TAB_LIST, R.string.tab_list, R.id.tab_1));
        mTabHost.addTab(newTab(TAB_FINDS, R.string.tab_finds, R.id.tab_2));
        mTabHost.addTab(newTab(TAB_FINDS_GPS, R.string.tab_finds_gps, R.id.tab_3));
    }

    private TabHost.TabSpec newTab(String tag, int labelId, int tabContentId) {
        Log.d(TAG, "buildTab(): tag=" + tag);

        View indicator = LayoutInflater.from(getActivity()).inflate(
                R.layout.tab,
                (ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
        ((TextView) indicator.findViewById(R.id.text)).setText(labelId);

        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(tag);
        tabSpec.setIndicator(indicator);
        tabSpec.setContent(tabContentId);
        return tabSpec;
    }

    @Override
    public void onTabChanged(String tabId) {
        Log.d(TAG, "onTabChanged(): tabId=" + tabId);
        if (TAB_LIST.equals(tabId)) {
            updateTab(tabId, R.id.tab_1);
            mCurrentTab = 0;
            return;
        }
        if (TAB_FINDS.equals(tabId)) {
            updateTab(tabId, R.id.tab_2);
            mCurrentTab = 1;
            return;
        }
        if (TAB_FINDS_GPS.equals(tabId)) {
            updateTab(tabId, R.id.tab_3);
            mCurrentTab = 2;
            return;
        }
    }

    private void updateTab(String tabId, int placeholder) {
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag(tabId) == null) {
            if (TAB_LIST.equals(tabId)) {
                fm.beginTransaction()
                        .replace(placeholder, new FriendListFragment(), tabId)
                        .commit();
            }
            if(TAB_FINDS.equals(tabId))
            {
                fm.beginTransaction()
                        .replace(placeholder, new SearchFriendFragment(), tabId)
                        .commit();
            }
            if(TAB_FINDS_GPS.equals(tabId))
            {
                fm.beginTransaction()
                        .replace(placeholder, new SearchGPSFragment(), tabId)
                        .commit();
            }
        }
    }

}

