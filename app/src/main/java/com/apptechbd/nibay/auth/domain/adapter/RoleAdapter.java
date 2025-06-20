package com.apptechbd.nibay.auth.domain.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.apptechbd.nibay.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class RoleAdapter extends BaseAdapter {
    private Context context;
    private List<String> roleList;

    public RoleAdapter(Context context, List<String> roleList) {
        this.context = context;
        this.roleList = roleList;
    }

    @Override
    public int getCount() {
        return roleList != null ? roleList.size() : 0;
    }

//    @Override
//    public Object getItem(int position) {
//        return position;
//    }

    @Override
    public Object getItem(int position) {
        return roleList.get(position);
    }

    public String getRoleAt(int position) {
        return (roleList != null && position >= 0 && position < roleList.size()) ? roleList.get(position) : null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View rootView = LayoutInflater.from(context).inflate(R.layout.row_role, parent, false);

        Log.d("RoleAdapter","text = "+roleList.get(position));
        MaterialTextView txtRole = rootView.findViewById(R.id.text_role);
        txtRole.setText(roleList.get(position));

        return rootView;
    }
}
