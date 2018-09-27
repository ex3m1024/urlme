# urlme

## About
A simple URL shortener service with a working (unofficial) name of url.me. Based on Spring Boot.

## Requirements
In order to compile and run, install JDK, Gradle and Docker.  
The project can also be launched without Docker, compile and run ApiApplication.java. The app will start on port 8080 by default.  

## How to launch

*If on Windows, launch gradlew.bat instead of ./gradlew*  
*If on Linux, use sudo docker or add yourself to the docker group*

1. ./gradlew build docker --info
2. docker run -p 80:8080 --name urlmeapi -t me.url/api-docker
3. (optional) Add "127.0.0.1    url.me" to your hosts file (replace with Docker Machine's IP if needed)
4. Open 127.0.0.1 or your Docker Machine's IP (if on Windows/Mac) in browser, navigate to /list, check if an empty array is returned

### Storage
The project currently uses H2 database. The src/main/resources/application.properties file contains two DB variants - **inmem** and **separate file**.  
The latter is the default option:
* If run without Docker, the DB is saved in a small file in the current user's home folder.
* If run in Docker, the DB is persisted even docker stop and docker start are executed.
* If the container is deleted, the data is gone.

The inmem option:  
* Is faster, since it is run from RAM.
* Does not store any data on the hard drive. 
* Will not persist data if the Docker container is stopped.

## How to use

* **GET /list** Returns all shortened URLs with original URLs and request IPs
* **POST /generate** Shortens the specified URL, returns "code" that can be appended to "url.me/". The supplied URL must be valid and can start with http://, https:// or ftp://  
```
    Content-Type: application/json  
    Data:  
        {  
            "url": "https://www.oracle.com"  
        }  
```
* **GET /{code}** Redirects the user to the required webpage. If the code is incorrect, the user is redirected to url.me/  
*Example: url.me/17FDF2W* opens https://www.oracle.com.
* **GET /stats/ip** Returns an array of IP and count pairs. The list contains unique IPs and count of unique URLs shortened with it

## How to extend
* Add new methods to the MainController or add new Controllers.
* The UrlService contains URL business logic and works with saving/loading/finding data via the UrlEntityRepository.
* The LoadDatabase class allows working with data when the app is started (for example, fill the DB with some data).
* ApiApplicationTests.java tests the Controller via MockMVC (sends requests, checks the response).
* RepositoryTests.java works with the repository - test queries here.
* ServiceTests.java contains business logic tests. Data is faked via mocking the repository.

Execute *./gradlew test* to run tests.  
Made by Nikita R-T
