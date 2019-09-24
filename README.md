Hibernate Tech Talk
--

![Image](doc/hibernate-tech-talk.jpg)

If you’ve been working with Hibernate for some time, but still feel that there are a lot of unknown magic in the framework, it’s time to repeat core concepts, learn tricky internal mechanics and familiarize with traps that it’s so easy to fall into.

A lot of code and non-trivial puzzlers are waiting for you.

## Agenda

* Dirty checking and 1st level cache
* Embeddable types
* Proxy mechanism
* Common fetching problems (cartesian and n+1) and how to overcome them
* Hibernate and Spring’s @Transactional annotation
* Hibernate and Spring Data
* Hibernate and Lombok
* Batch operations

## About the project

Used technologies:
* Java 11
* [Spring Boot 2](https://spring.io/projects/spring-boot)
* [Spring Data](https://spring.io/projects/spring-data)
* [Hibernate 5](https://hibernate.org/orm/documentation/5.3/)
* [Lombok](https://projectlombok.org/)
* [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
* [Test Containers](https://www.testcontainers.org/) + [Spring Boot extension](https://github.com/testcontainers/testcontainers-spring-boot)
* [PostgreSQL](https://www.postgresql.org/)

Execute the following command to build the project and execute tests:
`./mvnw clean install`

## References

* [Java Persistence with Hibernate Second Edition by Christian Bauer (2018)](https://www.amazon.com/Java-Persistence-Hibernate-Christian-Bauer/dp/1617290459) - in my opinion, the best book about Hibernate
* [Blog about Hibernate by Vlad Mihalcea](https://vladmihalcea.com/tutorials/hibernate/)
* [Blog about Hibernate by Thorben Janssen](https://thoughts-on-java.org/)
* Articles:
    - [7 Tips to boost your Hibernate performance](https://thoughts-on-java.org/tips-to-boost-your-hibernate-performance/)
    - [10 Common Hibernate Mistakes That Cripple Your Performance](https://thoughts-on-java.org/common-hibernate-mistakes-cripple-performance/)
    - [Ultimate Guide to Implementing equals() and hashCode() with Hibernate](https://thoughts-on-java.org/ultimate-guide-to-implementing-equals-and-hashcode-with-hibernate/)
    - [Advanced Hibernate: Proxy Pitfalls](https://xebia.com/blog/advanced-hibernate-proxy-pitfalls/)
    - [The Dark Side of Hibernate Auto Flush](https://dzone.com/articles/dark-side-hibernate-auto-flush)
    - [Common Transaction Propagation Pitfalls in Spring Framework](https://medium.com/@safa_ertekin/common-transaction-propagation-pitfalls-in-spring-framework-2378ee7d6521)
    - [Data Objects aren't entities (Lombok and Hibernate)](https://mdeinum.github.io/2019-02-13-Lombok-Data-Ojects-Arent-Entities/)
