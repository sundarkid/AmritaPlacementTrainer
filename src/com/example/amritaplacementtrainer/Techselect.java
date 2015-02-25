package com.example.amritaplacementtrainer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Techselect extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_techselect);
		setupmessagebutton();
	}
	private void setupmessagebutton()
	{
		Button messageButton3=(Button)findViewById(R.id.techprac);
		messageButton3.setOnClickListener(new View.OnClickListener(){
			public void onClick(View arg0){
				startActivity(new Intent(Techselect.this,Techprac.class));
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.techselect, menu);
		return true;
	}

}
