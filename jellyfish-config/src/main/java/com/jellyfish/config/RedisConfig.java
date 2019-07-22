package com.jellyfish.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Redis单机或集群
 */
@Configuration
@EnableRedisHttpSession
public class RedisConfig {
  /*  @Bean
    public StringRedisTemplate redisTemplate(RedisConnectionFactory factory){
        StringRedisTemplate stringRedisTemplate=new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);

        return stringRedisTemplate;
    }*/
  @Autowired
  private LettuceConnectionFactory lettuceConnectionFactory;
  @Bean
  public StringRedisTemplate redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
      StringRedisTemplate stringRedisTemplate=new StringRedisTemplate();
      stringRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
      return stringRedisTemplate;
  }
}
