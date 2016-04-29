package com.example.quy2016.doantotnghiep;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.hust.news.ListRSSItemsActivity;

/**
 * Created by Ratan on 7/29/2015.
 */
public class HustNewsFragment extends Fragment {
    ImageButton imgbutton1 , imgbutton2 ,imgbutton3, imgbutton4 ;
    public  static  final  String LINK1 = "http://ctsv.hust.edu.vn/joomla-pages-2/smart-seach?format=feed&type=rss";
    public  static  final  String LINK2 = "http://ctsv.hust.edu.vn/k2-listing/itemlist/category/133-medical?format=feed&type=rss";
    public  static  final  String LINK3 = "http://dtdh.hust.edu.vn/tin-tuc/dao-tao?format=feed&amp;type=rss";
    public  static  final  String LINK4 = "http://dtdh.hust.edu.vn/xlht?format=feed&amp;type=rss";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_news_category,null);
        imgbutton1 = (ImageButton) view.findViewById(R.id.imageView1);
        imgbutton2 = (ImageButton) view.findViewById(R.id.imageView2);
        imgbutton3 = (ImageButton) view.findViewById(R.id.imageView3);
        imgbutton4 = (ImageButton) view.findViewById(R.id.imageView4);
        imgbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , ListRSSItemsActivity.class);
                i.putExtra("link",LINK1);
                startActivity(i);
            }
        });
        imgbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , ListRSSItemsActivity.class);
                i.putExtra("link",LINK2);
                startActivity(i);
            }
        });
        imgbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , ListRSSItemsActivity.class);
                i.putExtra("link",LINK3);
                startActivity(i);
            }
        });
        imgbutton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , ListRSSItemsActivity.class);
                i.putExtra("link",LINK4);
                startActivity(i);
            }
        });
        return  view;
    }
}
