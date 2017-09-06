package org.b3log.jhosts;

/**
 * Author: Zhang Yu
 * Date: 17年9月6日
 * Email: yu.zhang@7fresh.com
 */
public enum SystemEnum {
    LINUX("linux"),
    UNKOWN("?");

    SystemEnum(String system){
        this.system = system;
    }
    private String system;
    public static SystemEnum getSystemEnum(String sys){
        for(SystemEnum system : SystemEnum.values()){
            if(system.name().equalsIgnoreCase(sys)){
                return system;
            }
        }
        return UNKOWN;
    }
}
