package Travel_planApi.PlanApi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.model:gemini-2.5-flash}")
    private String model;

    private String geminiUrl() {
        return String.format(
                "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent?key=%s",
                model, apiKey
        );
    }

    /**
     * Generate text from Gemini. Returns either the generated text or a helpful error string.
     */
    public String generateText(String prompt) {
        if (prompt == null || prompt.isBlank()) {
            return "Error: prompt is empty.";
        }

        if (apiKey == null || apiKey.isBlank() || "YOUR_GEMINI_API_KEY_HERE".equals(apiKey)) {
            return "Error: Gemini API key not configured. Set gemini.api.key in application.properties or environment variable GEMINI_API_KEY.";
        }

        try {
            String url = geminiUrl();

            // Build request body according to generateContent shape
            ObjectNode body = objectMapper.createObjectNode();
            ArrayNode contents = objectMapper.createArrayNode();

            ObjectNode contentItem = objectMapper.createObjectNode();
            ArrayNode parts = objectMapper.createArrayNode();
            ObjectNode p = objectMapper.createObjectNode();
            p.put("text", prompt);
            parts.add(p);

            contentItem.set("parts", parts);
            contentItem.put("role", "user");
            contents.add(contentItem);
            body.set("contents", contents);

            // NOTE: Removed invalid fields (temperature, maxOutputTokens) to avoid 400 errors.

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(body), headers);
            ResponseEntity<String> resp = restTemplate.postForEntity(url, request, String.class);

            if (!resp.getStatusCode().is2xxSuccessful()) {
                String errBody = resp.getBody() == null ? "" : resp.getBody();
                return "Error: Gemini API returned HTTP " + resp.getStatusCodeValue() + ". " +
                        (errBody.length() > 1000 ? errBody.substring(0, 1000) + "...(truncated)" : errBody);
            }

            String rawJson = resp.getBody();
            if (rawJson == null || rawJson.isBlank()) {
                return "Error: Empty response from Gemini.";
            }

            // Parse common response shapes
            try {
                JsonNode root = objectMapper.readTree(rawJson);

                // candidates -> content -> parts[0].text
                if (root.has("candidates") && root.get("candidates").isArray() && root.get("candidates").size() > 0) {
                    JsonNode cand = root.get("candidates").get(0);
                    if (cand.has("content")) {
                        JsonNode content = cand.get("content");
                        if (content.has("parts") && content.get("parts").isArray() && content.get("parts").size() > 0) {
                            JsonNode partNode = content.get("parts").get(0);
                            if (partNode.has("text")) return partNode.get("text").asText();
                            return partNode.toString();
                        }
                        if (content.has("text")) return content.get("text").asText();
                    }
                    if (cand.has("text")) return cand.get("text").asText();
                }

                // output.items -> concat text fields
                if (root.has("output") && root.get("output").has("items")) {
                    JsonNode items = root.get("output").get("items");
                    if (items.isArray() && items.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (JsonNode it : items) {
                            if (it.has("text")) sb.append(it.get("text").asText());
                        }
                        if (sb.length() > 0) return sb.toString();
                    }
                }
            } catch (Exception parseEx) {
                // fall through and return raw JSON as fallback
            }

            // fallback: return raw JSON so client can inspect
            return rawJson;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling Gemini: " + e.getMessage();
        }
    }
}
