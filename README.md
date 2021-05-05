# Activity (Cycling) Service API
This **Activity Service API** has the following operations:
1. Creates an Activity with data provided in the uploaded CSV file.
2. Displays a single Activity summary data with below format:
   ```json
			{
				"name": "My first ride",
				"type": "cycling",
				"totalDistance": 11990,
				"totalDuration": "7 min:0 sec",
				"averagePower": 165.625,
				"averageCadence": 88.5
			}
   ```
3. Displays all Activities.
4. Deletes an Activity.
## Technology/Tools/Approach:
- [ ] Framework - Spring Boot: 2.3.0
- [ ] REST API - Spring Web
- [ ] Im-memory Database - H2 Database
- [ ] ORM Framework: Spring Data JPA
- [ ] Intellij IDEA
- [ ] Java 11
- [ ] Test Driven Development
- [ ] Maven 3.6.3
- [ ] Lombok

## Assumptions:
1. Total Duration of an Activity is displayed in the format of ``` x min:x sec```
2. Uploaded file is stored with new name **originalname-activityname-activitytype-YYYYMMDD-HHMMSS.csv**

## Usage Guidance:
### Pre-requisites:
1. JDK 11
2. Maven 3.6.3
3. Lombok setup

### Installation Steps:
1. If you are reading this in the GitHub repository [track-activity-service](https://github.com/AnandAili/track-activity-service), then
   Clone this repository into your local machine by executing the below command from the command terminal.
   ```bash
	  git clone https://github.com/AnandAili/track-activity-service.git
   ```
   >Note: If you want to clone this repository into your Intellij IDEA, please follow this link [Manage Projects hosted on GitHub](https://www.jetbrains.com/help/idea/manage-projects-hosted-on-github.html).
2. ```cd``` into the cloned repository ```track-activity-service```.
   ```bash
      cd track-activity-service/
   ```
3. Execute ```maven``` install phase
   ```bash
      mvn clean install
   ```
   and you should be able to see that the application is built successfully.
   ```txt
		[INFO]
		[INFO] Results:
		[INFO]
		[WARNING] Tests run: 27, Failures: 0, Errors: 0, Skipped: 1
		[INFO]
		[INFO]
		[INFO] --- maven-jar-plugin:3.2.0:jar (default-jar) @ track-activity-service ---
		[INFO] Building jar: /Users/aili/Desktop/assignment/track-activity-service/target/track-activity-service-0.0.1-SNAPSHOT.jar
		[INFO]
		[INFO] --- spring-boot-maven-plugin:2.3.0.RELEASE:repackage (repackage) @ track-activity-service ---
		[INFO] Replacing main artifact with repackaged archive
		[INFO]
		[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ track-activity-service ---
		[INFO] Installing /Users/aili/Desktop/assignment/track-activity-service/target/track-activity-service-0.0.1-SNAPSHOT.jar to /Users/aili/.m2/repository/org/tacx/interview/assignment/track-activity-service/0.0.1-SNAPSHOT/track-activity-service-0.0.1-SNAPSHOT.jar
		[INFO] Installing /Users/aili/Desktop/assignment/track-activity-service/pom.xml to /Users/aili/.m2/repository/org/tacx/interview/assignment/track-activity-service/0.0.1-SNAPSHOT/track-activity-service-0.0.1-SNAPSHOT.pom
		[INFO] ------------------------------------------------------------------------
		[INFO] BUILD SUCCESS
		[INFO] ------------------------------------------------------------------------
		[INFO] Total time:  32.040 s
		[INFO] Finished at: 2021-05-05T02:02:25+02:00
   ```
4. Now you are ready to start the ```track-activity-service``` application in your local machine.
   There are two ways to start the application:
	1. Spring Boot Plugin: Execute below maven command:
	   ```bash
		  mvn spring-boot:run
	   ```
	   You should be able to see the below logs, If the application has started successfully.
	   ```txt
			   $ mvn spring-boot:run
			 [INFO] Scanning for projects...
			 [INFO]
			 [INFO] --------< org.tacx.interview.assignment:track-activity-service >--------
			 [INFO] Building track-activity-service 0.0.1-SNAPSHOT
			 [INFO] --------------------------------[ jar ]---------------------------------
			 [INFO]
			 [INFO] >>> spring-boot-maven-plugin:2.3.0.RELEASE:run (default-cli) > test-compile @ track-activity-service >>>
			 [INFO]
			 [INFO] --- spring-javaformat-maven-plugin:0.0.6:validate (default) @ track-activity-service ---
			 [INFO]
			 [INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ track-activity-service ---
			 [INFO] Using 'UTF-8' encoding to copy filtered resources.
			 [INFO] Copying 2 resources
			 [INFO] Copying 0 resource
			 [INFO]
			 [INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ track-activity-service ---
			 [INFO] Nothing to compile - all classes are up to date
			 [INFO]
			 [INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @ track-activity-service ---
			 [INFO] Using 'UTF-8' encoding to copy filtered resources.
			 [INFO] Copying 2 resources
			 [INFO]
			 [INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ track-activity-service ---
			 [INFO] Nothing to compile - all classes are up to date
			 [INFO]
			 [INFO] <<< spring-boot-maven-plugin:2.3.0.RELEASE:run (default-cli) < test-compile @ track-activity-service <<<
			 [INFO]
			 [INFO]
			 [INFO] --- spring-boot-maven-plugin:2.3.0.RELEASE:run (default-cli) @ track-activity-service ---
			 [INFO] Attaching agents: []

			 .   ____          _            __ _ _
			 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
			 ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
			 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
			 '  |____| .__|_| |_|_| |_\__, | / / / /
			 =========|_|==============|___/=/_/_/_/
			 :: Spring Boot ::        (v2.3.0.RELEASE)

			 2021-05-05 02:14:40.457  INFO 39902 --- [           main] .t.i.a.t.TractActivityServiceApplication : Starting TractActivityServiceApplication on C02WH09QHTD6 with PID 39902 (/Users/aili/Desktop/assignment/track-activity-service/target/classes started by aili in /Users/aili/Desktop/assignment/track-activity-service)
			 2021-05-05 02:14:40.459  INFO 39902 --- [           main] .t.i.a.t.TractActivityServiceApplication : No active profile set, falling back to default profiles: default
			 2021-05-05 02:14:41.291  INFO 39902 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFERRED mode.
			 2021-05-05 02:14:41.338  INFO 39902 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 42ms. Found 1 JPA repository interfaces.
			 2021-05-05 02:14:41.698  INFO 39902 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
			 2021-05-05 02:14:41.706  INFO 39902 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
			 2021-05-05 02:14:41.706  INFO 39902 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.35]
			 2021-05-05 02:14:41.786  INFO 39902 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
			 2021-05-05 02:14:41.786  INFO 39902 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1292 ms
			 2021-05-05 02:14:41.889  INFO 39902 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
			 2021-05-05 02:14:41.964  INFO 39902 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
			 2021-05-05 02:14:41.968  INFO 39902 --- [           main] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:track-activity'
			 2021-05-05 02:14:42.100  INFO 39902 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
			 2021-05-05 02:14:42.128  INFO 39902 --- [         task-1] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
			 2021-05-05 02:14:42.149  INFO 39902 --- [           main] o.t.i.a.t.s.i.FileSystemStorageService   : Creating folder activityfiles...
			 2021-05-05 02:14:42.149  INFO 39902 --- [           main] o.t.i.a.t.s.i.FileSystemStorageService   : Folder activityfiles is already exists
			 2021-05-05 02:14:42.150  INFO 39902 --- [           main] o.t.i.a.t.s.i.FileSystemStorageService   : Create folder activityfiles successfully
			 2021-05-05 02:14:42.156  INFO 39902 --- [         task-1] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.4.15.Final
			 2021-05-05 02:14:42.240  INFO 39902 --- [         task-1] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.0.Final}
			 2021-05-05 02:14:42.309  INFO 39902 --- [         task-1] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
			 2021-05-05 02:14:42.315  WARN 39902 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
			 2021-05-05 02:14:42.694  INFO 39902 --- [         task-1] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
			 2021-05-05 02:14:42.699  INFO 39902 --- [         task-1] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
			 2021-05-05 02:14:42.870  INFO 39902 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 2 endpoint(s) beneath base path '/actuator'
			 2021-05-05 02:14:43.121  INFO 39902 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
			 2021-05-05 02:14:43.122  INFO 39902 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Context refreshed
			 2021-05-05 02:14:43.131  INFO 39902 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Found 1 custom documentation plugin(s)
			 2021-05-05 02:14:43.138  INFO 39902 --- [           main] s.d.s.w.s.ApiListingReferenceScanner     : Scanning for api listing references
			 2021-05-05 02:14:43.235  INFO 39902 --- [           main] DeferredRepositoryInitializationListener : Triggering deferred initialization of Spring Data repositories…
			 2021-05-05 02:14:43.305  INFO 39902 --- [           main] DeferredRepositoryInitializationListener : Spring Data repositories initialized!
			 2021-05-05 02:14:43.329  INFO 39902 --- [           main] .t.i.a.t.TractActivityServiceApplication : Started TractActivityServiceApplication in 3.551 seconds (JVM running for 6.832)
	   ```
	2. After 3rd step, the jar [track-activity-service-0.0.1-SNAPSHOT.jar](target/track-activity-service-0.0.1-SNAPSHOT.jar) is built successfully into **target** folder, so execute the below command to start the application.
	   ```bash
		   java -jar target/track-activity-service-0.0.1-SNAPSHOT.jar
	   ```
	   You should be able to see the below logs, If the application has started successfully.
	   ```txt
				 $ java -jar target/track-activity-service-0.0.1-SNAPSHOT.jar

				 .   ____          _            __ _ _
				 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
				 ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
				 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
				 '  |____| .__|_| |_|_| |_\__, | / / / /
				 =========|_|==============|___/=/_/_/_/
				 :: Spring Boot ::        (v2.3.0.RELEASE)

				 2021-05-05 02:21:39.962  INFO 40102 --- [           main] .t.i.a.t.TractActivityServiceApplication : Starting TractActivityServiceApplication v0.0.1-SNAPSHOT on C02WH09QHTD6 with PID 40102 (/Users/aili/Desktop/assignment/track-activity-service/target/track-activity-service-0.0.1-SNAPSHOT.jar started by aili in /Users/aili/Desktop/assignment/track-activity-service)
				 2021-05-05 02:21:39.964  INFO 40102 --- [           main] .t.i.a.t.TractActivityServiceApplication : No active profile set, falling back to default profiles: default
				 2021-05-05 02:21:41.564  INFO 40102 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFERRED mode.
				 2021-05-05 02:21:41.660  INFO 40102 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 81ms. Found 1 JPA repository interfaces.
				 2021-05-05 02:21:42.578  INFO 40102 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
				 2021-05-05 02:21:42.599  INFO 40102 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
				 2021-05-05 02:21:42.599  INFO 40102 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.35]
				 2021-05-05 02:21:42.739  INFO 40102 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
				 2021-05-05 02:21:42.739  INFO 40102 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 2705 ms
				 2021-05-05 02:21:42.913  INFO 40102 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
				 2021-05-05 02:21:43.268  INFO 40102 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
				 2021-05-05 02:21:43.279  INFO 40102 --- [           main] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:track-activity'
				 2021-05-05 02:21:43.534  INFO 40102 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
				 2021-05-05 02:21:43.596  INFO 40102 --- [           main] o.t.i.a.t.s.i.FileSystemStorageService   : Creating folder activityfiles...
				 2021-05-05 02:21:43.597  INFO 40102 --- [           main] o.t.i.a.t.s.i.FileSystemStorageService   : Folder activityfiles is already exists
				 2021-05-05 02:21:43.597  INFO 40102 --- [           main] o.t.i.a.t.s.i.FileSystemStorageService   : Create folder activityfiles successfully
				 2021-05-05 02:21:43.611  INFO 40102 --- [         task-1] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
				 2021-05-05 02:21:43.729  INFO 40102 --- [         task-1] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 5.4.15.Final
				 2021-05-05 02:21:44.022  INFO 40102 --- [         task-1] o.hibernate.annotations.common.Version   : HCANN000001: Hibernate Commons Annotations {5.1.0.Final}
				 2021-05-05 02:21:44.039  WARN 40102 --- [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
				 2021-05-05 02:21:44.243  INFO 40102 --- [         task-1] org.hibernate.dialect.Dialect            : HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
				 2021-05-05 02:21:45.227  INFO 40102 --- [         task-1] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
				 2021-05-05 02:21:45.239  INFO 40102 --- [         task-1] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
				 2021-05-05 02:21:45.563  INFO 40102 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 2 endpoint(s) beneath base path '/actuator'
				 2021-05-05 02:21:46.020  INFO 40102 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
				 2021-05-05 02:21:46.021  INFO 40102 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Context refreshed
				 2021-05-05 02:21:46.058  INFO 40102 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Found 1 custom documentation plugin(s)
				 2021-05-05 02:21:46.091  INFO 40102 --- [           main] s.d.s.w.s.ApiListingReferenceScanner     : Scanning for api listing references
				 2021-05-05 02:21:46.272  INFO 40102 --- [           main] DeferredRepositoryInitializationListener : Triggering deferred initialization of Spring Data repositories…
				 2021-05-05 02:21:46.448  INFO 40102 --- [           main] DeferredRepositoryInitializationListener : Spring Data repositories initialized!
				 2021-05-05 02:21:46.464  INFO 40102 --- [           main] .t.i.a.t.TractActivityServiceApplication : Started TractActivityServiceApplication in 7.87 seconds (JVM running for 8.615)
	   ```
	   >Note: The application won't start, If port 8080 is not available in your machine. In that case, try to execute below command by passing port number as a vm argument:
	   > ``` java -Dserver.port=<port-number> -jar target/track-activity-service-0.0.1-SNAPSHOT.jar ```
### Usage Guidance:
The above-mentioned APIs (create, get and delete an activity operations) are accessble from browser using swagger-ui. Please hit the below url from the browser, the swagger-ui with all the exposed APIs are listed.
[Swagger UI for Activity Service API](http://localhost:8080/swagger-ui.html)

or hit the below by updating the port number provided in the above steps.
```http://localhost:<port-number>/swagger-ui.html``` if you have changed the port number.

#### Create an Activity
If you expand the **activity-controller** from the opened swagger-ui, then you see a ```HTTP Post``` Operation called **createActivity**.
1. Select the right CSV file. you can download one from here [assignment_activity.csv](assignment_activity.csv).
2. Then click on the button **Try it out**.
3. Response code 200 is shown with Response body **Activity 1 is created**
4. If the response isn't 201, then possibly the CSV file is meeting the below-mentioned requirements.
> Note: Below files aren't accepted:
> 1. Files of different extensions are not accepted. for example: a.txt, *.pdf
> 2. Files having two extensions. for example a.exe.csv
> 3. Files having no data in them.
> 4. If file attachment is missed
5. The uploaded file will be stored under the same project direcy called *activityfiles*.
6. Connect to [H2 Database GUI](http://localhost:8080/h2-console). The below are the details:
	```properties
	spring.datasource.url=jdbc:h2:mem:track-activity
	spring.datasource.driverClassName=org.h2.Driver
	spring.datasource.username=app
	# for production env, fetch password from the secret services while starting the application
	spring.datasource.password=
	```
>Note: this folder name and path can be configured from [application.properties](src/main/resources/application.properties)
If everything goes well, you will see like below:
```txt
	    Request URL
		http://localhost:8080/activities/
	    Request Headers
		{
		"Accept": "*/*"
		}
	    Response Body
		Activity 1 is created
	    Response Code
		200
	    Response Headers
		{
		"connection": "keep-alive",
		"content-length": "0",
		"date": "Wed, 05 May 2021 00:50:59 GMT",
		"keep-alive": "timeout=60"
		}
```

#### Get an Activity by activityId
If you expand ```HTTP GET REQUEST``` called **getActivity**, then follow below steps:
1. Provide activityId received in the response body of the previous call.
2. Then click the button **Try it out!**
3. You should be able to see below HTTP response:
   ```txt
				Request URL
					http://localhost:8080/activities/1
				Request Headers
					{
					"Accept": "application/json"
					}
				Response Body
					{
						"name": "My first ride",
						"type": "cycling",
						"totalDistance": 11990,
						"totalDuration": "7 min:0 sec",
						"averagePower": 165.625,
						"averageCadence": 88.5
					}
				Response Code
					200
   ```
### Get all Activities
If you expand ```HTTP GET REQUEST``` called **getAllActivity**, then follow the below steps:
1. Click the button *Try it out!***
2. You should be able to see the below http response:
   ```text

				Request URL
					http://localhost:8080/activities/
				Request Headers
					{
						"Accept": "*/*"
					}
				Response Body
					[
						{
							"name": "My first ride",
							"type": "cycling",
							"totalDistance": 11990,
							"totalDuration": "7 min:0 sec",
							"averagePower": 165.625,
							"averageCadence": 88.5
						}
					]
				Response Code
					200
   ```
> Note: Please repeat the first operation **create an activity**  to see more activities.

#### Delete an Activity
If you expand ```HTTP GET REQUEST``` called **getAllActivity**, then follow below steps:
1. Provide activityId received in the response body of the create an activity operation.
2. Click the button *Try it out!***
3. You should be able to see the below http response:
   ```txt
				Request URL
					http://localhost:8080/activities/1
				Request Headers
					{
						"Accept": "*/*"
					}
				Response Body
					{
						"name": "My first ride",
						"type": "cycling",
						"totalDistance": 11990,
						"totalDuration": "7 min:0 sec",
						"averagePower": 165.625,
						"averageCadence": 88.5
					}
				Response Code
					200
   ```

#### Confirm the deletion of an activity by hitting **getAllActivities**
You might already observe that the response body is empty:
```txt
	Request URL
		http://localhost:8080/activities/
	Request Headers
		{
		  "Accept": "*/*"
		}
	Response Body
		[]
	Response Code
		200
```
## Improvements:
1. More validations on CSV file content for:
	1. http links
	2. excel formulas

2. Users should be able to get activities by Name as well. it helps if GUI has a search button.
3. Remove HTTP links and excel formulas before storing the file to file system

