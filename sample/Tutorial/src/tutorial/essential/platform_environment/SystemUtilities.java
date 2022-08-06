package tutorial.essential.platform_environment;

public class SystemUtilities {

    /**
     * The Java platform itself uses a Properties object to maintain its own configuration.
     * The System class maintains a Properties object that describes the configuration of the current working environment.
     * System properties include information about the current user, the current version of the Java runtime,
     * and the character used to separate components of a file path name.
     */
    void testSystemProperties() {
        System.out.println("path.separator=" + System.getProperty("path.separator"));
        System.out.println("file.separator=" + System.getProperty("file.separator"));
        System.out.println("line.separator=" + System.getProperty("line.separator"));
        System.out.println("java.class.path=" + System.getProperty("java.class.path"));
        System.out.println("java.home=" + System.getProperty("java.home"));
        System.out.println("user.dir=" + System.getProperty("user.dir"));
        System.out.println("user.home=" + System.getProperty("user.home"));
        System.out.println("user.name=" + System.getProperty("user.name"));
        System.out.println("java.vendor=" + System.getProperty("java.vendor"));

        System.getProperties().list(System.out);
    }

    /**
     * A security manager is an object that defines a security policy for an application.
     * This policy specifies actions that are unsafe or sensitive.
     * Any actions not allowed by the security policy cause a SecurityException to be thrown.
     * An application can also query its security manager to discover which actions are allowed.
     */
    void testSecurityManager() {
        SecurityManager appsm = System.getSecurityManager();
        if (null == appsm) {
            System.out.println("there is no security manager");
        }
    }

    public static void main(String[] args) throws Exception {
        SystemUtilities systemUtilities = new SystemUtilities();
        systemUtilities.testSystemProperties();
        systemUtilities.testSecurityManager();
    }

}
