/*
 * @Author: tianyu 
 * @Date: 2019-08-20 10:07:02 
 * @Last Modified by:   tianyu 
 * @Last Modified time: 2019-08-20 10:07:02 
 */

package com.jellyfish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableCaching
public class JellyfishApplication {
    public static void main(String[] args) {
        SpringApplication.run(JellyfishApplication.class, args);
    }

}
