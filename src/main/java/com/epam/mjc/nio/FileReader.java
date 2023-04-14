package com.epam.mjc.nio;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;

public class FileReader {

    public Profile getDataFromFile(File file) {
        String name;
        int age=0;
        String email;
        long phone=0;
        String data = "";
        try {
            data = String.valueOf(FileReader.readFromFileUsingFileChannel(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String strAge = cut(data, "Age: ");
        email = cut(data, "Email: ");
        String strPhone = cut(data, "Phone: ");
        name = cut(data, "Name: ");
        try{
            age = Integer.parseInt(strAge.trim());
            phone = Long.parseLong(strPhone.trim());}
        catch (NumberFormatException e){e.printStackTrace();}
        return new Profile(name,age,email,phone);
    }

    public static String cut(String text, String find) {

        String answer = "";
        int start = text.indexOf(find);
        text = text.substring(start);
        start = text.indexOf(find);
        int end = text.indexOf("\n");
        if (start > -1)answer = text.substring(start + find.length(), end);
        return answer;
    }
    public static StringBuilder readFromFileUsingFileChannel(File rfile) throws Exception {
        RandomAccessFile file = new RandomAccessFile(rfile, "r");
        FileChannel channel = file.getChannel();
        StringBuilder content = new StringBuilder();
        ByteBuffer buffer = ByteBuffer.allocate(256);
        int bytesRead = channel.read(buffer);
        while (bytesRead != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                content.append((char) buffer.get());
            }
            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        file.close();
        return content;
    }



}
