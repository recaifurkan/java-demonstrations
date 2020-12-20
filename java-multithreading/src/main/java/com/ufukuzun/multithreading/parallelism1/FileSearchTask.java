package com.ufukuzun.multithreading.parallelism1;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FileSearchTask extends RecursiveAction {
     static long count;

    private final File file;

    public FileSearchTask (File file) {
        this.file = file;
    }

    @Override
    protected void compute () {
        List<FileSearchTask> tasks = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) {
                    FileSearchTask newTask = new FileSearchTask(f);
                    tasks.add(newTask);
                    newTask.fork();
                } else if (f.getName().endsWith(".java")) {
                    System.out.println(f.getAbsolutePath());
                    count++;
                }
            }
        if (tasks.size() > 0) {
            for (FileSearchTask task : tasks) {
                task.join();
            }
        }
    }

    public static void main (String[] args) {
        long time = System.currentTimeMillis();
        FileSearchTask fileSearchTask = new FileSearchTask(new File("e:\\"));
        ForkJoinPool.commonPool().invoke(fileSearchTask);
        System.out.println("time taken : "+(System.currentTimeMillis()-time));
    }
}