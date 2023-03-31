## endorfy

#### :question: Purpose of existence
To simplify integration with offline players, without requirement to loop over whole world file to find an offline player, and lately recover his unique id.

#### :rocket: How it works?
*endorfy* stores player's unique id, username and allows for bidirectional lookups by their unique ids, usernames. So plugins 
using this library, can simply map a unique id to username, without any heavier operations.

#### :package: How, I can use that?
There are several ways to integrate our library with yours plugin, most recommended way is to use Maven.

##### :heart: Maven (pom.xml)
```xml
<repositories>
    <repository>
        <id>rafal-moe-repo-private</id>
        <url>https://repo.rafal.moe/private/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>moe.rafal</groupId>
        <artifactId>endorfy-api</artifactId>
        <version>1.0-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```