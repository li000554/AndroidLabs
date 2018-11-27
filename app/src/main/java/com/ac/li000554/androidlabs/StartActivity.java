package com.ac.li000554.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class StartActivity extends Activity {
    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        final Button listButton = findViewById(R.id.list_button);


        listButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){

                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);

                startActivityForResult(intent, 80);

            }

        });

        final Button chatButton = findViewById(R.id.chatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){

                Intent intent = new Intent(StartActivity.this, ChatWindow.class);

                startActivity(intent);

            }

        });

        final Button forecastButton = findViewById(R.id.forecastButton);
        forecastButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){

                Log.i(ACTIVITY_NAME, "You clicked button forecast");

                Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
                
                startActivity(intent);

            }

        });

        final Button toolbarButton = findViewById(R.id.testToolbar);
        toolbarButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){

                Log.i(ACTIVITY_NAME, "You clicked button Toolbar");

                Intent intent = new Intent(StartActivity.this, TestToolBar.class);

                startActivity(intent);

            }

        });

    }

    protected void onActivityResult(int requestCode, int responseCode, Intent data){

        if (requestCode == 10){

            Log.i(ACTIVITY_NAME,"Returned to StartActivity.onActivityResult");

        }


        if (responseCode == Activity.RESULT_OK) {

            String messagePassed = data.getStringExtra("Response");

            CharSequence text = "ListItemActivity passed: ";

            Toast.makeText(getApplicationContext(), text+messagePassed, Toast.LENGTH_LONG).show(); //this is the ListActivity

        }

    }

    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

}

