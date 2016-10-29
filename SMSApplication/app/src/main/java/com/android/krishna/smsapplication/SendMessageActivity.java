package com.android.krishna.smsapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendMessageActivity extends AppCompatActivity {

    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        final EditText ph = (EditText) findViewById(R.id.ph_et);
        final EditText msg = (EditText) findViewById(R.id.msg_et);

        send = (Button) findViewById(R.id.send_bt);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d("PLAYGROUND", "Permission is not granted, requesting");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 123);
            send.setEnabled(false);
        } else {
            Log.d("PLAYGROUND", "Permission is granted");
        }


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phno = ph.getText().toString();
                String message = msg.getText().toString();

                int number_length = phno.length();
                if (number_length < 10) {
                    Toast.makeText(getApplicationContext(), "Enter 10 digit number", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phno, null, message, null, null);
                        Toast.makeText(getApplicationContext(), "SMS Sent Successfully..",
                                Toast.LENGTH_LONG).show();
                        finish();
                        Intent it = new Intent(SendMessageActivity.this, HomeActivity.class);
                        startActivity(it);
                    } catch (Exception e) {

                        Toast.makeText(getApplicationContext(),
                                "SMS faild, please try again later!",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PLAYGROUND", "Permission has been granted");
//                textView.setText("You can send SMS!");
                send.setEnabled(true);
            } else {
                Log.d("PLAYGROUND", "Permission has been denied or request cancelled");
//                textView.setText("You can not send SMS!");
                send.setEnabled(false);
            }
        }
    }
}
