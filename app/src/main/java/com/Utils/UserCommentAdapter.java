package com.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quy2016.doantotnghiep.R;
import com.model.Comments;
import com.parse.ParseException;
import com.parse.ParseImageView;

import java.util.List;

/**
 * Created by QUY2016 on 5/2/2016.
 */

public class UserCommentAdapter extends RecyclerView.Adapter<UserCommentAdapter.MyViewHolder>
{
    private List<Comments> commentsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, content;
        public ParseImageView avatar;

        public MyViewHolder(View view) {
            super(view);
            user_name = (TextView) view.findViewById(R.id.cmtName);
            content = (TextView) view.findViewById(R.id.cmtDetail);
           // avatar = (ParseImageView) view.findViewById(R.id.cmtAvatar);
        }
    }


    public UserCommentAdapter(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_comments, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Comments comments = commentsList.get(position);
        try {
            holder.user_name.setText(comments.getUser().fetchIfNeeded().getString("name"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.content.setText(comments.getComment());
//        try {
//            holder.avatar.setParseFile(comments.getUser().fetchIfNeeded().getParseFile("user_avatar"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }
    public void insert(int position, Comments comment) {
        commentsList.add(position, comment);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Comments comment) {
        int position = commentsList.indexOf(comment);
        commentsList.remove(position);
        notifyItemRemoved(position);
    }
}
