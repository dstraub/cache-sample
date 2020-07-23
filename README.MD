Simple JBoss Infinispan Sample
==============================

I digged this example from old jee-workshops (about 5-6 years ago) for a colleague, making some adjustments for JBoss EAP 7.2

* run `mvn package` - this creates 2 JBoss Server Instances in `targed/node1` and `targed/node2`

* in `run-node` - adjust _JBOSS_EAP_BIN_ and _CLUSTER_IP_ according your system

* call `run-node 1` and `run-node 2` in different terminals (or send the jobs to backround)

* use the REST-API :
```
$ curl -X POST -d key=a -d value=b localhost:8180/cache
[cache1] set b
$ curl localhost:8280/cache
[cache2]
a=b
...
```

* Unfortunately, direct JNDI Lookups after the cache  doesn't work anymore
