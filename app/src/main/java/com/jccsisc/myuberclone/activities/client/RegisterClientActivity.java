package com.jccsisc.myuberclone.activities.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.jccsisc.myuberclone.R;
import com.jccsisc.myuberclone.includes.MyToolbar;
import com.jccsisc.myuberclone.models.Client;
import com.jccsisc.myuberclone.providers.AuthProvider;
import com.jccsisc.myuberclone.providers.ClientProvider;

import dmax.dialog.SpotsDialog;

public class RegisterClientActivity extends AppCompatActivity {

    AlertDialog mDialog;

    //objetos para hacer uso de client y driver
    AuthProvider mAuthProvider; //se encarga de la autenticacion
    ClientProvider mClientProvider; //se encarga de ingresar a los usuarios en nuestro nodo Clients

    //Views
    private TextInputEditText tieName, tieEmail, tiePassword, tieConfirm;
    private Button btnRegisterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        MyToolbar.showToolbar(RegisterClientActivity.this, "Regstrate", true);

        //Instanciamos para hacer uso del dialog de la libreria
        mDialog = new SpotsDialog.Builder().setContext(RegisterClientActivity.this).setMessage("Espere un momento").build();

        mAuthProvider = new AuthProvider();
        mClientProvider = new ClientProvider();

        tieName = findViewById(R.id.tieName);
        tieEmail = findViewById(R.id.tieEmailClient);
        tiePassword = findViewById(R.id.tiePwd);
        tieConfirm  = findViewById(R.id.tieConfirmPwd);

        btnRegisterClient = findViewById(R.id.btnRegisterClient);

        btnRegisterClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegister();
            }
        });

    }

    private void clickRegister() {
        String name = tieName.getText().toString();
        String email = tieEmail.getText().toString();
        String password = tiePassword.getText().toString();
        String confirPwd = tieConfirm.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirPwd.isEmpty()) {
            if (password.length() >=6) {
                if (password.equals(confirPwd)) {
                    register(name, email, password);
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

    private void register(String name, String email, String password) {
        mDialog.show();
        //con este metodo creamos el usuario en authentication.
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();//obtenemos el identificador del usuario que se acaba de logear para asignarlo a la db

                    Client client = new Client(id, name, email); //creamos un objeto de tipo cliente con esos valores
                    create(client); //le mandamos el obj de tipo cliente al metodo que lo registrará en la db
                }else {
                    Toast.makeText(getApplicationContext(), "Algo salió mal, se pudo registrar", Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });
    }

    private void create(Client client) {
        mClientProvider.create(client).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "se creó correctamente", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "No se pudo crear el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //guardar el usuario en la DB
   /* private void saveUser(String id, String name, String email) {
        String selectedUser = mPref.getString("user", ""); //obtenemos del prefer el valor que seleccionO
        //creamos un objeto de tipo cliente
        Client client = new Client();
        client.setName(name);
        client.setEmail(email);

        if (selectedUser.equals("driver")) {
            //lo guardamos en la dB
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
    }*/
}