package core;

import core.annotations.AttributesConfig;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ismail Isleem on 11/4/2018
 */
public class Core {

    public static String baseUrl = "";
    AttributesConfig attributesConfig = new AttributesConfig();

    public static Headers buildRequestHeaders(Header... header) {
        List<Header> headerList = new ArrayList<>();
        for (Header headerString : header) {
            headerList.add(headerString);
        }
        return new Headers(headerList);
    }

    public static List<RequestSpecification> buildRequestParams(RequestSpecification... param) {
        List<RequestSpecification> paramList = new ArrayList<>();
        for (RequestSpecification paramString : param) {
            paramList.add(paramString);
        }
        return paramList;
    }

    public static String buildRequestURL(String endPoint) {
        return baseUrl + endPoint;
    }

    public static String buildRequestBody(String replacementValues, String requestBody) {
        String requestBodyString = requestBody;
        for (String replacementValue : replacementValues.split(",")) {
            requestBodyString = requestBodyString.replace(replacementValue, "");
        }
        return null;
    }

    // This method returns a value as string from response object
    public static String getResponseValue(Response response, String key) {
        try {
            if (key.isEmpty())
                return response.getBody().jsonPath().get().toString();
            else
                return response.getBody().jsonPath().get(key).toString();
        } catch (Throwable throwable) {
            return "error: " + throwable.getMessage();
        }
    }

    // This method returns full response body
    public static String getResponseValue(Response response) {
        return getResponseValue(response, "");
    }

    // This method returns a value from HashMap
    public static String getStringValue(HashMap hashMap, String key) {
        try {
            return hashMap.containsKey(key) ? hashMap.get(key).toString() : "";
        } catch (Throwable throwable) {
            return "error: " + throwable.getMessage();
        }
    }

    // This method removes Pipelines from ArrayList elements
    public static ArrayList<String> replaceFromElements(ArrayList<?> arrayList, String delimiter) {
        return arrayList.stream().map(element -> String.valueOf(element).replace(delimiter, ""))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // This method removes not allowed characters for resources filename
    public static String buildResourcesFileName(String fileName) {
        return fileName.toLowerCase().trim().replace(" ", "_")
                .replace("-", "_").replace(".", "_")
                .replace("&", "_").replaceAll("_{2,}", "_");
    }

    // This method retrieves response status code
    public static String getResponseCode(Response response) {
        if (response != null)
            return String.valueOf(response.getStatusCode());
        else
            return "Response is Null";
    }

    // This method retrieves response time in milliseconds
    public static String getResponseTime(Response response) {
        if (response != null)
            return String.valueOf(response.getTime());
        else
            return "Response is Null";
    }
}
