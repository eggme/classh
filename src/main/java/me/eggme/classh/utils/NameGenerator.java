package me.eggme.classh.utils;

public class NameGenerator {
    public static String getName(String username){
        return username.substring(0,username.indexOf("@"));
    }
}
