package garbage_collection;

/**
 * 用来验证引用计数算法不能检测出循环引用
 * 虚拟机参数：-verbose:gc
 * VM选项: -XX:+PrintGCDetails 打印gc时信息
 */
public class ReferenceCountingGC {

    private Object instance = null;
    private static final int _1MB = 1024 * 1024;

    /** 这个成员属性唯一的作用就是占用一点内存 */
    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        ReferenceCountingGC objectA = new ReferenceCountingGC();
        ReferenceCountingGC objectB = new ReferenceCountingGC();
        objectA.instance = objectB;
        objectB.instance = objectA;
        objectA = null;
        objectB = null;

        System.gc();
    }

}
