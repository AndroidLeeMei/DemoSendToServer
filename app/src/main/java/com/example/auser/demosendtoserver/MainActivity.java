package com.example.auser.demosendtoserver;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String HTTP_URL="http://192.168.58.23/default2.aspx";
    EditText et_name,et_age,et_address,et_phoneNumber,et_favori1,et_favori2,et_favori3,et_favori4;
    RadioButton rbtn_male,rbtn_female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

    }

    private void findViews(){
        et_name=(EditText)findViewById(R.id.et_name);
        et_age=(EditText)findViewById(R.id.et_age);
        et_address=(EditText)findViewById(R.id.et_address);
        et_favori1=(EditText)findViewById(R.id.et_favor1);
        et_favori2=(EditText)findViewById(R.id.et_favor2);
        et_favori3=(EditText)findViewById(R.id.et_favor3);
        et_favori4=(EditText)findViewById(R.id.et_favor4);
        et_phoneNumber=(EditText)findViewById(R.id.et_phoneNumber);
        rbtn_male=(RadioButton)findViewById(R.id.rbtn_male);
        rbtn_female=(RadioButton)findViewById(R.id.rtn_female);
    }
    private  String createJsonString(){
        String name=et_name.getText().toString();
        Person p;
        Data data;
        Log.d("serv==","aa"+name);
        Log.d("serv==",name.equals(null)+"");
        Log.d("serv==",(name.equals(""))+"");
        if (!name.equals("")) {
            String address = et_address.getText().toString();
            int age = Integer.parseInt(et_age.getText().toString());
            String phoneNumber = et_phoneNumber.getText().toString();
            boolean isMale = rbtn_male.isChecked();
            ArrayList<String> favorites = new ArrayList<>();
            favorites.add(et_favori1.getText().toString());
            favorites.add(et_favori2.getText().toString());
            favorites.add(et_favori3.getText().toString());
            favorites.add(et_favori4.getText().toString());
            data=new Data(address,phoneNumber);
            p=new Person(name,age,isMale,data,favorites);
        }else{
            p=new Person();
        }

        return new Gson().toJson(p);
    }

    public void doSend(View target){
        String jsonStr=createJsonString();
        Log.d("serive==",createJsonString());
        new MyAsyncTask().execute(jsonStr);//類似thread物件的start(),將jsonStr利用背景執行緒將資料上傳
    }

    class MyAsyncTask extends AsyncTask<String,Integer,String>{//進行網路連線方法

        @Override
        protected String doInBackground(String... strings) {

            return uploadData(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("result="+ s);
        }
    }
    String uploadData(String jsonString) {
        HttpURLConnection conn = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            URL url = new URL(HTTP_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");//有兩種post(適合資料量多時),get,
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("Submit", "Submit")//1.視別字2.來若在server收到Submit=Submit判斷是由手機傳
                    .appendQueryParameter("JSON", jsonString);
            String query = builder.build().getEncodedQuery();
            out = new BufferedOutputStream(conn.getOutputStream());
            out.write(query.getBytes());
            out.flush();//資料可以送出去
            //伺服器的response
            in = new BufferedInputStream(conn.getInputStream());
            byte[] buf = new byte[1024];
            in.read(buf);
            String result = new String(buf); //將資料由位元組轉換成可讀的字
            return result.trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "Send Failed";
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (conn != null)
                conn.disconnect();
        }
    }


}
