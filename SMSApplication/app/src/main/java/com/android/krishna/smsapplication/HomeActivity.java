package com.android.krishna.smsapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button compose = (Button)findViewById(R.id.compose_bt);
        Button inbox = (Button)findViewById(R.id.inbox_bt);

        compose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(HomeActivity.this,SendMessageActivity.class);
                startActivity(it);
            }
        });

        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(HomeActivity.this,InboxActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("PLAYGROUND", "Permission has been granted");
//                textView.setText("You can send SMS!");

            } else {
                Log.d("PLAYGROUND", "Permission has been denied or request cancelled");
//                textView.setText("You can not send SMS!");

            }
        }
    }

}
