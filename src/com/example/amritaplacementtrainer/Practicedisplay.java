package com.example.amritaplacementtrainer;



import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Practicedisplay extends Activity {
	int i;
	TextView tv1;
	Button btnNext;
	RadioGroup rg;
	RadioButton rb0,rb1,rb2,rb3;
	int flag=0;
	String questions[]={"A train running at the speed of 60 km/hr crosses a pole in 9 seconds. What is the length of the train?",
	"A train 125 m long passes a man, running at 5 km/hr in the same direction in which the train is going, in 10 seconds. The speed of the train is:",
	"The length of the bridge, which a train 130 metres long and travelling at 45 km/hr can cross in 30 seconds, is:",
	"Two trains running in opposite directions cross a man standing on the platform in 27 seconds and 17 seconds respectively and they cross each other in 23 seconds. The ratio of their speeds is",
	"A train passes a station platform in 36 seconds and a man standing on the platform in 20 seconds. If the speed of the train is 54 km/hr, what is the length of the platform?"};
	String opt[]={"120 metres","180 metres","324 metres","150 metres","45 km/hr","50 km/hr","54 km/hr","55 km/hr","200 m","225 m","245 m","250 m","1 : 3","3 : 2","3 : 4","None of these","120 m","240 m","300 m","None of these"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practicedisplay);
		 tv1=(TextView)findViewById(R.id.tv);
	        btnNext=(Button)findViewById(R.id.btn);
	        rg=(RadioGroup)findViewById(R.id.radioGroup1);
	        rb0=(RadioButton)findViewById(R.id.radio0);
	        rb1=(RadioButton)findViewById(R.id.radio1);
	        rb2=(RadioButton) findViewById(R.id.radio2);
	        rb3= (RadioButton) findViewById(R.id.radio3);
	        
	        
	        tv1.setText(questions[flag]);
	       rb0.setText(opt[0]);
	        rb1.setText(opt[1]);
	        rb2.setText(opt[2]);
	        rb3.setText(opt[3]);
	        setupmessagebutton();
	    }
	        
	        private void setupmessagebutton()
	    	{
	    		
	    			Button messageButton5=(Button)findViewById(R.id.btn);
	    		messageButton5.setOnClickListener(new View.OnClickListener(){
	    			public void onClick(View arg0){
	    				
	    			
	    	for(i=0;i<4;i++)	
	    	{
	        
	        	tv1.setText(questions[flag*1]);
	        	rb0.setText(opt[flag*4]);
	        	rb1.setText(opt[(flag*4)+1]);
	        	rb2.setText(opt[(flag*4)+2]);
	        	rb3.setText(opt[(flag*4)+3]);
	        	
	        
	    	}
	    	
	    	flag++;
	    	if(flag>5)
			{
		Toast.makeText(getApplicationContext(),"YOU HAVE COME TO THE END",Toast.LENGTH_SHORT).show();
			}
	    			}
	    			
	    				
	    		});
	    	}


}
