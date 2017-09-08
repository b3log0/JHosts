package org.b3log.jhosts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Zhang Yu
 * Date: 17年9月6日
 * Email: yu.zhang@7fresh.com
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Host {
    private boolean flag;
    private String ipAddress;
    private String domainName;
}
