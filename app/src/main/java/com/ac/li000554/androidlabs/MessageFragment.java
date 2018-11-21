package com.ac.li000554.androidlabs;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {
    protected static final String ACTIVITY_NAME = "MessageFragment";
    protected boolean exists;
    TextView textView, idView;
    Button deleteBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_message_fragment, container, false);

        textView = (TextView)view.findViewById(R.id.textViewMessage);
        idView = (TextView) view.findViewById(R.id.textViewID);

        Bundle bundle = this.getArguments();
        String myValue = bundle.getString("Message");
        long id = bundle.getLong("ID");
        exists = bundle.getBoolean("isTablet");

        deleteBtn = (Button)view.findViewById(R.id.buttonDelete);
        deleteBtn.setOnClickListener(v -> {
            if (exists) {
                final ChatWindow parent = (ChatWindow) getActivity();
                //chatWindow.deleteMessage(id);
                parent.deleteMessage(id);
                getFragmentManager().beginTransaction().remove(MessageFragment.this).commit();
            }
            else {
                Intent intent = new Intent();
                intent.putExtra("id", id);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });

        textView.setText("");
        textView.setText(myValue);
        idView.setText("");
        idView.setText(String.valueOf(id));

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
