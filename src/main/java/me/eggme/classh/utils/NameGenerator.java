package me.eggme.classh.utils;

public class NameGenerator {
    public static String getName(String email){
        return email.substring(0,email.indexOf("@"));
    }
}
