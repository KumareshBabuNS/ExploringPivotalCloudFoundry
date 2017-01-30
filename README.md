# Exploring Pivotal Cloud Foundry

Links: 

  - https://docs.cloudfoundry.org/



1. Installation guide

    - https://docs.pivotal.io/pivotalcf/1-9/installing/
    
    
# Applications

## Debug

 - [Debug app in PCF](https://discuss.pivotal.io/hc/en-us/articles/221317307-How-to-remotely-debug-Java-applications-on-PCF-)

1. Update _manifest.yml_

	env:
	 JBP_CONFIG_DEBUG: '{enabled: true}'


2. Create SSH tunel 
	
	cf ssh -N -T -L 8000:localhost:8000 spring-music

3. Remote debug to _localhost_ at _8000_


## Application Manifest

  - https://docs.cloudfoundry.org/devguide/deploy-apps/manifest.html
    
    
    
# Useful links

  - https://docs.cloudfoundry.org/buildpacks/java/java-tips.html#servlet-gradle/ 
  - https://docs.cloudfoundry.org/buildpacks/java/spring-service-bindings.html
  - [Service Registry](http://docs.pivotal.io/spring-cloud-services/1-3/service-registry/resources.html)
  - [Microservice Registration and Discovery with Spring Cloud and Netflix's Eureka](https://spring.io/blog/2015/01/20/microservice-registration-and-discovery-with-spring-cloud-and-netflix-s-eureka)  
  - http://docs.pivotal.io/pivotalcf/1-9/devguide/services/user-provided.html