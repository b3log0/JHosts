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
    List<Hosts> getAllHosts();
    void formatHostsFile();

    /**接口层面应该只需要如下两个方法，只要下面两个方法实现，上面都可以用了**/

    List<String> readHostFile();
    void writeHostFile();
}
