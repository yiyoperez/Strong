package com.strixmc.common.cache;

import com.google.inject.Singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class BaseCache<K, V> implements Cache<K, V> {

  private final Map<K, V> cacheMap;

  public BaseCache() {
    this.cacheMap = new ConcurrentHashMap<>();
  }

  @Override
  public Map<K, V> get() {
    return this.cacheMap;
  }
}
