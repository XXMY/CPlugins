package com.cfw.plugins.thread;

/**
 * The abstract class of how to start up threads.
 *
 * Created by Duskrain on 2017/8/26.
 */
public abstract class Startup {

    private static boolean started;

    public abstract void startup() throws Exception;

}
