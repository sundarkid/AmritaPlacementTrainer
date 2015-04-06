package com.example.amritaplacementtrainer;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends Activity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setupmessagebutton();
	}

    EditText name,password;
    String Content,result,uid;

	private void setupmessagebutton()
	{
		name = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);
			Button messageButton2=(Button)findViewById(R.id.login1);
		messageButton2.setOnClickListener(new View.OnClickListener(){
			public void onClick(View arg0){

                if(name.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter the details.",Toast.LENGTH_LONG).show();
                }else
                    new LongOperation().execute("http://amritaplacements.co.in/login.php");


			}
			});
			
		TextView x = (TextView)	findViewById(R.id.forgotpassword);
		x.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){

                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
                finish();
			}
			
		

		});
	}

    public class LongOperation extends AsyncTask<String,Void,Void>{

        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        HttpClient Client = new DefaultHttpClient();

        protected Void doInBackground(String... params) {

            try {
                HttpPost httpPost = new HttpPost(params[0]);
                List<NameValuePair> postData = new ArrayList<NameValuePair>(2);
                postData.add(new BasicNameValuePair("regno", name.getText().toString()));
                postData.add(new BasicNameValuePair("pass",password.getText().toString()));
                httpPost.setEntity(new UrlEncodedFormEntity(postData));
                HttpResponse response = Client.execute(httpPost);
                Content = EntityUtils.toString(response.getEntity());
            }catch (IOException e){

            }

            return null;
        }

        protected void onPreExecute() {

            dialog.setMessage("Logging in...");
            dialog.show();

        }

        protected void onPostExecute(Void aVoid) {
            jsonDecode(Content);
            dialog.dismiss();
            if(result.equals("Success")) {
                SharedPreferences loginDetails = getSharedPreferences(MainActivity.fileName,0);
                SharedPreferences.Editor editor = loginDetails.edit();
                editor.putString("user_id",name.getText().toString());
                editor.putString("unique_id",uid);
                editor.commit();
                startActivity(new Intent(LoginActivity.this, Loginsuccess.class));
                finish();
            }else {
                Toast.makeText(LoginActivity.this,"Something went wrong please try again.",Toast.LENGTH_SHORT).show();
                name.setText("");
                password.setText("");
                name.requestFocus();
            }
        }
    }

    private void jsonDecode(String content) {
        try {
            JSONObject object = new JSONObject(content);
            result = object.get("result").toString();
            uid = object.get("uniq_id").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
