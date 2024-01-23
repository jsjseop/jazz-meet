FROM openjdk:17.0.1-jdk-slim
# 크롬 설치
RUN apt-get -y update
RUN apt -y install wget
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt -y install ./google-chrome-stable_current_amd64.deb
#

WORKDIR /app
COPY . .
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-Dserver.port=8080", "-Duser.timezone=Asia/Seoul", "-jar","app.jar"]
