package com.example.projecte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity {

    private FirebaseAuth mAuth;

    static final String TAG = "CREAUSUARIO";
    public static String ERROR = "";
    boolean errorclave = false;

    Button btAlta;
    EditText etCorreo;
    EditText etPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        btAlta = findViewById(R.id.btAlta);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etContraseña);

        btAlta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = etCorreo.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                btAlta.setEnabled(false);
                progressDialog.setMessage("Realizando registro en línea...");
                progressDialog.show();
                if (pass.length()<6){
                    errorclave = true;
                }
                else{
                    errorclave = false;
                }
                crearUsuario(usuario, pass);
            }
        });

    }

    void crearUsuario(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Log.d(TAG, "createUserWithEmail:success");
                            Intent intent = new Intent(Registro.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(Registro.this, "Registrado completado",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            if(errorclave == true){
                                SuperActivityToast.create(Registro.this, new Style(), Style.TYPE_BUTTON)
                                        //.setButtonText("UNDO")
                                        //.setButtonIconResource(R.drawable.ic_undo)
                                        .setProgressBarColor(Color.WHITE)
                                        .setText("Password inferior a 6 carácteres")
                                        .setDuration(Style.DURATION_SHORT)
                                        .setFrame(Style.FRAME_LOLLIPOP)
                                        .setColor(Color.parseColor("#F44336"))
                                        .setAnimations(Style.ANIMATIONS_POP).show();
                            }
                            else{
                                SuperActivityToast.create(Registro.this, new Style(), Style.TYPE_BUTTON)
                                        //.setButtonText("UNDO")
                                        //.setButtonIconResource(R.drawable.ic_undo)
                                        .setProgressBarColor(Color.WHITE)
                                        .setText("Fallo en el registro")
                                        .setDuration(Style.DURATION_SHORT)
                                        .setFrame(Style.FRAME_LOLLIPOP)
                                        .setColor(Color.parseColor("#F44336"))
                                        .setAnimations(Style.ANIMATIONS_POP).show();
                            }
                        }

                        btAlta.setEnabled(true);
                    }
                });
    }

}