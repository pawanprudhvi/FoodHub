package FoodHub.Hub.exceptions;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;



public class Validationexception  extends BindException {

    private static final long serialVersionUID = -3008190810654408281L;
    public Validationexception(BindingResult bindingresult)
    {
        super(bindingresult);
    }
}
