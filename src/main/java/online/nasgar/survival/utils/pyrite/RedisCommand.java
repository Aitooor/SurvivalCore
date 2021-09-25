package online.nasgar.survival.utils.pyrite;

import redis.clients.jedis.Jedis;

public interface RedisCommand<T> {

    /**
     * Execute Command.
     *
     * @param redis instance.
     */
    T execute(Jedis redis);

}

