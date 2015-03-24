package com.example.amritaplacementtrainer;



import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
    Question ques[];
    AnswerQuestion aq;
    String Content,Subject,userId,uniqueId;
    ArrayList<AnswerQuestion> answerQuestionArrayList;
    HttpResponse httpResponse;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practicedisplay);
        SharedPreferences loginDetails = getSharedPreferences(MainActivity.fileName,0);
        userId = loginDetails.getString("user_id","");
        uniqueId = loginDetails.getString("unique_id","");
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

            answerQuestionArrayList = new ArrayList<AnswerQuestion>();

        if (userId == "" || uniqueId == ""){
            Toast.makeText(Practicedisplay.this,"Something went wrong please and try logging in again.",Toast.LENGTH_LONG).show();
            finish();
        }else
	        new QuestionGetOperation().execute("http://amritaplacementtrainer.comlu.com/questions.php");

	        setupmessagebutton();
	    }
	        
        private void setupmessagebutton()
        {
            Button messageButton5=(Button)findViewById(R.id.btn);
            messageButton5.setOnClickListener(new View.OnClickListener(){
                public void onClick(View arg0){
                    RadioButton rd;
                    rd = new RadioButton(Practicedisplay.this);
                    aq = new AnswerQuestion();
                    if (rg.getCheckedRadioButtonId() != -1){
                        rd = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                        aq.answer = rd.getText().toString();
                    }
                    else
                        aq.answer = "";
                    aq.id = ques[i-1].id;
                    answerQuestionArrayList.add(aq);
                    if(i < ques.length)
                    {

                        tv1.setText(ques[i].que);
                        rb0.setText(ques[i].opt[0]);
                        rb1.setText(ques[i].opt[1]);
                        rb2.setText(ques[i].opt[2]);
                        rb3.setText(ques[i].opt[3]);

                        rg.clearCheck();
                    }
                    else {
                        Toast.makeText(Practicedisplay.this,"Test is complete.",Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < answerQuestionArrayList.size(); i++)
                            jsonArray.put(answerQuestionArrayList.get(i).getJsonObject());
                        new sendDataToServer().execute("http://amritaplacementtrainer.comlu.com/marks.php",jsonArray.toString());
                    }

                    i++;
                }
            });
        }


    // Class with extends AsyncTask class
    private class QuestionGetOperation extends AsyncTask<String, Void, Void> {

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

                // Server url call by Post method
                HttpPost httpPost = new HttpPost(urls[0]);
                List<NameValuePair> postData = new ArrayList<NameValuePair>(3);
                postData.add(new BasicNameValuePair("subject",Subject));
                postData.add(new BasicNameValuePair("user_id",userId));
                postData.add(new BasicNameValuePair("unique_id",uniqueId));
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
        if(!Content.equals("")) {
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

    private class sendDataToServer extends AsyncTask<String,Void,Void>{

        private final HttpClient Client = new DefaultHttpClient();
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(Practicedisplay.this);
        String marks;

        protected void onPreExecute() {
            Dialog.setMessage("Checking answer...");
            Dialog.show();
        }

        protected Void doInBackground(String... params) {

            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by Post method
                HttpPost httpPost = new HttpPost(params[0]);
                List<NameValuePair> postData = new ArrayList<NameValuePair>(1);
                postData.add(new BasicNameValuePair("answers",params[1]));
                httpPost.setEntity(new UrlEncodedFormEntity(postData));
                HttpResponse response = Client.execute(httpPost);
                httpResponse = response;
                marks = EntityUtils.toString(response.getEntity());
              //  if (marks.equals(null))
                //    marks = "something wrong";
                Log.d("marks", marks);
                // Decoding the json data
                marks = getMarkJsonDecode(marks);

            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void aVoid) {
            //Dialog.dismiss();
            Intent intent = new Intent(Practicedisplay.this,TestResult.class);
           // marks = "<html>" + marks +"</html>";
            Object o = httpResponse;
            intent.putExtra("mark",marks);
            startActivity(intent);
            finish();
        }
    }

    private String getMarkJsonDecode(String m) {
        JSONObject j = new JSONObject();
        try {
             j =  new JSONObject(m);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (!j.equals(null))
            try {
                return j.get("Marks").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        return "Error";
    }

}
