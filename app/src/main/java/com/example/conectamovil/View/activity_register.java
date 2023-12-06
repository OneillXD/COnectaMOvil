package com.example.conectamovil.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.conectamovil.R; // Asegúrate de tener la referencia correcta a tu archivo R
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_register extends AppCompatActivity {

    Button btnIniciar;
    private EditText editTextEmail, editTextPassword;
    private Button btnRegister, btnLogout;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnIniciar = findViewById(R.id.btnInicar);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogout = findViewById(R.id.btnLogout);

        mAuth = FirebaseAuth.getInstance();
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí agregas la lógica para ir al activity_login
                irAActivityLogin();
            }
        });
    }
        private void irAActivityLogin() {
            Intent intent = new Intent(this, activity_login.class);
            startActivity(intent);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados por el usuario
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Validar que los campos no estén vacíos
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(activity_register.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Crear el usuario utilizando Firebase Authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity_register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registro exitoso
                                    Toast.makeText(activity_register.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                                    // Redirigir al usuario a la pantalla de inicio de sesión (activity_login)
                                    Intent intent = new Intent(activity_register.this, activity_login.class);
                                    startActivity(intent);
                                    finish(); // Para cerrar esta actividad y prevenir que el usuario vuelva atrás

                                } else {
                                    // Registro fallido, mostrar un mensaje de error específico
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(activity_register.this, "Error al registrar: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar sesión en Firebase
                if (mAuth.getCurrentUser() != null) {
                    mAuth.signOut();
                    Toast.makeText(activity_register.this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity_register.this, "No hay sesión activa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
