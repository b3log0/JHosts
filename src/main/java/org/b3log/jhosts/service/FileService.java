package org.b3log.jhosts.service;

import org.b3log.jhosts.Host;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Zephyr on 17-9-6.
 */
public interface FileService {
    List<String> getGroup();

    List<Host> getHostsByGroup(String group);

    Map<String, Set<Host>> getAllGroupHosts();

    Map<String, Set<Host>> getAllHostGroups();

    List<Host> getAllHosts();

    String readHostFile();

    void backupHostsFile();

    void writeHostFile(Map<String, Set<Host>> hostMap);

    void writeToHostFile(List<Host> hostList);
}
