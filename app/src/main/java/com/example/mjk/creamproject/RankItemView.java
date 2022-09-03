package com.example.mjk.creamproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 8305-53 on 2017-11-10.
 */

public class RankItemView extends LinearLayout {
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    public RankItemView(Context context) {
        super(context);

        init(context);
    }

    public RankItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_rank_item, this, true);

        textView = (TextView) findViewById(R.id.txtRank);
        textView2 = (TextView) findViewById(R.id.txtDept);
        textView3 = (TextView) findViewById(R.id.txtName);
        textView4 = (TextView) findViewById(R.id.txtCream);

    }

    public void setRank(String Rank) {
        textView.setText(Rank);
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

}
