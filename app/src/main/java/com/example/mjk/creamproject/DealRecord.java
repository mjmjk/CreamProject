package com.example.mjk.creamproject;

import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class DealRecord extends AppCompatActivity {

    ListView listView;
    DealAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_record);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ImageView exitbtn = (ImageView)findViewById(R.id.exitBtn);

        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listview);

        adapter=new DealAdapter();

        adapter.addItem(new DealItem("2017/10/27", "전산팀", "김민정",30,true));
        adapter.addItem(new DealItem("2017/10/28", "전산팀", "김지원",50,false));
        adapter.addItem(new DealItem("2017/10/29", "영업팀", "김철수",10,true));
        adapter.addItem(new DealItem("2017/10/30", "홍보팀", "김영희",10,true));
        adapter.addItem(new DealItem("2017/10/31", "재무팀", "김재무",5,false));
        adapter.addItem(new DealItem("2017/11/01", "자원관리팀", "김자관",30,false));
        adapter.addItem(new DealItem("2017/11/01", "영업팀", "김영업",40,false));
        adapter.addItem(new DealItem("2017/11/01", "전산팀", "김전산",7,false));
        adapter.addItem(new DealItem("2017/11/01", "홍보팀", "김홍보",8,false));

        listView.setAdapter(adapter);

    }

    class DealAdapter extends BaseAdapter {
        ArrayList<DealItem> items = new ArrayList<DealItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(DealItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            DealItemView view = new DealItemView(getApplicationContext());

            DealItem item = items.get(position);
            view.setDate(item.getDate());
            view.setName(item.getName());
            view.setDept(item.getDept());
            view.setCream(item.getCream());
            view.setGTopt(item.isOpt());

            return view;
        }




    }
}
