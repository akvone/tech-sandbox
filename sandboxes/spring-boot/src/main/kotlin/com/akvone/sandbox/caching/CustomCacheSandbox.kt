package com.akvone.sandbox.caching

import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.cache.interceptor.SimpleCacheErrorHandler
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service


private const val MAIN_CACHE = "cache-1"
private const val CACHE_WHICH_THROWS_EXCEPTION_ON_PUT = "cache-2"

@Component
@Profile("feature-custom-cache")
class CustomCacheConfigurer : CachingConfigurerSupport() {

    /**
     * This should be a default CacheErrorHandler
     * [see the comment](https://stackoverflow.com/questions/34564285/how-to-setup-two-different-cachemanager-in-spring-boot)
     * from [CachingConfigurer] developer
     */
    override fun errorHandler(): CacheErrorHandler {
        return object : SimpleCacheErrorHandler() {
            private val log = LoggerFactory.getLogger(CustomCacheConfigurer::class.java)

            override fun handleCachePutError(exception: RuntimeException, cache: Cache, key: Any, value: Any?) {
                log.warn("Exception was thrown on `cache put` operation")
            }
        }
    }

}

@Component
@Profile("feature-custom-cache")
class CustomCacheManager : CacheManager {
    val mainCache = object : Cache by ConcurrentMapCache(MAIN_CACHE) {}
    val cacheWhichThrowsExceptionOnPut = object : Cache by ConcurrentMapCache(CACHE_WHICH_THROWS_EXCEPTION_ON_PUT) {
        override fun put(key: Any, value: Any?) {
            throw IllegalStateException("This exception should be swallowed")
        }
    }

    override fun getCache(name: String): Cache {
        return when (name) {
            MAIN_CACHE -> mainCache
            CACHE_WHICH_THROWS_EXCEPTION_ON_PUT -> cacheWhichThrowsExceptionOnPut
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCacheNames() = mutableListOf(MAIN_CACHE, CACHE_WHICH_THROWS_EXCEPTION_ON_PUT)

}

@Service
@Profile("feature-custom-cache")
class ServiceCachedByCustomCacheManager {


    @CachePut(cacheManager = "customCacheManager", cacheNames = [MAIN_CACHE])
    fun tryToPutObjectButThrowExceptionOnItsCalculation(): String {
        throw IllegalStateException()
    }

    @CachePut(cacheManager = "customCacheManager", cacheNames = [CACHE_WHICH_THROWS_EXCEPTION_ON_PUT])
    fun putObjectIntoCacheWhichThrowsExceptionOnPut(): String {
        return "large object"
    }

}
