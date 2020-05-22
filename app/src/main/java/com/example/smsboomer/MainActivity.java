package com.example.smsboomer;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.smsboomer.ui.stats.StatsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 2;
    public static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 3;
    public static final int MY_PERMISSIONS_REQUEST_READ_SMS = 4;
    public static String[] textAutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_stats, R.id.navigation_sms, R.id.navigation_param)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        textAutos = new String[]{"Salut", "Je suis pas dispo", "Je te réponds plus tard"};

        statePermission();
    }

    private void statePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_CONTACTS);
        int permissionCheck2 = ContextCompat.checkSelfPermission(
                this, Manifest.permission.SEND_SMS);
        int permissionCheck3 = ContextCompat.checkSelfPermission(
                this, Manifest.permission.RECEIVE_SMS);
        int permissionCheck4 = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_SMS);

        // ================================== READ CONTACTS ========================================

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                showExplanation("Permission to read contacts", "Accept the following permission", Manifest.permission.READ_CONTACTS, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                requestPermission(Manifest.permission.READ_CONTACTS, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }

        // ================================== SMS ========================================

        if (permissionCheck3 != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_SMS)) {
                showExplanation("Permission to access SMS", "Accept the following permission", Manifest.permission.RECEIVE_SMS, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            } else {
                requestPermission(Manifest.permission.RECEIVE_SMS, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }

        if (permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                showExplanation("Permission to access SMS", "Accept the following permission", Manifest.permission.SEND_SMS, MY_PERMISSIONS_REQUEST_SEND_SMS);
            } else {
                requestPermission(Manifest.permission.SEND_SMS, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

        if (permissionCheck4 != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {
                showExplanation("Permission to access SMS", "Accept the following permission", Manifest.permission.READ_SMS, MY_PERMISSIONS_REQUEST_READ_SMS);
            } else {
                requestPermission(Manifest.permission.READ_SMS, MY_PERMISSIONS_REQUEST_READ_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission Granted : Read Contacts", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied : Read Contacts", Toast.LENGTH_SHORT).show();
                }
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission Granted : Send SMS", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied : Send SMS", Toast.LENGTH_SHORT).show();
                }
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission Granted : Receive SMS", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied : Receive SMS", Toast.LENGTH_SHORT).show();
                }
            case MY_PERMISSIONS_REQUEST_READ_SMS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permission Granted : Read SMS", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied : Read SMS", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void showExplanation(String title, String message, final String permission, final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title).setMessage(message) .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                requestPermission(permission, permissionRequestCode);
            }
        });

        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,  new String[]{permissionName}, permissionRequestCode);
    }
}
