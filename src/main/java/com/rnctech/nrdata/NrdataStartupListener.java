package com.rnctech.nrdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * Service startup hook to load all (key, value) in
 * 
 * @Author Zilin Chen
 * @Date 2020/09/24
 */


@Component
public class NrdataStartupListener implements ApplicationListener<ContextRefreshedEvent> {
 
    private static final Logger logger  = Logger.getLogger(NrdataStartupListener.class.getName());
    static Properties p = new Properties();
    static String SAMPLES = "data/samples.properties";
    
 	@Autowired
 	JedisConnectionFactory jfactory;
 
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		try {
			logger.info("put all keys in Redis");
			p.load(new FileReader(new File(SAMPLES)));
			RedisConnection conn = jfactory.getConnection();
			conn.openPipeline();
			for (Entry<Object, Object> entry : p.entrySet()) {
				String prefix = entry.getKey().toString() + "/";
				String line;
				int i = 1;
				BufferedReader br = new BufferedReader(new FileReader(entry.getValue().toString()));
				while (null != (line = br.readLine())) {
					String key = prefix + i;
					conn.set(key.getBytes(), line.getBytes());
					i++;
				}
				br.close();
			}
			conn.close();
		} catch (Throwable t) {
			logger.warning(t.getMessage());
		}
	}
}
