-injars ..\ServerLauncher.jar
-outjars ..\ServerLauncher2.jar
-libraryjars C:\Program Files\Java\jre1.8.0_25\lib\rt.jar
-printmapping proguard.map
-dontshrink
-dontwarn
-keepattributes Signature
-keep class org.apache.commons.codec.binary.** { *; }
-keep interface org.apache.commons.codec.binary.** { *; }
-keep enum org.apache.commons.codec.binary.** { *; }
-keep class org.slf4j.** { *; }
-keep interface org.slf4j.** { *; }
-keep enum org.slf4j.** { *; }
-keep class com.sun.syndication.io.** { *; }
-keep interface com.sun.syndication.io.** { *; }
-keep enum com.sun.syndication.io.** { *; }
-keep class com.sun.syndication.feed.synd.** { *; }
-keep interface com.sun.syndication.feed.synd.** { *; }
-keep enum com.sun.syndication.feed.synd.** { *; }
-keep class com.darkorbit.objects.** { *; }
-keep class com.darkorbit.main.Launcher {
	public static void main(java.lang.String[]);
}