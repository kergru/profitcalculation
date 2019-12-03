package com.kgr.profitcalculation.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kgr.profitcalculation.repository.YearlyProfitCalculationRepository;
import com.kgr.profitcalculation.repository.redis.YearlyProfitCalculationRedisRepository;
import com.kgr.profitcalculation.repository.simple.YearlyProfitCalculationSimpleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.zalando.jackson.datatype.money.MoneyModule;

@Configuration
public class RepositoryConfiguration {

    @Value("${redis.host}")
    private String redisHost;

    @Bean(name = "yearlyProfitCalculationRepository")
    @ConditionalOnProperty(name = "calculation.cache", havingValue = "simple")
    YearlyProfitCalculationRepository yearlyProfitCalculationSimpleRepository() {
        System.out.println("###### Using simple cache");
        return new YearlyProfitCalculationSimpleRepository();
    }

    @Bean(name = "yearlyProfitCalculationRepository")
    @ConditionalOnProperty(name = "calculation.cache", havingValue = "redis")
    YearlyProfitCalculationRepository yearlyProfitCalculationRedisRepository() {
        return new YearlyProfitCalculationRedisRepository(redisTemplate());
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost));
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory());
        System.out.println("###### Using redis on host: " + redisConnectionFactory().getHostName());

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = getJackson2JsonRedisSerializer();
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    public Jackson2JsonRedisSerializer getJackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new MoneyModule());
        om.registerModule(new JavaTimeModule());
        om.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        om.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }
}
