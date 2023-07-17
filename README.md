
# Spring Boot 3 Demo Project

This project was made to explore the new features of SB3 and improve knowledge.


## Components

This is a very simple project, containing a single controller that you can do a CRUD of products. For this project, I choose to use a in memory database (H2), which you can reproduce all steps that a conventional relational database does.


## Startup

To test the power of native images, you will need to have docker installed and configured in your machine. You can choose whether run it using a normal docker image or using a native image.

To use normal images:

```
docker build -t <YOUR_IMAGE_NAME> .
```

To use native images:

```
mvn -Pnative spring-boot:build-image
```

Both of commands must be executed inside the root folder of project.

And then, all you have to do is run the created image earlier.

```
docker run --rm -p 8080:8080 <YOUR_IMAGE_NAME>
```
