package com.kitaplik.libraryservice.client;

import com.kitaplik.libraryservice.exception.BookNotFoundException;
import com.kitaplik.libraryservice.exception.ExceptionMessage;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class RetreiveMessageErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage message = null;
        /*
        InputStream için kullanım kolaylığı sağlayan bir kütüphane bulunmaktadır.
        Apache --> Commons-IO
        Bu kütüphane karmaşık String convert işlemlerini tek bir static metot ile yapabilmektedir.
         */
        try (InputStream body = response.body().asInputStream()){
            message = new ExceptionMessage((String) response.headers().get("date").toArray()[0],
                    response.status(),
                    HttpStatus.resolve(response.status()).getReasonPhrase(),
                    IOUtils.toString(body, StandardCharsets.UTF_8), //Commons-IO
                    response.request().url());
        }catch (IOException exception){
            return new Exception(exception.getMessage());
        }
        switch (response.status()){
            case 404:
                throw new BookNotFoundException(message);
            default:
                //handle ettiğimiz hatalar dışındakileri Default sınıfa bırakmış oluyoruz
                return errorDecoder.decode(methodKey, response);
        }
    }
}
