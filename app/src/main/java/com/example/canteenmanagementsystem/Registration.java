package com.example.canteenmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    EditText Fullname;
    EditText username;
    EditText email;
    EditText password;
    Button Register;
    ImageView Chefimg;
    FirebaseAuth mAuth;
    ProgressBar Progress;
    TextView registertext;

    private static final String TAG= "Registration";
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(getApplicationContext(), Content_Activity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Progress= findViewById(R.id.regProgress);
        mAuth= FirebaseAuth.getInstance();
        Fullname = findViewById(R.id.Fullnameedit);
        username = findViewById(R.id.usernameedit);
        email = findViewById(R.id.emailedit);
        password = findViewById(R.id.passwordedit);
        Register=findViewById(R.id.Regbutton2);
        Chefimg=findViewById(R.id.Cheffimg);
        registertext= findViewById(R.id.regtext);
        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),introActivity.class);
                startActivity(intent);
                finish();

            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Progress.setVisibility(View.VISIBLE);
                String emailedit,Passwordedit,Fullnme,usrname;
                emailedit= String.valueOf(email.getText());
                Passwordedit = String.valueOf(password.getText());
                Fullnme= String.valueOf(Fullname.getText());
                usrname= String.valueOf(username.getText());


                if (TextUtils.isEmpty(emailedit)){
                    Toast.makeText(Registration.this,"Enter your email",Toast.LENGTH_SHORT).show();
                    email.setError("Email is Required");
                    email.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(emailedit).matches()){
                    Toast.makeText(Registration.this,"Please Re-enter your email",Toast.LENGTH_SHORT).show();
                    email.setError("Valid Email is Required");
                    email.requestFocus();
                }else if (TextUtils.isEmpty(Passwordedit)) {
                    Toast.makeText(Registration.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    password.setError("Password is Required");
                    password.requestFocus();
                }else if (Passwordedit.length()<8) {
                    Toast.makeText(Registration.this, "Password Should be atleast 8 digits", Toast.LENGTH_SHORT).show();
                    password.setError("Password is Too Weak");
                    password.requestFocus();
                }else if (TextUtils.isEmpty(Fullnme)) {
                    Toast.makeText(Registration.this, "Enter your Full name", Toast.LENGTH_SHORT).show();
                    Fullname.setError("Full Name is Required");
                    Fullname.requestFocus();
                }else if (TextUtils.isEmpty(usrname)){
                    Toast.makeText(Registration.this,"Enter your username",Toast.LENGTH_SHORT).show();
                    username.setError("Username is Required");
                    username.requestFocus();
                }else {
                    registeruser(emailedit,Passwordedit,Fullnme,usrname);
                }
            }
        });
            }

    private void registeruser(String emailedit, String Passwordedit, String fullnme, String usrname) {
        FirebaseAuth mAuth= FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(emailedit, Passwordedit)
                .addOnCompleteListener(Registration.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser= mAuth.getCurrentUser();
                            UserProfileChangeRequest profileChangeRequest= new UserProfileChangeRequest.Builder().setDisplayName(String.valueOf(Fullname)).build();
                            firebaseUser.updateProfile(profileChangeRequest);
                            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails (Fullname,username);
                            DatabaseReference referenceprofile= FirebaseDatabase.getInstance().getReference("Registered Users");
                            referenceprofile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        firebaseUser.sendEmailVerification();
                                        Toast.makeText(Registration.this, "Registeration Successfull. Please Verify Your Email.",
                                                Toast.LENGTH_LONG).show();
                                        Intent intent= new Intent(Registration.this, introActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(Registration.this, "Registeration Failed. Please Try Again",
                                                Toast.LENGTH_LONG).show();
                                    }
                                    Progress.setVisibility(View.VISIBLE);
                                }
                            });

                        }else{
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                password.setError("Your Password is too Weak. Kindly use a mix of alphabets, numbers,symbols");
                                password.requestFocus();
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                email.setError("Your E-mail is invalid or Already in Use. Kindly Re-Enter");
                                email.requestFocus();
                            }catch (FirebaseAuthUserCollisionException e){
                                email.setError("User is Already Register With This Email. Please Use Another E-mail");
                                email.requestFocus();
                            }catch (Exception e){
                                Log.e(TAG,e.getMessage());
                                Toast.makeText(Registration.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                            Progress.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
