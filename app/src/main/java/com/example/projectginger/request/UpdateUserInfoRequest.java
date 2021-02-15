package com.example.projectginger.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserInfoRequest extends StringRequest {

    final static private String URL = "http://ginger.dothome.co.kr/UpdateUserInfo.php";
    private Map<String,String> parameters;

    public UpdateUserInfoRequest(String userID, String userPassword , String userArea , String userEmail, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("userPassword",userPassword);
        parameters.put("userArea",userArea);
        parameters.put("userEmail",userEmail);

    }


    @Override
    public Map<String, String> getParams(){
        return parameters;
    }


}
