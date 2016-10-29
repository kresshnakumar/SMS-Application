package com.android.krishna.smsapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class InboxActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private  static InboxActivity inst;

    ArrayList<String> messagesList = new ArrayList<String>();
    ListView smsLV;
    ArrayAdapter array;
    HashMap<String,ArrayList<String>> groupMessages = new HashMap<String,ArrayList<String>>();
    public static InboxActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        smsLV = (ListView) findViewById(R.id.inbox_lv);
        array = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messagesList);
        smsLV.setAdapter(array);
        smsLV.setOnItemClickListener(this);

        refreshInboxMessages();
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            Log.d("PLAYGROUND", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 123);
        }
        else {
            Log.d("PLAYGROUND", "Permission is granted");
        }
    }

    public void refreshInboxMessages() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
        array.clear();
        groupMessages.clear();
        do {
            String str = smsInboxCursor.getString(indexAddress) ;
            String s = smsInboxCursor.getString(indexBody) ;

            if(groupMessages.containsKey(str)) {
                ArrayList<String> ary = groupMessages.get(str);
                ary.add(0,s);
            }
            else {
                ArrayList<String> ary = new ArrayList<String>();
                ary.add(s);
                array.add(str);
                groupMessages.put(str,ary);
            }
        } while (smsInboxCursor.moveToNext());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try {

            String smsMessage = (String)parent.getItemAtPosition(position);
            ArrayList<String> str = new ArrayList<String>();
            str = groupMessages.get(smsMessage);
            Intent it = new Intent(InboxActivity.this,ViewActivity.class);
            it.putExtra("phonenumber",smsMessage);
            it.putStringArrayListExtra("messagearraylist",str);
            startActivity(it);

//            String address = smsMessages[0];
//            String smsMessage = "";
//            for (int i = 1; i < smsMessages.length; ++i) {
//                smsMessage += smsMessages[i];
//            }
//
//            String smsMessageStr = address + "\n";
//            smsMessageStr += smsMessage;
//            Toast.makeText(this, smsMessage, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateInboxList(final String msg) {
        array.insert(msg, 0);
        array.notifyDataSetChanged();
    }
    public void inboxList(String adrs, String body) {
        if(groupMessages.containsKey(adrs)) {
            ArrayList<String> ary = groupMessages.get(adrs);
            ary.add(0,body);
        }
        else {
            ArrayList<String> ary = new ArrayList<String>();
            ary.add(body);
            groupMessages.put(adrs,ary);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PLAYGROUND", "Permission has been granted");
            } else {
                Log.d("PLAYGROUND", "Permission has been denied or request cancelled");
            }
        }
    }
}
