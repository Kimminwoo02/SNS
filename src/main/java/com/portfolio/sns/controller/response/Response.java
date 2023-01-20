package com.portfolio.sns.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private String resultCode;
    private T result;

    public static Response<Void> error(String errorCode){
        return new Response<>(errorCode,null);
    }
    public static Response<Void> success(){
        return new Response<Void>("success",null);
    }

    public static <T> Response<T> success(T result){
        return new Response<>("success",result);
    }

    public String toStream(){
        if(result==null){
            return "{" +
                    "\"resultCode\":" +"\""+resultCode + "\"," +
                    "\"result\":"+ null + "}";
        }
        {
            return "{" +
                    "\"resultCode\":" +"\""+resultCode + "\"," +
                    "\"result\":" + "\"" + result + "\"" + "}";
        }
    }

}
