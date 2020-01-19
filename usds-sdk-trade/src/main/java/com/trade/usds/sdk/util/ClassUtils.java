package com.trade.usds.sdk.util;
 
/**
 * @Description Class工具类
 */
public class ClassUtils {

    /**
     * 获取调用的类名
     * 
     * @return String
     */
    public static String getClassName() {
        StackTraceElement[] stacktrace =getStacktrace() ;
        StackTraceElement e = stacktrace[2];
        String className = e.getClassName();
        return className;
    }
   
    /**获取当前堆栈跟踪中的元素
     */
    public static StackTraceElement[] getStacktrace() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
   
        return stacktrace;
    }
    
    /**获取调用的方法名
     */
    public static String getMethodName() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        String methodName = e.getMethodName();
        return methodName;
    }

    /**获取名称
     */
    public static String getFileName() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        String methodName = e.getFileName();
        return methodName;
    }

    /**获取当前行
     */
    public static int getLineNumber() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        int line = e.getLineNumber();
        return line;
    }

  /*  public static void main(String[] args) {
        System.out.println("当前运行的类："+getClassName());
        System.out.println("当前执行的方法："+getMethodName());
        System.out.println("当前文件名："+getFileName());
        System.out.println("当前执行的行数："+getLineNumber());
    }*/
}