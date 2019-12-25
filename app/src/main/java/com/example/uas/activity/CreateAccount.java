package com.example.uas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uas.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Create New Account";

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private EditText edtEmail;
    private EditText edtPass;
    private EditText edtPassConf;
    private TextView btnDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.emailinput);
        edtPass = findViewById(R.id.passwordinput);
        edtPassConf = findViewById(R.id.passwordinput);
        btnDaftar = findViewById(R.id.Signup);

        btnDaftar.setOnClickListener(this);

    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete: " + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Toast.makeText(CreateAccount.this, "User Account Created",
                                    Toast.LENGTH_SHORT).show();
                            onAuthSuccess(task.getResult().getUser());

                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(CreateAccount.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }

                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            edtEmail.setError("Pick other username");
                            Toast.makeText(CreateAccount.this, "User already exist",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        String p = edtPass.getText().toString().trim();
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(p.getBytes());
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        writeNewAdmin(user.getUid(), generatedPassword, username);

        startActivity(new Intent(CreateAccount.this, Login.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean validateForm() {
        CharSequence email = edtEmail.getText().toString();
        boolean result = true;
        String cP = edtPassConf.getText().toString().trim();
        final String p = edtPass.getText().toString().trim();

        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setError("Required");
            edtEmail.requestFocus();
            result = false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Please input valid email");
            edtEmail.requestFocus();
            result = false;
        } else {
            edtEmail.setError(null);
        }

        if (TextUtils.isEmpty(edtPass.getText().toString())) {
            edtPass.setError("Required");
            edtPass.requestFocus();
            result = false;
        } else {
            edtPass.setError(null);
        }

        if (TextUtils.isEmpty(edtPassConf.getText().toString())) {
            edtPassConf.setError("Required");
            edtPassConf.requestFocus();
            result = false;
        } else {
            edtPassConf.setError(null);
        }

        if (!p.equals(cP)) {
            edtPassConf.setError("Password does not match");
            edtPassConf.requestFocus();
            result = false;
        }

        if (edtPass.length() < 8) {
            edtPass.setError("More than 8 characters allowed");
            edtPass.requestFocus();
            result = false;
        }
        return result;
    }

    private  void writeNewAdmin(String userId, String name, String password) {
        User user = new User(name, password);
        mDatabase.child("User").child(userId).setValue(user);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.Signup) {
            signUp();
        }
    }
}
