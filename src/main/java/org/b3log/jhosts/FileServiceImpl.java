package org.b3log.jhosts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Zhang Yu
 * Date: 17年9月6日
 * Email: yu.zhang@7fresh.com
 */
class FileServiceImpl implements FileService{
    private final String IP_ADDRESS_REG = "[0-9]+.[0-9]+.[0-9]+.[0-9]+";

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
    public List<Hosts> getAllHosts() {
        List<String> lines = readHostFile();
        List<Hosts> hostsList = new ArrayList<>();
        Pattern pattern = Pattern.compile(IP_ADDRESS_REG);
        for(String line : lines){
            Matcher m = pattern.matcher(line);
            if (m.find()){
                Boolean flag = !line.trim().startsWith("#");
                String ipAddress = m.group();
                String domainName = line.split("\\s+")[1]; //假定hosts文件格式是规范的（应当通过format进行初始化）
                hostsList.add(Hosts.builder().flag(flag).ipAddress(ipAddress).domainName(domainName).build());
            }
        }
        return hostsList;
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

    @Override
    public List<String> readHostFile(){
        List<String> result = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(getHostFile()));
            String line = bufferedReader.readLine();
            while(line!=null){
                result.add(line);
                line = bufferedReader.readLine();
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void writeHostFile() {
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
