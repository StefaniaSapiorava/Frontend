package by.bsu.controller;

import by.bsu.entities.Service;
import by.bsu.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/services")
//@CrossOrigin(origins = "*")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping("/list")
    public String listService(Model model) {
        List<Service> Services = serviceRepository.findAll();
        model.addAttribute("Services", Services);
        return "/services/list";
    }
}