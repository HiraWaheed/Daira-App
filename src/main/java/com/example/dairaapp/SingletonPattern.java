package com.example.dairaapp;

public class SingletonPattern {
    static int i = 0;
    private static SingletonPattern singletonPattern; //instance

    private String username;
    private SingletonPattern(){}
    public static SingletonPattern getSingletonPattern(){ //Instance
        if (singletonPattern == null && i == 0){
            i++;
            singletonPattern = new SingletonPattern();
            return singletonPattern;
        }
        else{
            return null;
        }
    }

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return  username;
    }
}
