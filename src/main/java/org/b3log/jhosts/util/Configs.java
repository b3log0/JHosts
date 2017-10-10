package org.b3log.jhosts.util;

import org.b3log.jhosts.service.HostFileService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Author: Zhang Yu
 * Date: 17年10月10日
 * Email: yu.zhang@7fresh.com
 */
public class Configs {
    private Map<String,String> props = new HashMap<>();

    private void readProps() throws IOException {
        Properties prop = new Properties();
        prop.load(Configs.class.getClassLoader().getResourceAsStream("host.properties"));
        for(String name: prop.stringPropertyNames()){
            props.put(name,prop.getProperty(name));
        }
    }

    public static String getProp(String name){
        return "";
    }

}
