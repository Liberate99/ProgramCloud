package com.yunjuanyunshu.modules.util;

/**
 * @Author  carl on 2017/10/08.
 */

public class CommandWaitForThread extends Thread {

    private Process process;

    private boolean finish = false;

    private int exitValue = -1;

    public CommandWaitForThread(Process process) {
        this.process = process;
    }

    @Override
    public void run(){
        try{
            this.exitValue = process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            finish = true;
        }
    }

    public boolean isFinish(){
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public int getExitValue(){
        return exitValue;
    }
}
