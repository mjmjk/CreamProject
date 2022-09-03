package com.example.mjk.creamproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static com.example.mjk.creamproject.R.layout.jointoast;

public class JoinOne extends AppCompatActivity {

    Button checkButton;
    LinearLayout textLayout;

    static String  hashPasswd;

    String myJSON;

    private static final String TAG_RESULT="result";
    private static final String TAG_USERID = "id";
    private static final String TAG_USERDEPT = "department";
    private static final String TAG_USERCREAM = "cream";
    private static final String TAG_USERNAME ="name";
    private static final String TAG_BANK ="bank";
    private static final String TAG_ACCOUNT ="bankNum";
    private static final String TAG_USERLEVEL ="level";
    private static final String TAG_POINTOPTION ="pointOption";
    private static final String TAG_PHONE = "phone";
    private  static final String TAG_GAUGE = "gauge";

    private static final String TAG_DONATIONCREAM = "donationCream";
    private static final String TAG_GIVECREAM = "giveCream";
    private static final String TAG_TAKECREAM = "takeCream";
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;

    static String id;
    static String dept;
    static String name;
    static String level;
    static String cream;
    static String bank;
    static String bankNum;
    static String phone;
    static String gauge;
    EditText idEditText;
    EditText pweditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_one);

        idEditText = (EditText)findViewById(R.id.ideditText);
        pweditText = (EditText)findViewById(R.id.pweditText);
        Button checkButton = (Button)findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InsertData task = new InsertData();
                SHA256(pweditText.getText().toString());
                task.execute(idEditText.getText().toString(),hashPasswd);

                Intent intent = new Intent(getApplicationContext(),JoinToast.class);

                intent.putExtra("actionId",id);
                intent.putExtra("actionDept",dept);
                intent.putExtra("actionName",name);
                intent.putExtra("userLevel",level);
                intent.putExtra("userCream",cream);
                intent.putExtra("userBank",bank);
                intent.putExtra("userBankNum",bankNum);
                intent.putExtra("userPhone",phone);
                intent.putExtra("userGauge",gauge);
               

                startActivity(intent);
            }
        });



    }

    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(JoinOne.this,
                    "Please Wait", null, true, true);


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            if (result == null){
                Toast.makeText(getApplicationContext(), "안돼",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                myJSON = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String company_ID= (String)params[0];
            String company_password= (String)params[1];



            String serverURL = "http://192.168.25.39/joinTwo.php";
            String postParameters = "company_ID=" + company_ID+ "&company_password=" + company_password;

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
    public String SHA256(String str){


        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
           hashPasswd=hexString.toString();

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }

    }

    private void showResult(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULT);


            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String userID = c.getString(TAG_USERID);
                String userDept = c.getString(TAG_USERDEPT);
                String userName = c.getString(TAG_USERNAME);
                String userLevel = c.getString(TAG_USERLEVEL);
                String userCream = c.getString(TAG_USERCREAM);
                String userBank = c.getString(TAG_BANK);
                String userBankNum = c.getString(TAG_ACCOUNT);
                String userPhone = c.getString(TAG_PHONE);
                String userGauge = c.getString(TAG_GAUGE);

                id=userID.toString();
                dept=userDept.toString();
                name=userName.toString();
                level=userLevel.toString();
                cream=userCream.toString();
                bank=userBank.toString();
                bankNum=userBankNum.toString();
                phone=userPhone.toString();
                gauge=userGauge.toString();

            }





        } catch (JSONException e) {

            Toast.makeText(getApplicationContext(), "아이디 오류!!",
                    Toast.LENGTH_SHORT).show();
        }
    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (resultCode == RESULT_OK) {
//            id = data.getStringExtra("actionId");
//            dept = data.getStringExtra("actonDept");
//            name= data.getStringExtra("actionName");
//            level = data.getStringExtra("userLevel");
//            cream =data.getStringExtra("userCream");
//            bank = data.getStringExtra("userBank");
//            bankNum = data.getStringExtra("userBankNum");
//            phone = data.getStringExtra("userPhone");
//            gauge= data.getStringExtra("userGauge");
//        }
//    }
}
