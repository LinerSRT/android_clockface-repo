package com.liner.clockfacerepo;

import java.io.File;

public class Config {

    public static class Firebase{
        public static String DATABASE_REFERENCE_PATH = "database";
        public static String DATABASE_USERS_PATH = DATABASE_REFERENCE_PATH + File.pathSeparator + "users";
        public static String DATABASE_WATCHFACE_PATH = DATABASE_REFERENCE_PATH + File.pathSeparator + "watchfaces";

        public static boolean ENABLE_EMAIL_VERIFICATION = false;
        public static boolean ENABLE_LOGGING = true;
    }

}
