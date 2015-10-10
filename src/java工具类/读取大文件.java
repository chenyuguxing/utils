package java工具类;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

class HandleTextFile {
	// 使用commons-io.jar包的FileUtils的类进行读取 
    public static void readTxtFileByFileUtils(String fileName) { 
        File file = new File(fileName); 
        try { 
            LineIterator lineIterator = FileUtils.lineIterator(file, "UTF-8"); 
            while (lineIterator.hasNext()) { 
                String line = lineIterator.nextLine(); 
                System.out.println(line); 
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 
     
    // 使用Scanner进行读取 
    public static void readTxtByScanner(String fileName) { 
        FileInputStream fileInputStream = null;  
        Scanner scanner = null; 
         
        try { 
            fileInputStream = new FileInputStream(fileName); 
            scanner = new Scanner(fileInputStream, "UTF-8"); 
            while (scanner.hasNext()) { 
                String line = scanner.nextLine(); 
                System.out.println(line); 
            } 
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        } finally { 
            if (fileInputStream != null) { 
                try { 
                    fileInputStream.close(); 
                } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
            if (scanner != null) { 
                scanner.close(); 
            } 
        } 
         
    } 
 
    // 使用cache进行读取 
    public static void readTxtByStringBuffer(String fileName) throws IOException { 
        File file = new File(fileName); 
         
        BufferedReader reader = null; 
         
        try { 
            reader = new BufferedReader(new FileReader(file), 10 * 1024 * 1024); 
            String stringMsg = null; 
            while ((stringMsg = reader.readLine()) != null) { 
                System.out.println(stringMsg); 
            } 
            reader.close(); 
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        }  
    } 
     
    public static void main(String[] args) { 
        try { 
            HandleTextFile.readTxtByStringBuffer("D:\\test\\customer_info.txt"); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 
}
