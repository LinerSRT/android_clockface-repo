package com.liner.clockfacerepo.auth;

import android.content.Context;

public class Auth {
    private static Auth auth;
    private Context context;

    public static Auth getInstance(Context context) {
        return auth == null ? auth = new Auth(context) : auth;
    }

    private Auth(Context context) {
        this.context = context;
    }

    //TODO Add register, login, check user methods here
}
