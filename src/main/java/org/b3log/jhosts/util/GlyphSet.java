package org.b3log.jhosts.util;

import com.jfoenix.svg.SVGGlyph;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by Zephyr on 17-9-8.
 */
public class GlyphSet {
    public static SVGGlyph getGlyph(String name, Paint fill) {
        String path;
        switch (name) {
            case "repeat":
                path = repeat;
                break;
            case "check_circle":
                path = check_circle;
                break;
            case "check_circle_o":
                path = check_circle_o;
                break;
            case "save":
                path = save;
                break;
            case "download":
                path = download;
                break;
            default:
                path = plane;
        }
        return new SVGGlyph(-1, name, path, fill);
    }

    private static final String plane = "M1008 6.286q18.857 13.714 15.429 36.571l-146.286 877.714q-2.857 16.571-18.286 25.714-8 4.571-17.714 4.571-6.286 "
            + "0-13.714-2.857l-258.857-105.714-138.286 168.571q-10.286 13.143-28 13.143-7.429 "
            + "0-12.571-2.286-10.857-4-17.429-13.429t-6.571-20.857v-199.429l493.714-605.143-610.857 "
            + "528.571-225.714-92.571q-21.143-8-22.857-31.429-1.143-22.857 18.286-33.714l950.857-548.571q8.571-5.143 18.286-5.143"
            + " 11.429 0 20.571 6.286z";
    private static final String repeat = "M877.714 804.571v-256q0-14.857-10.857-25.714t-25.714-10.857h-256q-24 0-33.714 22.857-9.714 22.286 8 39.429l78.857 78.857q-84.571 78.286-199.429 78.286-59.429 0-113.429-23.143t-93.429-62.571-62.571-93.429-23.143-113.429 23.143-113.429 62.571-93.429 93.429-62.571 113.429-23.143q68 0 128.571 29.714t102.286 84q4 5.714 13.143 6.857 8 0 14.286-5.143l78.286-78.857q5.143-4.571 5.429-11.714t-4.286-12.857q-62.286-75.429-150.857-116.857t-186.857-41.429q-89.143 0-170.286 34.857t-140 93.714-93.714 140-34.857 170.286 34.857 170.286 93.714 140 140 93.714 170.286 34.857q84 0 162.571-31.714t139.714-89.429l74.286 73.714q16.571 17.714 40 8 22.286-9.714 22.286-33.714z";
    private static final String check_circle = "M733.714 531.428q0 16-10.286 26.286l-52 51.429q-10.857 10.857-25.714 10.857t-25.714-10.857l-233.143-232.571-129.143 129.143q-10.857 10.857-25.714 10.857t-25.714-10.857l-52-51.429q-10.286-10.286-10.286-26.286 0-15.429 10.286-25.714l206.857-206.857q10.857-10.857 25.714-10.857 15.429 0 26.286 10.857l310.286 310.286q10.286 10.286 10.286 25.714zM877.714 438.857q0-119.429-58.857-220.286t-159.714-159.714-220.286-58.857-220.286 58.857-159.714 159.714-58.857 220.286 58.857 220.286 159.714 159.714 220.286 58.857 220.286-58.857 159.714-159.714 58.857-220.286z";
    private static final String check_circle_o = "M669.143 486.286l-241.143-241.143q-10.857-10.857-25.714-10.857t-25.714 10.857l-168 168q-10.857 10.857-10.857 25.714t10.857 25.714l58.286 58.286q10.857 10.857 25.714 10.857t25.714-10.857l84-84 157.143 157.143q10.857 10.857 25.714 10.857t25.714-10.857l58.286-58.286q10.857-10.857 10.857-25.714t-10.857-25.714zM749.714 438.857q0 84.571-41.714 156t-113.143 113.143-156 41.714-156-41.714-113.143-113.143-41.714-156 41.714-156 113.143-113.143 156-41.714 156 41.714 113.143 113.143 41.714 156zM877.714 438.857q0-119.429-58.857-220.286t-159.714-159.714-220.286-58.857-220.286 58.857-159.714 159.714-58.857 220.286 58.857 220.286 159.714 159.714 220.286 58.857 220.286-58.857 159.714-159.714 58.857-220.286z";
    private static final String save = "M219.429 73.143h438.857v219.429h-438.857v-219.429zM731.429 73.143h73.143v512q0 8-5.714 22t-11.429 19.714l-160.571 160.571q-5.714 5.714-19.429 11.429t-22.286 5.714v-237.714q0-22.857-16-38.857t-38.857-16h-329.143q-22.857 0-38.857 16t-16 38.857v237.714h-73.143v-731.429h73.143v237.714q0 22.857 16 38.857t38.857 16h475.429q22.857 0 38.857-16t16-38.857v-237.714zM512 603.428v182.857q0 7.429-5.429 12.857t-12.857 5.429h-109.714q-7.429 0-12.857-5.429t-5.429-12.857v-182.857q0-7.429 5.429-12.857t12.857-5.429h109.714q7.429 0 12.857 5.429t5.429 12.857zM877.714 585.143v-530.286q0-22.857-16-38.857t-38.857-16h-768q-22.857 0-38.857 16t-16 38.857v768q0 22.857 16 38.857t38.857 16h530.286q22.857 0 50.286-11.429t43.429-27.429l160-160q16-16 27.429-43.429t11.429-50.286z";
    private static final String download = "M731.429 182.857q0 14.857-10.857 25.714t-25.714 10.857-25.714-10.857-10.857-25.714 10.857-25.714 25.714-10.857 25.714 10.857 10.857 25.714zM877.714 182.857q0 14.857-10.857 25.714t-25.714 10.857-25.714-10.857-10.857-25.714 10.857-25.714 25.714-10.857 25.714 10.857 10.857 25.714zM950.857 310.857v-182.857q0-22.857-16-38.857t-38.857-16h-841.143q-22.857 0-38.857 16t-16 38.857v182.857q0 22.857 16 38.857t38.857 16h265.714l77.143-77.714q33.143-32 77.714-32t77.714 32l77.714 77.714h265.143q22.857 0 38.857-16t16-38.857zM765.143 636q9.714-23.429-8-40l-256-256q-10.286-10.857-25.714-10.857t-25.714 10.857l-256 256q-17.714 16.571-8 40 9.714 22.286 33.714 22.286h146.286v256q0 14.857 10.857 25.714t25.714 10.857h146.286q14.857 0 25.714-10.857t10.857-25.714v-256h146.286q24 0 33.714-22.286z";
}
