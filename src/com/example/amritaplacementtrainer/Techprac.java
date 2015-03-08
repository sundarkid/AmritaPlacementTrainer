package com.example.amritaplacementtrainer;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class Techprac extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_techprac);
	}

    public void clicking(View v){
        Button b = (Button)v;
        Intent intent = new Intent(this,Practicedisplay.class);
        intent.putExtra("subject",b.getText());
        startActivity(intent);
        finish();
    }


}
