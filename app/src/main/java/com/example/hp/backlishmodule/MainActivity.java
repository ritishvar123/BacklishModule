package com.example.hp.backlishmodule;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity{

    int STORAGE_PERMISSION_CODE = 1;
    InputStreamVolleyRequest request;
    int count;
    Button button;
    DownloadManager downloadManager;
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
        button = (Button)findViewById(R.id.button);
        /*if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You have already granted permission !!", Toast.LENGTH_LONG).show();
        } else {
            requestStorageapermission();
        }*/
        requestStorageapermission();
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


    private void requestStorageapermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            /*new AlertDialog.Builder(MainActivity.this).setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                    , STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();*/
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , STORAGE_PERMISSION_CODE);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}
                    , STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                init();
                expandableListAdapter = new ExpandableListAdapter(MainActivity.this, listDataHeader, listHashMap, img_dot);
                expandableListView.setAdapter(expandableListAdapter);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Please wait for 5 seconds !! Downloading.", Toast.LENGTH_LONG).show();
                        String url = "https://boxinall.in/BIAOfficeAttendence/export.php";
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
                        requestQueue.add(stringRequest);

                /*String mUrl="https://boxinall.in/kshitiz/data.csv";
                request = new InputStreamVolleyRequest(Request.Method.GET, mUrl, MainActivity.this, MainActivity.this, null);
                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(),
                        new HurlStack());
                mRequestQueue.add(request);*/

                /*String mUrl = "https://boxinall.in/kshitiz/data.csv";
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
                mRequestQueue.add(request);*/

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
                /*Uri uri = Uri.parse("https://r1---sn-qxa7snee.googlevideo.com/videoplayback?" +
                        "ipbits=0&lmt=1540827994292147&ratebypass=yes&c=WEB&txp=5531432&clen=10097738&dur=198.646&source=" +
                        "youtube&expire=1551696355&requiressl=yes&itag=18&ei=g618XOXHNJavhAfm06SQBg&pl=41&mime=video%2Fmp" +
                        "4&id=o-AIv8CuIzADqMVXPlcSqH9cZUuLUOpBKB753LMlKS5Xzx&sparams=clen,dur,ei,expire,gir,id,ip,ipbits" +
                        ",ipbypass,itag,lmt,mime,mip,mm,mn,ms,mv,pl,ratebypass,requiressl,source&fvip=1&key=cms1&ip=" +
                        "109.167.113.9&gir=yes&signature=5492003D2E2995562E74E882505362F0A7D84F05.55B458E2AAC8ECD23CED7" +
                        "2FE9D50C563343C9A1E&video_id=0tejvIxP-A4&title=Tom+and+Jerry+Episode+157+The+Mouse+from+H+U+N+G" +
                        "+E+R+Part+1&rm=sn-nxg8pivnupob-n89e7l,sn-nxg8pivnupob-h5q67e,sn-h5qly76&req_id=c08d5d10388ca3e" +
                        "e&redirect_counter=3&cms_redirect=yes&ipbypass=yes&mip=2409:4053:81a:49ea:35d3:36d2:5313:a877&" +
                        "mm=30&mn=sn-qxa7snee&ms=nxu&mt=1551674675&mv=m");*/
                                Uri uri = Uri.parse("https://boxinall.in/BIAOfficeAttendence/member_details.csv");
                                Uri uri2 = Uri.parse("https://boxinall.in/BIAOfficeAttendence/timing_details.csv");
                                DownloadManager.Request request = new DownloadManager.Request(uri);
                                DownloadManager.Request request2 = new DownloadManager.Request(uri2);
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request2.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setDestinationInExternalPublicDir("/bia", "member_details.csv");
                                request2.setDestinationInExternalPublicDir("/bia", "timing_details.csv");
                                Long reference = downloadManager.enqueue(request);
                                Long reference2 = downloadManager.enqueue(request2);
                                Toast.makeText(MainActivity.this, "Download Completed", Toast.LENGTH_LONG).show();
                            }
                        }, 5000);
                    }
                });
            } else {
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}