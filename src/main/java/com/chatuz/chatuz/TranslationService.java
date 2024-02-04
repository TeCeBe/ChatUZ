package com.chatuz.chatuz;
import java.util.HashMap;
import java.util.Map;

public class TranslationService {
    private static final Map<String, Map<String, String>> translations = new HashMap<>();

    static {
        // Tłumaczenia dla języka polskiego
        Map<String, String> plTranslations = new HashMap<>();
        plTranslations.put("login", "Zaloguj");
        plTranslations.put("username", "Nazwa użytkownika");
        plTranslations.put("password", "Hasło");
        plTranslations.put("loginButton", "Zaloguj");
        plTranslations.put("invalid_login", "Zły login");
        plTranslations.put("reset_link_sent", "Link do resetowania hasła na mailu");
        plTranslations.put("create_account", "Stwórz konto");
        plTranslations.put("forgot_password", "Nie pamiętasz hasła? Odzyskaj je");

        // Tłumaczenia dla języka angielskiego
        Map<String, String> enTranslations = new HashMap<>();
        enTranslations.put("login", "Login");
        enTranslations.put("username", "Username");
        enTranslations.put("password", "Password");
        enTranslations.put("loginButton", "Login");
        enTranslations.put("invalid_login", "Invalid login");
        enTranslations.put("reset_link_sent", "Password reset link sent to email");
        enTranslations.put("create_account", "Create Account");
        enTranslations.put("forgot_password", "Forgot Password? Recover it");

        translations.put("pl", plTranslations);
        translations.put("en", enTranslations);
    }

    public static Map<String, String> getTranslations(String language) {
        return translations.get(language);
    }
}