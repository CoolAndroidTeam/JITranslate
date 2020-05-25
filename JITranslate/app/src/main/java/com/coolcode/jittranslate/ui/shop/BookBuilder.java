package com.coolcode.jittranslate.ui.shop;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

class BookBuilder {
    static List<BuyBook> jsonParse(JsonObject root) {

        List<BuyBook> bookData = new ArrayList<>();

        JsonObject item;
        JsonObject volumeInfo;
        JsonObject imageLinks;
        JsonObject saleInfo;
        JsonObject listPrice;

        JsonArray itemArray;
        JsonArray authorsArray;
        JsonArray categoryArray;

        String title;
        String authors;
        String category;
        String smallThumbnail;
        String avgRating;
        String price;
        String description;
        String buyLink;
        String previewLink;

        itemArray = root.getAsJsonArray("items");
        for (int i = 0; i < itemArray.size(); i++) {

            JsonElement element_item = itemArray.get(i);
            item = element_item.getAsJsonObject();

            volumeInfo = item.getAsJsonObject("volumeInfo");
            JsonElement element_title = volumeInfo.get("title");
            title = element_title.getAsString();


            authors = "";

            if (volumeInfo.has("authors") && !volumeInfo.get("authors").isJsonNull()) {
                authorsArray = volumeInfo.getAsJsonArray("authors");

                if (authorsArray.size() > 1) {
                    for (int j = 0; j < authorsArray.size(); j++) {

                        JsonElement element_author = authorsArray.get(j);
                        authors += element_author.getAsString() + "\n";
                    }
                } else {
                    JsonElement element_author = authorsArray.get(0);
                    authors += element_author.getAsString();
                }
            } else {
                authors = "Unknown";
            }

            category = "";

            if (volumeInfo.has("categories") && !volumeInfo.get("categories").isJsonNull()) {
                categoryArray = volumeInfo.getAsJsonArray("categories");

                if (categoryArray.size() > 1) {
                    for (int j = 0; j < categoryArray.size(); j++) {
                        JsonElement element_category = categoryArray.get(j);
                        category += element_category.getAsString() + "|";


                    }
                } else {
                    JsonElement element_category = categoryArray.get(0);
                    category += element_category.getAsString();
                }
            } else {
                category = "No Category";
            }

            if (volumeInfo.has("averageRating") && !volumeInfo.get("averageRating").isJsonNull()) {
                JsonElement element_rating = volumeInfo.get("averageRating");
                avgRating = element_rating.getAsString();

            } else {
                avgRating = "0";
            }

            if (volumeInfo.has("imageLinks") && !volumeInfo.get("imageLinks").isJsonNull()) {
                imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                JsonElement element_smallThumbnail = imageLinks.get("smallThumbnail");
                smallThumbnail = element_smallThumbnail.getAsString();
            } else {
                smallThumbnail = "https://www.sylvansport.com/wp/wp-content/uploads/2018/11/image-placeholder-1200x800.jpg";
            }

            if (volumeInfo.has("description") && !volumeInfo.get("description").isJsonNull()) {
                JsonElement element_description = volumeInfo.get("description");
                description = element_description.getAsString();

            } else {
                description = "No description for this Book!";
            }

            if (volumeInfo.has("previewLink") && !volumeInfo.get("previewLink").isJsonNull()) {
                JsonElement element_preview = volumeInfo.get("previewLink");
                previewLink = element_preview.getAsString();

            } else {
                previewLink = " ";
            }

            saleInfo = item.getAsJsonObject("saleInfo");

            price = " ";
            buyLink = " ";
            if (saleInfo.has("saleability") && !saleInfo.get("saleability").isJsonNull()) {
                JsonElement element_saleability = saleInfo.get("saleability");
                if (element_saleability.getAsString().equals("FOR_SALE")) {
                    if (saleInfo.has("listPrice")) {
                        listPrice = saleInfo.getAsJsonObject("listPrice");
                        JsonElement element_price = listPrice.get("amount");
                        price = Double.toString(element_price.getAsDouble());
                    }
                    if (saleInfo.has("currencyCode")) {
                        JsonElement element_currencyCode = saleInfo.get("currencyCode");
                        price += element_currencyCode.getAsString();
                    }
                    if(saleInfo.has("buyLink")) {
                        JsonElement element_buyLink = saleInfo.get("buyLink");
                        buyLink = element_buyLink.getAsString();
                    }

                } else {

                    price = "Free";
                    buyLink = " ";
                }

            }

            bookData.add(new BuyBook(smallThumbnail, title, category, avgRating, authors, price, description, buyLink, previewLink));

        }
        return bookData;
    }
}
