package com.nikola.todoapplication.custom;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nikola.todoapplication.R;

public class ListAdapter extends ArrayAdapter {

    private final String[] nameArray;
    private final String[] dateArray;
    private final Activity activity;

    public ListAdapter(Activity context, String[] nameArray, String[] dateArray){
        super(context, R.layout.listview_row , nameArray);
        this.nameArray = nameArray;
        this.dateArray = dateArray;
        this.activity = context;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=
                activity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);


        TextView nameTextField = rowView.findViewById(R.id.name_text);
        TextView dateTextField = rowView.findViewById(R.id.date_text);

        nameTextField.setText(nameArray[position]);
        dateTextField.setText(dateArray[position]);

        return rowView;
    };
}
