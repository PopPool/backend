FROM amazoncorretto:17

# /deploy 디렉터리 생성
RUN mkdir /deploy

# JAR 파일 변수 지정
ARG JAR_FILE=build/libs/poppool-0.0.1-SNAPSHOT.jar

# deploy 폴더로 jar 파일 복사
ADD ${JAR_FILE} /deploy/poppool-0.0.1-SNAPSHOT.jar

# 서버 포트 설정
EXPOSE 8080

# 환경 변수로 프로파일 설정
ENV SPRING_PROFILES_ACTIVE=dev

# jar 파일 실행
ENTRYPOINT ["java", "-jar", "/deploy/poppool-0.0.1-SNAPSHOT.jar"]
