package com.example.amritaplacementtrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.protocol.HTTP;


public class TestResult extends Activity {

    String mark;
    TextView marks;
    Button goHome;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        Bundle extras = getIntent().getExtras();
        mark = extras.getString("mark");

        marks = (TextView) findViewById(R.id.textViewMark);
        webView = (WebView) findViewById(R.id.webView);
       // marks.setText(mark);
        goHome = (Button) findViewById(R.id.buttonGoHome);

        webView.loadData(mark,"text/htm", "UTF-8");
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestResult.this,Loginsuccess.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
