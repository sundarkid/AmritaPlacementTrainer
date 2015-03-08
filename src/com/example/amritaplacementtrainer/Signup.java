package com.example.amritaplacementtrainer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		setupmessagebutton();
	}
	private void setupmessagebutton()
	{
		Button messageButton3=(Button)findViewById(R.id.signup_login);
	messageButton3.setOnClickListener(new View.OnClickListener(){
		public void onClick(View arg0){
			startActivity(new Intent(Signup.this,Signupsuccess.class));
		}

	});
	 
		Button messageButton9=(Button)findViewById(R.id.reset);
		findViewById(R.id.register);
		findViewById(R.id.name);
		findViewById(R.id.emailaddress);
		findViewById(R.id.phonenumber);
		findViewById(R.id.passnew);
		
		
				
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



}
