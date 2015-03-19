package com.example.amritaplacementtrainer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
        init();
		setupmessagebutton();
	}

    EditText register,name,phone,email,pass;
    String Content;
    Button messageButton9,messageButton3;
    private void init() {

        messageButton3=(Button)findViewById(R.id.signup_login);
        register = ((EditText) findViewById(R.id.register));
        name = ((EditText) findViewById(R.id.name));
        email = ((EditText) findViewById(R.id.emailaddress));
        phone = ((EditText) findViewById(R.id.phonenumber));
        pass = ((EditText) findViewById(R.id.passnew));
        messageButton9=(Button)findViewById(R.id.reset);

    }

    private void setupmessagebutton()
	{
	messageButton3.setOnClickListener(new View.OnClickListener(){
		public void onClick(View arg0){
            if(!(register.getText().toString().equals("") || name.getText().toString().equals("") ||
                    email.getText().toString().equals("") || phone.getText().toString().equals("") || pass.getText().toString().equals("")))
                new LongOperation().execute("http://amritaplacementtrainer.comlu.com/signup.php");
            else
                Toast.makeText(Signup.this,"Please enter all the details", Toast.LENGTH_LONG).show();
		}

	});

		
		
				
	messageButton9.setOnClickListener(new View.OnClickListener(){
		public void onClick(View arg0){
			((EditText) findViewById(R.id.register)).setText("");
			((EditText) findViewById(R.id.name)).setText("");
			((EditText) findViewById(R.id.emailaddress)).setText("");
			((EditText) findViewById(R.id.phonenumber)).setText("");
			((EditText) findViewById(R.id.passnew)).setText("");
		}	
		
	});
	}

    public class LongOperation extends AsyncTask<String,Void,Void>{
        private final HttpClient Client = new DefaultHttpClient();
        private ProgressDialog Dialog = new ProgressDialog(Signup.this);
        protected Void doInBackground(String... params) {

            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by Post method
                HttpPost httpPost = new HttpPost(params[0]);
                List<NameValuePair> postData = new ArrayList<NameValuePair>(5);
                postData.add(new BasicNameValuePair("name",name.getText().toString()));
                postData.add(new BasicNameValuePair("email",email.getText().toString()));
                postData.add(new BasicNameValuePair("phone",phone.getText().toString()));
                postData.add(new BasicNameValuePair("registerno",register.getText().toString()));
                postData.add(new BasicNameValuePair("pass",pass.getText().toString()));
                httpPost.setEntity(new UrlEncodedFormEntity(postData));
                HttpResponse response = Client.execute(httpPost);
                Content = EntityUtils.toString(response.getEntity());
                Log.d("Content", Content);
                // Decoding the json data


            } catch (ClientProtocolException e) {
                cancel(true);
            } catch (IOException e) {
                cancel(true);
            }

            return null;
        }

        protected void onPreExecute() {
            Dialog.setMessage("Signing up...");
            Dialog.show();
        }

        protected void onPostExecute(Void aVoid) {
            Dialog.dismiss();
            jsonDecode();
            startActivity(new Intent(Signup.this, Signupsuccess.class));
            finish();
        }

    }

    private void jsonDecode() {

        try {
            JSONObject object = new JSONObject(Content);
            Toast.makeText(this,object.get("result").toString(),Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
