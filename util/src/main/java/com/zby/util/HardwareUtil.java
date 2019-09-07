package com.zby.util;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * @author ZhuBingYang
 * @date 2019-09-01
 */
public class HardwareUtil {

    public static int getCoreNumbers() {
        int coreNumbers = 0;
        try {
            File dir = new File("/sys/devices/system/cpu/");

            class CpuFilter implements FileFilter {
                CpuFilter() {
                }

                public boolean accept(File pathname) {
                    return Pattern.matches("cpu[0-9]+", pathname.getName());
                }
            }

            File[] files = dir.listFiles(new CpuFilter());
            coreNumbers = files.length;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (coreNumbers < 1) {
            coreNumbers = Runtime.getRuntime().availableProcessors();
        }

        if (coreNumbers < 1) {
            coreNumbers = 1;
        }

        Logger.D.log("ThreadTool", "CPU cores: " + coreNumbers);
        return coreNumbers;
    }
}
