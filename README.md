# springboot304

스프링부트 

어플리케이션 설정은 application properties
lombok 같은 건 설정, 플러그인에서 추가
build.gradle 사용

프로젝트할때는 vm웨어말고 본컴에서 하는 것이 좋다.
프로젝트할때는 3버전 사용
프로젝트할때는 스프링 실행 시 배너 바꿀 수 있음 > https://devops.datenkollektiv.de/banner.txt/index.html

조장은 마리아DB 포트 열어줘야함

JPA 설정할 때 update를 쓰는 것이 create를 쓰는 것보다 좋음 * 책에 create 사용한 것이 있는데 update로 바꿔 써라

api 실행했을 때 에러가 발생한 상태라면 그레이들 확인
쿼리DSL에 에러가 발생했다. 그레이들 쿼리DSL 확인 클린, 초기화, 다시 실행

엔티티는 DB용
DTO는 일회용으로 보면 됨
