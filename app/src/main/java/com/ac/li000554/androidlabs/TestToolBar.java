package com.ac.li000554.androidlabs;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class TestToolBar extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "TestToolbar";

    EditText messageInput;

    TextView view;

    String notificationMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tool_bar);

        Toolbar toolbarLab8 = findViewById(R.id.toolbar_lab8);
        setSupportActionBar(toolbarLab8);

        toolbarLab8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Snackbar.make(v, "Snackbar...", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        view = findViewById(R.id.snackBar);
        int id = item.getItemId();

        switch(id){
            case R.id.appleItm:
                Log.d(ACTIVITY_NAME, "Apple selected");
                Snackbar.make(findViewById(R.id.appleItm), notificationMsg, Snackbar.LENGTH_LONG).show();
                //Snackbar.make(view, "You selected apple.", Snackbar.LENGTH_SHORT).show();

                break;
            case R.id.breadItm:

                /*Snackbar.make(view, "You selected bread.", Snackbar.LENGTH_SHORT).show();*/
                builder.setMessage(R.string.dialog_message_goback);
                builder.setTitle(R.string.dialog_title_toolbar_yes_stay)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                Intent resultIntent = new Intent();

                                setResult(Activity.RESULT_OK, resultIntent);

                                finish();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                onStop();

                            }

                        })
                        .show();

                break;
            case R.id.cherryItm:
                //Start an dialog…
                Snackbar.make(view, "You selected Cherry.", Snackbar.LENGTH_SHORT).show();

                LayoutInflater inflater = this.getLayoutInflater();
                View v = inflater.inflate(R.layout.activity_item3_dialogue, null);

                messageInput = v.findViewById(R.id.dialogue_user_msg);

                builder.setView(v)

                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                notificationMsg = messageInput.getText().toString();

                                Snackbar.make(view, notificationMsg, Snackbar.LENGTH_SHORT).show();

                                /*SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userMessage", input);
                                editor.commit();*/

                                onStop();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {
                                onStop();

                            }
                        })
                        .show();

                break;
            case R.id.about:

                Toast.makeText(this, "Version 1.0 /Developed by Guodong Li", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;

    }

}
