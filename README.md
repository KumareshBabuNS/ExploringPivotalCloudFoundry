# Exploring Pivotal Cloud Foundry 

  - [Pivotal Cloud Foundry](https://pivotal.io/platform) 
  - [Pivotal Cloud Foundry Documentation](https://docs.pivotal.io/pivotalcf/1-9/installing/pcf-docs.html) 
  - [Pivotal Platform](https://pivotal.io/platform)
  - [Pivotal Network](https://network.pivotal.io)
  - [Cloud Foundry](https://docs.cloudfoundry.org)



1. Installation guide

  - https://docs.pivotal.io/pivotalcf/1-9/installing/
    
2. Local development UI

  - https://console.local.pcfdev.io/
    
    Login credentials: user/pass
    
## Applications

### Debug

 - [Debug app in PCF](https://discuss.pivotal.io/hc/en-us/articles/221317307-How-to-remotely-debug-Java-applications-on-PCF-)

1. Update _manifest.yml_

	env:
	 JBP_CONFIG_DEBUG: '{enabled: true}'


2. Create SSH tunel 
	
	cf ssh -N -T -L 8000:localhost:8000 spring-music

3. Remote debug to _localhost_ at _8000_


### Push app to PCF

    cf push --hostname <app-name>
    

## Service Gateway

  - https://content.pivotal.io/blog/creating-a-service-gateway-in-cloud-foundry-part-1
  - https://content.pivotal.io/blog/creating-a-service-gateway-in-cloud-foundry-part-2

## Route service
    
  - http://docs.pivotal.io/pivotalcf/1-9/services/route-services.html
  - https://docs.pivotal.io/pivotalcf/1-7/devguide/services/route-binding.html

    
## Database service

> https://docs.pivotal.io/pcf-dev/dev-services.html

Lookup marketplace

    cf marketplace

Create service instance

    cf create-service p-mysql 512mb portal_mysql

## Service Registry
 
  https://network.pivotal.io/products/p-spring-cloud-services

## Service Brokers 

https://docs.cloudfoundry.org/services/api.html
[Managing Service Brokers](https://docs.cloudfoundry.org/services/managing-service-brokers.html)


> The service broker is the component of the service that implements the Service Broker API, 
  for which a platform’s marketplace is a client. Service brokers are responsible for advertising 
  a catalog of service offerings and service plans to the marketplace, and acting on requests 
  from the marketplace for provisioning, binding, unbinding, and deprovisioning.

## Useful links

  - [Manifest.yml](https://docs.cloudfoundry.org/devguide/deploy-apps/manifest.html)
  - https://docs.cloudfoundry.org/buildpacks/java/java-tips.html#servlet-gradle/ 
  - https://docs.cloudfoundry.org/buildpacks/java/spring-service-bindings.html
  - [Service Registry](http://docs.pivotal.io/spring-cloud-services/1-3/service-registry/resources.html)
  - https://content.pivotal.io/blog/now-available-spring-cloud-services-for-pivotal-cloud-foundry
  - http://docs.pivotal.io/spring-cloud-services/1-3/service-registry/writing-client-applications.html
  - [Microservice Registration and Discovery with Spring Cloud and Netflix's Eureka](https://spring.io/blog/2015/01/20/microservice-registration-and-discovery-with-spring-cloud-and-netflix-s-eureka)  
  - http://docs.pivotal.io/pivotalcf/1-9/devguide/services/user-provided.html