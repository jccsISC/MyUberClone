package com.jccsisc.myuberclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
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
import com.jccsisc.myuberclone.models.Client;

import dmax.dialog.SpotsDialog;

public class RegisterClientActivity extends AppCompatActivity {

    private Toolbar toolbar;
    SharedPreferences mPref;
    FirebaseAuth mAuth;
    DatabaseReference mDataBase;
    AlertDialog mDialog;

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

        //Instanciamos para hacer uso del dialog de la libreria
        mDialog = new SpotsDialog.Builder().setContext(RegisterClientActivity.this).setMessage("Espere un momento").build();

        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);

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
            if (password.length() >=6) {
                if (password.equals(confirPwd)) {
                    mDialog.show();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String id = mAuth.getCurrentUser().getUid();//obtenemos el identificador del usuario que se acaba de logear
                                saveUser(id, name, email);
                            }else {
                                Toast.makeText(getApplicationContext(), "Algo salió mal, se pudo registrar", Toast.LENGTH_SHORT).show();
                            }
                            mDialog.dismiss();
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "La contraseña no conincide", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "La contraseña debe de tener minimo 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), "Ingrese Todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUser(String id, String name, String email) {
        String selectedUser = mPref.getString("user", "");
        Client client = new Client();
        client.setName(name);
        client.setEmail(email);

        if (selectedUser.equals("driver")) {
            mDataBase.child("Users").child("Drivers").child(id).setValue(client).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Falló el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else if (selectedUser.equals("client")) {
            mDataBase.child("Users").child("Clients").child(id).setValue(client).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Falló el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}