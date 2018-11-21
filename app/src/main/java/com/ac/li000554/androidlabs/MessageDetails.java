package com.ac.li000554.androidlabs;

import android.app.Activity;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        String message = getIntent().getStringExtra("Message");
        Long messageId = getIntent().getLongExtra("ID", 0);

        Bundle bundle = new Bundle();
        bundle.putString("Message", message);
        bundle.putLong("ID", messageId);

        MessageFragment mFragment = new MessageFragment();
        mFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().add(R.id.fragment_location, mFragment).commit();
    }

}
