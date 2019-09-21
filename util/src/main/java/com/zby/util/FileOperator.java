package com.zby.util;

import com.zby.util.constant.PermissionConstant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * author ZhuBingYang
 * date   2019-09-05
 */
public class FileOperator {
    private static final String TAG = "FileOperator";

    public static boolean isDir(String filePath) {
        return isDir(openFile(filePath));
    }

    private static boolean isDir(File file) {
        if (fileNotExists(file)) {
            logFileNotExists();
            return false;
        }

        return file.isDirectory();
    }

    public static boolean isFileExists(String filePath) {
        return isFileExists(openFile(filePath));
    }

    private static boolean isFileExists(File file) {
        return !fileNotExists(file);
    }

    public static File openFile(String filePath) {
        if (!hasStoragePermission()) {
            logPermissionError();
            return null;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        } else {
            return file;
        }
    }

    public static File openOrCreateFile(String filePath) {
        if (!hasStoragePermission()) {
            logPermissionError();
            return null;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            openOrCreateDir(filePath.substring(filePath.lastIndexOf("/")));
        }
        try {
            boolean success = file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static File openOrCreateDir(String dirPath) {
        if (!hasStoragePermission()) {
            logPermissionError();
            return null;
        }

        File dir = new File(dirPath);
        if (!dir.exists() || !dir.isDirectory()) {
            boolean success = dir.mkdirs();
            return dir;
        }

        return null;
    }

    public static boolean rename(String filePath, String newName) {
        return rename(openFile(filePath), newName);
    }

    public static boolean rename(File file, String name) {
        if (fileNotExists(file)) {
            logFileNotExists();
            return false;
        }
        File newFile = new File(file.getParent() + File.separator + name);
        if (newFile.exists()) {
            return file.renameTo(newFile);
        }
        return false;
    }

    public static boolean delete(String filePath) {
        return delete(openFile(filePath));
    }

    private static boolean delete(File file) {
        if (fileNotExists(file)) {
            logFileNotExists();
            return false;
        }
        return file.delete();
    }

    public static boolean deleteDir(String filePath) {
        return deleteDir(openFile(filePath));
    }

    public static boolean deleteDir(File dir) {
        if (fileNotExists(dir)) {
            logFileNotExists();
            return false;
        }

        if (!dir.isDirectory()) {
            Logger.D.log(TAG, dir.getAbsolutePath() + " is not a directory.");
            return false;
        }

        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    private static boolean fileNotExists(File file) {
        return file == null || !file.exists();
    }


    public static void write(String filePath, String text, boolean append) {
        File file = openFile(filePath);
        if (fileNotExists(file) || file.isDirectory()) {
            logFileNotExists();
            return;
        }

        if (text == null) {
            Logger.D.log(TAG, "content can not be null.");
            return;
        }

        try {
            FileWriter filerWriter = new FileWriter(file, append);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(text);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(String filePath, Collection texts, boolean append) {
        File file = openFile(filePath);
        if (file == null || !file.exists()) {
            logFileNotExists();
            return;
        }
        try {
            FileWriter filerWriter = new FileWriter(file, append);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            for (Object text : texts) {
                if (text != null) {
                    bufWriter.write(text.toString());
                }

            }
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void logFileNotExists() {
        Logger.D.log(TAG, "file not exists.");
    }

    private static void logPermissionError() {
        Logger.D.log(TAG, "need storage permission.");
    }

    private static boolean hasStoragePermission() {
        return PermissionUtil.hasAnyPermission(AppUtil.getApplication(), PermissionConstant.GROUP_STORAGE);
    }

}
