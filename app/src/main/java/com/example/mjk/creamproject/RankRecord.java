package com.example.mjk.creamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RankRecord extends AppCompatActivity {
    ListView listView;
    RankRecord.RankAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_record);

        listView = (ListView) findViewById(R.id.listview);

        adapter=new RankAdapter();

        adapter.addItem(new RankItem("1위", "전산팀", "김민정",120));
        adapter.addItem(new RankItem("2위", "보안팀", "김지원",100));
        adapter.addItem(new RankItem("3위", "홍보팀", "김소정",80));
        adapter.addItem(new RankItem("4위", "보안팀", "김태민",75));
        adapter.addItem(new RankItem("5위", "홍보팀", "노혜진",60));
        adapter.addItem(new RankItem("6위", "전산팀", "박소영",50));
        adapter.addItem(new RankItem("7위", "재무팀", "이지민",45));
        adapter.addItem(new RankItem("8위", "보안팀", "한유림",42));
        adapter.addItem(new RankItem("9위", "영업팀", "신소이",40));
        adapter.addItem(new RankItem("10위", "영업팀", "임예지",38));
        adapter.addItem(new RankItem("11위", "관리팀", "최주영",35));
        adapter.addItem(new RankItem("12위", "홍보팀", "최은정",10));


        listView.setAdapter(adapter);

    }

    class RankAdapter extends BaseAdapter {
        ArrayList<RankItem> items = new ArrayList<RankItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(RankItem item) {
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
            RankItemView view = new RankItemView(getApplicationContext());

            RankItem item = items.get(position);
            view.setRank(item.getRank());
            view.setName(item.getName());
            view.setDept(item.getDept());
            view.setCream(item.getCream());

            return view;
        }
    }
}
