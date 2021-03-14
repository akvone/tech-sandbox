package com.akvone.caching

import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CachedService {

    private val log = LoggerFactory.getLogger(CachedService::class.java)

    @Cacheable(cacheManager = "cacheManager", cacheNames = ["main"])
    fun constructLargeObject(id: String): String {
        log.info("Constructing large object")
        return "Hello $id"
    }
}