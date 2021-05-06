package cz.czechitas.java2webapps.ukol5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;

/**
 * Kontroler obsluhující registraci účastníků dětského tábora.
 */
@Controller
@RequestMapping("/")
public class RegistraceController {

    @GetMapping("")
    public ModelAndView formular() {
        ModelAndView modelAndView = new ModelAndView("formular");
        modelAndView.addObject("form", new RegistraceForm());
        return modelAndView;
    }

    @PostMapping("")
    public Object form(@ModelAttribute("form") @Valid RegistraceForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formular";
        }

        Period period = form.getDatumNarozeni().until(LocalDate.now());
        int vek = period.getYears();

        if (vek < 9 || vek > 15 || form.getSport().size() <= 1) {
            if (vek < 9 || vek > 15) {
                bindingResult.rejectValue("datumNarozeni", "", "To by nešlo.");
            }
            if (form.getSport().size() <= 1) {
                bindingResult.rejectValue("sport", "", "To by uz vubec nešlo.");
            }
            return "formular";
        }

        return new ModelAndView("potvrzenyFormular");
    }
}
