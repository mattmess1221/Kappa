###Kappa - Core
This application is a tool for developers to ensure that their
 annotations are used correctly. It is an annotation processor
 run automatically by the javac. There is support for Forge's
 annotations, which can help new modders who aren't familiar
 with the annotation rules. It works with both 1.7.10 and 1.8
 versions.

###Using
Using this with javac is as easy as adding the jar to your
 classpath.

####Universal
You can use a plugin I made to automatically enable annotation
 processing in Eclipse and IntelliJ and add any annotation
 processors on the classpath. Just add this to your
 buildscript and run your ide's gradle task.
```
plugins {
	id 'mnm.gradle.ap-ide' version '1.0.2'
}
```

####Eclipse
To allow Eclipse to use this, you will need to enable annotation
 processing in the compiler settings. See the guide
 [here](http://help.eclipse.org/luna/index.jsp?topic=%2Forg.eclipse.jdt.doc.isv%2Fguide%2Fjdt_apt_getting_started.htm)
 for more details.

####IntelliJ
IntelliJ will also need annotation processing enabled. See the
 guide [here](https://www.jetbrains.com/idea/help/configuring-annotation-processing.html)
 for details.