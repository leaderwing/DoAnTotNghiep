package com.hust.friend_find;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quy2016.doantotnghiep.R;
import com.model.ProfileUser;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/21/2016.
 */
public class SearchFriendFragment extends Fragment{
    SearchView search;
    ListView search_results;
    ImageButton btnVoice;
    View view;
    String found = "N";
    ArrayList<ProfileUser> userResult = new ArrayList<>();
    ArrayList<ProfileUser> filterUserResult = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_search_friend,container,false);
        search = (SearchView) view.findViewById(R.id.searchView);
        search.setQueryHint("Tìm kiếm tên bạn bè...");
        search_results = (ListView) view.findViewById(R.id.listview_search);
        btnVoice = (ImageButton) view.findViewById(R.id.search_voice);
        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener()
        {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                //Toast.makeText(activity, String.valueOf(hasFocus),Toast.LENGTH_SHORT).show();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.length() > 0)
                {

                    search_results.setVisibility(view.VISIBLE);
                    myAsyncTask m = (myAsyncTask) new myAsyncTask().execute(newText);
                }
                else
                {

                    search_results.setVisibility(view.INVISIBLE);
                }



                return false;
            }

        });
        return view;
    }
    public void filterUserArray(String newText)
    {
        String pName;
        filterUserResult.clear();
        for (int i = 0; i < userResult.size(); i++)
        {
            pName = userResult.get(i).getAuthorName().toLowerCase();
            if ( pName.contains(newText.toLowerCase()))
            {
                filterUserResult.add(userResult.get(i));

            }
        }

    }

    class myAsyncTask extends AsyncTask<String, Void, String>
    {
        String url = new String();
        String textSearch;
        ProgressDialog pd;
        String mathFound = "N";
        //ArrayList returnResults ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd= new ProgressDialog(getActivity());
            pd.setCancelable(false);
            pd.setMessage("Searching...");
            pd.getWindow().setGravity(Gravity.CENTER);
            pd.show();
        }

        @Override
        protected String doInBackground(String... sText) {

            //ProfileUser profileUser;
            url = "" + sText[0];
            String return_Results = getUserList(url.trim());
            this.textSearch = sText[0];
            return  return_Results;
        }
        public String getUserList(String url)
        {
            ParseQuery query =  new ParseQuery("user_details");
            // query.whereStartsWith("Name",url);
            // Log.d(query.toString() , "Query :");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject user : objects) {
                            // returnResults = new ArrayList();
                            ProfileUser profileUser = new ProfileUser();
                            profileUser.setAuthorName(user.getString("Name"));
                            profileUser.setSchool(user.getString("user_school"));
                            if(!user.getParseFile("user_avatar").isDirty())
                            profileUser.setPhotoFile(user.getParseFile("user_avatar"));
                            //returnResults.add(profileUser);
                            mathFound = "N";
                            for (int i = 0; i < userResult.size(); i++) {
                                if (userResult.get(i).getAuthorName().equals(profileUser.getAuthorName())) {
                                    mathFound = "Y";
                                }
                            }
                            if (mathFound == "N") {
                                userResult.add(profileUser);
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Error :" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });

            return ("OK");
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            if(result.equalsIgnoreCase("Exception Caught"))
            {
                Toast.makeText(getActivity(), "Unable to connect to server,please try later", Toast.LENGTH_LONG).show();

                pd.dismiss();
            }
            else
            {
                //calling this method to filter the search results from productResults and move them to
                //filteredProductResults
                filterUserArray(textSearch);
                search_results.setAdapter(new SearchResultsAdapter(getActivity(),filterUserResult));
                pd.dismiss();
            }
        }

    }
    class SearchResultsAdapter extends BaseAdapter
    {
        private LayoutInflater layoutInflater;

        private ArrayList<ProfileUser> userDetails=new ArrayList<>();
        int count;
        Context context;

        public SearchResultsAdapter( Context context,  ArrayList<ProfileUser> userDetails) {
            layoutInflater = LayoutInflater.from(context);
            this.context = context;
            this.count = userDetails.size();
            this.userDetails = userDetails;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int position) {
            return userDetails.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ProfileUser user = userDetails.get(position);
            if(convertView == null)
            {
                convertView = layoutInflater.inflate(R.layout.list_item_friends_search,null);
                holder = new ViewHolder();
                holder.avatar = (ParseImageView) convertView.findViewById(R.id.avatar);
                holder.userName = (TextView) convertView.findViewById(R.id.user_name);
                holder.user_school = (TextView) convertView.findViewById(R.id.user_school);
                holder.add_friend = (ImageButton) convertView.findViewById(R.id.add);
                holder.add_friend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                convertView.setTag(holder);

            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            if(!user.getPhotoFile().isDirty())
            holder.avatar.setParseFile(user.getPhotoFile());
            holder.userName.setText(user.getAuthorName());
            holder.user_school.setText(user.getSchool());
            return  convertView ;
        }

    }
    static class ViewHolder
    {
        ParseImageView avatar;
        TextView userName;
        TextView user_school;
        ImageButton add_friend;
    }
}