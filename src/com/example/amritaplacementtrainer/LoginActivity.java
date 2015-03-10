package com.example.amritaplacementtrainer;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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
    String Content;

	private void setupmessagebutton()
	{
		name = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);
			Button messageButton2=(Button)findViewById(R.id.login1);
		messageButton2.setOnClickListener(new View.OnClickListener(){
			public void onClick(View arg0){


        //        new LongOperation().execute("http://amritaplacementtrainer.comlu.com/login.php");

				startActivity(new Intent(LoginActivity.this, Loginsuccess.class));
			}
			});
			
		TextView x = (TextView)	findViewById(R.id.forgotpassword);
		x.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
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
                postData.add(new BasicNameValuePair("name", name.getText().toString()));
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
            jsonDecode();
            dialog.dismiss();
        }
    }

    private void jsonDecode() {
        //TODO decode the data
    }


}
