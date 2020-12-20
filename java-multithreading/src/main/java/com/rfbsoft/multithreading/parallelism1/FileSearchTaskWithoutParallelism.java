package com.rfbsoft.multithreading.parallelism1;

import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FileSearchTaskWithoutParallelism extends RecursiveAction {
     static long count;

    private final File file;

    public FileSearchTaskWithoutParallelism (File file) {
        this.file = file;
    }

    @Override
    protected void compute () {
        //List<FileSearchTaskWithoutParallelism> tasks = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) {
                    FileSearchTaskWithoutParallelism newTask =
                                new FileSearchTaskWithoutParallelism(f);
                  //  tasks.add(newTask);
                    newTask.compute();
                } else if (f.getName().endsWith(".java")) {
                    System.out.println(f.getAbsolutePath());
                    count++;
                }
            }
        /*if (tasks.size() > 0) {
            for (FileSearchTaskWithoutParallelism task : tasks) {
                task.join();
            }
        }*/
    }

    public static void main (String[] args) {
        long time = System.currentTimeMillis();
        FileSearchTaskWithoutParallelism fileSearchTask =
                  new FileSearchTaskWithoutParallelism(new File("d:\\"));
        ForkJoinPool.commonPool().invoke(fileSearchTask);
        System.out.println("time taken : "+(System.currentTimeMillis()-time));
    }
}