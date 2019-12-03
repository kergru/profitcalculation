package com.kgr.profitcalculation.repository.redis;

import com.kgr.profitcalculation.domain.YearlyProfitCalculation;
import com.kgr.profitcalculation.repository.YearlyProfitCalculationRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;

public class YearlyProfitCalculationRedisRepository implements YearlyProfitCalculationRepository {

    private final RedisTemplate<?, ?> redisTemplate;

    private final HashOperations hashOperations;

    private HashMapper<Object, byte[], byte[]> mapper = new ObjectHashMapper();

    public YearlyProfitCalculationRedisRepository(RedisTemplate<?, ?> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public YearlyProfitCalculation findByYear(int year) {
        return (YearlyProfitCalculation) hashOperations.get("YEARLYPROFITCALCULATION", year);
    }

    @Override
    public void save(YearlyProfitCalculation yearlyCalculation) {
        hashOperations.put("YEARLYPROFITCALCULATION", yearlyCalculation.getYear(), yearlyCalculation);
    }
}
