vraptor-external-view
=====================

vraptor-external-view allows you to develop system's front-end layer separated of the back-end layer, and still using it on controller results.

##How to install?
```xml
<dependency>
    <groupId>br.com.caelum</groupId>
    <artifactId>vraptor-external-view</artifactId>
    <version>1.0.1</version>
</dependency>
```

###How to use ?
To load views outside of your vraptor based project, you need to follow two steps described bellow:

- Create a configuration file named `vraptor-external.properties` in your resources dir with the following keys:
```properties
default.view.path /path/to/the/view/files
default.view.extension .vm
```

- So, all you have to do is add the `@ExternalView` annotation in the controller's method that you want to return an external view. 
e.g.:
```java
public class HelloController {
  @Inject Result result;

  @Deprecated
  public void HelloController() {}
  
  /*
  * This method will return the default resource: 
  * /webapp/WEB-INF/jsp/hello/hello.jsp
  */
  @Get 
  @Path("/hello/{message}")
  public void hello(String message) {
    result.include("message", message);
  }
 
  
  /*
  * This method will return an external resource in the path
  * /path/to/the/view/files/externalHello.jsp
  */
  @Get
  @Path("/external/hello/{message}")
  @ExternalView
  public void externalHello(String message) {
    result.include("message", message);
  }
}
```
You can also set the view's ContentType. Default content-type is text/html.
e.g.:
```java
@ExternalView(contentType = "text/html")
public void clientForm() {}