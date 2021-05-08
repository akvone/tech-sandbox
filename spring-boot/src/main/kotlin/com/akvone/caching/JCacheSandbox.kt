package com.akvone.caching

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import javax.cache.CacheManager


@Component
@Profile("feature-jcache")
class CacheRunner(
    private val cachedService: CachedService
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        cachedService.constructLargeObject("200")
        cachedService.constructLargeObject("200")
    }
}

@Component
@Profile("feature-jcache")
class CacheCustomizer : JCacheManagerCustomizer {

    private val log = LoggerFactory.getLogger(CacheCustomizer::class.java)

    override fun customize(cacheManager: CacheManager) {
        log.info("List of caches {}", cacheManager.cacheNames)
    }
}

@Service
@Profile("feature-jcache")
class CachedService {

    private val log = LoggerFactory.getLogger(CachedService::class.java)

    @Cacheable(cacheManager = "cacheManager", cacheNames = ["main"])
    fun constructLargeObject(id: String): String {
        log.info("Constructing large object")
        return "Hello $id"
    }
}