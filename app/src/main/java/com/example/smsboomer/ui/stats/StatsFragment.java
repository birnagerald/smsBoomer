package com.example.smsboomer.ui.stats;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smsboomer.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;

public class StatsFragment extends Fragment {

    private StatsViewModel statsViewModel;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        statsViewModel =
                ViewModelProviders.of(this).get(StatsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        final TextView smsTotal = root.findViewById(R.id.sms_total);
        final TextView contactsTotal = root.findViewById(R.id.contact_total);

        statsViewModel.getTotalSMS().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                int permissionCheck = ContextCompat.checkSelfPermission(
                        getContext(), Manifest.permission.READ_SMS);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    smsTotal.setText(s + " : " + getTotalSms());
                }
            }
        });

        statsViewModel.getTotalContacts().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                int permissionCheck = ContextCompat.checkSelfPermission(
                        getContext(), Manifest.permission.READ_CONTACTS);

                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    contactsTotal.setText(s + " : " + getTotalContacts());
                }
            }
        });

        int permissionCheck = ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.READ_SMS);
        int permissionCheck2 = ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.READ_CONTACTS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED && permissionCheck2 == PackageManager.PERMISSION_GRANTED) {
            recyclerView = root.findViewById(R.id.list_contacts);
            layoutManager = new LinearLayoutManager(this.getContext());
            recyclerView.setLayoutManager(layoutManager);

            adapter = new RecyclerAdapter(getContactsNames(), getContactsPhones(), getContactsSMSTotal(), this.getContext());
            recyclerView.setAdapter(adapter);
        }
        return root;
    }


    public String getTotalSms() {

        int total = 0;
        Uri SMS_INBOX = Uri.parse("content://sms/conversations/");

        CursorLoader cl = new CursorLoader(getContext(), SMS_INBOX, null, null, null, null);

        Cursor c = cl.loadInBackground();

        String[] count = new String[c.getCount()];

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            count[i] = c.getString(c.getColumnIndexOrThrow("msg_count"));
            total += Integer.parseInt(count[i]);
            c.moveToNext();
        }
        return Integer.toString(total);
    }

    public String getTotalContacts() {

        int total;
        Uri CONTACTS = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        CursorLoader cl = new CursorLoader(Objects.requireNonNull(getContext()), CONTACTS, null, null, null, null);

        Cursor c = cl.loadInBackground();

        total = c.getCount();
        return Integer.toString(total);
    }

    public ArrayList<String> getContactsNames() {

        ArrayList<String> contactsNames = new ArrayList<>();
        Uri message = Uri.parse("content://sms/inbox");

        CursorLoader cl = new CursorLoader(Objects.requireNonNull(getContext()), message, null, null, null, null);
        Cursor c = cl.loadInBackground();

        assert c != null;
        int totalSMS = c.getCount();
        if (c.moveToFirst()) {

            for (int i = 0; i < totalSMS; i++) {
                String contact = getContactName(getContext(), c.getString(c.getColumnIndexOrThrow("address")));
                if (!contactsNames.contains(contact)) {
                    contactsNames.add(contact);
                }
                c.moveToNext();
            }
        }
        c.close();
        return contactsNames;
    }

    public String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri,
                new String[] { ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return contactName;
    }

    public ArrayList getContactsSMSTotal() {

        ArrayList<String> contactsSmsTotal = new ArrayList<>();
        LinkedHashMap<String, Integer> contacts = new LinkedHashMap<>();
        Uri message = Uri.parse("content://sms/inbox");

        CursorLoader cl = new CursorLoader(Objects.requireNonNull(getContext()), message, null, null, null, null);
        Cursor c = cl.loadInBackground();

        assert c != null;
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                String contact = getContactName(getContext(), c.getString(c.getColumnIndexOrThrow("address")));
                if (contacts.containsKey(contact)) {
                    Integer count_contact = contacts.get(contact);
                    contacts.put(contact, ++count_contact);
                } else {
                    contacts.put(contact, 1);
                }
                c.moveToNext();
            }

            for (LinkedHashMap.Entry<String, Integer> entry : contacts.entrySet()) {
                contactsSmsTotal.add(Integer.toString(entry.getValue()));
            }

        }
        c.close();
        return contactsSmsTotal;
    }

    public ArrayList<String> getContactsPhones() {

        ArrayList<String> contactsPhones = new ArrayList<>();
        Uri message = Uri.parse("content://sms/inbox");

        CursorLoader cl = new CursorLoader(getContext(), message, null, null, null, null);
        Cursor c = cl.loadInBackground();

        assert c != null;
        int totalSMS = c.getCount();
        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                String phone = c.getString(c.getColumnIndexOrThrow("address"));
                if (!contactsPhones.contains(phone)) {
                    contactsPhones.add(phone);
                }

                c.moveToNext();
            }
        }
        c.close();
        return contactsPhones;
    }

}
