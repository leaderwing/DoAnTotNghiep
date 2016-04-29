package com.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quy2016.doantotnghiep.R;
import com.model.Post_Info;

import java.util.List;

/**
 * Created by Administrator on 4/25/2016.
 */
public class PostInfoAdapter extends RecyclerView.Adapter<PostInfoAdapter.MyViewHolder> {
    private List<Post_Info> postsList;
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title , author , date , viewNum , commentNum ;
        ImageView imgAvatar;

        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.txtTitle);
            author = (TextView) view.findViewById(R.id.txtAuthor);
            date = (TextView) view.findViewById(R.id.createdAt);
            viewNum = (TextView) view.findViewById(R.id.numView);
            commentNum = (TextView) view.findViewById(R.id.numComment);
            imgAvatar = (ImageView) view.findViewById(R.id.imageAvatar);
        }


    }
    public PostInfoAdapter( List<Post_Info> postsList) {
        this.postsList = postsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_info,parent,false);
        return  new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Post_Info post_info = postsList.get(position);
        holder.title.setText(post_info.getTitle());
        holder.author.setText(post_info.getAuthorName());
        //holder.date.setText(post_info.getDate().toString());
        holder.viewNum.setText(String.valueOf(post_info.getNumberPost()));
        holder.commentNum.setText(String.valueOf(post_info.getNumberPost()));
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }
}
