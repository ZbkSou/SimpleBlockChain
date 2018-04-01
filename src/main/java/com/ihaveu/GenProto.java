package com.ihaveu;

import java.io.IOException;

/**
 * Created by ZBK on 2018-03-31.
 */
public class GenProto {
    public static void main(String[] args) {
        String protoFile = "chaincode.proto";
        String strCmd = "F:\\tool\\protobuftool\\bin\\protoc.exe -I=F:\\project\\javaProject\\BlackChain\\src\\proto --java_out=./src/main/java ./proto/"+ protoFile;
        try {
            Runtime.getRuntime().exec(strCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }//通过执行cmd命令调用protoc.exe程序
    }
}
