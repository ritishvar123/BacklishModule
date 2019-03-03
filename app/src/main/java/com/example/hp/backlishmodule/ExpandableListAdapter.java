package com.example.hp.backlishmodule;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends android.widget.BaseExpandableListAdapter {

    Context context;
    List<String> listDataHeader;
    List<Integer> img_dot;
    HashMap<String, List<String>> listHashMap;
    TextView groupItemName, childItemName1, childItemName2, childItemName3;
    ImageView imageView, childItemName4;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap,
                                 List<Integer> img_dot){
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
        this.img_dot = img_dot;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;/*listHashMap.get(listDataHeader.get(i)).size();*/
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1); // i = group item   i1 = child item
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if (view==null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group_item, null);
        }
        groupItemName = (TextView)view.findViewById(R.id.group_item_name);
        imageView = (ImageView)view.findViewById(R.id.imageView);
        groupItemName.setText(headerTitle);
        imageView.setImageResource(img_dot.get(i));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        //String childText = (String)getChild(i, i1);
        if (view==null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_child_items, null);
        }
        childItemName1 = (TextView)view.findViewById(R.id.child_item_name1);
        childItemName2 = (TextView)view.findViewById(R.id.child_item_name2);
        childItemName3 = (TextView)view.findViewById(R.id.child_item_name3);
        childItemName4 = (ImageView)view.findViewById(R.id.child_item_name4);
        childItemName1.setText((String)getChild(i, 0));
        childItemName2.setText((String)getChild(i, 1));
        childItemName3.setText((String)getChild(i, 2));
        childItemName4.setImageResource(Integer.parseInt((String)getChild(i, 3)));
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}