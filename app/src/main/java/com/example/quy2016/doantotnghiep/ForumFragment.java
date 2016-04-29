package com.example.quy2016.doantotnghiep;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.quy2016.doantotnghiep.R;
import com.hust.forum.DetailTopicsActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 4/20/2016.
 */
public class ForumFragment extends Fragment {
    private ListView lv;
    private ArrayList<HashMap<String, String>> array;
    private SimpleAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.forum_main_category,container ,false);
        lv = (ListView) view.findViewById(R.id.list2);
        loadData();
        String from[] = {"image" , "title" , "postNum" , "memberNum"};
        int to[] = {R.id.imgk56 ,R.id.txK56,R.id.post1,R.id.member1};
        adapter = new SimpleAdapter(getContext(),array,R.layout.forum_main_category_row,from,to);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), DetailTopicsActivity.class);
                i.putExtra("position",position);
                startActivity(i);
            }
        });
        return view;
    }

    private void loadData()
    {
        array = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> temp1 = new HashMap<String, String>();
        temp1.put("image",String.valueOf(R.drawable.k56));
        temp1.put("title","SINH VIÊN KHÓA 56");
        temp1.put("postNum","18");
        temp1.put("memberNum","300");
        array.add(temp1);
        HashMap<String, String> temp2 = new HashMap<String, String>();
        temp2.put("image",String.valueOf(R.drawable.k57));
        temp2.put("title","SINH VIÊN KHÓA 57");
        temp2.put("postNum","21");
        temp2.put("memberNum","230");
        array.add(temp2);
        HashMap<String, String> temp3 = new HashMap<String, String>();
        temp3.put("image",String.valueOf(R.drawable.k58));
        temp3.put("title","SINH VIÊN KHÓA 58");
        temp3.put("postNum","18");
        temp3.put("memberNum","300");
        array.add(temp3);
        HashMap<String, String> temp4 = new HashMap<String, String>();
        temp4.put("image",String.valueOf(R.drawable.k59));
        temp4.put("title","SINH VIÊN KHÓA 59");
        temp4.put("postNum","18");
        temp4.put("memberNum","300");
        array.add(temp4);
        HashMap<String, String> temp5 = new HashMap<String, String>();
        temp5.put("image",String.valueOf(R.drawable.k60));
        temp5.put("title","SINH VIÊN KHÓA 60");
        temp5.put("postNum","18");
        temp5.put("memberNum","300");
        array.add(temp5);

    }
}
