package com.ct.ksl.mobywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by sreek on 31/05/18.
 */

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signup = findViewById(R.id.signUp);
        Button restore = findViewById(R.id.restore);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(login);
            }
        });

        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                restore intent

            }
        });
    }
}
