package com.example.amritaplacementtrainer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Loginsuccess extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loginsuccess);
		setupmessagebutton();
	}
	private void setupmessagebutton()
	{
		
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
	
	}



}
