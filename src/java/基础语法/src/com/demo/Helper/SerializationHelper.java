package com.demo.Helper;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class SerializationHelper {
    public static <T> T FromXml(String xml) throws Exception {
        var in = new ByteArrayInputStream(xml.getBytes("utf-8"));
        var bufer=new BufferedInputStream(in);
        try(var decoder = new XMLDecoder(bufer)){
            return (T) decoder.readObject();
        }
    }

    public static <T> String ToXml(T entity) throws Exception {
        var out = new ByteArrayOutputStream();
        var bufer=new BufferedOutputStream(out);
    try(    var encoder = new XMLEncoder(bufer)){
        encoder.writeObject(entity);
        encoder.flush();
        encoder.close();
        return out.toString("utf-8");
        }


    }
}
