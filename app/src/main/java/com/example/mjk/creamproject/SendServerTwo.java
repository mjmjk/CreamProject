package com.example.mjk.creamproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SendServerTwo extends AppCompatActivity {

   TextView giveCreamtxt;
    Button okBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_server_two);

        giveCreamtxt = (TextView)findViewById(R.id.giveCreamtxt);
        okBtn = (Button)findViewById(R.id.okBtn);

        String actionId = getIntent().getStringExtra("actionId");
        String actionDept = getIntent().getStringExtra("actionDept");
        String actionName = getIntent().getStringExtra("actionName");
        String donationCream = getIntent().getStringExtra("donationCream");

        this.setTitle(actionId + " " + actionDept + " " + actionName);

        giveCreamtxt.setText(String.valueOf(donationCream));

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyPage.class);
                startActivity(intent);
            }
        });

    }
}
