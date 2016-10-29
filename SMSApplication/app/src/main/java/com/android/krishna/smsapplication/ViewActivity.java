package com.android.krishna.smsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ListView msglv = (ListView)findViewById(R.id.msg_lv);
        TextView phtv = (TextView)findViewById(R.id.noview_tv);
        Intent intent = getIntent();
        String phn = intent.getStringExtra("phonenumber");
        phtv.setText(phn);
        ArrayList<String> aryy= new ArrayList<String>();
        aryy = intent.getStringArrayListExtra("messagearraylist");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                aryy );

        msglv.setAdapter(arrayAdapter);
        msglv.setOnItemClickListener(this);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try {
            String smsMessage = (String)parent.getItemAtPosition(position);
            Toast.makeText(getApplicationContext(),smsMessage,Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
