package com.chatuz.chatuz;
public class CastHelper {
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        try {
            return (T) obj;
        } catch (ClassCastException e) {
            return null;
        }
    }
}
