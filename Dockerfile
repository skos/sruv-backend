FROM alpine:latest
COPY . /var/lib/sruv
RUN apk add --no-cache openjdk8 libstdc++ bash \
 && chmod +x /var/lib/sruv/gradlew \
 && cd /var/lib/sruv \
 && if [ -e env.lst ]; then source env.lst; fi \
 && ./gradlew build --stacktrace
