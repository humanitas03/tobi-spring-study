##  Appendix : chapter 1 더 알아보기

* 저는 본 예제를 진행하면서 SpringBoot 프로젝트로 예제를 작성할 예정입니다.(5/30 기준으로 2.5.0 version을 사용하네요..)

* 제가 처음 신입으로 신규 시스템 구축 프로젝트에 들어갔을 때도 그렇고, 현재 회사에서도 신규 시스템 개발하는데 있어서 Springboot 2 기반으로 제작을 하는 추세라서..
  Legacy 프로젝트가 아닌 이상 대부분은




### A1. Springboot
![img](https://user-images.githubusercontent.com/55119239/74508917-976b0d80-4f43-11ea-9c97-f479de176bf3.png)


#### A1.1 개념
* https://spring.io/projects/spring-boot
> Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run".

> We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss. Most Spring Boot applications need minimal Spring configuration.

* 스프링부트는 "단독 실행가능한" 상용화 수준의 스프링 기반 어플리케이션을 만들 수 있는 도구 입니다.
* 자동화된 설정, 라이브러리 지원, 내장 WAS(톰캣, 언더토, 제티...)을 제공하여 쉽고 빠르게 배포가능한 패키지(jar파일)로 만들 수도 있습니다.
    * 물론 WAR로 배포 가능하도록 설정이 가능합니다.
* __Maven이나 Gradle__을 이용해서 의존성들을 관리할 수 있으며, starter 라이브러리만으로도 초기에 세팅이 필요한 라이브러리들을 "최적화된 버전"으로 세팅이 가능하게 도와줍니다.
    * 예제 진행하면서 개인적으로 gradle 공부할 겸.. gradle  빌드를 사용할 계획입니다.
    * 루트 디렉토리의 build.gradle 파일에서 의존성 관리가 가능합니다.
    *
* Docker Integration, Acuator 등등 다양한 기능과 연동되어 확장이 가능한 환경을 제공합니다.


#### A1.2 Spring Boot 설정
* @SpringbootApplication 어노테이션은 Spring Web Application에 필요한 다양한 설정 정보들을 자동화 해줍니다.
* 아래는 Spring initializr로 Web Application 프로젝트를 생성시 자동으로 생성되는 Main App class 입니다.
```java
@SpringBootApplication  
public class TobispringApplication {  
  
    public static void main(String[] args) {  
        SpringApplication.run(TobispringApplication.class, args);  
  }  
  
}
```

* @SpringbootApplication 설정 안에는 @ComponentScan이라는 어노테이션이 있습니다.
* 기본 디폴트로 main App 클래스의 루트 패키지 기준으로 @Component 어노테이션으로 설정된 컴포넌트들을 ApplicationContext에 빈으로 등록하게 됩니다.
* @EnableAutoConfiguration 을 통해서 spring.factories 안에 있는 수많은 자동 설정들을 등록합니다.
```java
@Target(ElementType.TYPE)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
@Inherited  
@SpringBootConfiguration  
@EnableAutoConfiguration  
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),  
  @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })  
public @interface SpringBootApplication {
```

* 자동설정과 관련된 자세한 내용은 아래

[공식 Doc]
* https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.developing-auto-configuration

[블로그]
* https://medium.com/empathyco/how-spring-boot-autoconfiguration-works-6e09f911c5ce
* https://donghyeon.dev/spring/2020/08/01/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8%EC%9D%98-AutoConfiguration%EC%9D%98-%EC%9B%90%EB%A6%AC-%EB%B0%8F-%EB%A7%8C%EB%93%A4%EC%96%B4-%EB%B3%B4%EA%B8%B0/
  *http://dveamer.github.io/backend/SpringBootAutoConfiguration.html#@SpringBootApplication

#### A1.3 SpringBoot에서 Bean 설정 관리

**A1.3.1 등록**
* Springboot에서 Bean은 두가지 방식으로 등록이 가능합니다.
    * **Component Scan**이용
    * 직접 Bean을 등록

* **Component Scan**을 이용

```java
@Component		// 이 어노테이션으로 스프링 IOC 컨테이너가 관리하는 Bean으로 등록이 됩니다.
public class BeanComponentSample {
	//내부 코드 작성...
}
```


* **직접 Bean을 등록**
* 토비 책의 DaoFactory 클래스 처럼, @Configuration을 통해 설정
> 반드시 @Configuration 이라는 어노테이션 안에 있는 클래스에서 빈을 등록해야 합니다.
>


**A1.3.2 사용**
* @Autowired 또는 @Inject를 통해 주입을 시킬수 있습니다
```java
@RestController
public class HelloController{

	@Autowired
	private HelloService helloService;
	
	...
	
}
```

* 기본적으로 ApplicationContext에 등록된 bean 명칭으로 변수명을 지정하면, 생성자로 주입 받을 수 있습니다.
```java
@Service
public class HelloService{
	
	private final HelloBean helloBean;	//생성자 주입의 경우 멤벼 변수를 final로 설정이 가능합니다.
	
	public HelloService(HelloBean helloBean) {
		this.helloBean = helloBean;
	}
}
```
* 빈이 여러개라면 @Primary 어노테이션으로 우선순위(기본 디폴트)로 지정할 수 있습니다.
* @Qualifer("빈명칭")을 통해서 ApplicationContext에 등록된  Bean중에 내가 사용하고자 하는 빈을 사용할 수 도 있습니다.
* ApplicationContext에서 getBean()으로 직접 꺼내도 됩니다.

---

### A2. 의존성 주입(DI)에 대한 보충 설명.
> 출처 : 스프링 인 액션(4th Edition)

* 『스프링 인 액션(4th Edition)』에서는 DI의 필요성과 그 개념을 "기사(knight)와 임무(Quest)"의 비유를 들어 흥미롭게 설명하는 부분이 있습니다.

#### A2.1.1 종속객체 주입의 필요성

소녀를 구하는 기사(DamselRescuingKnight)가 직접 구출 임무(Quest)를 부여하는 클래스 입니다.
```java
public class DamselRescuingKnight implements Knight {
	private RescueDamselQuest quest;	//기사가 해야되는 퀘스트
	
	public DamselRescuingKnight() {
		//기사가 직접 퀘스트를 만든다.
		this.quest = new RescueDamselQuest();
	}
	
	public void embarkOnQuest() {
		quest.embark();
	}
}
```

* DamselRescuingKnight가 RescueDamselQuest에 강하게 결합이되어 있습니다
    * 객체의 강결합은 RescueDamselQuest(공주를 구하는 퀘스트)의 변화에 DamselRescuingKnight(공주를 구하는 기사)에 영향을 받는다는 의미입니다.

* 의존 객체의 강결합으로 인해 책에서는 두 가지 대표적인 문제를 지적합니다.
    * RescueDamselQuest가 아닌 다른 Quest가 필요한 경우, 기사의 행위(퀘스트를 수행한다)에 변화가 없음에도 소스 수정이 불가피 합니다.
    * 결합도가 높은 코드는 **테스트와 재활용에 어렵고, 사이드 이펙트**가 빈번한 경향이 있습니다.

* 책에서는 **DI**를 이용하여 객체 생성 시점에 객체 외부에서 생성되는 객체가 필요한 의존성을 주입하는 개념이 필요하다고 말합니다.


>  Quest라는 인터페이스를 생성자로 주입받는 BraveKnight
```java
public class BraveKnight implement Knight {
	private Quest quest;	//인터페이스 타입입니다.
	public BraveKnight(Quest quest) {
		this.quest = quest;	//Knight 외부로 부터 Quest를 받습니다.
	}
	
	public void embarkOnQuest(){
		quest.embark();
	}
}
```

* BraveKnight는 특정 Quest 구현체에 결합이 되지 않아 이전 소스에 비해 느슨한 결합도를 가지게 됩니다.
    * Quest구현체에 따라 BraveKnight는 "공주를 구하는 미션(RescueQuest)', "용을 처치하는 미션(SlayDragonQuest)" 등등 다양한 퀘스트를 수행할 수 있게 됩니다.
    * 종속 객체를 바꿀수 있게 되면서 **모의 테스트(Mocking)**에 용의해집니다.

> Mockito 라이브러리를 사용한 BraveKnight의 모의 테스트
```java
public class BraveKnightTest {
	@Test
	public void knightShouldEmbarkOnQuest() {
		Quest mockQuest = mock(Quest.class);
		BraveKnight knight = new BraveKnight(mockQuest);	//모의 퀘스트 주입
		knight.embarkOnQuest();
		/*embarkOnQuest()메서드를 호출 한 후, Quest의 embark()메서드가 정확히 1번 호출이 되었는지 확인하는 코드 라인*/
		verify(mockQuest, times(1)).embark();
	}
}
```


---

### A3. Spring Container와 Bean
> 출처 : 스프링 인 액션(4th Edition)

#### A3.1 스프링 컨테이너에 대한 개념

* Spring기반 애플리케이션에서는 **스프링 컨테이너(Spring Container)** 안에서 객체의 생명주기가 관리가 됩니다.

* 아래의 그림은 스프링 컨테이너 안에서 객체가 관계를 맺고 관되는 그림을 단순화 해서 표현한 예시 입니다.

![](https://miro.medium.com/max/785/1*PIFheZ8h8OO_hss9PETXIw.png)
> 이미지 출처 : https://medium.com/lifeinhurry/what-is-spring-container-spring-core-9f6755966fe9

* 스프링에는 여러 컨테이너 구현체가 존재하고, 크게 두가지로 분류가 가능합니다.
    1)	 **빈 팩토리(Bean Factory)** : DI에 대한 기본적인 지원을 제공하는 단순한 컨테이너 입니다.
    2) **애플리케이션 컨텍스트(org.springframework.context.ApplicationContext 인터페이스에 의해 정의)** : 빈 팩토리를 확장(extends?)하여 프로퍼티 텍스트를 읽고 애플리케이션 프레임워크 서비스를 제공하는 컨테이너 입니다.

#### A3.2 어플리케이션 컨텍스트의 종류


책에서 자주 접할 수 있는 몇가지 어플리케이션 컨텍스트 종류를 열거 합니다.

* AnnotationConfigApplicationContext : 하나 이상의 자바 기반 설정 클래스에서 스프링 애플리케이션 컨텍스트를 로드
* AnnotationConfigWebApplicationContext: 하나 이상의 자바 기반 설정 클래스에서 스프링 웹 어플리케이션 컨텍스트를 로드한다.
* ClassPathXmlApplicationContext : 클래스패스(classpath)에 위치한 XML 파일에서 컨텍스트 정의 내용을 로드한다.
* FileSystemXmlApplicationContext: 파일 경로로 지정된 XML 파일에서 컨텍스트 정의 내용을 로드한다.
* XmlWebApplicationContext : 웹 애플리케이션에 포함된 XML파일에서 컨텍스트 정의 내용을 로드한다.

#### A3.3 빈의 일생

* 일반적인 자바 어플리케이션의 생명주기는 단순합니다.
    * new 키워드를 이용해 빈을 인스턴스화 합니다.
    * 더이상 사용되지 않는 빈은 가비기 컬렉션 후보가 되어 사라지게 됩니다.

* 스프링 컨테이너 내에서 빈의 생명주기는 정교합니다.

![](https://i.stack.imgur.com/hfnfy.png)
> 출처 : https://stackoverflow.com/questions/48670503/life-cycle-of-a-spring-bean/48689908


1. 스프링이 빈을 인스턴스화 한다.
2. 스프링이 값과 빈의 레퍼런스를 빈의 프로퍼티에 주입힌다.
3. 빈이 BeanNameAware를 구현하면 스프링이 빈의 ID를 setBeanName() 메소드에 넘긴다.
4. 빈이 BeanFactoryAware를 구현하면 setBeanFactory() 메소드를 호출하여 빈 팩토리(BeanFactory) 자체를 넘긴다.
5. 빈이 ApplicationContextAware를 구현하면 스프링이  setApplicationContext() 메소드를 호출하고 둘러싼 애플리케이션 컨텍스트(enclosing application context)에 대한 참조를 넘긴다.
6. 빈이 BeanPostProcessor 인터페이스를 구현하면 스프링은 postProcessBeforeInitialization() 메소드를 호출한다.
7. 빈이 InitializingBean 인터페이스를 구현하면 스프링은 postProcessAfterInitialization() 메소드를 호출
8. BeanPostProcessor를 구현하면 스프링은 postProcessAfterInitialization() 호출
9. 이 상태가 되면 빈은 어플리케이션에서 사용할 준비가 된 것이며, 채플리케이션 컨텍스트가 소멸할 때까지 애플리케이션 컨텍스트에 남아 있다.
   10.빈이 DisposableBean 인터페이스를 구현하면 스프링은 destroy() 메소드를 호출한다. 마찬가지로 빈이 destroy-method와 함께 선언됐으면 지정된 메소드가 호출된다.

 ----
### A4. Spring 3 ~ 5 까지..


* 토비 스프링 책에서 커버하는 **Spring 버전은 3.X**
* 스프링 인 액션(4th)에서 커버하는 **Spring버전은 4.0**
* 대중화 되고 있는 SpringBoot 2.X 버전의 프로젝트는 **Spring 5.X** 버전
    * 제 실무에서도 Spring5.x의 스펙을 많이 활용이 되고 있기에, 스터디 진행하면서 간단한게 관련된 기술 스택도 정리해보도록 하겠습니다.

각 버전별 Spring 스펙에 대해 간략하게 살펴 볼 수 있는 블로그 추가합니다.

* https://server-engineer.tistory.com/775
* https://velog.io/@hygoogi/스프링-버전-별-특징