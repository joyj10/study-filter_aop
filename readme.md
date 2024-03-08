# Filter & Interceptor

## Filter
- 최앞단 영역
- 클라이언트 요청을 가장 먼저 만나는 곳
- 역할
    - 들어온 데이터를 변화해서 전달
    - 들어온 데이터 즉, JSON 바디에 대해서 모든 내용을 기록하는 등의 로그 시스템으로 많이 사용
- 필터는 웹 컨텍스트라고 해서 톰캣에서 관리하는 영역

### LoggerFilte
- 필터에서 스프링으로 들어오기 전에 정제되지 않은 req, res에 대해 로그를 찍기 위해 사용
- `HttpServletRequestWrapper`
    - req를 위 클래스를 받는 경우 로그를 찍으면 데이터가 소진되어서 로직 실행이 정상적으로 안됨
    - 그래서 ContentCachingRequestWrapper 클래스로 사용함

## Interceptor
- 필터보다 안쪽 위치
- 컨트롤러 가기 앞단에 있는 스프링에서 관리하는 빈
- 핸들러 맵핑을 통해서 어디로 가야할 지 방향이 정해진 상태
- 역할
    - 컨트롤러가 가지고 있는 어노테이션을 찾아서 작업 처리
    - 인증 처리를 주로 함


### OpenApiInterceptor
- 필터 이후에 핸들러 맵핑이 일어나고 나면 그 후에 전달
- 인터셉터 동작하게 하기 위해서는 config 설정 필요

### 인터셉터 등록
```java
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final OpenApiInterceptor openApiInterceptor;
    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(openApiInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/user/**")
                .order(2);
    }
}

```

- 위와 같이 여러개 등록도 가능하고
    - .order() 로 순서 지정도 가능
        - 높은 순서가 먼저 실행됨



# AOP
- Aspect Oriented Programming
- 관점 지향 프로그램
- AOP 애노테이션
- AspectJ
    - AspectJ는 AOP를 위한 Java 프로그래밍 언어 확장이며, Java 플랫폼에서 작동
    - AspectJ는 코드에 삽입되는 관점(Aspect)를 정의하며, 이 관점은 특정 "조인 포인트"에서 실행되는 동작을 정의
    - 이를 통해 개발자는 공통적인 관심사를 모듈화하고 코드의 다른 부분에 재사용할 수 있음
