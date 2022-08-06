package tutorial.essential.basicio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashSet;
import java.util.Set;

import static java.nio.file.StandardOpenOption.*;

public class FileIO {

    void testPath() {
        Path p1 = Paths.get("/tmp/foo");
        Path p2 = Paths.get(URI.create("file:///Users/joe/FileTest.java"));
        Path p3 = FileSystems.getDefault().getPath("/users/sally");
        Path p4 = Paths.get(System.getProperty("user.home"),"logs", "foo.log");
        System.out.format("p1: %s%n", p1.toString());
        System.out.format("p2: %s%n", p2.toString());
        System.out.format("p3: %s%n", p3.toString());
        System.out.format("p4: %s%n", p4.toString());


        Path path = Paths.get("C:\\home\\joe\\foo");
        System.out.format("toString: %s%n", path.toString());
        System.out.format("getFileName: %s%n", path.getFileName());
        System.out.format("getName(0): %s%n", path.getName(0));
        System.out.format("getNameCount: %d%n", path.getNameCount());
        System.out.format("subpath(0,2): %s%n", path.subpath(0,2));
        System.out.format("getParent: %s%n", path.getParent());
        System.out.format("getRoot: %s%n", path.getRoot());
        System.out.format("toUri: %s%n", path.toUri());
        System.out.format("toAbsolutePath: %s%n", path.toAbsolutePath());
        System.out.format("%s%n", path.resolve("bar"));

        try {
            Path path2 = Paths.get("C:\\Users\\Default\\Desktop");
            System.out.format("toRealPath().toString(): %s%n", path2.toRealPath().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Path p1 = Paths.get("joe");
        //Path p2 = Paths.get("sally");
        Path p1_to_p2 = p1.relativize(p2);
        Path p2_to_p1 = p2.relativize(p1);
        System.out.format("p1_to_p2: %s%n", p1_to_p2.toString());
        System.out.format("p2_to_p1: %s%n", p2_to_p1.toString());
    }

    void testFile() throws IOException {
        String encoding = System.getProperty("file.encoding");
        System.out.format("file.encoding: %s%n", encoding);

        Path file = Paths.get("testFile.txt");
        Charset charset = Charset.forName("US-ASCII");
        String s = "12345\r\n";
        BufferedWriter writer = null;
        try {
            writer = Files.newBufferedWriter(file, charset, CREATE, APPEND);
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        } finally {
            if (writer != null)
                writer.close();
        }

        System.out.format("toAbsolutePath: %s%n", file.toAbsolutePath());
        System.out.format("isRegularFile: %s%n", Files.isRegularFile(file));
        System.out.format("isReadable: %s%n", Files.isReadable(file));
        System.out.format("isExecutable: %s%n", Files.isExecutable(file));

        Path path = Paths.get("C:\\home\\joe\\foo");
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException x) {
            System.err.println(x);
        }

        Path emptyFile = Paths.get("d:\\emptyfile.txt");
        try {
            // Create the empty file with default permissions, etc.
            Files.createFile(emptyFile);
        } catch (FileAlreadyExistsException x) {
            System.err.format("file named %s already exists%n", emptyFile);
        } catch (IOException x) {
            // Some other sort of failure, such as permissions.
            System.err.format("createFile error: %s%n", x);
        }

        try {
            Path tempFile = Files.createTempFile(null, ".myapp");
            System.out.format("The temporary file has been created: %s%n", tempFile);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    void testLog() {
        // Create the set of options for appending to the file.
        Set<OpenOption> options = new HashSet<OpenOption>();
        options.add(APPEND);
        options.add(CREATE);

        // Create the custom permissions attribute.
        Set<PosixFilePermission> perms =
                PosixFilePermissions.fromString("rw-r-----");
        FileAttribute<Set<PosixFilePermission>> attr =
                PosixFilePermissions.asFileAttribute(perms);

        // Convert the string to a ByteBuffer.
        String s = "Hello World! ";
        byte data[] = s.getBytes();
        ByteBuffer bb = ByteBuffer.wrap(data);

        Path file = Paths.get("./permissions.log");

        try (SeekableByteChannel sbc = Files.newByteChannel(file, options/*, attr*/)) {
            sbc.write(bb);
        } catch (IOException x) {
            System.out.println("Exception thrown: " + x);
        }
    }

    void testRandomAccessFiles() {
        Path file = Paths.get("./permissions.log");

        String s = "I was here!\n";
        byte data[] = s.getBytes();
        ByteBuffer out = ByteBuffer.wrap(data);

        ByteBuffer copy = ByteBuffer.allocate(12);

        try (FileChannel fc = FileChannel.open(file, READ, WRITE)) {
            // Read the first 12
            // bytes of the file.
            int nread;
            do {
                nread = fc.read(copy);
            } while (nread != -1 && copy.hasRemaining());

            // Write "I was here!" at the beginning of the file.
            fc.position(0);
            while (out.hasRemaining())
                fc.write(out);
            out.rewind();

            // Move to the end of the file.  Copy the first 12 bytes to
            // the end of the file.  Then write "I was here!" again.
            long length = fc.size();
            fc.position(length-1);
            copy.flip();
            while (copy.hasRemaining())
                fc.write(copy);
            while (out.hasRemaining())
                fc.write(out);
        } catch (IOException x) {
            System.out.println("I/O Exception: " + x);
        }
    }

    void testDirectory() throws IOException {
        Files.createDirectories(Paths.get("test/javatest"));

        Path dir = Paths.get(".");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file: stream) {
                System.out.println(file.getFileName());
            }
        } catch (IOException | DirectoryIteratorException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            System.err.println(x);
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{txt,class,jar}")) {
            for (Path entry: stream) {
                System.out.println(entry.getFileName());
            }
        } catch (IOException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can // only be thrown by newDirectoryStream.
            System.err.println(x);
        }

    }

    void test() throws IOException {
        testPath();
        testFile();
        testLog();
        testRandomAccessFiles();
        testDirectory();
    }

    public static void main(String[] args) throws IOException {
        FileIO fileIO = new FileIO();
        fileIO.test();
    }
}

