package com.lf.binary;

public class BinaryAdd3 {

    public static String addBinary(String a, String b) {
        int maxLength = Math.max(a.length(),b.length());
        StringBuffer result = new StringBuffer(maxLength);
        int lastNum = 0;
        for(int i = 0; i<maxLength;i++){
            if(i < a.length()){
                //从末尾开始取字符，并与0运算如果也为0，则相减结果为0，否则相减为1
                lastNum += a.charAt(a.length() - 1 - i) - '0';
            }

            if(i < b.length()){
                lastNum += b.charAt(b.length() - 1 - i) - '0';
            }
            //如果为2，则取模为0，后面进1，如果为1则取模为1
            result.append(lastNum % 2);
            //2时需要进1
            lastNum = lastNum / 2;
        }

        if(lastNum == 1){
            result.append("1");
        }
        return result.reverse().toString();
    }

    public static void main(String[] args) {
        String aa = "100";
        String bb = "1011";
        BinaryAdd3 add3 = new BinaryAdd3();
        System.out.println(addBinary(aa, bb));
    }
}
