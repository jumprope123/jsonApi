package shin.spring.mvc.restApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class RestAPI {

    @GetMapping("list")
    public String callAPI(){

        Map<String,Object> result = new HashMap<>();

        String jsonResult = "";

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
                    " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<?> entity = new HttpEntity<>(headers);

            String url = "https://yts.mx/api/v2/list_movies.json?sort_by=rating&limit=20";
            UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

            ResponseEntity<Map> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET, entity, Map.class);
            result.put("statusCode", resultMap.getStatusCodeValue());
            result.put("header", resultMap.getHeaders());
            result.put("body", resultMap.getBody());

            LinkedHashMap linkedHashMap = (LinkedHashMap) resultMap.getBody().get("data");
            ArrayList<Map> movies = (ArrayList<Map>) linkedHashMap.get("movies");
            LinkedHashMap lm = new LinkedHashMap();
            System.out.println(movies);
            for (Map obj : movies){
                lm.put("title",obj.get("title"));
                lm.put("year",obj.get("year"));
                lm.put("genres",obj.get("genres"));
                lm.put("summary",obj.get("summary"));
                lm.put("background_image_original",obj.get("background_image_original"));

            }


            ObjectMapper mapper = new ObjectMapper();
            jsonResult = mapper.writeValueAsString(lm);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonResult;

    }

}

//https://vmpo.tistory.com/27