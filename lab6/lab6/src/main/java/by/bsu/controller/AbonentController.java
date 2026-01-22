package by.bsu.controller;

import by.bsu.entities.Abonent;
import by.bsu.repository.AbonentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/abonents")
public class AbonentController {

    @Autowired
    private AbonentRepository abonentRepository;


    @GetMapping("/list")
    public String listAbonents(Model model) {
        List<Abonent> abonents = abonentRepository.findAll();
        model.addAttribute("Abonents", abonents);
        return "/abonents/list";
    }
    @GetMapping("/add")
    public String showPage(){
        return "/abonents/add";
    }
    @PostMapping("/add")
    public String addAbonent(@RequestParam("Name") String name,
                              @RequestParam("Phone") String phone,
                              @RequestParam("Email") String email,
                              Model model) {
        Abonent abonent = new Abonent();
        abonent.setEmail(email);
        abonent.setName(name);
        abonent.setPhone(phone);
        abonent.setBlocked(false);
        abonentRepository.save(abonent);
        model.addAttribute("success", "Abonent added successfully!");
        return "abonents/add";
    }
    @GetMapping("/get")
    public String getAbonentById(@RequestParam("id") Long id, Model model) {
        Abonent abonent = abonentRepository.findById(Math.toIntExact(id)).orElse(null);

        if (abonent != null) {
            model.addAttribute("abonent", abonent);
        } else {
            model.addAttribute("error", "Abonent not found with ID: " + id);
        }

        return "abonents/details";
    }

}