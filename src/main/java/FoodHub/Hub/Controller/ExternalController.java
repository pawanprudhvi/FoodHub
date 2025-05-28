package FoodHub.Hub.Controller;

import FoodHub.Hub.DTO.Book;
import FoodHub.Hub.DTO.Bookresponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RestController
public class ExternalController {

    @Autowired
    private WebClient webclient;
    @GetMapping("/lotr")
    public List<Book> lotr()
    {
        Bookresponse response = webclient.get()
                .uri("https://openlibrary.org/search.json?q=the+lord+of+the+rings")
                .retrieve()
                .bodyToMono(Bookresponse.class)
                .block();
        return response.getDocs();
    }
}
