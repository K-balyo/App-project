package com.example.projectginger.request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddRequest extends StringRequest {

    final static private String URL = "http://ginger.dothome.co.kr/ItemAdd.php";
    private Map<String,String> parameters;

    public AddRequest(String userID, String itemID , Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("itemID",itemID);
    }

    @Override
    public Map<String, String> getParams(){
        return parameters;
    }


}
