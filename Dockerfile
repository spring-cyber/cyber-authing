FROM 113.108.106.175:444/cmp/java:11

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD target/cyber-authing-1.0.0-SNAPSHOT.jar /home
