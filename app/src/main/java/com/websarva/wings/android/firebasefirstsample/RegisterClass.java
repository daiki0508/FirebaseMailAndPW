package com.websarva.wings.android.firebasefirstsample;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegisterClass extends MainActivity{
    private final FirebaseAuth mAuth;
    private final MainActivity mainActivity;
    RegisterClass(MainActivity mainActivity){
        this.mAuth = MainActivity.mAuth;
        this.mainActivity = mainActivity;
    }

    void CreateUser(String mail, String pass){
        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d("success","createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            mainActivity.updateUI(Objects.requireNonNull(user));
                        }else {
                            Log.w("Error","createUserWithEmail:failure",task.getException());
                            Toast.makeText(RegisterClass.this,"認証エラー",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
