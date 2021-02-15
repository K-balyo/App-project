package com.example.projectginger.fragment;

import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.TextView;

import com.example.projectginger.Item;
import com.example.projectginger.Post;
import com.example.projectginger.R;
import com.example.projectginger.activity.LoginActivity;
import com.example.projectginger.activity.MainActivity;
import com.example.projectginger.activity.RegisterActivity;
import com.example.projectginger.activity.WritePostActivity;
import com.example.projectginger.adapter.ItemListAdapter;
import com.example.projectginger.adapter.PostListAdapter;

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
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance(String param1, String param2) {
        PostFragment fragment = new PostFragment();
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

    private ArrayAdapter searchnnbAdapter;
    //private Spinner searchnnbSpinner;


    private String postType = "";

    private ListView postListView;
    private PostListAdapter adapter;
    private List<Post> postList;

    @Override
    public void onActivityCreated(Bundle b) {
        super.onActivityCreated(b);

        final RadioGroup itemTypeGroup = (RadioGroup) getView().findViewById(R.id.postkind);
       // searchnnbSpinner = (Spinner) getView().findViewById(R.id.postnnb);

        itemTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                RadioButton typeButton = (RadioButton) getView().findViewById(i);
                postType = typeButton.getText().toString();
            //    searchnnbAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.postnnb, android.R.layout.simple_spinner_dropdown_item);
               // searchnnbSpinner.setAdapter(searchnnbAdapter);
            }
        });
        postListView = (ListView)getView().findViewById(R.id.postListView);
        postList = new ArrayList<Post>();
        adapter = new PostListAdapter(getContext().getApplicationContext(),postList,this);
        postListView.setAdapter(adapter);

        Button searchButton = (Button)getView().findViewById(R.id.postsearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostFragment.BackgroundTask().execute();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            try {
                target = "http://ginger.dothome.co.kr/PostList.php?postType=" + URLEncoder.encode(postType, "UTF-8") +
                      //  "&postnnb=" + URLEncoder.encode(searchnnbSpinner.getSelectedItem().toString(), "UTF-8") +
                        "&userID=" + URLEncoder.encode(MainActivity.userID, "UTF-8");

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
                postList.clear();
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                int postID;
                String userID;
                String title;
                String area;
                int state;
              //  int itemRival;
                String content;
                Date CRE_DATE = null;
                Date MOD_DATE  = null;

                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    postID = object.getInt("postID");
                    userID = object.getString("userID");
                    title = object.getString("title");
                    area = object.getString("area");
                    state = object.getInt("state");
                    content = object.getString("content");
                    //itemRival = object.getInt("COUNT(postlike.postID)");
                    //Post post = new Post(postID, userID, title, area, state, content, CRE_DATE, MOD_DATE,itemRival);
                    Post post = new Post(postID, userID, title, area, state, content, CRE_DATE, MOD_DATE);
                    postList.add(post);
                    count++;
                }
                if (count == 0 ){
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(PostFragment.this.getActivity());
                    dialog = builder.setMessage("조회된 항목이 없습니다.\n 한번 더 확인해주세요. ")
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