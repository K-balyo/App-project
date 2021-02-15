package com.example.projectginger.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WritePostRequest extends StringRequest {

    final static private String URL = "http://ginger.dothome.co.kr/PostAdd.php";
    private Map<String,String> parameters;

    public WritePostRequest(String userID, String title ,String content,String area ,String sc, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("title",title);
        parameters.put("content",content);
        parameters.put("area",area);
        parameters.put("sc",sc);
    }


    @Override
    public Map<String, String> getParams(){
        return parameters;
    }


}
