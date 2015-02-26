package com.example.amritaplacementtrainer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Signup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setupmessagebutton();
    }

    EditText name, register, email, phone, pass;
    String result;

    private void setupmessagebutton() {
        Button messageButton3 = (Button) findViewById(R.id.signup_login);
        messageButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(isNetworkAvailable())
                    new LongOperation().execute(null, null, null);
                else
                    Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(Signup.this,Signupsuccess.class));
            }

        });

        Button messageButton9 = (Button) findViewById(R.id.reset);
        register = (EditText) findViewById(R.id.register);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.emailaddress);
        phone = (EditText) findViewById(R.id.phonenumber);
        pass = (EditText) findViewById(R.id.passnew);

        messageButton9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                register.setText("");
                name.setText("");
                email.setText("");
                phone.setText("");
                pass.setText("");
            }

        });
    }

    // TODO THis is where internet connection takes place
    class LongOperation extends AsyncTask<Void, Void, Void> {


        private ProgressDialog Dialog = new ProgressDialog(Signup.this);

        @Override
        protected Void doInBackground(Void... voids) {
            HttpClient httpclient = new DefaultHttpClient();
            // TODO link to send the data to
            HttpPost httppost = new HttpPost("http://amritaplacementtrainer.comlu.com/signup.php");

            try {
                List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);

                // TODO this is where the post data is created with key and value for sending
                namevaluepairs.add(new BasicNameValuePair("name", name.getText().toString()));

                namevaluepairs.add(new BasicNameValuePair("pass", pass.getText().toString()));

                namevaluepairs.add(new BasicNameValuePair("registerno", register.getText().toString()));

                namevaluepairs.add(new BasicNameValuePair("email", email.getText().toString()));

                namevaluepairs.add(new BasicNameValuePair("phone", phone.getText().toString()));

                UrlEncodedFormEntity formentity;

                formentity = new UrlEncodedFormEntity(namevaluepairs);

                httppost.setEntity(formentity);

                HttpResponse response = httpclient.execute(httppost);
                // TODO the result is got from server and stored in result string
                result = EntityUtils.toString(response.getEntity());

            } catch (ClientProtocolException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Registration Error", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPreExecute() {
            Dialog.setMessage("Registering");
            Dialog.show();
        }


        protected void onPostExecute(Void unused) {
            String ans = jsonDecode();
            if (ans.equals("success")) {
                Toast.makeText(getApplicationContext(), "You have been signed up.\n Please confirm your email id.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
            Log.d("result", ans);

        }

    }

    public String jsonDecode() {

        String result = "";

        try {
            Log.d("Inside json decode", "");
            JSONObject jsonResponse = new JSONObject(this.result);

            result = jsonResponse.getString("result");

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return result;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.signup, menu);
        return true;
    }

    // Checking for network connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
