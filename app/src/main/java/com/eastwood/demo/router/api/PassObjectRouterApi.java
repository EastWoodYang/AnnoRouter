package com.eastwood.demo.router.api;

import com.eastwood.common.router.annotation.Activity;
import com.eastwood.common.router.annotation.Param;
import com.eastwood.common.router.annotation.Path;
import com.eastwood.common.router.annotation.RouterHost;
import com.eastwood.common.router.annotation.RouterScheme;
import com.eastwood.common.router.annotation.Strict;
import com.eastwood.demo.router.RouterAActivity;
import com.eastwood.demo.router.SerializableObject;

import java.util.List;
import java.util.Map;

@RouterScheme("winA")
@RouterHost("pass.winwin.com")
public interface PassObjectRouterApi {

    @Strict
    @Path("path")
    @Activity(RouterAActivity.class)
    void method(@Param("param1") String param1, @Param("param2") int[] param2);

    @Path("path")
    @Activity(RouterAActivity.class)
    void method(@Param("serializableObject") SerializableObject serializableObject);


    @Path("mix")
    @Activity(RouterAActivity.class)
    void startActivity(
            @Param("int") int intValue,
            @Param("int") Integer intValueObject,
            @Param("intArray") int[] intValueArray,
            @Param("intArray") Integer[] intValueArrayObject);


    @Path("basedVariable")
    @Activity(RouterAActivity.class)
    void startActivity(@Param("byte") byte byteValue,
                       @Param("int") int intValue,
                       @Param("short") short shortValue,
                       @Param("long") long longValue,
                       @Param("float") float floatValue,
                       @Param("double") double doubleValue,
                       @Param("boolean") boolean booleanValue,
                       @Param("char") char charValue);

    @Path("basedVariableObject")
    @Activity(RouterAActivity.class)
    void startActivity(@Param("byte") Byte byteValue,
                       @Param("int") Integer intValue,
                       @Param("short") Short shortValue,
                       @Param("long") Long longValue,
                       @Param("float") Float floatValue,
                       @Param("double") Double doubleValue,
                       @Param("boolean") Boolean booleanValue,
                       @Param("char") Character charValue);

    @Path("basedVariableArray")
    @Activity(RouterAActivity.class)
    void startActivity(@Param("byte") byte[] byteValue,
                       @Param("int") int[] intValue,
                       @Param("short") short[] shortValue,
                       @Param("long") long[] longValue,
                       @Param("float") float[] floatValue,
                       @Param("double") double[] doubleValue,
                       @Param("boolean") boolean[] booleanValue,
                       @Param("char") char[] charValue);


    @Path("basedVariableObjectArray")
    @Activity(RouterAActivity.class)
    void startActivity(@Param("byte") Byte[] byteValue,
                       @Param("int") Integer[] intValue,
                       @Param("short") Short[] shortValue,
                       @Param("long") Long[] longValue,
                       @Param("float") Float[] floatValue,
                       @Param("double") Double[] doubleValue,
                       @Param("boolean") Boolean[] booleanValue,
                       @Param("char") Character[] charValue);

    @Path("stringVariable")
    @Activity(RouterAActivity.class)
    void startActivity(@Param("String") String stringValue);

    @Path("stringVariableArray")
    @Activity(RouterAActivity.class)
    void startActivity(@Param("StringArray") String[] stringValue);

    @Path("serializableObjectVariable")
    @Activity(RouterAActivity.class)
    void startActivity(@Param("serializableObject") SerializableObject serializableObject);

    @Path("listSerializableObjectVariable")
    @Activity(RouterAActivity.class)
    void startActivity(@Param("serializableObjectList") List<SerializableObject> serializableObjectList);

    @Path("mapSerializableObjectVariable")
    @Activity(RouterAActivity.class)
    void startActivity(@Param("serializableObjectMap") Map<String, SerializableObject> serializableObjectMap);

}