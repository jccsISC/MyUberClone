package com.jccsisc.myuberclone.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jccsisc.myuberclone.R;
import com.jccsisc.myuberclone.activities.client.MapClientActivity;
import com.jccsisc.myuberclone.activities.client.RegisterClientActivity;
import com.jccsisc.myuberclone.activities.driver.MapDriveActivity;
import com.jccsisc.myuberclone.activities.driver.RegisterDriverActivity;
import com.jccsisc.myuberclone.includes.MyToolbar;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText tieGmail, tiePassword;
    private Button btnLogin;

    FirebaseAuth mAuth;
    DatabaseReference mDataBase;

    SharedPreferences mPref;

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tieGmail = findViewById(R.id.tieEmail);
        tiePassword = findViewById(R.id.tiePassword);
        btnLogin = findViewById(R.id.btnLogin);

        MyToolbar.showToolbar(LoginActivity.this, "Login", true);

        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();

        //Instanciamos para hacer uso del dialog de la libreria
        mDialog = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Espere un momento").build();

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    private void login() {
        String email = tieGmail.getText().toString();
        String password = tiePassword.getText().toString();
        String selectedUser = mPref.getString("user", ""); //obtenemos del prefer el valor que seleccionO

        if (!email.isEmpty() && !password.isEmpty()) {
            if (password.length()>=6) {
                mDialog.show(); //mostramos nuestro dialoggo
                //verificamos si el usuario existe
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (selectedUser.equals("driver")) {
                                Intent intent1 = new Intent(getApplicationContext(), MapDriveActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //Con esto ya no podrá regresar al activity anterior
                                startActivity(intent1);
                            } else if (selectedUser.equals("client")) {
                                Intent intent = new Intent(getApplicationContext(), MapClientActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //Con esto ya no podrá regresar al activity anterior
                                startActivity(intent);
                            }

                        }else {
                            Toast.makeText(getApplicationContext(), "El email o password son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                        mDialog.dismiss(); //dejamos de mostrar el dialogo
                    }
                });
            }else {
                Toast.makeText(getApplicationContext(), "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Email y Contraseña son Obligatiorios", Toast.LENGTH_SHORT).show();
        }
    }
}