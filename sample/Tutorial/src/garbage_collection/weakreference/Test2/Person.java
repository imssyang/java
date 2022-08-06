package garbage_collection.weakreference.Test2;

public class Person {
    private String name;
    private Apple apple;

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, Apple apple) {
        this.name = name;
        this.apple = apple;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Apple getApple() {
        return apple;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    /**
     * 覆盖finalize，在回收的时候会执行。
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Person： " + name + " finalize。");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}' + ", hashCode:" + this.hashCode();
    }

}
