# GRPC JAVA client library for PyTorch served using TorchServe

## Prerequisites
Ensure you have Maven installed.

## Compilation and Packaging

Compile the project using the following command:
```sh
mvn clean compile
```

Package the project using the following command:
```sh
mvn clean package
```

The required JAR file will be created at:
```
target/client-1.0-SNAPSHOT.jar
```

## Using the Java Library

If you are able to build the library, keep the client JAR file ready. If not, download the JAR file from [here](https://drive.google.com/file/d/1gqrhCboKJlU8lcZwXyBhTi3GekG2k6pZ/view?usp=sharing).

### Adding the JAR to Your Project

Place the `client-1.0-SNAPSHOT.jar` file in a directory within your project, for example, `libs/`.

Add the JAR file to your build path. If you are using Maven, you can install the JAR file locally and add it as a dependency:

```sh
mvn install:install-file -Dfile=libs/client-1.0-SNAPSHOT.jar -DgroupId=com.example.torchserve -DartifactId=client -Dversion=1.0-SNAPSHOT -Dpackaging=jar
```

Update your `pom.xml` file to include the JAR as a dependency:

```xml
<dependencies>
    <dependency>
        <groupId>com.example.torchserve</groupId>
        <artifactId>client</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

### Example Usage

```java
package com.example.newproject;

import com.example.torchserve.client.TorchServeClient;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        TorchServeClient client = new TorchServeClient("localhost", 8080);
        Map<String, byte[]> inputData = new HashMap<>();
        inputData.put("example_key", "example_input".getBytes());
        byte[] result = client.predict("example_model", inputData);
        System.out.println("Prediction result: " + new String(result));
    }
}
```