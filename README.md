<p align="center">
  <h1 align="center">Lost Ark Fetcher</h1>
</p>
<p align="center">
  <img src="http://ForTheBadge.com/images/badges/made-with-java.svg" alt="Made with Java">
  <br>
  <img src="https://img.shields.io/github/license/lilmayu/lost-ark-fetcher.svg" alt="License">
  <img src="https://img.shields.io/github/v/release/lilmayu/lost-ark-fetcher.svg" alt="Version">
</p>
<p align="center">
    Java library for fetching Lost Ark related things
  <br>
  Made by <a href="https://mayuna.dev">Mayuna</a>
</p>

## Instalation
### Maven
```xml
<dependency>
    <groupId>dev.mayuna</groupId>
    <artifactId>lost-ark-fetcher</artifactId>
    <version>VERSION</version>
</dependency>

<!-- Dependencies -->
<dependency>
    <groupId>dev.mayuna</groupId>
    <artifactId>simple-java-api-wrapper</artifactId>
    <version>1.0.2</version>
</dependency>
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.14.3</version>
</dependency>
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.9.0</version>
</dependency>
```
### Gradle
```groovy
repositories {
    mavenCentral()
}

dependencies {
    // Change 'implementation' to 'compile' in old Gradle versions
    implementation 'dev.mayuna:lost-ark-fetcher:VERSION'
    
    // Dependencies
    implementation 'dev.mayuna:simple-java-api-wrapper:1.0.2'
    implementation 'org.jsoup:jsoup:1.14.3'
    implementation 'com.google.code.gson:gson:2.9.0'
}
```
- Replace `VERSION` with your desired version. (Remove "v" before version number)
- For version number see latest [Maven Repository](https://mvnrepository.com/artifact/dev.mayuna/lost-ark-fetcher) release (should be same with Github Release though)
- You can also use [GitHub Releases](https://github.com/lilmayu/lost-ark-fetcher/releases)

## How to use
```java
// This library is not completed, expect changes

LostArkFetcher lostArkFetcher = new LostArkFetcher();

lostArkFetcher.fetchServers();

lostArkFetcher.fetchNewsTags();
lostArkFetcher.fetchNews();
lostArkFetcher.fetchNews(lostArkNewsTag);

lostArkFetcher.fetchForums(forumCategory);
lostArkFetcher.fetchForumTopic(topic); // LostArkForumTopic contains only few fields - not completed

// Example
lostArkFetcher.fetchServers().execute() // Returns CompletableFuture
        .thenAccept(lostArkServers -> {
            for (LostArkServer server : lostArkServers.get()) {
                System.out.println(server.getName() + " (" + server.getRegion() + "): " + server.getStatus());
            }
        });
```