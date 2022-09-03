package com.example.mjk.creamproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GiveOption extends AppCompatActivity {

    LinearLayout linear1;
    LinearLayout linear2;
    LinearLayout linear3;
    LinearLayout linear4;
    LinearLayout linear5;
    String userId;
    String userDept;
    String userName;

    Spinner bankSpinner;
    EditText editAccount;

    TextView txtBank;
    TextView txtAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_option);
        userId = getIntent().getStringExtra("actionId");
        userDept = getIntent().getStringExtra("userDept");
        userName = getIntent().getStringExtra("userName");
        String bank = getIntent().getStringExtra("bank");
        String bankNum = getIntent().getStringExtra("bankNum");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
       txtBank = (TextView)findViewById(R.id.txtBank);
        txtAccount = (TextView)findViewById(R.id.txtAccount);

        bankSpinner = (Spinner)findViewById(R.id.spinner);
        editAccount = (EditText)findViewById(R.id.editAccount);

        this.setTitle(userId + " " + userDept + " " + userName);



        txtBank.setText(bank);
        txtAccount.setText(bankNum);


        Button OkBtn = (Button)findViewById(R.id.Okbtn);

        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bank = bankSpinner.getSelectedItem().toString();
                String bankNum = editAccount.getText().toString();
                BankData task = new BankData();
                task.execute(userId,bank,bankNum);

                Toast.makeText(getApplicationContext(),"은행과 계좌번호 변경 완료",Toast.LENGTH_LONG).show();
                Intent outIntent = new Intent(getApplicationContext(), MyPage.class);
                outIntent.putExtra("bank",bank );
                outIntent.putExtra("bankNum",bankNum );
                setResult(RESULT_OK, outIntent);
                finish();

            }
        });

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Switch sw = (Switch)findViewById(R.id.switch1);
        linear1 =(LinearLayout)findViewById(R.id.linear1);
        linear2 =(LinearLayout)findViewById(R.id.linear2);
        linear3 =(LinearLayout)findViewById(R.id.linear3);
        linear4 =(LinearLayout)findViewById(R.id.linear4);
        linear5 =(LinearLayout)findViewById(R.id.linear5);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // TODO Auto-generated method stub
                if(isChecked==true) {
                    InsertData task = new InsertData();
                    task.execute(userId,"true");
                    linear1.setVisibility(View.INVISIBLE);
                    linear2.setVisibility(View.INVISIBLE);
                    linear3.setVisibility(View.INVISIBLE);
                    linear4.setVisibility(View.INVISIBLE);
                    linear5.setVisibility(View.INVISIBLE);

                }
                else{
                    InsertData task = new InsertData();
                    task.execute(userId,"false");
                    linear1.setVisibility(View.VISIBLE);
                    linear2.setVisibility(View.VISIBLE);
                    linear3.setVisibility(View.VISIBLE);
                    linear4.setVisibility(View.VISIBLE);
                    linear5.setVisibility(View.VISIBLE);
                }


            }

        });




    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(GiveOption.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

        }


        @Override
        protected String doInBackground(String... params) {

            String userID = (String)params[0];
            String donationOption = (String)params[1];



            String serverURL = "http://192.168.25.39/giveOption.php";
            String postParameters = "userID=" + userID + "&donationOption=" + donationOption;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();

            } catch (Exception e) {

                return null;
            }

        }
    }

    class BankData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(GiveOption.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

        }


        @Override
        protected String doInBackground(String... params) {

            String userID = (String)params[0];
            String bank = (String)params[1];
            String bankNum = (String)params[2];


            String serverURL = "http://192.168.25.39/changeBank.php";
            String postParameters = "userID=" + userID + "&bank=" + bank + "&bankNum=" + bankNum;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();

            } catch (Exception e) {

                return null;
            }

        }
    }
}
