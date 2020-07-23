
package training.demo.cache;


import org.infinispan.Cache;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/")
public class DemoCache {

	@Resource(lookup="java:jboss/infinispan/cache/demo/default")
	private Cache<String, String> cache;

	private String serverName;
	
	private static final AtomicInteger atomicCounter = new AtomicInteger();

	@PostConstruct
	public void postConstruct() {
		serverName = System.getProperty("jboss.server.name");
	}

	@GET
	public String list() {
		StringBuilder sb = new StringBuilder();
		for (String key: cache.keySet()) {
			sb.append('\n').append(key).append('=').append(cache.get(key));
		}
		return msg(sb);
	}

	@DELETE
	public String clear() {
		cache.clear();
		return msg("cache cleared");
	}

	
	@GET
    @Path("/{key}")
	public String get(@PathParam("key") String key) {
		if (key == null) {
			return msg("KEY is null");
		}
		String value = cache.get(key);		
		return msg("get " + value);
	}
	
	@POST
	public String put(@FormParam("key") String key, @FormParam("value") String value) {
		StringBuilder sb = new StringBuilder();
		if (key == null) {
			return msg(sb.append("KEY is NULL\n"));
		}
		if (cache.containsKey(key)) {
			sb.append("replace ").append(cache.get(key)).append(" with ");
		} else {
			sb.append("set ");
		}
		cache.put(key, value);
		return msg(sb.append(value));
	}
	
	@PUT
	public String random() {
		String key = serverName + "-" + atomicCounter.incrementAndGet();
		String value = UUID.randomUUID().toString();
		cache.put(key, value);
		return msg("add " + key + "=" + value);
	}

	
	/**
	 */
	private String msg(Object msg) {
		return "[" + serverName + "] " + msg + '\n';
	}


}
