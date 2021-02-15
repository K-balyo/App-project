package com.example.projectginger.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projectginger.Item;
import com.example.projectginger.R;
import com.example.projectginger.activity.MainActivity;
import com.example.projectginger.adapter.StatisticsItemListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private ListView pickListView;
    private StatisticsItemListAdapter adapter;
    private List<Item> pickList;
    public static int totalCredit = 0;
    public static TextView total;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);
        pickListView = (ListView)getView().findViewById(R.id.pickListView);
        pickList = new ArrayList<Item>();
        adapter = new StatisticsItemListAdapter(getContext().getApplicationContext(), pickList,this);
        totalCredit = 0;
        total = (TextView)getView().findViewById(R.id.total);

        pickListView.setAdapter(adapter);
        new BackgroundTask().execute();
    }


    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;
        @Override
        protected void onPreExecute() {
            try {
                target = "http://ginger.dothome.co.kr/StatisticsItemList.php?userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                int itemID;
                String iname;
                String kind;
                String fee;
                String area;
                int itemRival;
                while (count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    itemID = object.getInt("itemID");
                    iname = object.getString("iname");
                    kind = object.getString("kind");
                    fee = object.getString("fee");
                    area = object.getString("area");
                    itemRival = object.getInt("COUNT(pick.itemID)");
                    pickList.add(new Item(itemID,iname,kind,fee,area,itemRival));
                    count++;
                }
                adapter.notifyDataSetChanged();
                int itemn = pickList.size();
                totalCredit = itemn;
                total.setText(totalCredit+"개");
            }catch (Exception e){
                e.printStackTrace();
                new BackgroundTask().execute();
            }
            }
        }

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }
}