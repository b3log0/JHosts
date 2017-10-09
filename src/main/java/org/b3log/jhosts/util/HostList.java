package org.b3log.jhosts.util;

import org.b3log.jhosts.Host;

import java.util.LinkedList;

/**
 * Author: Zhang Yu
 * Date: 17年10月9日
 * Email: yu.zhang@7fresh.com
 */
public class HostList extends LinkedList<Host>{

    /**
     * 更新一条host记录
     * @param host 包含原本的位置和新的host信息
     * @param pos Host列表中的第一个的位置
     */
    public void update(Host host,int pos) {
        super.remove(host.getLine());
        super.add(pos,host);
    }
}
