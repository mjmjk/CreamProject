package com.example.mjk.creamproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Medal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medal);

        String[] items = {"선행 10번을 달성했습니다!","크림 100개를 모았습니다!","선행 20번을 달성했습니다!",
                "선행 30번을 달성했습니다!","크림 200개를 모았습니다!","선행 40번을 달성했습니다!",
                "선행 50번을 달성했습니다!","크림 300개를 모았습니다!","선행 60번 달성했습니다!",
                "선행 70번을 달성했습니다!","크림 400개를 모았습니다!","선행 80번을 달성했습니다!",
                "선행 90번을 달성했습니다!","크림 500개를 모았습니다!","선행 100번을 달성했습니다!"};

        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        ListView listView = (ListView) findViewById(R.id.medalList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = String.valueOf(parent.getItemAtPosition(position));
                    }
                }
        );
    }
}
