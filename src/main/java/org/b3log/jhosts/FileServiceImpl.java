package org.b3log.jhosts;

import org.apache.commons.lang3.StringUtils;

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
class FileServiceImpl implements FileService {
    private final String IP_ADDRESS_REG = "[0-9]+.[0-9]+.[0-9]+.[0-9]+";
    private static String file;
    private String file_bak = "/home/zephyr/Documents/hostsBak";

    FileServiceImpl() {
        if (StringUtils.isBlank(file)) { //TODO 待重构为springboot项目并使用参数配置
            switch (SystemEnum.getSystemEnum(System.getProperty("os.name"))) {
                case LINUX:
                    file = "/etc/hosts";
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
        List<Host> hostsList = getAllHosts();
        Map<String, Set<Host>> hostsMap = new HashMap<>();
        for (Host host : hostsList) {
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
        List<Host> hostsList = getAllHosts();
        Map<String, Set<Host>> hostsMap = new HashMap<>();
        for (Host host : hostsList) {
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
    public List<Host> getAllHosts() {
        List<String> lines = Arrays.stream(readHostFile().split("\\n+")).collect(Collectors.toList());
        List<Host> hostsList = new ArrayList<>();
        Pattern pattern = Pattern.compile(IP_ADDRESS_REG);
        for (int i = 0; i < lines.size(); i++) {
            Matcher m = pattern.matcher(lines.get(i));
            if (m.find()) {
                Boolean flag = !lines.get(i).trim().startsWith("#");
                String ipAddress = m.group();
                String domainName = lines.get(i).split("\\s+")[1]; //假定hosts文件格式是规范的（应当通过format进行初始化）
                hostsList.add(Host.builder().flag(flag).ipAddress(ipAddress).domainName(domainName).build());
            }
        }
        return hostsList;
    }

    @Override
    public void formatHostsFile() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
        this.file_bak += "." + dateFormat.format(new Date());
        backUpHostFile(this.file_bak);
        writeHostFile(getAllGroupHosts());
    }

    @Override
    public String readHostFile() {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
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

    @Override
    public void writeHostFile(Map<String, Set<Host>> hostMap) {
        try {
            //TODO 此处路径为host路径，测试期间暂时不动
            BufferedWriter out = new BufferedWriter(new FileWriter("/home/zephyr/Documents/hostsTest"));
            for (String group : hostMap.keySet()) {
                out.write(MessageFormat.format("## {0}", group));
                out.newLine();
                for (Host host : hostMap.get(group)) {
                    out.write(MessageFormat.format("{0}{1} {2}", host.isFlag() ? "" : "#", host.getIpAddress(), host.getDomainName()));
                    out.newLine();
                }
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
