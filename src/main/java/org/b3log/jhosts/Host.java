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
    private boolean enable;
    private String ipAddress;
    private String domainName;

    @Override
    public boolean equals(Object obj) {
        Host host = (Host) obj;
        if (this.ipAddress.equals(host.ipAddress) && this.domainName.equalsIgnoreCase(host.domainName)) {
            return true;
        } else {
            return false;
        }
    }
}
