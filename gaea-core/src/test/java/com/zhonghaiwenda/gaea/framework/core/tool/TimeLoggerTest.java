package com.zhonghaiwenda.gaea.framework.core.tool;

import org.junit.jupiter.api.Test;


class TimeLoggerTest {


    @Test
    public void time() throws Exception {
        TimeLogger timeLogger = new TimeLogger("随便");
        timeLogger.start();
        Thread.sleep(200);
        timeLogger.end();

    }

}
