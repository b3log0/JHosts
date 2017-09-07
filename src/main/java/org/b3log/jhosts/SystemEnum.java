package org.b3log.jhosts;

/**
 * Author: Zhang Yu
 * Date: 17年9月6日
 * Email: yu.zhang@7fresh.com
 */
public enum SystemEnum {
    LINUX("linux"),
    MAC_OS_X("mac os x"),
    UNKOWN("?");

    SystemEnum(String system){
        this.system = system;
    }
    private String system;
    public static SystemEnum getSystemEnum(String sys){
        for(SystemEnum se : SystemEnum.values()){
            if(se.system.equalsIgnoreCase(sys)){
                return se;
            }
        }
        return UNKOWN;
    }
}
