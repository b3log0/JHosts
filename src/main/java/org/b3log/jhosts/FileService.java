package org.b3log.jhosts;

import java.util.List;
import java.util.Map;

/**
 * Created by yaya on 17-9-6.
 */
public interface FileService {
    List<String> getGroup();
    List<Hosts> getHostsByGroup(String group);
    Map<String,List<Hosts>> getAllGroupHosts();
    List<String> getAllHosts();
    void formatHostsFile();
}
