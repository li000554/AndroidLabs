package com.ac.li000554.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import static com.ac.li000554.androidlabs.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends Activity {

    ListView listView;
    Button buttonSend;
    EditText editText;
    ArrayList<String> messageList;
    public ChatAdapter messageAdapter;
    ChatDatabaseHelper chatDatabaseHelper; //lab5
    public  String ACTIVITY_NAME = "Chat Window";
    private String chatWindow = ChatWindow.class.getSimpleName();
    public SQLiteDatabase database;
    Cursor cursor;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        /* lab4 */

        listView = (ListView) findViewById(R.id.chatView);
        buttonSend = (Button) findViewById(R.id.sendButton);
        editText = (EditText) findViewById(R.id.chatBox);
        messageList = new ArrayList<>();


        final ChatAdapter messageAdapter = new ChatAdapter(this);//means chatWindow
        listView.setAdapter(messageAdapter);

        //Lab5 :read and write in a  table.(step 5,6) this is (ChatWindow )a context obj.
        chatDatabaseHelper = new ChatDatabaseHelper(this);
        database = chatDatabaseHelper.getWritableDatabase();

        cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
       /* */

        if(cursor != null){
            while(cursor.moveToNext()){
                final String chatWindow = cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) );

                Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + chatWindow );

                messageList.add(chatWindow);
            }
            Log.i(ACTIVITY_NAME, "Cursor's Column Count "+cursor.getColumnCount());

        }

        for(int j = 0; j < cursor.getColumnCount(); j++){
            Log.i(ACTIVITY_NAME, "Cursor's Column Count " + cursor.getColumnCount());
            System.out.println(cursor.getColumnName(j));
        }

        buttonSend.setOnClickListener((view)-> {

                data = editText.getText().toString();
                messageList.add(data);
                messageAdapter.notifyDataSetChanged();      //restarts the process of getCount()/getView()

                //lab5 add message into table, contentValues will be put into table
                ContentValues contentValues = new ContentValues();
                contentValues.put(ChatDatabaseHelper.KEY_MESSAGE, data);
                database.insert(TABLE_NAME, "null", contentValues);

                editText.setText("");
        });

       /* editText.setOnKeyListener((view, keyCode, event )->{
            if(event.getAction() == KeyEvent.ACTION_DOWN){
                switch (keyCode){
                    case KeyEvent.KEYCODE_DPAD_CENTER:break;
                    case KeyEvent.KEYCODE_ENTER:
                        messageList.add(editText.getText().toString());
                        messageAdapter.notifyDataSetChanged(); //restart getCount()/getView()
                        chatDatabaseHelper.insertDB(editText.getText().toString());   //insert into database
                        editText.setText("");
                        return true;

                        default:break;
                }
            }return false;

        });*/

    }

   /* public void displaySQL(){
        cursor = chatDatabaseHelper.getRecords();
        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast() ){
                Log.i(chatWindow, "SQL message: " + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            }
            Log.i(chatWindow, "Cursor's  column count = " + cursor.getColumnCount());
        }
        for(int i = 0; i < cursor.getColumnCount(); i++){
            Log.i(chatWindow, "The" + i + "row is "+ cursor.getColumnName(i));
        }
    }*/

    @Override
    protected  void onResume(){
        super.onResume();
        Log.i(chatWindow, "In onResume()");
    }

    @Override
    protected  void onStart(){
        super.onStart();
        //chatDatabaseHelper.onOpen(chatDatabaseHelper.getWritableDatabase());
       // displaySQL();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    //implement onDestroy fuction
    @Override
    protected void onDestroy(){
        super.onDestroy();
        chatDatabaseHelper.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    //inner class
    private class ChatAdapter extends ArrayAdapter<String> {

        ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        @Override
        public int getCount() {
            return messageList.size();
        }

        @Override
        public String getItem(int position) {
            return messageList.get(position);
        }

        @Override
        public long getItemId(int position){
            return super.getItemId(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position % 2 == 0) result = inflater.inflate(R.layout.layout_row_incoming,null);
            else    result = inflater.inflate(R.layout.layout_row_outgoing,null);

            TextView message = (TextView) result.findViewById(R.id.message);
            message.setText(getItem(position));
            return result;
        }

    }
}