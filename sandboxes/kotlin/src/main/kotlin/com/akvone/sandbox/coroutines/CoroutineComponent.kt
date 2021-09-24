package com.akvone.sandbox.coroutines

import kotlinx.coroutines.future.await
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class CoroutineComponent {

    suspend fun supplyAsync(value: String): String {
        Executors.newWorkStealingPool()
        val completableFuture: CompletableFuture<String> = CompletableFuture.supplyAsync { value }
        return completableFuture.await()
    }
}
