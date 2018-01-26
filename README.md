# cheers
This is a sample application used for my kubernetes introduction courses

## Dev, Builds and Run

### run locally
in order to make it run locally 

````
$mvn clean package vertx:run
````

### run in docker

````
mvn clean -DskipTests package docker:build docker:start docker:watch
````


## docker images released


https://hub.docker.com/r/kanedafromparis/cheers/

# Play with probs

You can use /liveness and /readiness to check kubernetes probs

You can set CHEER_SLOW_READINESS or CHEER_SLOW_LIVENESS to any positive number

## Curl & jq Tips


### to get cheers
```
curl http://127.0.0.1:8080/api/1.0/cheers
```

### to get a specific cheer

```
curl http://127.0.0.1:8080/api/1.0/cheers/ | jq .[1]
```
or prefer 

```
curl http://127.0.0.1:8080/api/1.0/cheers/1 
```

### to get a random cheer

```
curl http://127.0.0.1:8080/api/1.0/randomcheers
```


### for uploading cheer

```
curl -H "Content-Type: application/json" -X POST -d '{"intro":"you enlight my day","cause":"you look gorgeous"}' http://127.0.0.1:8080/api/1.0/cheers
```

### for deleting cheer
```
curl -i -X DELETE http://127.0.0.1:8080/api/1.0/cheers/6
```

-i is for you to have feedback ;-)
HTTP/1.1 204 No Content means that the resource does not exist

### for FQDN query

```
curl -i -H "Content-Type: application/json" -X POST -d '{"fqdn":"google.com"}' http://127.0.0.1:8080/api/1.0/info/dns
```