package com.insilicogen.common.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionUtil {

    public static void log(Exception e) {
        log.error("    ### ERROR" , e);
    }
}
