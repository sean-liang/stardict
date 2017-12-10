FROM openjdk:8-jre-alpine
WORKDIR /stardict
RUN wget -q -O /stardict/stardict-0.2.2.jar https://github.com/sean-liang/stardict/releases/download/v0.2.2/stardict-0.2.2.jar
ENTRYPOINT ["java", "-jar", "stardict-0.2.2.jar"]
CMD ["validate", "./input/"]
