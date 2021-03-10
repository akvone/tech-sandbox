package com.akvone.caching

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.lang.Thread.sleep

@Service
class CachedService {

    @Cacheable(cacheManager = "cacheManager", cacheNames = ["main"])
    fun constructLargeObject(id: String): String {
        sleep(1000)
        return "Hello $id"
    }
}