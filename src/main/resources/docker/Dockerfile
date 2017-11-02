FROM java:8u92-jre-alpine
ENV JAVA_OPTS="-server -Xmx4g -Xms1g -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled"
# Add the micro Service
ADD odp-npi-app*.jar odp-npi-app.jar
RUN ls -la odp-npi-app.jar &&\
    touch odp-npi-app.jar
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar odp-npi-app.jar" ]