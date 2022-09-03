package com.example.mjk.creamproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mjk on 2017-11-26.
 */

public class JoinToast  extends AppCompatActivity {
    static String id;
    static String dept;
    static String name;
    static String level;
    static String cream;
    static String bank;
    static String bankNum;
    static String phone;
    static String gauge;

    TextView nametxt;
    TextView depttxt;
    TextView phonetxt;
    TextView banktxt;
    TextView accounttxt;

    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        id = getIntent().getStringExtra("actionId");
        dept = getIntent().getStringExtra("actionDept");
        name = getIntent().getStringExtra("actionName");
        level =getIntent().getStringExtra("userLevel");
        cream =getIntent().getStringExtra("userCream");
        bank = getIntent().getStringExtra("userBank");
        bankNum = getIntent().getStringExtra("userBankNum");
        phone = getIntent().getStringExtra("userPhone");
        gauge = getIntent().getStringExtra("usergauge");
        ActionBar actionBar = getSupportActionBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jointoast);



        this.setTitle(id + " " + dept + " " + name);
        nametxt = (TextView)findViewById(R.id.nametxt);
        depttxt = (TextView)findViewById(R.id.depttxt);
        phonetxt = (TextView)findViewById(R.id.phonetxt);
        banktxt = (TextView)findViewById(R.id.banktxt);
        accounttxt = (TextView)findViewById(R.id.accounttxt);
        nextButton = (Button)findViewById(R.id.nextButton);
        nametxt.setText(id);
        depttxt.setText(dept);
        phonetxt.setText(phone);
        banktxt.setText(bank);
        accounttxt.setText(bankNum);






        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent outIntent = new Intent(getApplicationContext(), MyPage.class);


                outIntent.putExtra("actionId",id);
                outIntent.putExtra("actionDept",dept);
                outIntent.putExtra("actionName",name);
               startActivity(outIntent);
                finish();
            }
        });
    }
}
