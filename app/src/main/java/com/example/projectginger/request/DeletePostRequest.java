package com.example.projectginger.request;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeletePostRequest extends StringRequest {

    final static private String URL = "http://ginger.dothome.co.kr/DeletePost.php";


    private Map<String,String> parameters;

    public DeletePostRequest(String userID, String postID , Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();

        parameters.put("userID",userID);
        parameters.put("postID",postID);
        Log.e("로그","userID"+userID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }


}
