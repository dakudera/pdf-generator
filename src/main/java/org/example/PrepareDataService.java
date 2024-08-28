package org.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PrepareDataService {

    private final String imagePath;

    public PrepareDataService(String imagePath) {
        this.imagePath = imagePath;
    }

    public Set<Map.Entry<String, String>> prepare() throws IOException {
        String base64Image = ImageUtil.encodeImageToBase64(imagePath);
        String imgTag = "data:image/png;base64," + base64Image;
        // Perform replacements
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("statementId", "20240200001");
        valueMap.put("senderName", "Harish Jay Raj");
        valueMap.put("senderCardName", "DEBET CARD");
        valueMap.put("senderCardNumber", "4242 4242 4242 4242");

        valueMap.put("recipientName", "Harish Jaz Rachel");
        valueMap.put("recipientCardName", "Debet Card");
        valueMap.put("recipientCardNumber", "4242 4242 4242 4242");

        valueMap.put("sumPayment", "20");
        valueMap.put("fee", "1");
        valueMap.put("currency", "USD");
        valueMap.put("image", imgTag);
        return valueMap.entrySet();
    }

}
