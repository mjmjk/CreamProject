package com.example.mjk.creamproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import static com.example.mjk.creamproject.R.color.colorRed;

public class MyPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        int flag=1;
    LinearLayout GiveBtn;
    LinearLayout donationBtn;
    LinearLayout dealBtn;
    LinearLayout rankBtn;
    LinearLayout medalBtn;

    TextView txtLevel;
    TextView txtCream;

    TextView donationCream;
    TextView giveCream;
    TextView takeCream;


    String actionId;
    String actionDept;
    String actionName;
    String userBank;
    String userBankNum;
    String userGauge;

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

    public static int sumGiveCream;
    public static int sumDonationCream;
    public static int sumTakeCream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionId = getIntent().getStringExtra("actionId");
        actionDept = getIntent().getStringExtra("actionDept");
        actionName = getIntent().getStringExtra("actionName");

        actionId ="3000123002";
        actionDept="전산팀";
        actionName="김지원";

        txtLevel = (TextView)findViewById(R.id.txt_Level);
        txtCream = (TextView)findViewById(R.id.txt_Cream);
        donationCream = (TextView)findViewById(R.id.donationCream);
        giveCream=(TextView)findViewById(R.id.giveCream);
        takeCream=(TextView)findViewById(R.id.takeCream);

        this.setTitle(actionId + " " + actionDept + " " + actionName);

        sumDonationCream=0;
        sumTakeCream=0;
        sumGiveCream=0;
        personList = new ArrayList<HashMap<String,String>>();


        GetData task1 = new GetData();
        task1.execute(actionId);
        GiveData task2 = new GiveData();
        task2.execute(actionId);
        TakeData task3 = new TakeData();
        task3.execute(actionId);
        DonationData task4 = new DonationData();
        task4.execute(actionId);

        final Button dealOpt=(Button)findViewById(R.id.dealOpt);
        dealOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag==0) {
                    dealOpt.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    dealOpt.setText("거래 허용 N");
                    flag =1;
                    final InsertData task = new InsertData();
                    task.execute(actionId,"false");

                }else
                {
                    dealOpt.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    dealOpt.setText("거래 허용 Y");
                    flag =0;
                    final InsertData task = new InsertData();
                    task.execute(actionId,"true");

                }
            }
        });

        Button donationOpt = (Button)findViewById(R.id.donationOpt);
        donationOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),GiveOption.class);
                intent.putExtra("actionId",actionId);
                intent.putExtra("userDept",actionDept);
                intent.putExtra("userName",actionName);
                intent.putExtra("bank",userBank);
                intent.putExtra("bankNum",userBankNum);
                recreate();
                startActivityForResult(intent, 0);
            }
        });

        GiveBtn =(LinearLayout)findViewById(R.id.GiveBtn);
        donationBtn =(LinearLayout)findViewById(R.id.donationBtn);
        dealBtn =(LinearLayout)findViewById(R.id.dealBtn);
        rankBtn =(LinearLayout)findViewById(R.id.rankBtn);
        medalBtn =(LinearLayout)findViewById(R.id.medalBtn);

        GiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SendOne.class);
                intent.putExtra("actionId",actionId);
                intent.putExtra("actionDept",actionDept);
                intent.putExtra("actionName",actionName);
                intent.putExtra("txtCream",txtCream.getText());
                intent.putExtra("txtLevel",txtLevel.getText());
                intent.putExtra("userGauge",userGauge);
                startActivity(intent);
            }
        });

        donationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SendServerOne.class);
                intent.putExtra("actionId",actionId);
                intent.putExtra("actionDept",actionDept);
                intent.putExtra("actionName",actionName);
                intent.putExtra("txtCream",txtCream.getText());
                intent.putExtra("txtLevel",txtLevel.getText());
                intent.putExtra("userGauge",userGauge);
                startActivity(intent);
            }
        });

        dealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DealRecord.class);
                startActivity(intent);

            }
        });

        rankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(),DealRecord.class);
                //startActivity(intent);

            }
        });

        medalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Medal.class);
                startActivity(intent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(getApplicationContext(),Left.class);
            startActivity(intent);
            finish();


        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    class InsertData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MyPage.this,
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
            String creamOption = (String)params[1];



            String serverURL = "http://192.168.25.47/mypageCreamOption.php";
            String postParameters = "userID=" + userID + "&creamOption=" + creamOption;

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


    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MyPage.this,
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
            String userID = (String)params[0];

            String serverURL = "http://192.168.25.47/mypage.php";
            String postParameters = "userID=" + userID;

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



    class GiveData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MyPage.this,
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
                showResult1();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String userID = (String)params[0];

            String serverURL = "http://192.168.25.47/giveMypage.php";
            String postParameters = "userID=" + userID;

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

    class TakeData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MyPage.this,
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
                showResult2();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String userID = (String)params[0];

            String serverURL = "http://192.168.25.47/takeMypage.php";
            String postParameters = "userID=" + userID;

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

    class DonationData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(MyPage.this,
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
                showResult3();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String userID = (String)params[0];
            String serverURL = "http://192.168.25.47/donationMypage.php";
            String postParameters = "userID=" + userID;

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
                    this.userBank=userBank;
                    this.userBankNum=userBankNum;
                    this.userGauge=userGauge;
                    txtLevel.setText(userLevel);
                    txtCream.setText(userCream);

                }



            } catch (JSONException e) {

                Toast.makeText(getApplicationContext(), "아이디 오류!!",
                        Toast.LENGTH_SHORT).show();
            }
        }

    private void showResult1(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULT);


            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String giveCream = c.getString(TAG_GIVECREAM);

                sumGiveCream+=Integer.parseInt(giveCream);
            }

            giveCream.setText(String.valueOf(sumGiveCream));

        } catch (JSONException e) {

            Toast.makeText(getApplicationContext(), "아이디 오류!!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showResult2(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULT);


            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String takeCream = c.getString(TAG_TAKECREAM);

                sumTakeCream+=Integer.parseInt(takeCream);
            }

            takeCream.setText(String.valueOf(sumTakeCream));

        } catch (JSONException e) {

            Toast.makeText(getApplicationContext(), "아이디 오류!!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void showResult3(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULT);


            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String donationCream = c.getString(TAG_DONATIONCREAM);

                sumDonationCream+=Integer.parseInt(donationCream);
            }
            donationCream.setText(String.valueOf(sumDonationCream));

        } catch (JSONException e) {

            Toast.makeText(getApplicationContext(), "아이디 오류!!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public int levelUp(int defaultCream,int takeCream)
    {

        int creamInt=30;
        int levelInt=4;
        int defaultExp=100;
        int levelExp=0;
        int gaugeInt=30;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            userBank = data.getStringExtra("bank");
            userBankNum = data.getStringExtra("bankNum");
        }
    }

}


















