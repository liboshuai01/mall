package com.liboshuai.mall.tiny.zlearning.Test.literal.day09.d2_recusion;

public class RecursionDemo02 {
    public static void main(String[] args) {
        System.out.println(f(7));
    }
    public static int f(int n){
        if (n==1){
            return 1;
        }else {
            return f(n-1)*n;
        }
    }
}
