package com.jccsisc.myuberclone.activities.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jccsisc.myuberclone.R;
import com.jccsisc.myuberclone.activities.MainActivity;
import com.jccsisc.myuberclone.providers.AuthProvider;

public class MapClientActivity extends AppCompatActivity {

    private Button btnLogout;
    AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_client);

        btnLogout = findViewById(R.id.btnLogout);
        mAuthProvider = new AuthProvider();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuthProvider.logout();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}