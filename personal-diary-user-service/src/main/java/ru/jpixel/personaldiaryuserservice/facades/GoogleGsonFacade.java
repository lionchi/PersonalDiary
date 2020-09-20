package ru.jpixel.personaldiaryuserservice.facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import ru.jpixel.personaldiaryuserservice.adapters.InterfaceAdapter;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
public class GoogleGsonFacade<T> {

    private final Class<T> tClass;

    public String toJson(T object) {
        return createGson().toJson(object, tClass);
    }

    public T fromJson(byte[] data, String charset) throws UnsupportedEncodingException {
        return createGson().fromJson(new String(data, charset), tClass);
    }

    private Gson createGson() {
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(tClass, new InterfaceAdapter<T>());
        return builder.create();
    }
}
