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
import com.example.projectginger.request.DeleteRequest;
import com.example.projectginger.Item;
import com.example.projectginger.activity.MainActivity;
import com.example.projectginger.R;
import com.example.projectginger.fragment.StatisticsFragment;

import org.json.JSONObject;

import java.util.List;

public class StatisticsItemListAdapter extends BaseAdapter {

    private Context context;
    private List<Item> itemList;
    private Fragment parent;
    private String userID = MainActivity.userID;

    public StatisticsItemListAdapter(Context context, List<Item> itemList, Fragment parent) {
        this.context = context;
        this.itemList = itemList;
        this.parent = parent;
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
        View v = View.inflate(context, R.layout.statistics, null);
        TextView area = (TextView)v.findViewById(R.id.itemarea);
        TextView kind = (TextView) v.findViewById(R.id.itemkind);
        TextView fee = (TextView) v.findViewById(R.id.itemfee);
        TextView state = (TextView) v.findViewById(R.id.itemstate);
        TextView iname = (TextView) v.findViewById(R.id.iteminame);


        area.setText(itemList.get(i).getArea());
        kind.setText(itemList.get(i).getKind());
        iname.setText(itemList.get(i).getIname());
        state.setText("⭐"+itemList.get(i).getItemRival());
        fee.setText("입장료 : "+itemList.get(i).getFee());
       // state.setText("⭐"+itemList.get(i).getState());

        v.setTag(itemList.get(i).getItemID());

        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
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
                            itemList.remove(i);
                            StatisticsFragment.total.setText(itemList.size()+"개");
                            notifyDataSetChanged();

                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                            AlertDialog dialog = builder.setMessage("삭제에 실패하였습니다.")
                                    .setNegativeButton("다시 시도",null)
                                    .create();
                            dialog.show();
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                };
                DeleteRequest deleteRequest = new DeleteRequest(userID , itemList.get(i).getItemID() +"", responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(deleteRequest);
            }
        });
        return v;
    }
}
