package com.coolcode.jittranslate.dbentities;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import com.squareup.moshi.Json;

public interface TranslationApi {

    class TranslationResponse {

        @Json(name = "code")
        private Integer code;
        @Json(name = "lang")
        private String lang;
        @Json(name = "text")
        private List<String> text = null;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public List<String> getText() {
            return text;
        }

        public void setText(List<String> text) {
            this.text = text;
        }

    }

    class TranslationText {
        private String originalText;
        private String originalEncodedText;
        private String translatedText;
        public TranslationText(String text) {
            originalText = text;
            try {
                originalEncodedText = URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
            } catch (UnsupportedEncodingException e) {
                Log.d("encode_string", e.getMessage());
                originalEncodedText = text;
            }
        }

        public String getOriginalEncodedText() {
            return originalEncodedText;
        }

        public void setTranslatedText(String translatedText) {
            this.translatedText = translatedText;
        }

        public String getTranslatedText() {
            return translatedText;
        }

        public String getOriginalText() {
            return originalText;
        }
    }

    @POST("/api/v1.5/tr.json/translate")
    Call<TranslationApi.TranslationResponse> getTranslation(@Query("lang") String language, @Query("key") String apiKey, @Query("text") String text);

}