package com.ct.ksl.mobywallet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by sreek on 27/05/18.
 */

public class FreezeActivity extends AppCompatActivity {
    Button freezeBtn;
    Button unfreezeBtn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeze);

        freezeBtn = findViewById(R.id.freezeBtn);
        unfreezeBtn = findViewById(R.id.unfreezeBtn);

        freezeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(FreezeActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setTitle("Progress");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(90000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }).start();
                Intent account = new Intent(FreezeActivity.this, AccountActivity.class);
                startActivity(account);
            }
        });

        unfreezeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(FreezeActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setTitle("Progress");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(90000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }).start();
                Intent account = new Intent(FreezeActivity.this, AccountActivity.class);
                startActivity(account);
            }
        });

    }

}
