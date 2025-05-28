//package FoodHub.Hub.Configuration;
//
//import com.fasterxml.jackson.core.JacksonException;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//
//import java.io.IOException;
//
//public class LowerCaseDeserialiser extends JsonDeserializer<String> {
//    @Override
//    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
//        String emailId=jsonParser.getText();
//        return emailId.toLowerCase();
//
//    }
//}
