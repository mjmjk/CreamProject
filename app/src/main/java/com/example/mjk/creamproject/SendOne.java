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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SendOne extends AppCompatActivity {

    EditText idEditText;
    EditText nameeditText;
    EditText creameditText;
    EditText whyeditText;
    Button nextButton;

    public static int levelInt;
    public static int gaugeInt;
    public static int creamInt;
    public static int defaultExp;
    public static int levelExp;
    public static int defaultCream1;
    public static int refundCream;

    String myJSON;



    private static final String TAG_RESULT="result";
    private static final String TAG_USERLEVEL ="level";
    private  static final String TAG_GAUGE = "gauge";
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_one);

        levelInt=0;
        gaugeInt=0;
        refundCream=0;
        personList = new ArrayList<HashMap<String,String>>();

        idEditText = (EditText)findViewById(R.id.idEditText);
        nameeditText=(EditText)findViewById(R.id.nameeditText);
        creameditText =(EditText)findViewById(R.id.creameditText);
        whyeditText=(EditText)findViewById(R.id.whyeditText);
        nextButton = (Button)findViewById(R.id.nextButton);

        Intent intent = getIntent();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        final String actionId = intent.getStringExtra("actionId");
       final String actionDept = intent.getStringExtra("actionDept");
       final String actionName = intent.getStringExtra("actionName");
       final String cream = intent.getStringExtra("txtCream");
       final String level = intent.getStringExtra("txtLevel");
        final String gauge = intent.getStringExtra("userGauge");

        creamInt = Integer.parseInt(cream);
        defaultExp = 0;
        levelExp = 0;
        defaultCream1 = 100;


        this.setTitle(actionId + " " + actionDept + " " + actionName);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int giveCreamInt= Integer.parseInt(creameditText.getText().toString());
               // levelUp(defaultCream1,giveCreamInt);
                int creamInt = Integer.parseInt(cream);
                if (giveCreamInt>creamInt)
                {
                    Toast.makeText(getApplicationContext(),"보내려는 크림이 현재 가지고 있는 크림보다 큽니다",Toast.LENGTH_LONG).show();

                }
                else
                {

                    RefundCream(giveCreamInt,Integer.parseInt(level));
                    InsertData task = new InsertData();
                    task.execute(actionId,idEditText.getText().toString(),creameditText.getText().toString(),whyeditText.getText().toString(),String.valueOf(refundCream));

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    //LevelData task2 = new LevelData();
                   // task2.execute(idEditText.getText().toString(),String.valueOf(levelInt),String.valueOf(gaugeInt));
                    Intent intent = new Intent(getApplicationContext(),SendTwo.class);
                    intent.putExtra("actionId",actionId);
                    intent.putExtra("actionDept",actionDept);
                    intent.putExtra("actionName",actionName);
                    intent.putExtra("cream",cream);
                    intent.putExtra("takeId",idEditText.getText().toString());
                    intent.putExtra("takeCream",creameditText.getText().toString());
                    intent.putExtra("why",whyeditText.getText().toString());
                    intent.putExtra("refundCream",String.valueOf(refundCream));
                    intent.putExtra("level",String.valueOf(levelInt));
                    intent.putExtra("gauge",String.valueOf(gaugeInt));
                    intent.putExtra("name",nameeditText.getText().toString());
                    intent.putExtra("giveCream",creameditText.getText().toString());
                    Toast.makeText(getApplicationContext(),actionId + " " + idEditText.getText().toString()  + " " +creameditText.getText().toString()
                            +" " + String.valueOf(levelInt) + " " + String.valueOf(gaugeInt) ,Toast.LENGTH_LONG).show();
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

            progressDialog = ProgressDialog.show(SendOne.this,
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

            String giveId= (String)params[0];
            String takeId = (String)params[1];
            String givePoint = (String)params[2];
            String why = (String)params[3];
            String refundCream = (String)params[4];


            String serverURL = "http://192.168.25.47/sendOne.php";
            String postParameters = "giveId=" + giveId + "&takeId=" + takeId + "&givePoint=" + givePoint + "&why=" + why + "&refundCream=" + refundCream;

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

    class LevelData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SendOne.this,
                    "Please Wait", null, true, true);


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

        }


        @Override
        protected String doInBackground(String... params) {


            String takeId = (String)params[0];
            String level = (String)params[1];
            String gauge = (String)params[2];



            String serverURL = "http://192.168.25.47/takeOne.php";
            String postParameters = "userID=" + takeId + "&level=" + level + "&gauge=" + gauge;

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

    public int levelUp(int defaultCream,int takeCream)
    {


        int leftCream=0;//takeCream에서 경험치에 들어가고 남은양
        int calCream=0;//경험치에 들어갈 크림
        int needCream=0;//레벨 업하기위해서 앞으로 채워야할 크림의양
        creamInt+=takeCream;
        defaultExp=(int) Math.round(defaultCream*0.1);
        if(defaultCream<10)//무조건 defaulCream은 10이상이여야함.
        {
            return 0;
        }
        else
        {
            while(true)
            {
                switch(levelInt)
                {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        levelExp=(int) Math.round(((defaultCream/4)*(levelInt-1)))+defaultExp;//레벨마다 쌓아야하는 경험치
                        needCream=(int) Math.round((100-gaugeInt)*levelExp/100);//쌓아야하는 크림

                        if(takeCream>=needCream)//받은 크림이 앞으로 남은 경험치보다 많으면
                        {
                            leftCream=takeCream-needCream;//남겨둘 크림
                            calCream=takeCream-leftCream;//경험치에 쌓을 크림
                        }else{//받으 크림이 레벨업하기에는 부족하면
                            leftCream=0;
                            calCream=0;
                        }

                        if(takeCream<needCream)//레벨업하기에 부족한 크림을 경험치에 추가
                        {
                            gaugeInt=(int)Math.round((takeCream/(double)levelExp)*100);
                        }
                        else{//레벨업하기에는 많은 크림을 경험치에 추가
                            gaugeInt+=(int)Math.round((calCream/(double)levelExp)*100);
                        }
                        if(gaugeInt>=100)//레벨업
                        {
                            levelInt++;
                            gaugeInt-=100;
                            takeCream=leftCream;//남은 크림을 takeCream으로
                        }
                        else{
                            takeCream=0;
                        }
                        break;

                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                        levelExp=(int) Math.round(((defaultCream/3)*(levelInt-1)))+defaultExp;//레벨마다 쌓아야하는 경험치
                        needCream=(int) Math.round((100-gaugeInt)*levelExp/100);//쌓아야하는 크림

                        if(takeCream>=needCream)//받은 크림이 앞으로 남은 경험치보다 많으면
                        {
                            leftCream=takeCream-needCream;//남겨둘 크림
                            calCream=takeCream-leftCream;//경험치에 쌓을 크림
                        }else{//받으 크림이 레벨업하기에는 부족하면
                            leftCream=0;
                            calCream=0;
                        }

                        if(takeCream<needCream)//레벨업하기에 부족한 크림을 경험치에 추가
                        {
                            gaugeInt=(int)Math.round((takeCream/(double)levelExp)*100);
                        }
                        else{//레벨업하기에는 많은 크림을 경험치에 추가
                            gaugeInt+=(int)Math.round((calCream/(double)levelExp)*100);
                        }
                        if(gaugeInt>=100)//레벨업
                        {
                            levelInt++;
                            gaugeInt-=100;
                            takeCream=leftCream;//남은 크림을 takeCream으로
                        }
                        else{
                            takeCream=0;
                        }
                        break;
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                        levelExp=(int) Math.round(((defaultCream/2)*(levelInt-1)))+defaultExp;//레벨마다 쌓아야하는 경험치
                        needCream=(int) Math.round((100-gaugeInt)*levelExp/100);//쌓아야하는 크림

                        if(takeCream>=needCream)//받은 크림이 앞으로 남은 경험치보다 많으면
                        {
                            leftCream=takeCream-needCream;//남겨둘 크림
                            calCream=takeCream-leftCream;//경험치에 쌓을 크림
                        }else{//받으 크림이 레벨업하기에는 부족하면
                            leftCream=0;
                            calCream=0;
                        }

                        if(takeCream<needCream)//레벨업하기에 부족한 크림을 경험치에 추가
                        {
                            gaugeInt=(int)Math.round((takeCream/(double)levelExp)*100);
                        }
                        else{//레벨업하기에는 많은 크림을 경험치에 추가
                            gaugeInt+=(int)Math.round((calCream/(double)levelExp)*100);
                        }
                        if(gaugeInt>=100)//레벨업
                        {
                            levelInt++;
                            gaugeInt-=100;
                            takeCream=leftCream;//남은 크림을 takeCream으로
                        }
                        else{
                            takeCream=0;
                        }
                        break;

                }
                if(takeCream>0)
                {
                    continue;
                }
                else
                {
                    break;
                }

            }
            return 0;
        }

    }

    public void RefundCream(int takeCream,int level)
    {
        switch(level)
        {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                refundCream=(int)Math.round(takeCream*0.1);
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                refundCream=(int)Math.round(takeCream*0.2);
                break;
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
                refundCream=(int)Math.round(takeCream*0.3);
                break;
        }
    }

    private void showResult(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULT);


            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String userLevel = c.getString(TAG_USERLEVEL);
                String userGauge = c.getString(TAG_GAUGE);
                levelInt = Integer.parseInt(userLevel);
                gaugeInt= Integer.parseInt(userGauge);


            }



        } catch (JSONException e) {

            Toast.makeText(getApplicationContext(), "아이디 오류!!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
