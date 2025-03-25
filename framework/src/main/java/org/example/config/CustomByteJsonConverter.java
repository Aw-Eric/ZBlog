package org.example.config;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

public class CustomByteJsonConverter implements HttpMessageConverter<Object> {

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }


    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return MediaType.APPLICATION_JSON.equals(mediaType) && clazz == byte[].class;
    }


    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.APPLICATION_JSON);
    }


    @Override
    public Object read(Class<?> clazz, org.springframework.http.HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }


    @Override
    public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (o instanceof byte[]) {
            // 直接将 byte[] 发送给客户端
            outputMessage.getBody().write((byte[]) o);
        } else {
            // 其他类型的数据由 Jackson 转换器处理
            throw new IOException("Unsupported type: " + o.getClass().getName());
        }
    }

}
