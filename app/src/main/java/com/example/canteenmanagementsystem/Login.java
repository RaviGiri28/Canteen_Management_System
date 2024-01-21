package com.example.canteenmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText username,username2;
    EditText password;
    Button LogIn;
    ImageView Chefimg;
    FirebaseAuth mAuth;
    ProgressBar Progressbar;
    TextView Logtext;

    private static final String TAG= "Login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("LogIn");

        Progressbar= findViewById(R.id.LogProgress);
        mAuth= FirebaseAuth.getInstance();
        username = findViewById(R.id.emailu);
        password = findViewById(R.id.loginpassword);
        LogIn=findViewById(R.id.LogInbtn);
        Chefimg=findViewById(R.id.Cheffimg);
        Logtext= findViewById(R.id.textlogin);
        username2= findViewById(R.id.emailu);
        Logtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Registration.class);
                startActivity(intent);
                finish();

            }
        });
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Progressbar.setVisibility(View.VISIBLE);
                String emailedit,Passwordedit,uusername;
                emailedit= String.valueOf(username.getText());
                Passwordedit = String.valueOf(password.getText());


                if (TextUtils.isEmpty(emailedit)){
                    Toast.makeText(Login.this,"Enter your Email or Username",Toast.LENGTH_SHORT).show();
                    username.setError("Email or Username Is Required");
                    username.requestFocus();
                }else if (TextUtils.isEmpty(Passwordedit)){
                    Toast.makeText(Login.this,"Enter your password",Toast.LENGTH_SHORT).show();
                    password.setError("Password is Required");
                    password.requestFocus();
                }else {
                    Progressbar.setVisibility(View.VISIBLE);
                    loginuser(emailedit,Passwordedit);
                }
            }
        });
    }

    private void loginuser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser= mAuth.getCurrentUser();

                    if (firebaseUser.isEmailVerified()){
                        Toast.makeText(Login.this,"LogIn Successfull",Toast.LENGTH_SHORT).show();
                        Intent intent= new Intent(getApplicationContext(), Content_Activity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        firebaseUser.sendEmailVerification();
                        mAuth.signOut();
                        showAlertDialog();

                    }
                }else {
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        username.setError("User is doesn't exists or no longer valid. Please register again.");
                        username.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        username.setError("Invalid Credential. Kindly check and re-enter");
                        username.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(Login.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                Progressbar.setVisibility(View.GONE);
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(Login.this);
        builder.setTitle("Email not Verified");
        builder.setMessage("Please verify your e-mail now. You cant't login without e-mail verification");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent= new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
    protected void onStart(){
        super.onStart();
        if (mAuth.getCurrentUser()!=null){
            Toast.makeText(Login.this,"Already logged in!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login.this,Content_Activity.class));
            finish();
        }else{
            Toast.makeText(Login.this,"You Can LogIn Now!",Toast.LENGTH_SHORT).show();
        }
    }
}