package com.ssm.vue.jd.util;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ListPosition;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

public class JedisUtil {
	private static JedisPool jedisPool;
	private static GenericObjectPoolConfig config = new GenericObjectPoolConfig();
	private static String maxTotal;
	private static String maxIdle;
	private static String minIdle;
	private static String port;
	private static String host;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("jedis");
		maxTotal = bundle.getString("maxTotal");
		maxIdle = bundle.getString("maxIdle");
		minIdle = bundle.getString("minIdle");
		port = bundle.getString("port");
		host = bundle.getString("host");
		
		config.setMaxTotal(Integer.parseInt(maxTotal));//最大连接数
		config.setMinIdle(Integer.parseInt(minIdle));//最小空闲连接数
		config.setMaxIdle(Integer.parseInt(maxIdle));//最大空闲连接数
		jedisPool = new JedisPool(config,host,Integer.parseInt(port));
	}
	
	//获取jedis
		public static Jedis getJedis() {
			return jedisPool.getResource();
		}
		
		//封装string类型的方法
		//指定key的值
		public static String set(String key,String value) {
			Jedis jedis = getJedis();
			String set = jedis.set(key, value);
			jedis.close();  
			return set;
		}
		
		////根据key获取value
		public static String get(String key) {
			Jedis jedis = getJedis();
			String value = jedis.get(key);
			jedis.close();
			return value;
		}
		
		
		//返回 key 中字符串值的子字符（注：这是个左右都闭合的区间）
		public static String getrange(String key,int start,int end) {
			Jedis jedis = getJedis();
			String substring = jedis.getrange(key,start,end);
			jedis.close();
			return substring;
		}
		
		//将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
		public static String getSet(String key,String value) {
			Jedis jedis = getJedis();
			String oldstring = jedis.getSet(key, value);
			jedis.close();
			return oldstring;
		}
		
		//对 key 所储存的字符串值，获取指定偏移量上的位(bit)。
		//当偏移量 OFFSET 比字符串值的长度大，或者 key 不存在时，返回 false
		public static boolean getbit(String key,int offset) {
			Jedis jedis = getJedis();
			Boolean getbit = jedis.getbit(key, offset);
			jedis.close();
			return getbit;
		}
		
		//获取所有(一个或多个)给定 key 的值。
		public static List<String> mget(String...keys) {
			Jedis jedis = getJedis();
			List<String> mget = jedis.mget(keys);
			jedis.close();
			return mget;
		}
		
		//对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
		//true代表1false代表0
		public static void setbit(String key,int offset,boolean value) {
			Jedis jedis = getJedis();
			jedis.setbit(key, offset, value);
			jedis.close();
		}
		
		//将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位)。
		public static String setex(String key,int seconds,String value) {
			Jedis jedis = getJedis();
			String setex = jedis.setex(key, seconds, value);
			jedis.close();
			return setex;
		}
		
		//只有在 key 不存在时设置 key 的值。
		public static long setnx(String key,String value) {
			Jedis jedis = getJedis();
			Long setnx = jedis.setnx(key, value);
			jedis.close();
			return setnx;
		}
		
		//用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始。
		public static long setrange(String key,int offset,String value) {
			Jedis jedis = getJedis();
			Long setrange = jedis.setrange(key, offset, value);
			jedis.close();
			return setrange;
		}
		
		//返回 key 所储存的字符串值的长度。
		public static long strlen(String key) {
			Jedis jedis = getJedis();
			Long strlen = jedis.strlen(key);
			jedis.close();
			return strlen;
		}
		
		//同时设置一个或多个 key-value 对。
		public static String mset(String...keysvalues) {
			Jedis jedis = getJedis();
			String mset = jedis.mset(keysvalues);
			jedis.close();
			return mset;
		}
		
		//同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
		public static long msetnx(String...keysvalues) {
			Jedis jedis = getJedis();
			Long msetnx = jedis.msetnx(keysvalues);
			jedis.close();
			return msetnx;
		}
		
		//这个命令和 SETEX 命令相似，但它以毫秒为单位设置 key 的生存时间，而不是像 SETEX 命令那样，以秒为单位。
		public static String psetex(String key,long milliseconds,String value) {
			Jedis jedis = getJedis();
			String psetex = jedis.psetex(key, milliseconds, value);
			jedis.close();
			return psetex;
		}
		
		//将 key 中储存的数字值增一。
		public static long incr(String key) {
			Jedis jedis = getJedis();
			Long incr = jedis.incr(key);
			jedis.close();
			return incr;
		}
		
		//将 key 所储存的值加上给定的增量值（increment） 。
		public static long incrBy(String key,long integer) {
			Jedis jedis = getJedis();
			Long incrBy = jedis.incrBy(key, integer);
			jedis.close();
			return incrBy;
		}
		
		//将 key 所储存的值加上给定的浮点增量值（increment） 。
		public static double incrByFloat(String key,double increment) {
			Jedis jedis = getJedis();
			Double incrByFloat = jedis.incrByFloat(key, increment);
			jedis.close();
			return incrByFloat;
		}
		
		//将 key 中储存的数字值减一.
		public static long decr(String key) {
			Jedis jedis = getJedis();
			Long decr = jedis.decr(key);
			jedis.close();
			return decr;
		}
		
		//key 所储存的值减去给定的减量值（decrement） 。
		public static long decrBy(String key,long integer) {
			Jedis jedis = getJedis();
			Long decrBy = jedis.decrBy(key, integer);
			jedis.close();
			return decrBy;
		}
		
		//如果 key 已经存在并且是一个字符串， APPEND 命令将指定的 value 追加到该 key 原来值（value）的末尾。
		public static long append(String key,String value) {
			Jedis jedis = getJedis();
			Long append = jedis.append(key, value);
			jedis.close();
			return append;
		}
		
		
		/*
		 * Hash类型封装
		 * */
		//删除一个或多个哈希表字段
		public static long hdel(String key,String... fields) {
			Jedis jedis = getJedis();
			Long hdel = jedis.hdel(key, fields);
			jedis.close();
			return hdel;
		}
		
		//查看哈希表 key 中，指定的字段是否存在。
		public static Boolean hexists(String key,String field) {
			Jedis jedis = getJedis();
			Boolean hexists = jedis.hexists(key, field);
			jedis.close();
			return hexists;
		}
		
		//获取存储在哈希表中指定字段的值。
		public static String hget(String key,String field) {
			Jedis jedis = getJedis();
			String hget = jedis.hget(key, field);
			jedis.close();
			return hget;
		}
		
		//获取在哈希表中指定 key 的所有字段和值
		public static Map<String, String> hgetAll(String key) {
			Jedis jedis = getJedis();
			Map<String, String> hgetAll = jedis.hgetAll(key);
			jedis.close();
			return hgetAll;
		}
		
		//为哈希表 key 中的指定字段的整数值加上增量 increment 
		public static long hincrBy(String key,String field,int integer) {
			Jedis jedis = getJedis();
			Long hincrBy = jedis.hincrBy(key, field, integer);
			jedis.close();
			return hincrBy;
		}
		
		//为哈希表 key 中的指定字段的浮点数值加上增量 increment 
		public static Double hincrByFloat(String key,String field,Double value) {
			Jedis jedis = getJedis();
			Double hincrByFloat = jedis.hincrByFloat(key, field, value);
			jedis.close();
			return hincrByFloat;
			
		}
		
		//获取所有哈希表中的字段
		public static Set<String> hkeys(String key) {
			Jedis jedis = getJedis();
			Set<String> hkeys = jedis.hkeys(key);
			jedis.close();
			return hkeys;
		}
		
		//获取哈希表中字段的数量
		public static Long hlen(String key) {
			Jedis jedis = getJedis();
			Long hlen = jedis.hlen(key);
			jedis.close();
			return hlen;
		}
		
		//获取所有给定字段的值
		public static List<String> hmget(String key,String...fields) {
			Jedis jedis = getJedis();
			List<String> hmget = jedis.hmget(key, fields);
			jedis.close();
			return hmget;
		}
		
		//同时将多个 field-value (域-值)对设置到哈希表 key 中。
		public static String hmset(String key,Map<String,String> hash) {
			Jedis jedis = getJedis();
			String hmset = jedis.hmset(key, hash);
			jedis.close();
			return hmset;
			
		}
		
		//将哈希表 key 中的字段 field 的值设为 value 。
		public static Long hset(String key,Map<String,String> hash) {
			Jedis jedis = getJedis();
			Long hset = jedis.hset(key, hash);
			jedis.close();
			return hset;
		}
		
		//只有在字段 field 不存在时，设置哈希表字段的值。
		public static Long hsetnx(String key,String field,String value) {
			Jedis jedis = getJedis();
			Long hsetnx = jedis.hsetnx(key, field, value);
			jedis.close();
			return hsetnx;
		}
		
		//获取哈希表中所有值。
		public static List<String> hvals(String key) {
			Jedis jedis = getJedis();
			List<String> hvals = jedis.hvals(key);
			jedis.close();
			return hvals;
		}
		
		//迭代哈希表中的键值对。
		public static ScanResult<Entry<String, String>> hscan(String key,String cursor) {
			Jedis jedis = getJedis();
			ScanResult<Entry<String, String>> hscan = jedis.hscan(key, cursor);
			jedis.close();
			return hscan;
		}
		
		
		/*
		 * list类型
		 * 
		 * */
		
		//移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
		public static List<String> blpop(String...keys) {
			Jedis jedis = getJedis();
			List<String> blpop = jedis.blpop(keys);
			jedis.close();
			return blpop;
			
		}
		public static List<String> blpop(int timeout,String...keys) {
			Jedis jedis = getJedis();
			List<String> blpop = jedis.blpop(timeout, keys);
			jedis.close();
			return blpop;
			
		}
		
		//移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
		public static List<String> brpop(String...keys) {
			Jedis jedis = getJedis();
			List<String> blpop = jedis.brpop(keys);
			jedis.close();
			return blpop;
			
		}
		public static List<String> brpop(int timeout,String...keys) {
			Jedis jedis = getJedis();
			List<String> blpop = jedis.brpop(timeout, keys);
			jedis.close();
			return blpop;
			
		}
		
		//从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
		public static String brpoplpush(String source,String destination,int timeout) {
			Jedis jedis = getJedis();
			String brpoplpush = jedis.brpoplpush(source, destination, timeout);
			jedis.close();
			return brpoplpush;
		}
		
		//通过索引获取列表中的元素
		public static String lindex(String key,long index) {
			Jedis jedis = getJedis();
			String lindex = jedis.lindex(key, index);
			jedis.close();
			return lindex;
		}
		
		//在列表的元素前或者后插入元素
		public static Long linsert(String key,ListPosition where,String  pivot,String value) {
			Jedis jedis = getJedis();
			Long linsert = jedis.linsert(key, where, pivot, value);
			jedis.close();
			return linsert;
		}
		
		//获取列表长度
		public static long llen(String key) {
			Jedis jedis = getJedis();
			Long llen = jedis.llen(key);
			jedis.close();
			return llen;
		}
		
		//移出并获取列表的第一个元素
		public static String lpop(String key) {
			Jedis jedis = getJedis();
			String lpop = jedis.lpop(key);
			jedis.close();
			return lpop;
		}
		
		//将一个或多个值插入到列表头部
		public static Long lpush(String key,String...values) {
			Jedis jedis = getJedis();
			Long lpush = jedis.lpush(key, values);
			jedis.close();
			return lpush;
		}
		
		//将一个值插入到已存在的列表头部
		public static Long lpushhx(String key,String...values) {
			Jedis jedis = getJedis();
			Long lpushx = jedis.lpushx(key, values);
			jedis.close();
			return lpushx;
		}
		
		//获取列表指定范围内的元素
		public static List<String> lrange(String key,int start,int end) {
			Jedis jedis = getJedis();
			List<String> lrange = jedis.lrange(key, start, end);
			jedis.close();
			return lrange;
		}
		
		//移除列表元素
		public static Long lrem(String key,long count,String value) {
			Jedis jedis = getJedis();
			Long lrem = jedis.lrem(key, count, value);
			jedis.close();
			return lrem;
		}
		
		//通过索引设置列表元素的值
		public static String lset(String key,int index,String value) {
			Jedis jedis = getJedis();
			String lset = jedis.lset(key, index, value);
			jedis.close();
			return lset;
		}
		
		//对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
		public static String ltrim(String key,int start,int end) {
			Jedis jedis = getJedis();
			String ltrim = jedis.ltrim(key, start, end);
			jedis.close();
			return ltrim;
		}
		
		//移除列表的最后一个元素，返回值为移除的元素。
		public static String rpop(String key) {
			Jedis jedis = getJedis();
			String rpop = jedis.rpop(key);
			jedis.close();
			return rpop;
		}
		
		//移除列表的最后一个元素，并将该元素添加到另一个列表并返回
		public static String rpoplpush(String source,String destination) {
			Jedis jedis = getJedis();
			String rpoplpush = jedis.rpoplpush(source, destination);
			jedis.close();
			return rpoplpush;
		}
		
		//在列表中添加一个或多个值到列表尾部
		public static Long rpush(String key,String...values) {
			Jedis jedis = getJedis();
			Long rpush = jedis.rpush(key, values);
			jedis.close();
			return rpush;
			
		}
		
		//为已存在的列表添加值
		public static Long rpushx(String key,String...values) {
			Jedis jedis = getJedis();
			Long rpushx = jedis.rpushx(key, values);
			jedis.close();
			return rpushx;
		}
		
		/*
		 * 无序集合set
		 * 
		 * */
		//向集合添加一个或多个成员
		public static Long sadd(String key,String...members) {
			Jedis jedis = getJedis();
			Long sadd = jedis.sadd(key, members);
			jedis.close();
			return sadd;
		}
		
		//获取集合的成员数
		public static Long scard(String key) {
			Jedis jedis = getJedis();
			Long scard = jedis.scard(key);
			jedis.close();
			return scard;
		}
		
		//返回第一个集合与其他集合之间的差异。
		public static Set<String>  sdiff(String...keys) {
			Jedis jedis = getJedis();
			Set<String> sdiff = jedis.sdiff(keys);
			jedis.close();
			return sdiff;
		}
		
		//返回给定所有集合的差集并存储在 destination 中
		public static Long sdiffstore(String destination,String...keys) {
			Jedis jedis = getJedis();
			Long sdiffstore = jedis.sdiffstore(destination, keys);
			jedis.close();
			return sdiffstore;
		}
		
		//返回给定所有集合的交集
		public static Set<String> sinter(String...keys){
			Jedis jedis = getJedis();
			Set<String> sinter = jedis.sinter(keys);
			jedis.close();
			return sinter;
		}
		
		//返回给定所有集合的交集并存储在 destination 中
		public static Long sinterstore(String destination,String...keys){
			Jedis jedis = getJedis();
			Long sinterstore = jedis.sinterstore(destination, keys);
			jedis.close();
			return sinterstore;
		}
		
		
		//判断 member 元素是否是集合 key 的成员
		public static Boolean sismember(String key,String member) {
			Jedis jedis = getJedis();
			Boolean sismember = jedis.sismember(key, member);
			jedis.close();
			return sismember;
		}
		
		//返回集合中的所有成员
		public static Set<String> smembers(String key){
			Jedis jedis = getJedis();
			Set<String> smembers = jedis.smembers(key);
			jedis.close();
			return smembers;
		}
		
		//将 member 元素从 source 集合移动到 destination 集合
		public static Long smove(String source,String destination,String member) {
			Jedis jedis = getJedis();
			Long smove = jedis.smove(source, destination, member);
			jedis.close();
			return smove;
		}
		
		//移除并返回集合中的一个随机元素
		public static String spop(String key) {
			Jedis jedis = getJedis();
			String spop = jedis.spop(key);
			jedis.close();
			return spop;
		}
		
		//返回集合中一个或多个随机数
		public static List<String> srandmember(String key,int count) {
			Jedis jedis = getJedis();
			List<String> srandmember = jedis.srandmember(key, count);
			jedis.close();
			return srandmember;
		}
		
		//移除集合中一个或多个成员
		public static Long srem(String key,String...members) {
			Jedis jedis = getJedis();
			Long srem = jedis.srem(key, members);
			jedis.close();
			return srem;
		}
		
		//返回所有给定集合的并集
		public static Set<String> sunion(String...keys) {
			Jedis jedis = getJedis();
			Set<String> sunion = jedis.sunion(keys);
			jedis.close();
			return sunion;
		}
		
		//所有给定集合的并集存储在 destination 集合中
		public static Long sunionstore(String destination,String...keys) {
			Jedis jedis = getJedis();
			Long sunionstore = jedis.sunionstore(destination, keys);
			jedis.close();
			return sunionstore;
		}
		
		/*
		 * 有序集合sorted set
		 * 
		 * */
		
		//向有序集合添加一个或多个成员，或者更新已存在成员的分数
		public static Long zadd(String key,double score,String member) {
			Jedis jedis = getJedis();
			Long zadd = jedis.zadd(key, score, member);
			jedis.close();
			return zadd;
		}
		
		//获取有序集合的成员数
		public static Long  zcard(String key) {
			Jedis jedis = getJedis();
			Long zcard = jedis.zcard(key);
			jedis.close();
			return zcard;
		}
		
		//计算在有序集合中指定区间分数的成员数
		public static Long zcount(String key,double min,double max) {
			Jedis jedis = getJedis();
			Long zcount = jedis.zcount(key, min, max);
			jedis.close();
			return zcount;
		}
		
		//有序集合中对指定成员的分数加上增量 increment
		public static Double zincrby(String key,double score,String member) {
			Jedis jedis = getJedis();
			Double zincrby = jedis.zincrby(key, score, member);
			jedis.close();
			return zincrby;
		}
		
		//计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 destination 中
		public static Long zinterstore(String destination,String...keys) {
			Jedis jedis = getJedis();
			Long zinterstore = jedis.zinterstore(destination, keys);
			jedis.close();
			return zinterstore;
		}
		
		//在有序集合中计算指定字典区间内成员数量
		public static Long zlexcount(String key,String min,String max) {
			Jedis jedis = getJedis();
			Long zlexcount = jedis.zlexcount(key, min, max);
			jedis.close();
			return zlexcount;
		}
		
		//通过索引区间返回有序集合指定区间内的成员
		public static Set<String> zrange(String key,int start,int end){
			Jedis jedis = getJedis();
			Set<String> zrange = jedis.zrange(key, start, end);
			jedis.close();
			return zrange;
		}
		
		//通过字典区间返回有序集合的成员
		public static Set<String> zrangeByLex(String key,String min,String max){
			Jedis jedis = getJedis();
			Set<String> zrangeByLex = jedis.zrangeByLex(key, min, max);
			jedis.close();
			return zrangeByLex;
		}
		
		//通过分数返回有序集合指定区间内的成员
		public static Set<String> zrangebyscore(String key,double min,double max){
			Jedis jedis = getJedis();
			Set<String> zrangeByScore = jedis.zrangeByScore(key, min, max);
			jedis.close();
			return zrangeByScore;
		}
		
		//返回有序集合中指定成员的索引
		public static Long zrank(String key,String member) {
			Jedis jedis = getJedis();
			Long zrank = jedis.zrank(key, member);
			jedis.close();
			return zrank;
		}
		
		//移除有序集合中的一个或多个成员
		public static Long zrem(String key,String...members) {
			Jedis jedis = getJedis();
			Long zrem = jedis.zrem(key, members);
			jedis.close();
			return zrem;
		}
		
		//移除有序集合中给定的字典区间的所有成员
		public static Long zremrangeByLex(String key,String min,String max) {
			Jedis jedis = getJedis();
			Long zremrangeByLex = jedis.zremrangeByLex(key, min, max);
			jedis.close();
			return zremrangeByLex;
		}
		
		//移除有序集合中给定的排名区间的所有成员
		public static Long  zremrangeByRank(String key,int start,int end) {
			Jedis jedis = getJedis();
			Long zremrangeByRank = jedis.zremrangeByRank(key, start, end);
			jedis.close();
			return zremrangeByRank;
		}
		
		//移除有序集合中给定的分数区间的所有成员
		public static Long  zremrangeByScore(String key,double min,double max) {
			Jedis jedis = getJedis();
			Long zremrangeByScore = jedis.zremrangeByScore(key, min, max);
			jedis.close();
			return zremrangeByScore;
		}
		
		//返回有序集中指定区间内的成员，通过索引，分数从高到低
		public static Set<String> zrevrange(String key,int start,int end) {
			Jedis jedis = getJedis();
			Set<String> zrevrange = jedis.zrevrange(key, start, end);
			jedis.close();
			return zrevrange;
		}
		
		////返回有序集中指定区间内的成员，通过索引，分数从高到低,同时返回分数
		public static Set<Tuple> zrevranges(String key,int start,int end) {
			Jedis jedis = getJedis();
			Set<Tuple> zrevrangeWithScores = jedis.zrevrangeWithScores(key, start, end);
			jedis.close();
			return zrevrangeWithScores;
		}
		
		//返回有序集中指定分数区间内的成员，分数从高到低排序
		public static Set<String> zrevrangeByScore(String key,double max,double min){
			Jedis jedis = getJedis();
			Set<String> zrevrangeByScore = jedis.zrevrangeByScore(key, max, min);
			jedis.close();
			return zrevrangeByScore;
		}
		
		//返回有序集中指定分数区间内的成员，分数从高到低排序,并返回分数
		public static Set<Tuple> zrevrangeByScoreW(String key,double max,double min){
			Jedis jedis = getJedis();
			Set<Tuple> zrevrangeByScoreWithScores = jedis.zrevrangeByScoreWithScores(key, max, min);
			jedis.close();
			return zrevrangeByScoreWithScores;
		}
		
		//返回有序集合中指定成员的排名，有序集成员按分数值递减(从大到小)排序
		public static Long zrevrank(String key,String member) {
			Jedis jedis = getJedis();
			Long zrevrank = jedis.zrevrank(key, member);
			jedis.close();
			return zrevrank;
		}
		
		//返回有序集中，成员的分数值
		public static Double zscore(String key,String member) {
			Jedis jedis = getJedis();
			Double zscore = jedis.zscore(key, member);
			jedis.close();
			return zscore;
		}
		
		//计算给定的一个或多个有序集的并集，并存储在新的 key 中
		public static Long zunionstore(String destination,String...keys) {
			Jedis jedis = getJedis();
			Long zunionstore = jedis.zunionstore(destination, keys);
			jedis.close();
			return zunionstore;
		}
		
		
		/*
		 * KEYS命令
		 * */
		
		//该命令用于在 key 存在时删除 key。
		public static Long del(String key) {
			Jedis jedis = getJedis();
			Long del = jedis.del(key);
			jedis.close();
			return del;
		}
		
		//序列化给定 key ，并返回被序列化的值。
		public byte[] dump(String key) {
			Jedis jedis = getJedis();
			byte[] dump = jedis.dump(key);
			jedis.close();
			return dump;
		}
		
		//检查给定 key 是否存在。
		public static Boolean exists(String key) {
			Jedis jedis = getJedis();
			Boolean exists = jedis.exists(key);
			jedis.close();
			return exists;
		}
		
		//为给定 key 设置过期时间，以秒计。
		public static Long expire(String key,int seconds) {
			Jedis jedis = getJedis();
			Long expire = jedis.expire(key, seconds);
			jedis.close();
			return expire;
		}
		
		//返回 key 所储存的值的类型。
		public static String  type(String key) {
			Jedis jedis = getJedis();
			String type = jedis.type(key);
			jedis.close();
			return type;
		}
		
		
		
		//关闭连接池
		public static void close() {
			JedisUtil.jedisPool.close();
		}
		
	
}
