package com.epam.mjc.nio;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;

public class FileReader {

    public Profile getDataFromFile(File file) {
        String name = null;
        int age = 0;
        String email = null;
        long phone = 0;
        String data;
        String strAge;
        String strPhone;
        try {
            data = String.valueOf(FileReader.readFromFileUsingFileChannel(file));
            strAge = cut(data, "Age: ");
            email = cut(data, "Email: ");
            strPhone = cut(data, "Phone: ");
            name = cut(data, "Name: ");
            age = Integer.parseInt(strAge.trim());
            phone = Long.parseLong(strPhone.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Profile(name, age, email, phone);
    }

    public static String cut(String text, String find) {
        if (text != null) {
            String answer = "";
            int start = text.indexOf(find);
            text = text.substring(start);
            start = text.indexOf(find);
            int end = text.indexOf("\n");
            if (start > -1) answer = text.substring(start + find.length(), end);
            return answer;
        } else return null;
    }

    public static StringBuilder readFromFileUsingFileChannel(File rfile) throws IOException {

        StringBuilder content = null;
        ByteBuffer buffer;
        try (RandomAccessFile file = new RandomAccessFile(rfile, "r")) {
            FileChannel channel = file.getChannel();
            content = new StringBuilder();
            buffer = ByteBuffer.allocate(256);
            int bytesRead = channel.read(buffer);
            while (bytesRead != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    content.append((char) buffer.get());
                }
                buffer.clear();
                bytesRead = channel.read(buffer);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return content;
    }
}



