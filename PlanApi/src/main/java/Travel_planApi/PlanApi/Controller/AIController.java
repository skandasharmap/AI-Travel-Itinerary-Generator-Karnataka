package Travel_planApi.PlanApi.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/ask")
    public ResponseEntity<String> askAI(@RequestBody Map<String, String> payload) {
        try {
            String question = payload.get("question");
            if (question == null || question.isEmpty()) {
                return ResponseEntity.badRequest().body("⚠️ Question cannot be empty");
            }

            // ✅ Build request JSON (correct Gemini schema)
            ObjectNode root = objectMapper.createObjectNode();
            ArrayNode contents = objectMapper.createArrayNode();

            ObjectNode part = objectMapper.createObjectNode();
            part.put("text", question);

            ObjectNode content = objectMapper.createObjectNode();
            content.set("parts", objectMapper.createArrayNode().add(part));
            contents.add(content);

            root.set("contents", contents);

            // 🚫 Removed generationConfig → not supported in v1beta

            // ✅ Call Gemini API
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity =
                    new HttpEntity<>(objectMapper.writeValueAsString(root), headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            GEMINI_URL + apiKey,
                            HttpMethod.POST,
                            entity,
                            String.class
                    );

            // ✅ Parse Gemini response
            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            String reply = jsonResponse
                    .path("candidates").get(0)
                    .path("content")
                    .path("parts").get(0)
                    .path("text").asText("⚠️ No reply from AI");

            return ResponseEntity.ok(reply);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body("❌ Error calling Gemini: " + e.getMessage());
        }
    }
}
