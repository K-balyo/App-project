package com.example.projectginger.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.projectginger.R;
import com.example.projectginger.request.RegisterRequest;
import com.example.projectginger.request.ValidateRequest;
import com.example.projectginger.request.WritePostRequest;

import org.json.JSONObject;

public class WritePostActivity extends AppCompatActivity {


    private ArrayAdapter adapter;
    private Spinner spinner;
    private String title;
    private String content;
    private String area;
    private String sc;
    private AlertDialog dialog;
    private String userID = MainActivity.userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writepost);

        spinner = (Spinner)findViewById(R.id.areaSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.area, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText titleText = (EditText) findViewById(R.id.titleEditText);
        final EditText contentText = (EditText) findViewById(R.id.contentEditText);

        RadioGroup wkGroup = (RadioGroup) findViewById(R.id.wkGroup);
        int wkGroupID = wkGroup.getCheckedRadioButtonId();
        sc = ((RadioButton) findViewById(wkGroupID)).getText().toString();

        wkGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton writekindButton = (RadioButton)findViewById(i);
                sc = writekindButton.getText().toString();
            }
        });

        Button writepostButton = (Button)findViewById(R.id.writepostButton);
        writepostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleText.getText().toString();
                String content = contentText.getText().toString();
                String area = spinner.getSelectedItem().toString();

                if (title.equals("")|| content.equals("") || area.equals("")|| sc.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(WritePostActivity.this);
                    dialog = builder.setMessage("빈 칸을 채워주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success){
                                AlertDialog.Builder builder = new AlertDialog.Builder(WritePostActivity.this);
                                dialog = builder.setMessage("글 등록에 성공하였습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                Intent intent = new Intent(WritePostActivity.this, MainActivity.class);
                                intent.putExtra("userID",userID);
                                WritePostActivity.this.startActivity(intent);
                                finish();
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(WritePostActivity.this);
                                dialog = builder.setMessage("글 등록에 실패했습니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                WritePostRequest writePostRequest = new WritePostRequest( userID , title, content, area, sc, responseListener);
                RequestQueue queue = Volley.newRequestQueue(WritePostActivity.this);
                queue.add(writePostRequest);
            }
        });
        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(WritePostActivity.this, MainActivity.class);
                    WritePostActivity.this.startActivity(registerIntent);
                }
            });

    }
    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}
