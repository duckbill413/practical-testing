# [섹션 #1] 테스트는 왜 필요할까?

## 단위 테스트(Unit test)

- **작은** 코드 단위를 **독립적**으로 검증하는 테스트
- 검증 속도가 빠르고 안정적이다.

### JUnit 5

- 단위 테스트를 위한 테스트 프레임워크
- XUnit - Kent Beck
    - SUnit(Smalltalk), JUnit(Java), NUnit(.NET)

### AssertJ

- 테스트 코드 작성을 원활하게 돕는 테스트 라이브러리
- 풍부한 API, 메서드 체이닝 지원

### 테스트가 어려운 영역을 구분하고 분리

- 주문은 10~22시 사이에만 가능
- 위의 요구사항에 대하여
- 테스트하기 어려운 영역을 구분하고 분리할 필요
- 외부로 분리할수록 테스트 가능한 코드는 많아진다.


- **테스트하기 어려운 영역**
    - 관측할 때마다 다른 값에 의존하는 코드
        - 현재 날짜/시간, 랜덤 값, 전역 변수/함수, 사용자 입력 등
    - 외부 세계에 영향을 주는 코드
        - 표준 출력, 메시지 발송, 데이터베이스에 기록하기 등

- **순수 함수**
    - 같은 입력에는 항상 같은 결과
    - 외부 세상과 단절된 형태
    - 테스트하기 쉬운 코드

---

# [섹션 #3] TDD: Test Driven Development

## Test Driven Development (TDD)

- 프로덕션 코드보다 테스트 코드를 먼저 작성하여 테스트가 구현 과정을 주도하도록 하는 방법론
- `RED -> GREEN -> REFACTOR` -> RED Cycle
- RED: 실패하는 테스트 작성
- GREEN: 테스트 통과 최소한의 코딩
- REFACTOR: 구현 코드 개선 테스트 통과 유지

### 피드백

- 선 기능 구현, 후 테스트 작성
    - 테스트 자체의 누락 가능성
    - 특정 테스트(해피 케이스) 케이스만 검증할 가능성
    - 잘못된 구현을 다소 늦게 발견할 가능성


- **선 테스트 작성, 후 기능 구현**
    - 복잡도가 낮은, 테스트 가능한 코드로 구현할 수 있게 한다.
    - 쉽게 발견하기 어려운 엣지(Edge) 케이스를 놓치지 않게 해준다.
    - 구현에 대한 빠른 피드백을 받을 수 있다.
    - 과감한 리팩토링이 가능해진다.

### TDD: 관점의 변화

- BEFORE: 테스트는 구현부 검증을 위한 보조 수단
- AFTER: 테스트와 상호 작용하며 발전하는 구현부
- 클라이언트 관점에서의 피드백을 주는 Test Driven

### 키워드 정리

- TDD
- `RED -> GREEN -> REFACTOR` Cycle
- 애자일(Agile) 방법론 vs. 폭포수 방법론
- 익스트림 프로그래밍 (XP, eXtreme Programming)
- 스크럼(Scrum), 칸반(kanban)

---

# [섹션 #4] 테스트는 [ ]다.

### DisplayName 을 섬세하게

- 명사의 나열보다 문장으로 (`다.` 로 끝나게)
- 테스트 행위에 대한 결과까지 기술하기
- 도메인 용어를 사용하여 한층 추상화된 내용을 담기 (메서드 자체의 관점보다 도메인 정책 관점으로)
- 테스트의 현상을 중점으로 기술하지 말 것

### BDD, Behavior Driven Development

- TDD 에서 파생된 개발 방법
- 함수 단위의 테스트에 집중하기보다, 시나리오에 기반한 테스트케이스(TC) 자체에 집주앟여 테스트
- 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수전(레벨)을 권장
- Given / When / Then
    - `Given`: 시나리오 진행에 필요한 모든 준비 과정 (객체, 값, 상태 등)
    - `When`: 시나리오 행동 진행
    - `Then`: 시나리오 진행에 대한 결과 명시, 검증
  > DisplayName 을 명확하게 작성할 수 있다.

### 키워드 정리

- `@DisplayName` - 도메인 정책, 용어를 사용한 명확한 문장
- Given / When / Then - 주어진 환경, 행동, 상태 변화
- TDD vs. BDD
- JUnit vs Spock
    - Spock: Given / When / Then 을 나눈 BDD Framework

# [섹션 #5] Spring & JPA 기반 테스트

## Layered Architecture

관심사의 분리

`User ⇔ Presentation ⇔ Business ⇔ Persistence ⇔ Data`

### 통합 테스트

- 여러 모듈이 협력하는 기능을 통합적으로 검증하는 테스트
- 일반적으로 작은 범위의 단위 테스트만으로는 기능 전체의 신뢰성을 보장할 수 없음
- 풍부한 단위 테스트 & 큰 기능 단위를 검증하는 통합 테스트

## Mock

https://site.mockito.org

### MockMvc

Mock(가짜) 객체를 사용해 스프링 MVC 동작을 재현할 수 있는 프레임워크

## 키워드 정리

### Layered Architecture

Layered Architecture는 소프트웨어를 여러 계층(Layer)으로 나누어 구성하는 아키텍처 스타일  
각 계층은 특정한 역할과 책임을 가지며, 일반적으로 상위 계층이 하위 계층을 의존하거나 호출하는 방식으로 동작  
이 아키텍처는 시스템을 이해하기 쉽게 만들고, 각각의 계층에 대한 독립적인 테스트와 유지보수가 가능하도록 합니다.

- Entity 객체가 DB와 너무 강결합
- JPA 와 너무 깊게 결합되어 있음
- JPA 엔터티가 Presentation Layer나 Application Layer에 노출되면 모델이 오염될 수 있음
- 계층 간의 강한 의존성으로 인해, 하위 계층의 변경이 상위 계층에 영향을 줄 수 있음
- 애플리케이션이 복잡해지면 계층 간의 커뮤니케이션이 복잡해질 수 있음

### Hexagonal Architecture

![](./images/hexagonal.png)
[Hexagonal Architecture Lecture](https://www.youtube.com/watch?v=qGp66Oc3zTg)

Hexagonal Architecture는 애플리케이션의 핵심 비즈니스 로직을 외부 세계(사용자 인터페이스, 데이터베이스, 외부 서비스 등)와 독립적으로 설계하기 위한 아키텍처 스타일  
애플리케이션의 내부 도메인 로직을 외부 환경으로부터 격리하여 유연성을 높이는 것이 목표

- 비즈니스 로직이 외부 기술에 의존하지 않기 때문에 높은 유연성과 테스트 용이성을 가집니다.
- 다양한 클라이언트(UI, API 등)에 동일한 도메인 로직을 재사용할 수 있습니다.
- 외부 시스템의 변경이 Core에 미치는 영향을 최소화할 수 있습니다.
- 구조가 상대적으로 복잡할 수 있으며, 이해하는 데 시간이 걸릴 수 있습니다.
- 작은 프로젝트에서는 과도한 설계가 될 수 있습니다.

### Layered Architecture vs Hexagonal Architecture 비교

- 의존성: Layered Architecture는 계층 간의 상하 의존성이 있지만, Hexagonal Architecture는 의존성을 역전시켜 외부 시스템이 Core를 참조하도록 설계됩니다.
- 유연성: Hexagonal Architecture는 외부 시스템과의 독립성을 강조하므로 변화에 더 유연하게 대처할 수 있습니다.
- 테스트 용이성: Hexagonal Architecture는 Core와 외부 시스템이 분리되어 있으므로, Core를 독립적으로 테스트하기가 더 쉽습니다.

# [섹션 #6] Mock 을 마주하는 자세

### Mockito Stubbing
- Mock 을 이용하여 외부 통신이나 DB 연결 등의 작업을 수행  
- MockMvc의 `when` -> `thenReturn`
- 외부와의 연결 없이도 테스트가 가능해 짐

### Test Double
- **Dummy**
  - 아무 것도 하지 않는 깡통 객체
- **Fake**
  - 단순한 형태로 동일한 기능은 수행하나, 프로덕션에서 쓰기에는 부족한 객체
  - `FakeRepository`
- **Stub**
  - 테스트에서 요청한 것에 대해 미리 준비한 결과를 제공하는 객체 그 외에는 응답하지 않는다.
- **Spy**
  - Stub 이면서 호출된 내용을 기록하여 보여줄 수 있는 객체
  - 일부는 실제 객체처럼 동작시키고 일부만 Stubbing 할 수 있다.
- **Mock**
  - 행위에 대한 기대를 명시하고, 그에 따라 동작하도록 만들어진 객체

> Mock 과 Stub의 차이  
> - [Test Double](https://martinfowler.com/articles/mocksArentStubs.html)
> - Stub 같은 경우는 어떤 기능을 요청했을 떄 Stub 의 **상태를 기록 및 검증** 하는데 초점
> - Mock 은 행위에 대해 검증을 중점


### @Mock, @Spy, @InjectMock
- `@Mock`: `MailServiceMockTest`
- `@Spy`: `MailServiceSpyTest`

### BDD Mockito
- `MailServiceBDDMockitoTest`

### Classicist vs. Mockist
- Classicist 는 최대한 기능을 Mock 하지말고 테스트를 하자는 주의
- Mockist 는 이미 동작 테스트가 완료된 기능에 대해서는 Mock 을 사용해서 테스트