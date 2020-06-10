package com.yunjuanyunshu.modules.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.yunjuanyunshu.modules.util.FileUtil;
import javax.servlet.ServletOutputStream;

/**
 * @Author  apple on 2017/6/1.
 */
public class ShellUtil {
    public static List<String> exec(String order,List<String> strs){
//        System.out.println("执行命令："+order);
        Process process = null;
        List<String> processList = new ArrayList<String>();

        if(strs!=null &&strs.size()>0){
            processList =strs;
        }
        String result = "";
        try {
            process = Runtime.getRuntime().exec(order);
            InputStream stderr = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println("<output></output>");
            while ((line = br.readLine()) != null){
                processList.add(line);
                System.out.println(line);
            }
            System.out.println("");
            int exitVal = process.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processList;
    }

    //控制shell脚本执行超时
    public static List<String> execTimeout(String[] order,String killshell){
        InputStreamReader stdISR = null;
        InputStreamReader errISR = null;
        List<String> processList = new ArrayList<String>();
        Process process = null;

        long timeout = 5*1000;

        try{
            process = Runtime.getRuntime().exec(order);
            CommandStreamGobbler errorGobbler = new CommandStreamGobbler(process.getErrorStream(),order,"ERR");
            CommandStreamGobbler outputGobbler = new CommandStreamGobbler(process.getInputStream(),order,"STD");

            errorGobbler.start();
            //必须先等待错误输出ready再建立标准输出
            while(!errorGobbler.isReady()){
                Thread.sleep(10);
            }
            outputGobbler.start();
            while(!outputGobbler.isReady()){
                Thread.sleep(10);
            }

            CommandWaitForThread commandThread = new CommandWaitForThread(process);
            commandThread.start();

            long commandTime = System.currentTimeMillis();
            long nowTime = System.currentTimeMillis();
            boolean timeoutFlag = false;
            while (!commandThread.isFinish()){
                if (nowTime - commandTime > timeout){
                    timeoutFlag = true;
                    break;
                } else {
                    Thread.sleep(1000);
                    nowTime = System.currentTimeMillis();
                }
            }
            if (timeoutFlag){
                errorGobbler.setTimeout(1);
                outputGobbler.setTimeout(1);
                System.out.println("正式执行命令超时");
                //要保证这条命令不会超时
                String[] killorder = {killshell,order[3]};
                ShellUtil.execScript(killorder);
            } else {
                errorGobbler.setRunflag(false);
                outputGobbler.setRunflag(false);
            }

            while(true){
                if (errorGobbler.isReadFinish() && outputGobbler.isReadFinish()){
                    if (timeoutFlag){
                        processList.add("timeout");
                    } else {
                        if (errorGobbler.getInfoList().size() > 0) {
                            processList = errorGobbler.getInfoList();
                        } else {
                            processList = outputGobbler.getInfoList();
                        }
                    }
                    break;
                }
                Thread.sleep(10);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
        return processList;
    }

    public static List<String> execScript(String[] order){
//        System.out.println("执行命令："+order);
        Process process = null;
        List<String> processList = new ArrayList<String>();
        String result = "";
        try {
            process = Runtime.getRuntime().exec(order);
            //获取标准输出
            BufferedReader stdinput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            //            获取错误输出
            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = null;
//            System.out.println("<output></output>");
            while ((line = stdinput.readLine()) != null||(line = stderr.readLine()) != null){
                processList.add(line);
//                System.out.println(line);
            }
//            System.out.println("");
            int exitVal = process.waitFor();
//            System.out.println("Process exitValue: " + exitVal);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return processList;
    }
    //下面是测试代码,需要用时可以打开
//    public static void mkFile(String filePathAndName, String fileContent) {
//        FileWriter resultFile = null;
//        try {
//            String filePath = filePathAndName;
//            filePath = filePath.toString();
//            String dPath =filePath.substring(0,filePath.lastIndexOf('/')) ;
//            File myFilePath = new File(filePath);
//            File fp = new File(dPath);
//            if (!fp.exists()) {
//                fp.mkdirs();// 目录不存在的情况下，创建目录。
//            }
//            if (!myFilePath.exists()) {
//                myFilePath.createNewFile();
//            }
//            resultFile = new FileWriter(myFilePath);
//            PrintWriter myFile = new PrintWriter(resultFile);
//            String strContent = fileContent;
//            myFile.println(strContent);
//            resultFile.close();
//
//        } catch (Exception e) {
//            System.out.println("新建目录操作出错");
//            e.printStackTrace();
//
//        }finally {
//            try {
//                if (null != resultFile) {
//                    resultFile.close();
//                }
//            }catch (Exception ex){
//                //LoggerUtil
//            }
//        }
//
//    }
//     public static void  main(String[] args){
//         try{
//
//         String code = " #include  <iostream>\n" +
//                 "    using namespace std;\n" +
//                 "    int m" +
//                 "ain() {\n" +
//                 "    cout<<\"HELLO\"<<endl;\n" +
//                 "    return 0;\n" +
//                 "    }";
//         String time = ""+System.currentTimeMillis();
//         //设置当前路径，执行编译命令的时候用到
//         File directory = new File("");
//         String fp = directory.getAbsolutePath() + "/code/"+"test"+"/";
//         String shell = directory.getAbsolutePath() + "/code/"+"shell"+"/makecplus.sh";
//         String filePath = directory.getAbsolutePath() + "/code/"
//                 +"test"+"/"+time+".cpp";
//             ShellUtil.mkFile(filePath,code);
//         //执行编译命令
//         String[] order ={shell,fp,filePath,"test-"+time};
//         List<String> sts = ShellUtil.execScript(order);
//             for(String sd:sts){
//                 System.out.println(sd);
//             }
//
//         }catch (Exception ex){
//             ex.printStackTrace();
//         }
//
//     }
}
