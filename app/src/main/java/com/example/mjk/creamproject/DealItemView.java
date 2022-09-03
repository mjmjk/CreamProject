package com.example.mjk.creamproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DealItemView extends LinearLayout {

    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;

    public DealItemView(Context context) {
        super(context);

        init(context);
    }

    public DealItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_deal_item, this, true);

        textView = (TextView) findViewById(R.id.txtDate);
        textView2 = (TextView) findViewById(R.id.txtDept);
        textView3 = (TextView) findViewById(R.id.txtName);
        textView4 = (TextView) findViewById(R.id.txtCream);
        textView5 = (TextView) findViewById(R.id.txtGTopt);

    }

    public void setDate(String date) {
        textView.setText(date);
    }

    public void setDept(String dept) {
        textView2.setText(dept);
    }

    public void setName(String Name) {
        textView3.setText(Name);
    }

    public void setCream(int cream) {
        textView4.setText(String.valueOf(cream));
    }

    public void setGTopt(boolean opt) {

        if(opt==true) {
            textView5.setText("줌");
        }
        else if(opt==false) {
            textView5.setText("받음");
        }

    }
}
