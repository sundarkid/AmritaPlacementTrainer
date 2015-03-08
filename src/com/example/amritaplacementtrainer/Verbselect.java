package com.example.amritaplacementtrainer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class Verbselect extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verbselect);
		setupmessagebutton();
	}
	private void setupmessagebutton()
	{
		
			Button messageButton6=(Button)findViewById(R.id.verbalp);
		messageButton6.setOnClickListener(new View.OnClickListener(){
			public void onClick(View arg0){
				startActivity(new Intent(Verbselect.this,Verbprac.class));
			}

		});
		
			Button messageButton13=(Button)findViewById(R.id.verbalmain);
		messageButton13.setOnClickListener(new View.OnClickListener(){
			public void onClick(View arg0){
				startActivity(new Intent(Verbselect.this,Verbmain.class));
			}

		});
	}



}
