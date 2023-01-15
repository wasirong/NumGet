package com.dhl.Threads;

import com.dhl.MainApp.LazySingleton;
import com.dhl.Util.MakeSound;

public class MyThread extends Thread {
    String path = "";
    public MyThread(String path) {
        this.path = path;
    }

    public void run() {
        MakeSound makeSound = new MakeSound();
        makeSound.playSound(path);
    }
}