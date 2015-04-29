package com.example.amritaplacementtrainer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;


public class FileActivity extends Activity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        webView = (WebView) findViewById(R.id.webViewFile);

        // Todo enter text in WebView
        // Todo enter text in WebView
        // Todo enter text in WebView
    }


}
