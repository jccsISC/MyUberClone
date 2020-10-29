package com.jccsisc.myuberclone.activities.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import com.jccsisc.myuberclone.models.Driver;
import com.jccsisc.myuberclone.providers.AuthProvider;
import com.jccsisc.myuberclone.providers.DriverProvider;

import dmax.dialog.SpotsDialog;

public class RegisterDriverActivity extends AppCompatActivity {

    AlertDialog mDialog;
    AuthProvider mAuthProvider; //se encarga de authenticar
    DriverProvider mDriverProvider; //para ingrear el usuario al nodo Drivers

    //Views
    private TextInputEditText tieName, tieEmail, tiePwd, tieConrfirm, tieVehicleBrand, tieVehiclePlate;
    private Button btnRegisterDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);

        MyToolbar.showToolbar(RegisterDriverActivity.this, "Registrate", true);

        mDialog = new SpotsDialog.Builder().setContext(RegisterDriverActivity.this).setMessage("Espere un momento").build();

        mAuthProvider = new AuthProvider();
        mDriverProvider = new DriverProvider();

        tieName = findViewById(R.id.tieName);
        tieEmail = findViewById(R.id.tieEmailDriver);
        tiePwd   = findViewById(R.id.tiePwd);
        tieConrfirm = findViewById(R.id.tieConfirmPwd);
        tieVehicleBrand = findViewById(R.id.tieVehicleBrand);
        tieVehiclePlate = findViewById(R.id.tieVehiclePlate);

        btnRegisterDriver = findViewById(R.id.btnRegisterDriver);

        btnRegisterDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickRegister();
            }
        });

    }

    private void clickRegister() {
        String name = tieName.getText().toString();
        String email = tieEmail.getText().toString();
        String password = tiePwd.getText().toString();
        String confirm = tieConrfirm.getText().toString();
        String marcaVehicle = tieVehicleBrand.getText().toString();
        String placaVehicle = tieVehiclePlate.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirm.isEmpty() && !marcaVehicle.isEmpty() && !placaVehicle.isEmpty()) {
            if (password.length() >=6) {
                if (password.equals(confirm)) {
                    register(name, email, password, marcaVehicle, placaVehicle);
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

    private void register(String name, String email, String password, String marcaVehicle, String placaVehicle) {
        mDialog.show();
        //con este metodo creamos al usuario en autenticacion
        mAuthProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    Driver driver = new Driver(id, name, email, marcaVehicle, placaVehicle);
                    create(driver);
                }else {
                    Toast.makeText(getApplicationContext(), "Algo salió mal, se pudo registrar", Toast.LENGTH_SHORT).show();
                }
                mDialog.dismiss();
            }
        });
    }

    private void create(Driver driver) {
        mDriverProvider.create(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
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
}