//package com.nrap.platform.api.swagger;
//
//import com.google.common.collect.Maps;
//import io.swagger.models.Path;
//import io.swagger.models.Swagger;
//import io.swagger.parser.SwaggerParser;
//import io.swagger.util.Json;
//
//import java.util.Map;
//
///**
// * Created by stzhang on 2017/3/2.
// */
//public class SwaggerParserTest {
//    public static void main(String args[]){
//        SwaggerParser parser = new SwaggerParser();
//        Swagger swagger = parser.read("http://localhost:8080/v2/api-docs");
//        Map<String, Path> swaggerPaths = swagger.getPaths();
//        swagger.setPaths(Maps.newHashMap());
//        String swaggerString = Json.pretty(swagger);
//        System.out.println(swaggerString);
//        swaggerString = Json.pretty(swaggerPaths);
//        System.out.println(swaggerString);
//        Swagger s = parser.parse(swaggerString);
//        s.getPaths();
//    }
//}
