package cn.com.intasect.logapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class IscTxtLoggerHelper {
    /**
     * 上下文对象
     */
    private Context context;
    private Activity activity;

    /**
     * 保留前几天的日期
     */
    private int durationDayToKeepLog = 1;

    /**
     * 存储的文件夹路径
     */
//    private String storageDir = Environment.getStorageDirectory().getAbsolutePath();
    private String storageDir;
    /**
     * 基本的日志文件夹
     */
    private String baseLogDir = "/iscLog/";

    /**
     * 文件名称的时间格式 yyyyMMdd
     * 获取当前日期:this.fileNameDateFormat.format(new Date(System.currentTimeMillis()))
     */
    private SimpleDateFormat fileNameDateFormat = new SimpleDateFormat("yyyyMMdd");
    /**
     * 文件内时间标识的时间格式 MM-dd HH:mm:ss
     * 获取当前时间:this.lineDateFormat.format(new Date(System.currentTimeMillis()))
     */
    private SimpleDateFormat lineDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");

    public final static String TAG = "IscTxtLogger";
    /**
     * 单利模式
     */
    private static IscTxtLoggerHelper instance = new IscTxtLoggerHelper();

    private static final int MY_PERMISSION_REQUEST_CODE = 10000;

    /**
     * 构造函数，在这里进行初始化操作
     */
    private IscTxtLoggerHelper() {

    }

    public static IscTxtLoggerHelper getInstance() {
        return instance;
    }

    /**
     * 初始化
     *
     * @param context 上下文对象
     */
    public void init(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        Log.d(TAG, "文件夹路径" + this.storageDir);
        Log.d(TAG, "当前日期（用作文件名称）" + this.fileNameDateFormat.format(new Date(System.currentTimeMillis())));
        Log.d(TAG, "当前时间（用作文件标识）" + this.lineDateFormat.format(new Date(System.currentTimeMillis())));
        // this.delFileByDate();// 测试删除日志功能
    }

//    private static IscTxtLogger instance = null;
//    public static IscTxtLogger getInstance(){
//        if(null == instance){
//            synchronized (IscTxtLogger.class){
//                if(null == instance) {
//                    instance = new IscTxtLogger();
//                }
//            }
//        }
//        return instance;
//    }

    /**
     * 记录信息
     *
     * @param message 信息
     */
    public void logInfo(String message) {
        String fileName = this.fileNameDateFormat.format(new Date(System.currentTimeMillis())) + "_info.txt";
        writeTxtToFile(message, this.baseLogDir, fileName);
    }

    /**
     * 记录网络信息
     *
     * @param message 信息
     */
    public void logNetwork(String message) {
        String fileName = this.fileNameDateFormat.format(new Date(System.currentTimeMillis())) + "_net.txt";
        writeTxtToFile(message, this.baseLogDir, fileName);
    }

    /**
     * 字符串写入到文本文件中
     *
     * @param content  输入的文本内容
     * @param dirPath  文件路径 相对于Environment.getStorageDirectory().getAbsolutePath()
     * @param fileName 文件名称
     */
    private void writeTxtToFile(String content, String dirPath, String fileName) {
        String storageDir = this.storageDir + dirPath;
        // 生成文件
        createFileIfNotExits(storageDir, fileName);

        String strFilePath = storageDir + fileName;

        // 增加时间头 + 内容 + 换行符号
        String strContent = this.lineDateFormat.format(new Date(System.currentTimeMillis())) + content + "\r\n";
        Log.d(TAG, "写入的文件名称是" + strFilePath);
        Log.d(TAG, "写入的文件内容是" + strContent);
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d(TAG, "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
                delFileByDate();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e(TAG, "Error on write File:" + e);
        }
    }

    /**
     * 创建文件
     *
     * @param dirPath  文件夹路径
     * @param fileName 文件名称
     * @return
     */
    private File createFileIfNotExits(String dirPath, String fileName) {
        File file = null;
        // 创建文件夹:生成文件夹之后，再生成文件，不然会出错
        makeDirectoryIfNotExits(dirPath);
        try {
            file = new File(dirPath + fileName);
            if (!file.exists()) {
                boolean isSuccess = file.createNewFile();
                if (isSuccess) {
                    Log.d(TAG, "createFileIfNotExits: 创建文件夹成功" + fileName);
                } else {
                    Log.d(TAG, "createFileIfNotExits: 创建文件夹失败" + fileName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 生成文件夹，如果文件夹不存在则生成，如果存在则不进行操作
     *
     * @param dirPath 文件夹路径
     */
    private static void makeDirectoryIfNotExits(String dirPath) {
        File file = null;
        try {
            file = new File(dirPath);
            if (!file.exists()) {
                boolean isSuccess = file.mkdir();
                if (isSuccess) {
                    Log.d(TAG, "makeDirectoryIfNotExits: 创建文件夹成功");
                } else {
                    // https://blog.csdn.net/m0_37707561/article/details/105563569
                    Log.d(TAG, "makeDirectoryIfNotExits: 创建文件夹失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("error:", e + "");
        }
    }


    /**
     * 计算两个日期直接相差多少天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getBetweenDay(Date date1, Date date2) {
        Calendar d1 = new GregorianCalendar();
        d1.setTime(date1);
        Calendar d2 = new GregorianCalendar();
        d2.setTime(date2);
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        System.out.println("days=" + days);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * 根据时间是否过期进行文件的删除
     */
    private void delFileByDate() {
        String path = this.storageDir + this.baseLogDir;
        File file = new File(path);
        File[] files = file.listFiles();
        getFileName(files);
    }


    private void getFileName(File[] files) {
        if (files != null) {// 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {
                    Log.i(TAG, "若是文件目录。继续读1" + file.getName() + file.getPath());
                    getFileName(file.listFiles());
                    Log.i(TAG, "若是文件目录。继续读2" + file.getName() + file.getPath());
                } else {
                    String fileName = file.getName();
                    if (fileName.endsWith(".txt")) {
                        try {
                            String fileDateStr = fileName.substring(0, fileName.lastIndexOf("_"));
                            Log.i(TAG, "删除文件名称的日期" + fileDateStr);
                            // 当前日期
                            SimpleDateFormat format = this.fileNameDateFormat;
                            String dateString = format.format(new Date(System.currentTimeMillis()));
                            Date today = format.parse(dateString);

                            // 文件时间
                            Date date1 = format.parse(fileDateStr.trim());
                            if (getBetweenDay(date1, today) > this.durationDayToKeepLog) {
                                // 移除文件
                                String ph = this.storageDir + this.baseLogDir + fileName;
                                File fe = new File(ph);
                                Log.i(TAG, "移除的文件" + fileDateStr + ph);
                                fe.delete();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                }
            }
        }
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this.context, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    /**
     * 点击按钮，将通讯录备份保存到外部存储器备。
     *
     * 需要3个权限(都是危险权限):
     *      1. 读取通讯录权限;
     *      2. 读取外部存储器权限;
     *      3. 写入外部存储器权限.
     */
    public void clickPermissions() {
        /**
         * 第 1 步: 检查是否有相应的权限，根据自己需求，进行添加相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
        // 如果这权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this.activity,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }


}
