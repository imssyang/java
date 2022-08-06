package tutorial.essential.platform_environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Some of the configuration utilities that help an application access its startup context.
 *
 * Properties:
 *   Properties are configuration values managed as key/value pairs.
 *   In each pair, the key and value are both String values.
 *   The key identifies, and is used to retrieve, the value, much as a variable name is used to retrieve the variable's value.
 *
 * Command-Line Arguments:
 *   A Java application can accept any number of arguments from the command line.
 *   This allows the user to specify configuration information when the application is launched.
 *
 * Environment Variables:
 *   Many operating systems use environment variables to pass configuration information to applications.
 *   Like properties in the Java platform, environment variables are key/value pairs, where both the key and the value are strings.
 *
 * Note:
 *   The System class maintains a Properties object that defines the configuration of the current working environment.
 */
public class ConfigurationUtilities {

    void testProperties() {
        // create and load default properties
        Properties defaultProps = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("defaultProperties");
            defaultProps.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // create application properties with default
        Properties applicationProps = new Properties(defaultProps);
        applicationProps.setProperty("name", "test-Properties");

        // writes out the application properties from the previous example using Properties.store.
        FileOutputStream out = null;
        try {
            out = new FileOutputStream("appProperties");
            applicationProps.store(out, "---No Comment---");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    void testCommandLineArgs(String args[]) {
        System.out.println("args.length: " + args.length);
        for (String s: args) {
            System.out.println(s);
        }
    }

    void testEnvVariables() {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n", envName, env.get(envName));
        }

        String specificKey = "Path";
        String specificValue = System.getenv(specificKey);
        if (specificValue != null) {
            System.out.format("[specific]%s=%s%n", specificKey, specificValue);
        } else {
            System.out.format("[specific]%s is" + " not assigned.%n", specificKey);
        }
    }

    public static void main(String args[]) {
        ConfigurationUtilities configurationUtilities = new ConfigurationUtilities();
        configurationUtilities.testCommandLineArgs(args);
        configurationUtilities.testEnvVariables();
        configurationUtilities.testProperties();
    }

}
