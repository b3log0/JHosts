package org.b3log.jhosts;

import lombok.Builder;
import lombok.Data;

/**
 * Author: Zhang Yu
 * Date: 17年9月6日
 * Email: yu.zhang@7fresh.com
 */
@Data
@Builder
public class Hosts {
    private boolean flag;
    private String ipAddress;
    private String domainName;
}
