package com.websarva.wings.android.firebasefirstsample;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignInClass extends MainActivity{
    private final MainActivity mainActivity;
    private final FirebaseAuth mAuth;

    SignInClass(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        this.mAuth = MainActivity.mAuth;
    }

    void SignIn(String email, String pass){
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d("success_signIn","signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mainActivity.updateUI(Objects.requireNonNull(user));
                        }else {
                            Log.w("Error","signInWithEmail:failure",task.getException());
                            Toast.makeText(SignInClass.this,"Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
