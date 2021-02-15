package com.example.projectginger.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LikePostRequest extends StringRequest {

    final static private String URL = "http://ginger.dothome.co.kr/PostLike.php";
    private Map<String,String> parameters;

    public LikePostRequest(String userID, String postID , Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("postID",postID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }


}
