package com.akvone.sandbox

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Some pros of this name:
 * 0. Does not overlap with #getLogger from other packages so it becomes the first in import section in IDEA
 */
fun Any.buildLogger(): Logger = LoggerFactory.getLogger(this::class.java)
