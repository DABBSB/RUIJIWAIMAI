package org.DABB;

import java.util.*;
public class Main {

    public static void main(String[] args) {
        Scanner se = new Scanner(System.in);
        int[] kkk = new int[5];
        while (true) {
            String A = se.next();
            if (Objects.equals(A, "arr")) {
                try {
                    int a = se.nextInt();
                    int k = kkk[a];
                } catch (Exception e) {
                    System.out.println(e);
                }
            } else if (Objects.equals(A, "null")) {
                try {
                    String t = null;
                    int k = t.length();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }else if(Objects.equals(A, "cast")){
                try {
                    Object DABB=new String("string");
                    System.out.println((Integer)DABB);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }else if(Objects.equals(A, "num")){
                try {
                    String t=se.next();
                    Integer u= Integer.parseInt(t);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }else {
                break;
            }
        }
        se.close();
    }
}
