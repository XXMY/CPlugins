package com.cfw.plugins.thread;

import org.springframework.util.StringUtils;

/**
 * Created by Duskrain on 2017/8/13.
 */
public abstract class AbstractThread implements Runnable {

    private String threadName;

    private int threadsNumbers = 1;

    public AbstractThread(){}

    public AbstractThread(String threadName, int threadNumbers){
        this.threadName = threadName;
        this.threadsNumbers = threadNumbers;
    }

    public void startup() {
        if(StringUtils.isEmpty(threadName))
            for(int i=0;i<this.threadsNumbers;i++){
                new Thread(this,threadName + "-" + i).start();
            }
        else
            for(int i=0;i<this.threadsNumbers;i++){
                new Thread(this).start();
            }
    }
}
