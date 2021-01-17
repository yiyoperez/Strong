package com.strixmc.common.cache;

import java.util.Map;
import java.util.Optional;


public interface Cache<K, V> {

  Map<K, V> get();

  default Optional<V> find(K id) {
    return Optional.ofNullable(get().get(id));
  }

  default void remove(K id) {
    get().remove(id);
  }

  default void add(K id, V object) {
    get().put(id, object);
  }

  default boolean exists(K id) {
    return get().containsKey(id);
  }

  default V getIfPresent(K id) {
    if (exists(id)) {
      return get().get(id);
    }
    return null;
  }
}