# 카카오 지도 API 사용 예제

## 실행 RUN

### 방법 1.  CMD창에서 docker hub public repository image 실행

#### 1. Image 다운로드
```
docker pull heddke7/pharmacy-recommendation-database
```
```
docker pull heddke7/pharmacy-recommendation-redis
```
```
docker pull heddke7/pharmacy-recommendation-app
```

#### 2. 이미지 실행
```
docker run -d -p 3306:3306 --name pharmacy-recommendation-database -e MARIADB_DATABASE=board -e MARIADB_ROOT_PASSWORD=1234 heddke7/pharmacy-recommendation-database
```
```
docker run -d -p 6379:6379 --name pharmacy-recommendation-database heddke7/pharmacy-recommendation-redis
```
```
docker run -d -p 80:8080 --name pharmacy-recommendation-app -e SPRING_DATASOURCE_USERNAME=root -e SPRING_DATASOURCE_PASSWORD=1234 -e SPRING_PROFILES_ACTIVE=prod -e KAKAO_REST_API_KEY=본인Key heddke7/pharmacy-recommendation-app
```

http://localhost 로 접속

---
### 방법 2. Git Clone IDE Local 환경

1. 프로젝트 경로에 .env파일 생성

```
SPRING_DATASOURCE_USERNAME= root
SPRING_DATASOURCE_PASSWORD= 1234
SPRING_PROFILES_ACTIVE= prod
KAKAO_REST_API_KEY =
```

2. JAR 파일 생성

```./gradlew bootJar```

3. 컨테이너 실행

``` docker-compose -up ```

4. http://localhost 로 접속

---


![image](https://github.com/Uadj/fc_pharmacy/assets/30551889/d9f41255-51d5-4207-9de5-5be5f67c1723)


![image](https://github.com/Uadj/fc_pharmacy/assets/30551889/f6ecd1f1-32bd-465f-b9f7-9140c960ee4f)


## 출저

* Fastcampus

* https://github.com/WonYong-Jang/Pharmacy-Recommendation
