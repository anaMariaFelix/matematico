package com.anamariafelix.matematico.controller;

import com.anamariafelix.matematico.exception.ResourceNotFoundException;
import com.anamariafelix.matematico.math.SimpleMath;
import com.anamariafelix.matematico.request.converters.NumberConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.anamariafelix.matematico.request.converters.NumberConverter.isNumeric;

@RequiredArgsConstructor
@RestController
@RequestMapping("/math")
public class MathController {

    private final SimpleMath simpleMath;

    @GetMapping(value="/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Please set a numeric value!");
        }
        Double result = simpleMath.sum(NumberConverter.convertToDouble(numberOne),NumberConverter.convertToDouble(numberTwo));
        return result;
    }

    //Ambiguidade de rotas

    @GetMapping(value="/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Please set a numeric value!");
        }
        Double result = simpleMath.subtraction(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
        return result;
    }

    @GetMapping(value="/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Please set a numeric value!");
        }
        Double result = simpleMath.multiplication(NumberConverter.convertToDouble(numberOne),NumberConverter.convertToDouble(numberTwo));
        return result;
    }

    @GetMapping(value="/division/{numberOne}/{numberTwo}")
    public Double division(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Please set a numeric value!");
        }
        Double result = simpleMath.division(NumberConverter.convertToDouble(numberOne),NumberConverter.convertToDouble(numberTwo));
        return result;
    }

    @GetMapping(value="/mean/{numberOne}/{numberTwo}")
    public Double mean(@PathVariable("numberOne") String numberOne, @PathVariable("numberTwo") String numberTwo) throws Exception {

        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) {
            throw new ResourceNotFoundException("Please set a numeric value!");
        }
        Double result = simpleMath.mean (NumberConverter.convertToDouble(numberOne),NumberConverter.convertToDouble(numberTwo));
        return result;
    }

    @GetMapping(value="/squareRoot/{number}")
    public Double squareRoot(@PathVariable("number") String number) throws Exception {

        if (!isNumeric(number)) {
            throw new ResourceNotFoundException("Please set a numeric value!");
        }
        Double result = simpleMath.squareRoot(NumberConverter.convertToDouble(number));
        return result;
    }

}
