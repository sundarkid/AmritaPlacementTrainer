package com.example.amritaplacementtrainer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setupmessagebutton();
	}
	private void setupmessagebutton()
	{
		
			Button messageButton2=(Button)findViewById(R.id.login1);
		messageButton2.setOnClickListener(new View.OnClickListener(){
			public void onClick(View arg0){
				startActivity(new Intent(LoginActivity.this,Loginsuccess.class));
			}
			});
			
		TextView x = (TextView)	findViewById(R.id.forgotpassword);
		x.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(LoginActivity.this,ForgotPassword.class));
			}
			
		

		});
	}
	



}
