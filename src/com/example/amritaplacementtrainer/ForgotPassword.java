package com.example.amritaplacementtrainer;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ForgotPassword extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);
		setupmessagebutton();
	}
	private void setupmessagebutton()
	{
		Button messageButton2=(Button)findViewById(R.id.retrievepass);
	messageButton2.setOnClickListener(new View.OnClickListener(){
		public void onClick(View arg0){
			Toast.makeText(getApplicationContext(),"Password has been sent your email", Toast.LENGTH_SHORT).show();
			finish();
		}

	});
	}


}
