package com.example.mjk.creamproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendTwo extends AppCompatActivity {

    TextView nameView;
    TextView creamView;
    Button okBtn;

    public static int levelInt;
    public static int gaugeInt;
    public static int creamInt;
    public static int defaultExp;
    public static int levelExp;
    public static int defaultCream;
    public static int refundCream;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_two);
         intent = getIntent();
        String takeName = intent.getStringExtra("name");
        String giveCream = intent.getStringExtra("giveCream");
        String actionId =intent.getStringExtra("actionId");
        String actionDept = intent.getStringExtra("actionDept");
        String actionName = intent.getStringExtra("actionName");
        String takeId = intent.getStringExtra("takeId");
        String takeCream = intent.getStringExtra("takeCream");
        String why = intent.getStringExtra("why");
        String refundCream = intent.getStringExtra("refundCream");
        String cream = intent.getStringExtra("cream");
        String level = intent.getStringExtra("level");
        String gauge = intent.getStringExtra("gauge");
        int giveCreamInt= Integer.parseInt(takeCream);
        int creamInt = Integer.parseInt(cream);
        levelInt=Integer.parseInt(level);
        gaugeInt = Integer.parseInt(gauge);
        levelInt = 1;
        gaugeInt=30;
        nameView = (TextView)findViewById(R.id.nameView);
        creamView = (TextView)findViewById(R.id.creamView);
        okBtn = (Button)findViewById(R.id.okBtn);

        nameView.setText(String.valueOf(takeName));
        creamView.setText(String.valueOf(giveCream));

        levelUp(100,giveCreamInt);
        LevelData task2 = new LevelData();
        Toast.makeText(getApplicationContext(),String.valueOf(levelInt) + String.valueOf(gaugeInt),Toast.LENGTH_LONG).show();
        task2.execute(takeId,String.valueOf(levelInt),String.valueOf(gaugeInt));

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MyPage.class);
                startActivity(intent);
            }
        });

        this.setTitle(actionId + " " + actionDept + " " + actionName);

    }

    public int levelUp(int defaultCream,int takeCream)
    {

        int level=levelInt;
        int leftCream=0;//takeCream에서 경험치에 들어가고 남은양
        int calCream=0;//경험치에 들어갈 크림
        int needCream=0;//레벨 업하기위해서 앞으로 채워야할 크림의양
        defaultExp=(int) Math.round(defaultCream*0.1);
        if(defaultCream<10)//무조건 defaulCream은 10이상이여야함.
        {
            return 0;
        }
        else
        {
            while(true)
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
                            gaugeInt+=(int)Math.round((takeCream/(double)levelExp)*100);
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
                            gaugeInt+=(int)Math.round((takeCream/(double)levelExp)*100);
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
                            gaugeInt+=(int)Math.round((takeCream/(double)levelExp)*100);
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

    class LevelData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SendTwo.this,
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

}
