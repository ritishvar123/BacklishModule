package com.example.hp.backlishmodule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
}
