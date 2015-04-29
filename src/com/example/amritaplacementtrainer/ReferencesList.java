package com.example.amritaplacementtrainer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
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


public class ReferencesList extends Activity {

    ListView listView;
    ArrayList<SingleRow> NamesAndLinks;
    EditText text;
    String Content, searchString;
    Boolean Check;
    Button search;
    MyListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references_list);
        listView = (ListView) findViewById(R.id.listViewReferences);
        search = (Button) findViewById(R.id.buttonSearch);
        NamesAndLinks = new ArrayList<SingleRow>();
        text = (EditText) findViewById(R.id.editTextSearch);
        listAdapter = new MyListAdapter(ReferencesList.this, NamesAndLinks);
        listView.setAdapter(listAdapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchString = text.getText().toString();
                new FileDetailsFromServer().execute("http://amritaplacements.co.in/atmp.php",searchString);

            }
        });


    }

    private class FileDetailsFromServer extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(ReferencesList.this);


        @Override
        protected void onPreExecute() {

            //UI Element
            Dialog.setMessage("Checking for references...");
            Dialog.show();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... urls) {

            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by Post method
                HttpPost httpPost = new HttpPost(urls[0]);
                List<NameValuePair> postData = new ArrayList<NameValuePair>(1);
                postData.add(new BasicNameValuePair("query", searchString));
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

        @Override
        protected void onPostExecute(Void aVoid) {

            // NOTE: You can call UI Element here.
            jsonDecode();
            // Close progress dialog
            Dialog.dismiss();
            listAdapter = new MyListAdapter(ReferencesList.this,NamesAndLinks);
            listView.setAdapter(listAdapter);
            if(Check)
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(NamesAndLinks.get(position).link));
                        startActivity(intent);

                    }
                });


            super.onPostExecute(aVoid);
        }

    }

    public void jsonDecode() {

        try {
            Log.d("Inside json decode", "");
            /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
            if (!Content.equals("")) {
                JSONObject jsonResponse = new JSONObject(Content);
                /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                /*******  Returns null otherwise.  *******/
                JSONArray jsonMainNode = jsonResponse.optJSONArray("References");
                /*********** Process each JSON Node ************/
                int lengthJsonArr = jsonMainNode.length();

                String name, link;
                NamesAndLinks.clear();
                for (int i = 0; i < lengthJsonArr; i++) {
                    /****** Get Object for each JSON node.***********/
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                    name = jsonChildNode.getString("name"); // Name is got
                    link = jsonChildNode.getString("link"); // Link is got

                    NamesAndLinks.add(new SingleRow(name, link)); // Added to our list
                    Check = true;
                }

            } else {
                Check = false;
                NamesAndLinks.add(new SingleRow("No files found", "Try some other keywords"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong try again later.", Toast.LENGTH_SHORT).show();
        }


    }

    /*

    for(int i = 0; i < 10; i++){
            temp = "File " + (i + 1);
            NamesAndLinks.add(new SingleRow(temp, "link to file" + (i + 1)));
        }
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,NamesAndLinks);
        MyListAdapter listAdapter = new com.example.amritaplacementtrainer.MyListAdapter(this,NamesAndLinks);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.amritaplacements.co.in/DATA_SET/operating_systems_2.docx"));
                startActivity(intent);
            }
        });

     */


}
