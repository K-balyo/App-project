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
import com.example.projectginger.request.AddRequest;
import com.example.projectginger.Item;
import com.example.projectginger.activity.MainActivity;
import com.example.projectginger.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends BaseAdapter {

    private Context context;
    private List<Item> itemList;
    private Fragment parent;
    private String userID = MainActivity.userID;


    private List<Integer> itemIDList;

    public ItemListAdapter(Context context, List<Item> itemList,Fragment parent) {
        this.context = context;
        this.itemList = itemList;
        this.parent = parent;

        itemIDList = new ArrayList<Integer>();
       // new BackgroundTask().execute();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.item, null);
        TextView kind = (TextView) v.findViewById(R.id.kind);
        TextView iname = (TextView) v.findViewById(R.id.iname);
        TextView fee = (TextView) v.findViewById(R.id.fee);
        TextView content = (TextView) v.findViewById(R.id.content);
        TextView address = (TextView) v.findViewById(R.id.address);
        //TextView state = (TextView) v.findViewById(R.id.state);

        kind.setText(itemList.get(i).getKind());
        iname.setText(itemList.get(i).getIname());
        fee.setText("입장료 : "+itemList.get(i).getFee());
        content.setText(itemList.get(i).getContent());
        address.setText(itemList.get(i).getAddress());
        //state.setText("❤"+itemList.get(i).getState());

        v.setTag(itemList.get(i).getItemID());

        Button addButton = (Button)v.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Response.Listener<String> responseListener = (response) -> {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                AlertDialog dialog = builder.setMessage("추가 되었습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                AlertDialog dialog = builder.setMessage("이미 찜 목록에 존재합니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();

                        }
                    };
                    AddRequest addRequest = new AddRequest(userID , itemList.get(i).getItemID() +"", responseListener);
                    RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                    queue.add(addRequest);
                }

        });
        return v;
    }
    //20210128

}
