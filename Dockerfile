# Java 17 Alpine 버전을 사용
FROM openjdk:17.0.4-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 애플리케이션 JAR 파일을 컨테이너로 복사
COPY build/libs/poppool-0.0.1-SNAPSHOT.jar /app/poppool-0.0.1-SNAPSHOT.jar

# Spring Boot 애플리케이션 실행
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/app/poppool-0.0.1-SNAPSHOT.jar"]
