package com.example.mjk.creamproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SendServerOne extends AppCompatActivity {
    EditText creamtxt;
    Button nextBtn;



    String myJSON;



    private static final String TAG_RESULT="result";
    private static final String TAG_USERLEVEL ="level";
    private  static final String TAG_GAUGE = "gauge";
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_server_one);



        personList = new ArrayList<HashMap<String,String>>();
        Intent intent = getIntent();

        creamtxt = (EditText)findViewById(R.id.creamtxt);
        nextBtn = (Button)findViewById(R.id.nextButton);
        ActionBar actionBar = getSupportActionBar();
        final String actionId = getIntent().getStringExtra("actionId");
        final String actionDept = getIntent().getStringExtra("actionDept");
        final String actionName = getIntent().getStringExtra("actionName");
        final String cream = intent.getStringExtra("txtCream");
        final String level = intent.getStringExtra("txtLevel");
        final String gauge = intent.getStringExtra("userGauge");


        this.setTitle(actionId + " " + actionDept + " " + actionName);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int giveCreamInt= Integer.parseInt(creamtxt.getText().toString());
                int creamInt = Integer.parseInt(cream);
                if (giveCreamInt>creamInt)
                {
                    Toast.makeText(getApplicationContext(),"보내려는 크림이 현재 가지고 있는 크림보다 큽니다",Toast.LENGTH_LONG).show();
                }
                else
                {
                    InsertData task = new InsertData();
                    task.execute(actionId,creamtxt.getText().toString());

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getApplicationContext(),SendServerTwo.class);
                    intent.putExtra("actionId",actionId);
                    intent.putExtra("actionDept",actionDept);
                    intent.putExtra("actionName",actionName);
                    intent.putExtra("donationCream",creamtxt.getText().toString());
                    startActivity(intent);

                }
            }
        });
    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SendServerOne.this,
                    "Please Wait", null, true, true);


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
        }


        @Override
        protected String doInBackground(String... params) {

            String userId= (String)params[0];
            String donationCream= (String)params[1];



            String serverURL = "http://192.168.25.39/donationServer.php";
            String postParameters = "userId=" + userId + "&donationCream=" + donationCream;

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
                errorString = e.toString();
                return null;
            }

        }
    }


}
