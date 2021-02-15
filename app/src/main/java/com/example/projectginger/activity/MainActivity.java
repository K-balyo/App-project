package com.example.projectginger.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectginger.fragment.ItemFragment;
import com.example.projectginger.Notice;
import com.example.projectginger.fragment.PostFragment;
import com.example.projectginger.R;
import com.example.projectginger.fragment.StatisticsFragment;
import com.example.projectginger.adapter.NoticeListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice>noticeList;
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        userID = getIntent().getStringExtra("userID");

        noticeListView = (ListView)findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);


        final Button itemButton = (Button)findViewById(R.id.itemButton);
        final Button postButton = (Button)findViewById(R.id.postButton);
        final Button staticsButton = (Button)findViewById(R.id.staticsButton);
        final Button writepostButton = (Button)findViewById(R.id.writepostButton);
        final LinearLayout notice = (LinearLayout)findViewById(R.id.notice);

        itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);
                itemButton.setBackgroundColor(getResources().getColor(R.color.colorpp));
                postButton.setBackgroundColor(getResources().getColor(R.color.colorp));
                staticsButton.setBackgroundColor(getResources().getColor(R.color.colorp));
                writepostButton.setBackgroundColor(getResources().getColor(R.color.colorp));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment , new ItemFragment());
                fragmentTransaction.commit();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);
                itemButton.setBackgroundColor(getResources().getColor(R.color.colorp));
                postButton.setBackgroundColor(getResources().getColor(R.color.colorpp));
                staticsButton.setBackgroundColor(getResources().getColor(R.color.colorp));
                writepostButton.setBackgroundColor(getResources().getColor(R.color.colorp));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment , new PostFragment());
                fragmentTransaction.commit();
            }
        });
        staticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);
                itemButton.setBackgroundColor(getResources().getColor(R.color.colorp));
                postButton.setBackgroundColor(getResources().getColor(R.color.colorp));
                staticsButton.setBackgroundColor(getResources().getColor(R.color.colorpp));
                writepostButton.setBackgroundColor(getResources().getColor(R.color.colorp));
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment , new StatisticsFragment());
                fragmentTransaction.commit();
            }
        });
        writepostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemButton.setBackgroundColor(getResources().getColor(R.color.colorp));
                postButton.setBackgroundColor(getResources().getColor(R.color.colorp));
                staticsButton.setBackgroundColor(getResources().getColor(R.color.colorp));
                writepostButton.setBackgroundColor(getResources().getColor(R.color.colorpp));
                Intent intent = new Intent(MainActivity.this, WritePostActivity.class);
                intent.putExtra("userID",userID);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });
        noticeListView.setAdapter(adapter);
        new BackgroundTask().execute();
    }
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;
        @Override
        protected void onPreExecute() {
            target = "http://ginger.dothome.co.kr/NoticeList.php";
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
                while ((temp = bufferedReader.readLine()) != null)
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
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String noticeContent, noticeName, noticeDate;
                while(count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice);
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
                new BackgroundTask().execute();
            }
            adapter.notifyDataSetChanged();

        }
    }

    private long lastTimeBackPressed;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500)
        {
            finish();
            return;
        }
        Toast.makeText(this,"'뒤로' 버튼을 한 번 더 눌러 종료합니다.", Toast.LENGTH_SHORT);
        lastTimeBackPressed = System.currentTimeMillis();
    }
}