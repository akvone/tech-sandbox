package com.akvone.caching

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import javax.cache.CacheManager

@Configuration
@EnableCaching
class CachingConfiguration

@Component
class CacheCustomizer: JCacheManagerCustomizer {

    private val log = LoggerFactory.getLogger(CacheCustomizer::class.java)

    override fun customize(cacheManager: CacheManager) {
        log.info("List of caches {}", cacheManager.cacheNames)
    }
}