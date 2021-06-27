### 토비의 스프링 스터디

## Docker로 mysql DB 띄우기
* 각자 OS에 맞는 Docker 를 설치한다.

* (windows 기준으로) Docker 설치후 CMD창에 아래와 같은 결과가 나오면 docker가 정상 설치가 된거임.

* 프로젝트 루트 디렉터리에서 아래 명령어 실행 
> cd docker
> docker-compose up -d

* 볼륨을 아예 다시 올려야 될때...
> docker-compose down -v


* intelliJ 에서 서버 모드로 DB를 띄우기 위해 아래 설정이 필요
  * servertimezone 설정이 필요함
> jdbc:mysql://localhost:3307/springbook?serverTimezone=Asia/Seoul



### Intellij에서 Database 서버모드 조회

![](./image/chapter01_2.jpg)


### 기타...

* Warning이긴 하지만, chapter 1에서 쓰는 mysql driver가 deprecated가 되어 다른 드라이버로 대체
    * 콘솔에 빨간줄은 다른 에러로 인해 생긴 부분입니다.
![](./image/chapter01_1.jpg)
      

### Chapter05. 서비스 추상화

#### 요구사항 추가

```
* 사용자 레벨은 BASIC, SILVER, GOLD 셋 중 하나,
* 사용자가 처음 가입하면 BASIC, 이후 활동에 따라 한단계 씩 업그레이드
* 가입 후 50회 이상 로그인 하면 BASIC -> SILVER 레벨이 됨
* 사용자 레벨의 변경 작업은 일정한 주기를 가지고 일괄로 진행(배치), 
변경 작업 전에는 조건을 충족하더라도 레벨변경이 일어나지 않음.
```