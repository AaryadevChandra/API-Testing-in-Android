package com.example.apitesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }

    public void APIButton(android.view.View v) throws IOException
    {
        OkHttpClient client = new OkHttpClient();
//
        Request request = new Request.Builder().url("https://cat-fact.herokuapp.com/facts").get().build();
        Response response = client.newCall(request).execute();
//
        String jsonString = response.body().string();
        char[] jsonChar = jsonString.toCharArray();
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<jsonString.length();i++)
        {
            if(jsonChar[i] == '[' || jsonChar[i] == ']')
            {
                continue;
            }
            else
            {
                sb.append(jsonChar[i]);
            }
        }

        jsonString = sb.toString();

        try
        {
            JSONObject obj = new JSONObject(jsonString);
            String fact = obj.getString("text");

            EditText responsetext = findViewById(R.id.responseText);

            responsetext.setText(fact);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}