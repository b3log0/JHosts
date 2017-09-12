package org.b3log.jhosts.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.b3log.jhosts.Host;
import org.b3log.jhosts.SystemEnum;
import org.b3log.jhosts.service.FileService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Author: Zhang Yu
 * Date: 17年9月6日
 * Email: yu.zhang@7fresh.com
 */
public class FileServiceImpl implements FileService {
    private static String host_file;
    private static String host_bak;
    private static String host_test;

    public FileServiceImpl() {
        if (StringUtils.isBlank(host_file)) { //TODO 待重构为springboot项目并使用参数配置
            switch (SystemEnum.getSystemEnum(System.getProperty("os.name"))) {
                case LINUX:
                    host_file = "/etc/hosts";
                    host_bak = "/home/zephyr/Documents/hostsBak";
                    host_test = "/home/zephyr/Documents/hostTest";
                    break;
                case MAC_OS_X:
                    host_file = "/etc/hosts";
                    host_bak = "/Users/yaya/Documents/hostsBak";
                    host_test = "/Users/yaya/Documents/hostTest";
                    break;
            }
        }
    }

    @Override
    public List<String> getGroup() {
        return new ArrayList<>(getAllGroupHosts().keySet());
    }

    @Override
    public List<Host> getHostsByGroup(String group) {
        return getAllGroupHosts().get(group).stream().sorted(Comparator.comparing(Host::getDomainName)).collect(Collectors.toList());
    }

    /**
     * 根据域名分组获取IP集合
     *
     * @return
     */
    @Override
    public Map<String, Set<Host>> getAllGroupHosts() {
        Map<String,Host> hostsList = getAllHosts();
        Map<String, Set<Host>> hostsMap = new HashMap<>();
        for (Host host : hostsList.values()) {
            if (hostsMap.get(host.getDomainName()) != null) {
                hostsMap.get(host.getDomainName()).add(host);
            } else {
                hostsMap.put(host.getDomainName(), new HashSet<>());
                hostsMap.get(host.getDomainName()).add(host);
            }
        }
        return hostsMap;
    }

    /**
     * 根据IP分组获取域名集合
     *
     * @return
     */
    @Override
    public Map<String, Set<Host>> getAllHostGroups() {
        Map<String,Host> hostsList = getAllHosts();
        Map<String, Set<Host>> hostsMap = new HashMap<>();
        for (Host host : hostsList.values()) {
            if (hostsMap.get(host.getIpAddress()) != null) {
                hostsMap.get(host.getIpAddress()).add(host);
            } else {
                hostsMap.put(host.getIpAddress(), new HashSet<>());
                hostsMap.get(host.getIpAddress()).add(host);
            }
        }
        return hostsMap;
    }

    @Override
    public Map<String, Host> getAllHosts() {
        List<String> lines = Arrays.stream(readHostFile().split("\\n+")).collect(Collectors.toList());
        Map<String, Host> hostsList = new HashMap<>();
        final String IP_ADDRESS_REG = "[0-9]+.[0-9]+.[0-9]+.[0-9]+";
        Pattern pattern = Pattern.compile(IP_ADDRESS_REG);
        for (int i = 0; i < lines.size(); i++) {
            Matcher m = pattern.matcher(lines.get(i));
            if (m.find()) {
                Boolean flag = !lines.get(i).trim().startsWith("#");
                String ipAddress = m.group();
                String domainName = lines.get(i).split("\\s+")[1]; //假定hosts文件格式是规范的（应当通过format进行初始化）
                //此处的序号应当在多次读取时保证一致
                //修改后，应当能根据提供的行号，在此处找到原处
                hostsList.put(String.valueOf(i),Host.builder().enable(flag).ipAddress(ipAddress).domainName(domainName).build());
            }
        }
        return hostsList;
    }

    @Override
    public void backupHostsFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
        host_bak += "." + dateFormat.format(new Date());
        backUpHostFile(host_bak);
    }

    /**
     * 读取host文件，总是读取真正的host文件
     *
     * @return
     */
    @Override
    public String readHostFile() {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(host_file));
            String line = bufferedReader.readLine();
            while (line != null) {
                result.append(line + "\n");
                line = bufferedReader.readLine();
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据分组写host文件
     * 组名单独作为一行
     *
     * @param hostMap
     */
    @Override
    public void writeHostFile(Map<String, Set<Host>> hostMap) {
        /*try {
            BufferedWriter out = new BufferedWriter(new FileWriter(host_test));
            for (String group : hostMap.keySet()) {
                out.write(MessageFormat.format("## {0}", group));
                out.newLine();
                for (Host host : hostMap.get(group)) {
                    out.write(MessageFormat.format("{0}{1} {2}", host.isEnable() ? "" : "#", host.getIpAddress(), host.getDomainName()));
                    out.newLine();
                }
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void writeToHostFile(Map<String,Host> hostList) {
        try {
            Map<String,Host> allHosts = getAllHosts();
            BufferedWriter out = new BufferedWriter(new FileWriter(host_file));
            for (String line : hostList.keySet()) {
                allHosts.put(line,hostList.get(line));
            }
            for(Host host : allHosts.values()){
                out.write(MessageFormat.format("{0}{1} {2}", host.isEnable() ? "" : "#", host.getIpAddress(), host.getDomainName()));
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void backUpHostFile(String path) {
        try {
            String hostFile = readHostFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(hostFile);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
