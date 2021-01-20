package com.liner.clockfacerepo.firebase.auth;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.liner.clockfacerepo.Config;
import com.liner.clockfacerepo.firebase.FireLog;
import com.liner.clockfacerepo.models.User;

import java.util.UUID;

public class Auth {
    private static Auth auth;
    private Context context;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    public static Auth getInstance(Context context) {
        return auth == null ? auth = new Auth(context) : auth;
    }

    private Auth(Context context) {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    public void login(User user) {
        if (!isLoggedIn() || isAnonymous()) {
            firebaseAuth.signInWithEmailAndPassword(user.email, user.password)
                    .addOnSuccessListener(authResult -> firebaseUser = authResult.getUser())
                    .addOnFailureListener(e -> {
                        FireLog.d(Auth.class.getSimpleName() + " Cannot sign in to account cause: " + e.getMessage());
                        firebaseUser = firebaseAuth.signInAnonymously().getResult().getUser();
                    });
        } else {
            FireLog.d(Auth.class.getSimpleName() + " | username: " + user.name + " already logged in!");
        }
    }

    public void logout() {
        if (isLoggedIn()) {
            firebaseAuth.signOut();
        } else {
            FireLog.d(Auth.class.getSimpleName() + " | Cannot sign out, no logged user found!");
        }
    }

    public boolean isAnonymous() {
        return firebaseUser != null && firebaseUser.isAnonymous();
    }

    public boolean isLoggedIn() {
        return firebaseUser != null && !firebaseUser.isAnonymous();
    }

    public boolean isEmailVerified() {
        return Config.Firebase.ENABLE_EMAIL_VERIFICATION ?
                isLoggedIn() && firebaseUser.isEmailVerified() :
                isLoggedIn();
    }

    public String getUID() {
        return isLoggedIn() ?
                firebaseUser.getUid():
                isAnonymous()?
                        firebaseUser.getUid():
                        String.valueOf(UUID.randomUUID());
    }

}
