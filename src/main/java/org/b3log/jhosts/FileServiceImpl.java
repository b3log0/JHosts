package org.b3log.jhosts;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * Author: Zhang Yu
 * Date: 17年9月6日
 * Email: yu.zhang@7fresh.com
 */
class FileServiceImpl implements FileService{

    @Override
    public List<String> getGroup() {
        List<String> groupList = new ArrayList<>();
        groupList.add("Zephyr Test 1");
        groupList.add("Zephyr Test 2");
        groupList.add("Zephyr Test 3");
        groupList.add("Zephyr Test 4");
        groupList.add("Zephyr Test 5");
        return groupList;
    }

    @Override
    public List<Hosts> getHostsByGroup(String group) {
        return null;
    }

    @Override
    public Map<String, List<Hosts>> getAllGroupHosts() {
        return null;
    }

    @Override
    public List<String> getAllHosts() {
        return null;
    }

    @Override
    public void formatHostsFile() {

    }

    private File getHostFile(){
        switch(SystemEnum.getSystemEnum(System.getProperty("os.name"))){
            case LINUX:
                return new File("/etc/hosts");
            default:
                return null;
        }
    }

    private ObservableMap readHostFile(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getHostFile()));
            String line = bufferedReader.readLine();
            while(line!=null){
                System.out.println(line);
                line = bufferedReader.readLine();
            }
            return FXCollections.observableHashMap();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeHostFile(){
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(getHostFile(),"rw");
            long fileLenth = randomAccessFile.length();
            randomAccessFile.seek(fileLenth);
            randomAccessFile.writeBytes("Hello World");
            randomAccessFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
