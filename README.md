sample-app
==========
This is one of three Git repositories, which work together to demonstrate using [Consul](https://www.consul.io) and 
[Vault](https://www.vaultproject.io) for configuration management:

* https://github.com/steve-perkins/config-properties - Contains:
  * Properties files for every environment (e.g. "dev", "staging", "production") in a hypothetical enterprise, and 
    every application within each environment.
  * A processor script, run by Gradle, which syncs all of the property information with Consul every time the Git 
    repository is updated.
* https://github.com/steve-perkins/config-client-lib - A client library which loads the appropriate config properties 
  for a given environment and application from Consul and Vault.
* https://github.com/steve-perkins/config-sample-app - A sample web application which retrieves its config properties 
  using the client library, and displays them in the browser.
  
This demo shows the use case of having two types of config properties:

1. **non-secret** values, which can and should be maintainable by developer teams (e.g. JDBC URL's).
2. **secret** values, which should only be viewable or maintainable by people with specialized access (e.g. 
   usernames and passwords)
   
The non-secret values are stored as-is in the `config-properties` repo, and loaded directly into Consul.  For *secret* 
values, Git (and Consul) store a Vault path for that property.  When the `config-client-lib` library encounters a 
secret, it loads the "true" value from this Vault path... and applications such as `config-sample-app` are none the 
wiser.

Setup
=====
1. Perform all of the steps outlined in the [config-properties](https://github.com/steve-perkins/config-properties) 
   and [config-client-lib](https://github.com/steve-perkins/config-client-lib) project README's.
2. This repo is a Gradle-based project, containing a [Spring Boot](https://projects.spring.io/spring-boot/)-based 
   web app.  The Gradle script includes a `bootRun` task for conveniently launching the Spring Boot app.  When 
   calling this task, you'll need to pass JVM system properties needed by the client lib to reach Consul and Vault:
   
```
gradlew bootRun -DENVIRONMENT=dev -DAPPLICATION=sampleapp -DCONSUL_HOST=127.0.0.1 -DVAULT_URL=http://127.0.0.1:8200 -DVAULT_USERNAME=vault_user -DVAULT_PASSWORD=vault_pass
```

3. You can access the web application in your browser at: [http://localhost:8080](http://localhost:8080).

4. Make some changes to the properties file in your `config-properties` project (it's not necessary to commit the 
   changes in Git).  Re-run the processor to sync the changes into Consul.
   
5. Hit the sample app's "refresh" endpoint ([http://localhost:8080/refresh](http://localhost:8080/refresh)) and confirm 
   that the changes you made are reflected in real time without a restart.
   