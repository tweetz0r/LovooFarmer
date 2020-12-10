package util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class OverseerThread implements Runnable {

    private final String iUrl;
    private final String fileName;
    private final String path;

    public OverseerThread(final Object parameter) {
        final ArrayList<Object> al = (ArrayList) parameter;
        iUrl = al.get(0).toString();
        fileName = al.get(1).toString();
        path = al.get(2).toString();

    }

    @Override
    public void run() {
        try {
            final String[] tmp2 = iUrl.split("/");
            final String name = tmp2[tmp2.length - 1].split("\\?")[0];
            final String[] tmp3 = name.split("\\.");

            if (new File(path + "/" + fileName + "." + tmp3[tmp3.length - 1]).exists())
                throw new FileAlreadyExistsException(null);

            System.out.println("Opening input stream");
            final InputStream in = new URL(iUrl).openStream();

            final File dir = new File(path + "/");
            if (!dir.exists())
                dir.mkdir();
            Files.copy(in, Paths.get(path + "/" + fileName + "." + tmp3[tmp3.length - 1]));
        } catch (final FileAlreadyExistsException e) {
            Printer.printToLog("Img exists, skipping...", Printer.LOGTYPE.DEBUG);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        System.out.println("Closed input stream");
    }
}
