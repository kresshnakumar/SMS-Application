package com.android.krishna.smsapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by krishnaKumar on 10/29/2016.
 */
public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            String smsBody="";
            String address = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                smsBody = smsMessage.getMessageBody().toString();
                address = smsMessage.getOriginatingAddress();

                smsMessageStr += address ;
//                smsMessageStr += smsBody + "\n";
            }
            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            //this will update the UI with message
            InboxActivity inst = InboxActivity.instance();
            inst.updateInboxList(smsMessageStr);
            inst.inboxList(address,smsBody);
        }
    }
}
