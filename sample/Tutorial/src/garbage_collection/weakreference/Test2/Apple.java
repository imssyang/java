package garbage_collection.weakreference.Test2;

public class Apple {

    private String name;
    private Person person;

    public Apple(String name) {
        this.name = name;
    }

    public Apple(String name, Person person) {
        this.name = name;
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * 覆盖finalize，在回收的时候会执行。
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Apple： " + name + " finalize。");
    }

    @Override
    public String toString() {
        return "Apple{" +
                "name='" + name + '\'' +
                '}' + ", hashCode:" + this.hashCode();
    }

}
