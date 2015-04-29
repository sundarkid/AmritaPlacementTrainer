package com.example.amritaplacementtrainer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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

public class Loginsuccess extends Activity {

    Button logOut;
    TextView noOfUsers;
    String user,uniqueId,Content,result,no_users;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loginsuccess);
		setupmessagebutton();
	}
	private void setupmessagebutton()
	{

        SharedPreferences preferences = getSharedPreferences(MainActivity.fileName,0);
        user = preferences.getString("user_id","");
        uniqueId = preferences.getString("unique_id","");
        no_users = preferences.getString("no_users","");
        noOfUsers = (TextView) findViewById(R.id.textViewLoggedInNo);
        noOfUsers.setText(no_users);
			Button messageButton0=(Button)findViewById(R.id.apti);
		messageButton0.setOnClickListener(new View.OnClickListener(){
			public void onClick(View arg0){
				startActivity(new Intent(Loginsuccess.this,Aptitudeselect.class));
			}

		});
		
			Button messageButton9=(Button)findViewById(R.id.verbal);
		messageButton9.setOnClickListener(new View.OnClickListener(){
			public void onClick(View arg0){
				startActivity(new Intent(Loginsuccess.this,Verbselect.class));
			}

		});
		
			Button messageButton8=(Button)findViewById(R.id.technical);
		messageButton8.setOnClickListener(new View.OnClickListener(){
			public void onClick(View arg0){
				startActivity(new Intent(Loginsuccess.this,Techselect.class));
			}

		});

        logOut = (Button) findViewById(R.id.buttonLogOut);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != "" && uniqueId != "")
                    new SignOutOperation().execute("http://amritaplacements.co.in/signout.php");
                else
                    Toast.makeText(Loginsuccess.this,"No valid login information, Please login again",Toast.LENGTH_LONG).show();
            }
        });
	
	}

    public class SignOutOperation extends AsyncTask<String,Void,Void>{

        ProgressDialog dialog = new ProgressDialog(Loginsuccess.this);
        HttpClient Client = new DefaultHttpClient();

        @Override
        protected Void doInBackground(String... params) {

            try {
                HttpPost httpPost = new HttpPost(params[0]);
                List<NameValuePair> postData = new ArrayList<NameValuePair>(2);
                postData.add(new BasicNameValuePair("user_id", user));
                postData.add(new BasicNameValuePair("unique_id", uniqueId));
                httpPost.setEntity(new UrlEncodedFormEntity(postData));
                HttpResponse response = Client.execute(httpPost);
                Content = EntityUtils.toString(response.getEntity());

            }catch (IOException e){

            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Signing out...");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            jsonDecode(Content);
            if (result.equalsIgnoreCase("success")){
                SharedPreferences preferences = getSharedPreferences(MainActivity.fileName,0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                finish();}
            else
                Toast.makeText(Loginsuccess.this,"Something went wrong try again",Toast.LENGTH_LONG).show();
            dialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }



    void jsonDecode(String c){
        try{
            JSONObject object = new JSONObject(c);
            result = object.get("result").toString();
        }catch (JSONException e){

        }
    }

}
