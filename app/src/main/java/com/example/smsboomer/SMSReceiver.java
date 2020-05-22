package com.example.smsboomer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Objects;

public class SMSReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    String msg, phone;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent activity = new Intent(context, MainActivity.class);
        activity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activity);

        if (Objects.equals(intent.getAction(), SMS_RECEIVED)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                // get sms objects
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[pdus.length];
                if (pdus.length == 0) {
                    return;
                }

                for (int i = 0; i < pdus.length; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = bundle.getString("format");
                        message[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    } else {
                        message[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    msg = message[i].getMessageBody();
                    phone = message[i].getOriginatingAddress();
                }
                Toast.makeText(context, "Message received : " + msg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
