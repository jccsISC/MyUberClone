package com.jccsisc.myuberclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterClientActivity extends AppCompatActivity {

    private Toolbar toolbar;
    SharedPreferences mPref;
    FirebaseAuth mAuth;
    DatabaseReference mDataBase;

    //Views
    private TextInputEditText tieName, tieEmail, tiePassword, tieConfirm;
    private Button btnRegisterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registrarse");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        String selectedUser = mPref.getString("user", "");
        Toast.makeText(getApplicationContext(), "usted seleccionó: "+ selectedUser, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();//hacemos referencia al nodo principal de nuestra base de datos

        tieName = findViewById(R.id.tieName);
        tieEmail = findViewById(R.id.tieEmailClient);
        tiePassword = findViewById(R.id.tiePwd);
        tieConfirm  = findViewById(R.id.tieConfirmPwd);

        btnRegisterClient = findViewById(R.id.btnRegisterClient);

        btnRegisterClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        String name = tieName.getText().toString();
        String email = tieEmail.getText().toString();
        String password = tiePassword.getText().toString();
        String confirPwd = tieConfirm.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirPwd.isEmpty()) {
            if (password.length()>=6) {
                if (password.equals(confirPwd)) {
                    Toast.makeText(getApplicationContext(), "Registrado correctamente", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "La contraseña no conincide", Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            Toast.makeText(getApplicationContext(), "Ingrese Todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}