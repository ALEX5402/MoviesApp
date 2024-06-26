##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.application.moviesapp.domain.model.** { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

##---------------End: proguard configuration for Gson  ----------


# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response
 -keep class com.application.moviesapp.data** {
     *;
 }


 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

 -dontwarn javax.annotation.Nullable
 -dontwarn javax.annotation.ParametersAreNonnullByDefault

 -keep class * extends com.google.protobuf.GeneratedMessageLite { *; }

 # Firebase Authentication
 -keep class com.google.firebase.auth.** { *; }
 -dontwarn com.google.firebase.**


#--------------------Chaquopy----------------------------

# Preserve Chaquopy classes and methods
-keep class com.chaquo.python.** { *; }

# Preserve PyTube classes and methods
-keep class com.github.oncename.pytube.** { *; }

# Keep annotations
-keepattributes *Annotation*

# Keep Python classes and methods
-keep class org.python.** { *; }

# Preserve Chaquopy build script API
-keep class com.chaquo.python.build.** { *; }

# Preserve your Python modules
-keep class com.application.moviesapp.data.python.** { *; }

# Avoid stripping out Python assets
-keep class com.application.moviesapp.MoviesApplication {
    *;
}

# Preserve JNI-related code
-keepclasseswithmembers class * {
    native <methods>;
}

# Preserve any classes used for reflection
-keepclassmembers class * {
    *;
}

# Preserve all enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
