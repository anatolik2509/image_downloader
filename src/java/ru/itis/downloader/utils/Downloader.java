package ru.itis.downloader.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Scanner;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;

public class Downloader {
    public void downloadByURI(String url, String folder) {
        URI uri = null;
        try {
            uri = new URI(url);
            URLConnection connection = uri.toURL().openConnection();
            InputStream stream = connection.getInputStream();
            String path = uri.toURL().getPath();
            int b = path.lastIndexOf('/');
            String name = path.substring(b == -1 ? 0:b);
            String type = connection.getContentType();
            String ext = MimeTypes.getDefaultMimeTypes().forName(type).getExtension();
            name += ext;
            FileOutputStream outputStream = new FileOutputStream(new File(String.valueOf(Paths.get(folder,name))));
            int in;
            while(((in = stream.read()) != -1)){
                outputStream.write(in);
            }
            outputStream.flush();
            outputStream.close();
        } catch (URISyntaxException | IOException | MimeTypeException e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Downloader downloader = new Downloader();
        downloader.downloadByURI("http://pm1.narvii.com/7144/96200bc8195eaa0af1e16397f5eff253f1a94ce8r1-2048-1365v2_uhq.jpg?test=1", "C:/test");
    }
}
