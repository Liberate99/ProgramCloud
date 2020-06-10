package com.yunjuanyunshu.modules.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**临时工具类 不是当前项目
 * @Author  apple on 2017/7/20.
 */
public class DownloadPicture {
    public static void main(String[] args) {
        DownloadPicture downloadPicture = new DownloadPicture();
        ArrayList<String> oList = downloadPicture.readUrlList();
        downloadPicture.downloadPicture(oList);
    }

    /**
     * 传入要下载的图片的url列表，将url所对应的图片下载到本地
     *
     * @param oList
     */
    private void downloadPicture(ArrayList<String> oList) {
        URL url = null;
        int imageNumber = 0;

//        for (String oString : oList) {
            try {
//                String[] strs = oString.split(",");
//                String suffix = strs[0].substring(strs[0].lastIndexOf("."), strs[0].length());
//                String[] strs1 = strs[0].split("_");
//                System.out.println(strs[0]);
//                System.out.println(strs1[1]);
//                String imageName = "/Users/apple/Desktop/pic_coderace/" + strs[2] + "_" + strs[1] + suffix;
                String filepath1 = "/Users/apple/Desktop/pic_coderace/";
                String filepath2 = "/Users/apple/Desktop/pic_coderace/err/";
//                System.out.println("url图片" + imageName);
                List<String> stsss1 = new ArrayList<>();
                List<String> stsss2 = new ArrayList<>();
                File file = new File(filepath1);
                    String[] filelist = file.list();
                    for (int i = 0; i < filelist.length; i++) {
                        File readfile  = new File(filepath1  + filelist[i]);
                        if (!readfile.isDirectory()) {
                            String[] strss =readfile.getName().split("_");
                            stsss1.add(strss[0]);
                            System.out.println(readfile.getName());
//                            if(strs1[1].equals(strss[1])){
//                                File newfile=new File(imageName);
//                                if(!readfile.exists()){
//                                    System.out.println("文件不存在："+filepath1 + "\\" + filelist[i]);
//                                }
//                                if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
//                                    System.out.println(imageName+"已经存在！");
//                                else{
//                                    boolean tr = readfile.renameTo(newfile);
//                                    System.out.println("重命名："+imageName +":"+tr);
//                                }
//
//
//                            }
                        }
                    }
                File file2 = new File(filepath2);
                String[] filelist2 = file2.list();
                for (int i = 0; i < filelist2.length; i++) {
                    File readfile2  = new File(filepath2  + filelist2[i]);
                    if (!readfile2.isDirectory()) {
                        System.err.println(readfile2.getName());
                        String[] strss2 =readfile2.getName().split("_");
                        stsss2.add(strss2[0]);
                    }
                }
            } catch (Exception  e) {
                e.printStackTrace();
            }
        }

//    }

    /**
     * 连接mysql数据库，通过查询数据库读取要下载的图片的url列表
     *
     * @return
     */
    private ArrayList<String> readUrlList() {
        ArrayList<String> urlList = new ArrayList<String>();
        try {
            Connection connection = (Connection) JdbcUtil.getConnection();
            Statement statement = (Statement) connection.createStatement();
            String sql = "select pnumber,pname,productionpath from production where (productionpath <> NULL or productionpath <> '')"; //查询语句换位相应select语句
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String url = resultSet.getString("productionpath");
                String pname = resultSet.getString("pname");
                String pnumber = resultSet.getString("pnumber");
                urlList.add(url + "," + pname + "," + pnumber);
//                System.out.println(url);
            }
            JdbcUtil.free(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return urlList;
    }
}
