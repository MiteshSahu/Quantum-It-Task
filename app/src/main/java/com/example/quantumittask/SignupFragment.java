package com.example.quantumittask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public class SignupFragment extends androidx.fragment.app.Fragment {


    LayoutInflater lf;
    View view;
    EditText emailidSignup,passwordSignup,phonenumber,name;
    Button btnSignup;
     private FirebaseAuth mFirebaseAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.fragment_signup,container,false);
        lf = getActivity().getLayoutInflater();
        view =  lf.inflate(R.layout.fragment_signup, container, false);

        emailidSignup = view.findViewById(R.id.email_et_registration);
        passwordSignup = view.findViewById(R.id.password_et_registration);
        btnSignup = view.findViewById(R.id.signup_button);
        name = view.findViewById(R.id.name_et);
        phonenumber = view.findViewById(R.id.phone_et);

      mFirebaseAuth = FirebaseAuth.getInstance();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signupEmail =  emailidSignup.getText().toString().trim();
                String signupPassword = passwordSignup.getText().toString().trim();


                if(phonenumber.getText().toString().isEmpty()||name.getText().toString().isEmpty()||signupEmail.isEmpty() || signupPassword.isEmpty()){
                    Toast.makeText(getContext(),"Fields are empty!!",Toast.LENGTH_LONG).show();
                }
                else if(signupPassword.length()<6)
                {
                    passwordSignup.setError("Password should be greater than 6");
                    passwordSignup.requestFocus();
                }
                else
                {
                    mFirebaseAuth.createUserWithEmailAndPassword(signupEmail,signupPassword).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(getContext(),"Signup Failed !!"+task.toString(),Toast.LENGTH_LONG).show();

                            }
                            else{
                                startActivity(new Intent(getContext(),HomeActivity.class));
                            }
                        }
                    });
                }

            }
        });
        return view;
    }
}
