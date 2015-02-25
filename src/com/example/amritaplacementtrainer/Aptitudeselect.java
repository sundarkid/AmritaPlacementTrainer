package com.example.amritaplacementtrainer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Aptitudeselect extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aptitudeselect);
		setupmessagebutton();
	}
	private void setupmessagebutton()
	{
		Button messageButton11=(Button)findViewById(R.id.prac);
	messageButton11.setOnClickListener(new View.OnClickListener(){
public void onClick(View arg0){
			startActivity(new Intent(Aptitudeselect.this,Practicedisplay.class));
		}

	});
	
		Button messageButton12=(Button)findViewById(R.id.maintest);
	messageButton12.setOnClickListener(new View.OnClickListener(){
		public void onClick(View arg0){
			startActivity(new Intent(Aptitudeselect.this,Aptimain.class));
		}

	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aptitudeselect, menu);
		return true;
	}

}
