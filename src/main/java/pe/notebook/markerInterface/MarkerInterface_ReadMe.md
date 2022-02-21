* Reference Site : https://codeahoy.com/java/marker-interface/

## 한줄 결론
Marker Interface는 폴리그램을 위한 동작으로 Annotation 방식과는 목적과 사용이 다르다.


A marker interface is basicaly empty, containing no methods or constants. It is used to merely indicate (at runtime) that the class implementing such an interface has some special property or attributes. These are sometimes known as “tag” or “placeholder” interfaces. Examples: Serializable and Cloneable. Implementing marker interfaces don’t make the object do more, rather it just says what it can do.
For example, let’s consider Cloneable. It’s a marker interface that contains no methods.

```java
public interface Cloneable
{
// Tagging interface only.
}
```

This interface is used in Object.clone() method, which requires that implementing classes are Cloneable otherwise an exception is thrown at runtime. The method is implemented like so in JDKs.

```java
protected Object clone() throws CloneNotSupportedException {
if (this instanceof Cloneable)
return VMObject.clone((Cloneable) this);
throw new CloneNotSupportedException("Object not cloneable");
}
```

Similarly, ObjectOutputStream.writeObject(Object obj) method checks if the object (passed as argument) implements the Serializable (marker) interface. If the answer is no, a NotSerializableException is thrown at runtime. If instead, the argument to writeObject(...) was of type Serializable, the check could have been performed at compile-time. But as it stands, any attempt to serialize an unserializable object will not fail until runtime.

## Annotations

Annotations were introduced later in Java as a way to reduce boiler plate code. They were considered an improvement over marker interfaces, developers calling them “Marker Interfaces 2.0” or “Marker Interfaces++”. However’ as we’ll explore later, annotations don’t make marker interfaces obsolete and both have their advantages and disadvantages.
We are all familiar with annotation and seen them everywhere: @Override, @FunctionalInterface, @Deprecated, etc. Annotation have many use cases. They can be use for generating informational messages e.g. @Deprecated, signaling compiler to do something i.e. ignore warnings with @SuppressWarnings, to detecting if any special operation needs to be taken. In Spring Boot, we mark classes with @Component annotation to tell Spring’s dependency injection that the class (bean) instance must be created and managed by Spring.
This is how the @Component annotation is defined:

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface Component {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any (or empty String otherwise)
	 */
	String value() default "";
}
```
This is how the Spring framework uses the @Component annotation to detect if an object is marked to be a component.

```java

protected String getComponentName(Object component) {
String name = null;
if (component.getClass().isAnnotationPresent(Component.class)) {
name = component.getClass().getAnnotation(Component.class).value();

    } 

    // truncated
         
    return name;
}
```

## Marker Interfaces vs Annotations
Marker interfaces allow us to take advantage of polymorphism where as annotations do not. However, in practice, the polymorphism can be used as a counter argument as well. For example, suppose you are building a system to handle various types of documents. You create a parent class called Document and many subclasses such as Agenda, Report, Proposal, Guide etc.

```java
// Parent class for all documents
public class Document {
}
```
In the main processing logic, you want to provide developers a way to tag Document subtypes that can be cached. To do this, you introduce a @Cacheable annotation.

```java
@Target(value=TYPE)
@Retention(value=RUNTIME)
public @interface Cacheable {
}
```
Then in your processing logic, you detect if the document can be cached and cache it if so:

```java
//해당 객체가 Document(Macker Interface class type 이고, @Cache annotation이 있으면 캐싱한다.
static void processDocument(Document doc) {
if (doc.getClass().isAnnotationPresent(Cacheable.class)) {
    cache.add(doc);
}

    // process the document

}
```
Now let’s say we want to cache the Guide subtype of Document using our newly built logic. We can simply mark it as @Cacheable

```java
@Cacheable
public class Guide extends Document {
}
```
What happens if you introduce further inheritance and create subclasses of Guide such as UserGuide or TechnicalGuide e.g.

public class TechnicalGuide extends Guide {
}
In this case, the annotation does not apply to the subtype i.e. the TechnicalGuide objects won’t be cached, even though the annotation is applied to its parent type i.e. the Guide. If we used marker interface instead of annotations (using instanceof to detect cacheable) it would have worked. This is not really an advantage or drawback of one over the other, rather it depends on your use case and the example illustrates this use case.

