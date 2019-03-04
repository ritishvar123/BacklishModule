package com.example.hp.backlishmodule;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity{

    InputStreamVolleyRequest request;
    int count;
    Button button;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> listDataHeader;
    List<Integer> img_dot;
    HashMap<String, List<String>> listHashMap;
    int[] id = {1, 2, 3, 4, 5};
    String[] name = {"Ritish", "Pallav", "Siddhant", "Sidharth", "Yash"};
    int[] img_status = {R.drawable.logo_2, R.drawable.logo_3, R.drawable.logo_4, R.drawable.logo_3, R.drawable.logo_2};
    String[] startTime = {"09:03:53", "11:15:02", "18:06:34", "14:42:02", "09:03:53"};
    String[] duration = {"23", "12", "23", "34", "67"};
    String[] lastwork = {"Chrome", "vlc", "Android Studio", "Pycharm", "File explorer"};
    int[] img_graph = {R.drawable.graph1, R.drawable.graph2, R.drawable.graph3, R.drawable.graph4, R.drawable.graph5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableListView = (ExpandableListView)findViewById(R.id.expandableListView);
        init();
        expandableListAdapter = new ExpandableListAdapter(MainActivity.this, listDataHeader, listHashMap, img_dot);
        expandableListView.setAdapter(expandableListAdapter);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String url = "https://boxinall.in/kshitiz/export.php";
                StringRequest stringRequest = new StringRequest(1, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Slow internet", Toast.LENGTH_LONG).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(stringRequest);*/

                /*String mUrl="https://boxinall.in/kshitiz/data.csv";
                request = new InputStreamVolleyRequest(Request.Method.GET, mUrl, MainActivity.this, MainActivity.this, null);
                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(),
                        new HurlStack());
                mRequestQueue.add(request);*/

                String mUrl = "https://boxinall.in/kshitiz/data.csv";
                InputStreamVolleyRequest request = new InputStreamVolleyRequest(Request.Method.GET, mUrl,
                        new Response.Listener<byte[]>() {
                            @Override
                            public void onResponse(byte[] response) {
                                // TODO handle the response
                                try {
                                    if (response!=null) {
                                        FileOutputStream outputStream;
                                        String name = "data.txt";
                                        //String name=<FILE_NAME_WITH_EXTENSION e.g reference.txt>;
                                        outputStream = openFileOutput(name, Context.MODE_PRIVATE);
                                        outputStream.write(response);
                                        outputStream.close();
                                        Toast.makeText(MainActivity.this, "Download complete.", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                                    e.printStackTrace();
                                }
                            }
                        } ,new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO handle the error
                        error.printStackTrace();
                    }
                }, null);
                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
                mRequestQueue.add(request);
            }
        });
    }

    public void init() {
        listDataHeader = new ArrayList<>();
        img_dot = new ArrayList<>();
        listHashMap = new HashMap<>();
        for (int i=0; i<name.length; i++){
            listDataHeader.add(id[i]+".\t\t"+name[i]);
            img_dot.add(img_status[i]);
            List<String> p = new ArrayList<>();
            p.add(startTime[i]);
            p.add(duration[i]);
            p.add(lastwork[i]);
            p.add(""+img_graph[i]);
            listHashMap.put(listDataHeader.get(i), p);
        }
    }

    /*@Override
    public void onErrorResponse(VolleyError error) {
        Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE. ERROR:: "+error.getMessage());
    }

    @Override
    public void onResponse(byte[] response) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            if (response!=null) {
                //Read file name from headers
                String content =request.responseHeaders.get("Content-Disposition").toString();
                StringTokenizer st = new StringTokenizer(content, "=");
                String filename = null;
                if (st.hasMoreElements()){
                    if (st.hasMoreElements()){
                        filename = st.nextToken();
                    }
                }*//*
                String[] arrTag = st.toArray();
                String filename = arrTag[1];*//*
                filename = filename.replace(":", ".");
                Log.d("DEBUG::RESUME FILE NAME", filename);

                try{
                    long lenghtOfFile = response.length;

                    //covert reponse to input stream
                    InputStream input = new ByteArrayInputStream(response);
                    File path = Environment.getExternalStorageDirectory();
                    File file = new File(path, filename);
                    map.put("resume_path", file.toString());
                    BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
                    byte data[] = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        output.write(data, 0, count);
                    }

                    output.flush();

                    output.close();
                    input.close();
                }catch(IOException e){
                    e.printStackTrace();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
            e.printStackTrace();
        }
    }*/


}