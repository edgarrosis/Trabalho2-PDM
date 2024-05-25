package com.example.trabalho2_pdm.database;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseDatabase  {

    private static FirebaseAuth auth;
    public static FirebaseAuth autenticacao(){
        if(auth==null){
            auth=FirebaseAuth.getInstance();
        }
        return auth;

    }
}
