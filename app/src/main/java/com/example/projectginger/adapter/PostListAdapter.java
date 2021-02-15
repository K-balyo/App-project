package com.example.projectginger.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.projectginger.Post;
import com.example.projectginger.activity.MainActivity;
import com.example.projectginger.R;
import com.example.projectginger.fragment.StatisticsFragment;
import com.example.projectginger.request.DeletePostRequest;
import com.example.projectginger.request.DeleteRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostListAdapter extends BaseAdapter {

    private Context context;
    private List<Post> postList;
    private Fragment parent;
    private String userID = MainActivity.userID;

    private List<Integer> postIDList;

    public PostListAdapter(Context context, List<Post> postList, Fragment parent) {
        this.context = context;
        this.postList = postList;
        this.parent = parent;

        postIDList = new ArrayList<Integer>();
       // new BackgroundTask().execute();
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int i) {
        return postList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.post, null);
        TextView area = (TextView) v.findViewById(R.id.area);
        TextView ID = (TextView) v.findViewById(R.id.state);
        TextView title = (TextView) v.findViewById(R.id.title);
        TextView content = (TextView) v.findViewById(R.id.content);

        area.setText(postList.get(i).getArea());
        title.setText(postList.get(i).getTitle());
       // state.setText("❤"+postList.get(i).getItemRival());
        ID.setText("작성자 : "+postList.get(i).getUserID());
        content.setText(postList.get(i).getContent());

        v.setTag(postList.get(i).getPostID());
/*
        //좋아요 처리 버튼 수정 필요
        TextView likeButton = (TextView)v.findViewById(R.id.state);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = (response) -> {
                    try
                    {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                            AlertDialog dialog = builder.setMessage("조하용.")
                                    .setPositiveButton("확인",null)
                                    .create();
                            dialog.show();

                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                            AlertDialog dialog = builder.setMessage("시러용.")
                                    .setNegativeButton("확인",null)
                                    .create();
                            dialog.show();
                            notifyDataSetChanged();
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();

                    }
                };
                LikePostRequest likePostRequestRequest = new LikePostRequest(userID , postList.get(i).getPostID() +"", responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(likePostRequestRequest);
            }

        });

 */
        Button deleteButton = (Button) v.findViewById(R.id.deletepostButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = (response) -> {
                    try
                    {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                            AlertDialog dialog = builder.setMessage("삭제 되었습니다.")
                                    .setPositiveButton("확인",null)
                                    .create();
                            dialog.show();
                            postList.remove(i);
                            notifyDataSetChanged();

                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                            AlertDialog dialog = builder.setMessage("본인 글만 삭제 가능합니다.")
                                    .setPositiveButton("확인",null)
                                    .create();
                            dialog.show();
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                };
                DeletePostRequest deletePostRequest = new DeletePostRequest(userID , postList.get(i).getPostID() +"", responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(deletePostRequest);
            }
        });
        return v;
    }
}
