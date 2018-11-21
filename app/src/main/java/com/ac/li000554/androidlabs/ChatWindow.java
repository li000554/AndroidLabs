package com.ac.li000554.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import static com.ac.li000554.androidlabs.ChatDatabaseHelper.TABLE_NAME;

public class ChatWindow extends Activity {

    private ListView listView;
    private Button sendButton;
    private EditText editText;
    private ArrayList<MessageResult> messageList;
    private ChatAdapter messageAdapter;
    private ChatDatabaseHelper chatDatabaseHelper; //lab5
    protected String ACTIVITY_NAME = "Chat Window";
    private String chatWindow = ChatWindow.class.getSimpleName();
    private SQLiteDatabase database;
    private Cursor cursor;
    private String data;
    protected boolean frameLayoutExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        /*Check if the frameLayout exists*/
        if(findViewById(R.id.frame) !=null){
            frameLayoutExists = true;
        }else   frameLayoutExists = false;

        /* lab4 */

        listView = (ListView) findViewById(R.id.chatView);
        sendButton = (Button) findViewById(R.id.sendButton);
        editText = (EditText) findViewById(R.id.chatBox);
        messageList = new ArrayList<>();

        messageAdapter = new ChatAdapter(messageList, this);//means chatWindow
        listView.setAdapter(messageAdapter);

        //Lab5 :read and write in a  table.(step 5,6) this is (ChatWindow )a context obj.
        chatDatabaseHelper = new ChatDatabaseHelper(this);
        database = chatDatabaseHelper.getWritableDatabase();

        getMessageFromDB();

        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            MessageResult msgResult = messageAdapter.getItem(position);

            if (frameLayoutExists) {
                MessageFragment myFragment = new MessageFragment();
                Bundle infoToPass =  new Bundle();
                infoToPass.putLong("ID", msgResult.getId());
                infoToPass.putString("Message", msgResult.getMsg());
                infoToPass.putBoolean("isTablet", frameLayoutExists);
                myFragment.setArguments(infoToPass);
                getFragmentManager().beginTransaction().replace(R.id.frame, myFragment).commit();
                              //messageFrameLayout
            } else {
                Intent next = new Intent(this, MessageDetails.class);
                next.putExtra("Message", msgResult.getMsg());
                next.putExtra("ID", msgResult.getId());
                next.putExtra("frameLayoutExists", frameLayoutExists);
                startActivityForResult(next, 10);
            }
        });


        sendButton.setOnClickListener((view)-> {

                data = editText.getText().toString();
                MessageResult m = new MessageResult(-1, data);
                messageList.add(m);
                messageAdapter.notifyDataSetChanged();      //restarts the process of getCount()/getView()

                //lab5 add message into table, contentValues will be put into table
                ContentValues contentValues = new ContentValues();
                contentValues.put(ChatDatabaseHelper.KEY_MESSAGE, data);
                database.insert(TABLE_NAME, "null", contentValues);

                editText.setText("");
        });
    }


    private void getMessageFromDB(){
        cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        /* */

        if(cursor != null){
            while(cursor.moveToNext()){
                final String chatWindow = cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) );
                final long id = cursor.getLong(cursor.getColumnIndex(chatDatabaseHelper.KEY_ID));

                MessageResult messageResult= new MessageResult(id, chatWindow);
                Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + chatWindow );
                messageList.add(messageResult);
            }
            Log.i(ACTIVITY_NAME, "Cursor's Column Count "+cursor.getColumnCount());
        }

        for(int j = 0; j < cursor.getColumnCount(); j++){
            Log.i(ACTIVITY_NAME, "Cursor's Column Count " + cursor.getColumnCount());
            System.out.println(cursor.getColumnName(j));
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 10) {
            long id = data.getLongExtra("id", -1);
            if (id != -1) deleteMessage(id);
        }
    }

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

    public void deleteMessage(long id) {
        final int deleted = database.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID + " = ?", new String[]{ String.valueOf(id) });
        messageList.clear();    //clear list
        getMessageFromDB();
        messageAdapter.notifyDataSetChanged();
    }

    //inner class
    private class ChatAdapter extends BaseAdapter {
        private ArrayList<MessageResult> list;
        private Context ctx;


        ChatAdapter(ArrayList<MessageResult> list, Context ctx) {
            this.list = list;
            this.ctx = ctx;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public MessageResult getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position){
            return list.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;

            if(position % 2 == 0) result = inflater.inflate(R.layout.layout_row_incoming,null);
            else    result = inflater.inflate(R.layout.layout_row_outgoing,null);

            TextView message = (TextView) result.findViewById(R.id.message);
            message.setText(getItem(position).getMsg());    //get String in position

            return result;
        }

    }
}