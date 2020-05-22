package com.example.smsboomer.ui.sms;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.content.CursorLoader;

import com.example.smsboomer.R;
import static com.example.smsboomer.MainActivity.textAutos;


public class SmsFragment extends Fragment {

    private SmsViewModel smsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        smsViewModel =
                ViewModelProviders.of(this).get(SmsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sms, container, false);

        final TextView lastSMS = root.findViewById(R.id.last_sms);
        final TextView phoneNo = root.findViewById(R.id.phone_no);
        final Button btn1 = root.findViewById(R.id.auto_1);
        final Button btn2 = root.findViewById(R.id.auto_2);
        final Button btn3 = root.findViewById(R.id.auto_3);

        final Button btnBomb = root.findViewById(R.id.bomb);

        smsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                int permissionCheck = ContextCompat.checkSelfPermission(
                        getContext(), Manifest.permission.READ_SMS);
                int permissionCheck2 = ContextCompat.checkSelfPermission(
                        getContext(), Manifest.permission.SEND_SMS);


                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    lastSMS.setText(getLastSMS());
                    phoneNo.setText(getLastSMSPhoneNo());
                }

                if (permissionCheck2 == PackageManager.PERMISSION_GRANTED) {

                    btn1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(getLastSMSPhoneNo(), null, textAutos[0], null, null);
                            Toast.makeText(getContext(), "SMS sent : " + textAutos[0],
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                    btn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(getLastSMSPhoneNo(), null, textAutos[1], null, null);
                            Toast.makeText(getContext(), "SMS sent : " + textAutos[1],
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                    btn3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(getLastSMSPhoneNo(), null, textAutos[2], null, null);
                            Toast.makeText(getContext(), "SMS sent : " + textAutos[2],
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                    btnBomb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SmsManager smsManager = SmsManager.getDefault();
                            for (int i = 0; i < 100; i++) {
                                smsManager.sendTextMessage(getLastSMSPhoneNo(), null, "You got bombed.", null, null);
                            }
                        }
                    });
                }
            }
        });
        return root;
    }

    public String getLastSMS() {

        Uri SMS_INBOX = Uri.parse("content://sms/inbox");

        CursorLoader cl = new CursorLoader(getContext(), SMS_INBOX, null, null, null, null);

        Cursor c = cl.loadInBackground();
        String lastText = "";

        if (c.moveToFirst()) {
            lastText = c.getString(c.getColumnIndexOrThrow("body"));
        }

        return lastText;
    }

    public String getLastSMSPhoneNo() {

        Uri SMS_INBOX = Uri.parse("content://sms/inbox");

        CursorLoader cl = new CursorLoader(getContext(), SMS_INBOX, null, null, null, null);

        Cursor c = cl.loadInBackground();
        String lastPhoneNo = "";

        if (c.moveToFirst()) {
            lastPhoneNo = c.getString(c.getColumnIndexOrThrow("address"));
        }

        return lastPhoneNo;
    }
}
