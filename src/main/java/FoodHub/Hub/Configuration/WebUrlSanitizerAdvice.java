package FoodHub.Hub.Configuration;


import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;

@ControllerAdvice
public class WebUrlSanitizerAdvice {
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(LocalDateTime.class,"reservationTime",new PropertyEditorSupport(){

            @Override
            public void setAsText(String value) throws IllegalArgumentException
            {
                System.out.println(value);

            }
        });
    }


    }
