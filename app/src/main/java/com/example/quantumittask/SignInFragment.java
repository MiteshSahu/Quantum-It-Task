package com.example.quantumittask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.quantumittask.databinding.ActivityMainBinding;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.concurrent.Executor;

public class SignInFragment extends androidx.fragment.app.Fragment implements GoogleApiClient.OnConnectionFailedListener {

    private ActivityMainBinding binding;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    private LoginButton loginButton;

    private SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private static final int SIGN_IN =1;

    private static final String EMAIL = "email";
    EditText emailidSignIn,passwordSignin;
    Button btnSignIn;
    private FirebaseAuth.AuthStateListener authStateListener;

    private AccessTokenTracker accessTokenTracker;

    LayoutInflater lf;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_signin,container,false);
        lf = getActivity().getLayoutInflater();
        view =  lf.inflate(R.layout.fragment_signin, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
                Log.d("MainActivity","2");

        emailidSignIn = view.findViewById(R.id.email_et_signin);
        passwordSignin = view.findViewById(R.id.password_et_signin);
        btnSignIn = view.findViewById(R.id.signin_button);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser!=null)
                {
                    Toast.makeText(getContext(),"login Succesfully!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getContext(),HomeActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getContext(),"Plz login",Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String signinEmail =  emailidSignIn.getText().toString();
                String signinPassword = passwordSignin.getText().toString();
                if(signinEmail.isEmpty())
                {
                    emailidSignIn.setError("Enter the email id");
                    emailidSignIn.requestFocus();
                }
                else if(signinPassword.isEmpty())
                {
                    passwordSignin.setError("Enter the password");
                    passwordSignin.requestFocus();
                }
                else if(signinEmail.isEmpty() && signinPassword.isEmpty())
                {
                    Toast.makeText(getContext(),"Fields are empty!!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(signinEmail,signinPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                             if(!task.isSuccessful())
                             {
                                 Toast.makeText(getContext(),"SignIn Failed!!",Toast.LENGTH_LONG).show();
                             }
                             else {
                                 Intent  i = new Intent(getContext(),HomeActivity.class);
                                 int check =0;
                                 i.putExtra("checkforlogin",check);
                                 startActivity(i);
                                 getActivity().finish();
                             }
                        }
                    });
                }

            }
        });




        //FacebookSdk.sdkInitialize(getContext());
        Log.d("MainActivity","3");
        loginButton = view.findViewById(R.id.facebook_img_button);
        mCallbackManager = CallbackManager.Factory.create();
     //  loginButton.setPermissions(Arrays.asList("email","public_profile"));
      //  loginButton.setPermissions(Arrays.asList(EMAIL));
      //   loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        Log.d("MainActivity","5");

        Log.d("MainActivity","6");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getContext(),"Success",Toast.LENGTH_LONG).show();
                Log.d("MainActivity","7");
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

                Toast.makeText(getContext(),"Canceled",Toast.LENGTH_LONG).show();

                Log.d("MainActivity","8");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getContext(),"Error ",Toast.LENGTH_LONG).show();

                Log.d("MainActivity","9"+error);
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    updateUI(user);
                }
                else{
                    //Toast.makeText(getContext(),"Login Failed!!",Toast.LENGTH_LONG).show();
                }
            }
        };
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null)
                {
                    mFirebaseAuth.signOut();
                }
                else
                {
                    Toast.makeText(getContext(),"Login Failed!222!",Toast.LENGTH_LONG).show();
                }
            }
        };


        GoogleSignInOptions gso  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(getContext()).enableAutoManage((FragmentActivity) getContext(),this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        signInButton = view.findViewById(R.id.google_img_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i,SIGN_IN);

            }
        });


        return view;
    }


    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user= mFirebaseAuth.getCurrentUser();
                    updateUI(user);
                }   else{
                    Toast.makeText(getContext(),"Login Failed!22!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
     //   mCallbackManager.onActivityResult(requestCode,resultCode,data);

        if(requestCode == SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                Intent  i = new Intent(getContext(),HomeActivity.class);
                int check =1;
                i.putExtra("checkforlogin",check);
                startActivity(i);
                getActivity().finish();
            }
        }
    }

    private void updateUI(FirebaseUser user) {
            if(user != null){
                Intent i = new Intent(getContext(),HomeActivity.class);
                startActivity(i);
            }else{
                Toast.makeText(getContext(),"Login Failed!!",Toast.LENGTH_LONG).show();
            }

    }
    public void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(authStateListener);
    }
//
    @Override
    public void onStop() {
        super.onStop();
        if(authStateListener!=null){
            mFirebaseAuth.removeAuthStateListener(authStateListener);
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
