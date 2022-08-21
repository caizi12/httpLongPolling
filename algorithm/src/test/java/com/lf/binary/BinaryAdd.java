package com.lf.binary;

public class BinaryAdd {

    public static String addBinary(String a, String b) {

        //短的字符前面全补0
        if (a.length() > b.length()) {
            b = String.format("%" + a.length() + "s", b).replaceAll(" ", "0");
        } else if (a.length() < b.length()) {
            a = String.format("%" + b.length() + "s", a).replaceAll(" ", "0");
        }

        int i = a.length();
        //倒序相加
        int lastNum = 0;
        StringBuffer result = new StringBuffer(a.length());
        while (i > 0) {
            char aChar = a.charAt(i - 1);
            char bChar = b.charAt(i - 1);
            int plusNum = (aChar + bChar) + lastNum;
            // 一个为1
            if (plusNum == 97) {
                result.append("1");
                lastNum = 0;
                //两个为1
            } else if (plusNum == 98) {
                result.append("0");
                lastNum = 1;
             //两个为1，并且后位又进1
            } else if (plusNum == 99) {
                result.append( "1");
                lastNum = 1;
            } else {
                result.append( "0");
                lastNum = 0;
            }
            i--;
        }
        if (lastNum == 1) {
            result.append( lastNum);
        }
        return result.reverse().toString();
    }

    public static void main(String[] args) {
        String aa = "1010";
        String bb = "1011";
        System.out.println(addBinary(aa, bb));
    }
}
