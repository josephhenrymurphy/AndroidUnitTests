package com.mobiquity.androidunittests.testutil;

import com.mobiquity.androidunittests.net.models.WolframResponse;

import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.InputStream;

public class TestFileUtil {

    public static String readFile(String file) {
        try {
            InputStream inputStream = new TestFileUtil().getClass().getClassLoader().getResourceAsStream(file);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static<T> T readXmlFile(Class<T> clazz, String file) {
        try {
            String fileContent = readFile(file);
            return new Persister().read(clazz, fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
