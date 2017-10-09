package org.b3log.jhosts.service;

import org.b3log.jhosts.Host;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Zhang Yu
 * Date: 17年10月9日
 * Email: yu.zhang@7fresh.com
 */
public class HostFileService {
    private static List<String> hostFile = new ArrayList<>();

    /**
     * TODO
     * 读取原始Host文件
     */
    public List<Host> readHostFile() throws IOException {
        List<Host> hostList = new ArrayList<>();
        Properties prop = new Properties();
        prop.load(HostFileService.class.getClassLoader().getResourceAsStream("host.properties"));
        String hostFilePath = prop.getProperty("host_file_path");
        //1 读取整个文档，将文本内容保存为字符串数组，并备份到hosts中
        BufferedReader bufferedReader = new BufferedReader(new FileReader(hostFilePath));
        String line = bufferedReader.readLine();
        final String IP_ADDRESS_REG = "[0-9]+.[0-9]+.[0-9]+.[0-9]+";
        Pattern pattern = Pattern.compile(IP_ADDRESS_REG);
        while (line != null) {
            HostFileService.hostFile.add(line);
            //2 将其中的纯IP+Domain组合保存为Host对象,记录所在行号
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                Boolean enable = !line.trim().startsWith("#");
                String ipAddress = m.group();
                String domainName = line.substring(line.lastIndexOf(ipAddress));
                hostList.add(Host.builder().line(HostFileService.hostFile.size()-1).enable(enable).ipAddress(ipAddress).domainName(domainName).build());
            }
            line = bufferedReader.readLine();
        }
        return hostList;
    }

    public boolean writeHostFile(List<Host> hostList) {
        //1 根据行号信息，将Host信息写入到静态成员hostFile中
        //2 重写host文件
        //3 将最近改动的内容放到List的最上方
        return false;
    }

    public static void main(String[] args) {
        try {
            System.out.println(new HostFileService().readHostFile().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
