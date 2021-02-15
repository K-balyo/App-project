package com.example.projectginger.fragment;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.projectginger.Item;
import com.example.projectginger.R;
import com.example.projectginger.adapter.ItemListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemFragment newInstance(String param1, String param2) {
        ItemFragment fragment = new ItemFragment();
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

    private ArrayAdapter areaAdapter;
    private Spinner areaSpinner;
    private ArrayAdapter feeAdapter;
    private Spinner feeSpinner;

    private String itemType = "";
   // private String itemArea = "";
   // private String itemfee = "";

    private ListView itemListView;
    private ItemListAdapter adapter;
    private List<Item> itemList;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        final RadioGroup itemTypeGroup = (RadioGroup) getView().findViewById(R.id.kind);
        areaSpinner = (Spinner) getView().findViewById(R.id.areaSpinner);
        feeSpinner = (Spinner) getView().findViewById(R.id.admissionfeeSpinner);

        itemTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                RadioButton typeButton = (RadioButton) getView().findViewById(i);
                itemType = typeButton.getText().toString();
                areaAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.area, android.R.layout.simple_spinner_dropdown_item);
                areaSpinner.setAdapter(areaAdapter);
                feeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.fee, android.R.layout.simple_spinner_dropdown_item);
                feeSpinner.setAdapter(feeAdapter);
            }
        });
        itemListView = (ListView)getView().findViewById(R.id.itemListView);
        itemList = new ArrayList<Item>();
        adapter = new ItemListAdapter(getContext().getApplicationContext(),itemList,this);
        itemListView.setAdapter(adapter);

        Button searchButton = (Button)getView().findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://ginger.dothome.co.kr/ItemList.php?kind=" + URLEncoder.encode(itemType, "UTF-8") +
                        "&area=" + URLEncoder.encode(areaSpinner.getSelectedItem().toString(), "UTF-8") + "&fee=" + URLEncoder.encode(feeSpinner.getSelectedItem().toString(), "UTF-8");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                itemList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                int itemID;
                String iname;
                String kind;
                int price;
                String area;
                String address;
                int state;
                String image;
                String tag;
                String content;
                Date cDate = null;
                Date mDate  = null;
                String Latitude;
                String Longitude;
                String fee;
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    itemID = object.getInt("itemID");
                    iname = object.getString("iname");
                    kind = object.getString("kind");
                    price = object.getInt("price");
                    area = object.getString("area");
                    address = object.getString("address");
                    state = object.getInt("state");
                    tag = object.getString("tag");
                    content = object.getString("content");
                    fee = object.getString("fee");
                    image = object.getString("image");
                    Latitude = object.getString("Latitude");
                    Longitude = object.getString("Longitude");
                    Item item = new Item(itemID, iname, kind, price, area, address, state, image, tag, content, cDate, mDate, Latitude, Longitude, fee);
                    itemList.add(item);
                    count++;
                }
                if (count == 0 ){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ItemFragment.this.getActivity());
                    dialog = builder.setMessage("조회된 항목이 없습니다.\n 지역을 확인해주세요. ")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                }
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                new BackgroundTask().execute();
            }
        }
    }
}