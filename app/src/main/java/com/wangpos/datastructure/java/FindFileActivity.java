package com.wangpos.datastructure.java;

import com.wangpos.datastructure.core.BaseActivity;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by qiyue on 2017/12/31.
 */

public class FindFileActivity extends BaseActivity {
    @Override
    protected void initData() {

    }

    @Override
    protected String getTextData() {
        return null;
    }

    @Override
    protected int getImageData() {
        return 0;
    }

    @Override
    protected String getResultData() {
        return null;
    }

    @Override
    protected String getTimeData() {
        return null;
    }

    @Override
    protected String getSpaceTimeData() {
        return null;
    }

    @Override
    protected String getWendingXingData() {
        return null;
    }

    @Override
    protected String getSummaryData() {
        return null;
    }


    // 50MB
    static int MAX_SIZE = 10 * 1024 * 1024;

    public static boolean findMaxFile(File dir, List<FileBean> fileList) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files == null) {
                return false;
            }
            int max = 0;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isDirectory()) {
                    if (findMaxFile(file, fileList)) {
                        max++;
                    }
                } else {
                    //大文件直接添加
                    if (getFileSize(file) > MAX_SIZE) {
//                        LogUtils.D_I("file", "path1 = " + file.getAbsolutePath() + "  size=" + format(getFileSize(file) * 1f / 1024 / 1024));
                        fileList.add(new FileBean(file.getAbsolutePath(), format(getFileSize(file) * 1f / 1024 / 1024)));
                        max++;
                    }
                }
                if ((i == (files.length - 1)) && max == 0) {
                    if (getFileSize(dir) > MAX_SIZE) {
                        //notice 只要是找到就要max++
//                        LogUtils.D_I("file", "path2 = " + dir.getAbsolutePath() + "  size=" + format(getFileSize(dir) * 1f / 1024 / 1024));
                        fileList.add(new FileBean(dir.getAbsolutePath(), format(getFileSize(dir) * 1f / 1024 / 1024)));
                        max++;
                    }
                }
            }
            if (max > 0) {
                return true;
            }
            return false;
        } else {
            if (getFileSize(dir) > MAX_SIZE) {
//                LogUtils.D_I("file", "path3 = " + dir.getAbsolutePath() + "  size=" + format(getFileSize(dir) * 1f / 1024 / 1024));
                fileList.add(new FileBean(dir.getAbsolutePath(), format(getFileSize(dir) * 1f / 1024 / 1024)));
                return true;
            }
        }
        return false;
    }



    /**
     * 获取文件夹下所有文件大小
     *
     * @param f
     * @return
     */
    public static long getFileSize(File f) {

        long size = 0;
        try {
            if (!f.isDirectory()) {
                size = f.length();
            } else {
                File flist[] = f.listFiles();
                for (int i = 0; i < flist.length; i++) {
                    final File file = flist[i];
                    if (file == null) {
                        continue;
                    }
                    if (file.isDirectory()) {
                        size = size + getFileSize(file);
                    } else {
                        size = size + file.length();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return size;
    }


    public static double format(float f) {
        BigDecimal b = new BigDecimal(f);
        /**总使用流量*/
        double total = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        return total;
    }


}
