package com.example.amritaplacementtrainer;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    public static String fileName = "Login_Details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences loginDetails = getSharedPreferences(fileName,0);
        SharedPreferences.Editor editor = loginDetails.edit();
        editor.clear();

        setupmessagebutton();
    }
private void setupmessagebutton()
{
	Button messageButton2=(Button)findViewById(R.id.login);
messageButton2.setOnClickListener(new View.OnClickListener(){
	public void onClick(View arg0){
		startActivity(new Intent(MainActivity.this,LoginActivity.class));
	}

});
Button messageButton1=(Button)findViewById(R.id.signup);
messageButton1.setOnClickListener(new View.OnClickListener(){
	public void onClick(View arg0){
		startActivity(new Intent(MainActivity.this,Signup.class));
	}

});
	}


}
