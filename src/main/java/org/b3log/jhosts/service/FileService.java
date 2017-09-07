package org.b3log.jhosts.service;

import org.b3log.jhosts.Host;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yaya on 17-9-6.
 */
public interface FileService {
    List<String> getGroup();
    List<Host> getHostsByGroup(String group);
    Map<String,Set<Host>> getAllGroupHosts();
    Map<String,Set<Host>> getAllHostGroups();
    List<Host> getAllHosts();
    void formatHostsFile();

    /**接口层面应该只需要如下两个方法，只要下面两个方法实现，上面都可以用了**/

    String readHostFile();

    void writeHostFile(Map<String, Set<Host>> hostMap);
//    void writeHostFile(String path);
}
