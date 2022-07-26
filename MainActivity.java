package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextmail, editTextpw;
    private Button login,register;
    private CheckBox gostergizle;
    private TextView resetpw;


    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);


        editTextmail = (EditText) findViewById(R.id.anaid);
        editTextpw = (EditText) findViewById(R.id.anapw);
        gostergizle = (CheckBox) findViewById(R.id.gostergizle);

        resetpw = (TextView) findViewById(R.id.sifreunuttum) ;
        resetpw.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();



        gostergizle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editTextpw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    editTextpw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, com.example.myapplication.register.class));
                break;

        case R.id.login:
            userlogin();
        break;

            case R.id.sifreunuttum:
                startActivity(new Intent(this, com.example.myapplication.resetpw2.class));
                break;


    }
}

    private void userlogin() {
        String mail = editTextmail.getText().toString().trim();
        String pw = editTextpw.getText().toString().trim();

        if (mail.isEmpty()) {
            editTextmail.setError("Lütfen e-posta adresinizi girin!");
            editTextmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            editTextmail.setError("Lütfen geçerli bir e-posta adersi girin!");
            editTextmail.requestFocus();
        }


        if (pw.isEmpty()) {
            editTextpw.setError("Lütfen şifrenizi girin!");
            editTextpw.requestFocus();
        }
        mAuth.signInWithEmailAndPassword(mail,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, activity_piyasa.class));
                }else{
                    Toast.makeText(MainActivity.this, "Giriş başarısız. Lütfen girdiğiniz bilgileri kontrol edin!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    }