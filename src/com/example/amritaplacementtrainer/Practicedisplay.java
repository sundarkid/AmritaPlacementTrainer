package com.example.amritaplacementtrainer;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amritaplacementtrainer.dataClass.Question;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Practicedisplay extends Activity {
	int i = 1;
	TextView tv1;
	Button btnNext;
	RadioGroup rg;
	RadioButton rb0,rb1,rb2,rb3;
	int flag=1;
    Question ques[];
    String Content,Subject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practicedisplay);
        Subject = "";
        Bundle extras = getIntent().getExtras();
        Subject = extras.getString("subject");
		 tv1=(TextView)findViewById(R.id.tv);
	        btnNext=(Button)findViewById(R.id.btn);
	        rg=(RadioGroup)findViewById(R.id.radioGroup1);
	        rb0=(RadioButton)findViewById(R.id.radio0);
	        rb1=(RadioButton)findViewById(R.id.radio1);
	        rb2=(RadioButton) findViewById(R.id.radio2);
	        rb3= (RadioButton) findViewById(R.id.radio3);
	        
	    new LongOperation().execute("http://amritaplacementtrainer.comlu.com/questions.php");

	        setupmessagebutton();
	    }
	        
        private void setupmessagebutton()
        {
            Button messageButton5=(Button)findViewById(R.id.btn);
            messageButton5.setOnClickListener(new View.OnClickListener(){
                public void onClick(View arg0){
                    if(flag < ques.length)
                    {
                        tv1.setText(ques[i].que);
                        rb0.setText(ques[i].opt[0]);
                        rb1.setText(ques[i].opt[1]);
                        rb2.setText(ques[i].opt[2]);
                        rb3.setText(ques[i].opt[3]);
                    }
                    else {
                        Toast.makeText(Practicedisplay.this,"Test is complete.",Toast.LENGTH_SHORT).show();
                        //TODO send answers to the server
                        finish();
                    }
                    flag++;
                    i++;
                }
            });
        }


    // Class with extends AsyncTask class
    private class LongOperation  extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Practicedisplay.this);

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            Dialog.setMessage("Downloading Questions..");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by GET method
                HttpPost httpPost = new HttpPost(urls[0]);
                List<NameValuePair> postData = new ArrayList<NameValuePair>(1);
                postData.add(new BasicNameValuePair("subject",Subject));
                httpPost.setEntity(new UrlEncodedFormEntity(postData));
                HttpResponse response = Client.execute(httpPost);
                Content = EntityUtils.toString(response.getEntity());
                Log.d("Content", Content);
                // Decoding the json data


            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.
            jsonDecode();
            // Close progress dialog
            Dialog.dismiss();
            // Setting the first question
            tv1.setText(ques[0].que);
            rb0.setText(ques[0].opt[0]);
            rb1.setText(ques[0].opt[1]);
            rb2.setText(ques[0].opt[2]);
            rb3.setText(ques[0].opt[3]);
        }


    }

    public void jsonDecode(){

        try {
            Log.d("Inside json decode","");
            /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
        if(Content.equals("")) {
            JSONObject jsonResponse = new JSONObject(Content);
            /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
            /*******  Returns null otherwise.  *******/
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Questions");
            /*********** Process each JSON Node ************/
            int lengthJsonArr = jsonMainNode.length();
            ques = new Question[lengthJsonArr];

            for (int i = 0; i < lengthJsonArr; i++) {
                /****** Get Object for each JSON node.***********/
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                Log.d("child node",jsonChildNode.toString(1));
                /******* Fetch node values **********/
                ques[i] = new Question();
                ques[i].id = Integer.parseInt(jsonChildNode.optString("id_no"));
                if(jsonChildNode.get("question").equals(null))
                    ques[i].que = "";
                else
                    ques[i].que = jsonChildNode.optString("question");
                String[] opt = new String[4];
                if(jsonChildNode.get("optionA").equals(null))
                    opt[0] = "";
                else
                    opt[0] = jsonChildNode.get("optionA").toString();
                if(jsonChildNode.get("optionB").equals(null))
                    opt[1] = "";
                else
                    opt[1] = jsonChildNode.get("optionB").toString();
                if(jsonChildNode.get("optionC").equals(null))
                    opt[2] = "";
                else
                    opt[2] = jsonChildNode.get("optionC").toString();
                if(jsonChildNode.get("optionD").equals(null))
                    opt[3] = "";
                else
                    opt[3] = jsonChildNode.optString("optionD");
                ques[i].setOpt(opt);
            }
        }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this,"Something went wrong try again later.",Toast.LENGTH_SHORT).show();
        }


    }

}
