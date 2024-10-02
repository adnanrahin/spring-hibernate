### Build The Project
1. This is Multi-Module Maven Project, so it's requires to install all the dependent modules before spun up the restful webservice. 
2. run `mvn clean install -DskipTests` this will trigger the build without the integration tests.
3. To run the build with integration tests we need build the docker postgres database.
4. `cd docker-db` and do `docker-compose up -d`
5. Then run `mvn clean install`

### Deploy The Project
1. To Deploy the project I have used `wildfly-14.0.1.Final`
2. Please Download the wildfly from here [wildfly](https://download.jboss.org/wildfly/14.0.1.Final/wildfly-14.0.1.Final.tar.gz)
3. Start the wildfly server and Deploy the Application from the UI.
