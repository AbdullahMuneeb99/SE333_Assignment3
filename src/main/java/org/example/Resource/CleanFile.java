// Created CleanFile so I can test for correct code as well.
// Couldn't think of a different way so made a similar file to BuggyFile

package org.example.Resource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CleanFile {

    public void method0() {
        // No issues here
        int a = 5;
        int b = 10;
        int sum = a + b;
        System.out.println(sum);
    }

    public void method1() {
        int x = 3;
        try {
            x = x + 1;
        } catch (Exception e) {
            // Proper handling
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void method2() {
        String str = "Safe string";
        System.out.println(str.length());
    }

    public void method3() {
        int x = 4;
        if (x < 3) {
            x++;
        } else {
            x--;
        }
    }

    private String getStringFromDatabase(boolean valid) {
        if (valid) {
            return "Valid String";
        } else {
            return "Fallback String";
        }
    }

    public void method4() {
        String data = getStringFromDatabase(true);
        if (data != null) {
            System.out.println(data);
        }
    }

    public void method5() {
        String s1 = "test";
        String s2 = new String("test");
        boolean equal = s1.equals(s2);
        System.out.println(equal);
    }

    public void method6() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("file.txt");
            // simulate read
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO Exception: " + e.getMessage());
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    System.err.println("Error closing file: " + e.getMessage());
                }
            }
        }
    }

    public void method7() {
        int x = 10;
        int y = 2;
        int z = x / y;
        System.out.println(z);
    }

    public void testMethod() {
        int a = 5;
        int b = a + 1;
        int c = b * 2;
        System.out.println(c);
    }
}

