package com.example.amritaplacementtrainer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Signupsuccess extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signupsuccess);
		setupmessagebutton();
	}
	private void setupmessagebutton()
	{
		Button messageButton4=(Button)findViewById(R.id.login);
	messageButton4.setOnClickListener(new View.OnClickListener(){
		public void onClick(View arg0){
			startActivity(new Intent(Signupsuccess.this,LoginActivity.class));
		}

	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signupsuccess, menu);
		return true;
	}

}
