
package se.m777.helpdesk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import se.m777.helpdesk.service.TicketService;

import java.security.Principal;

@Controller
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/tickets";
    }

    @GetMapping("/tickets")
    public String listTickets(Model model, Principal principal) {
        model.addAttribute("tickets",
                ticketService.getTickets(principal.getName()));

        return "tickets/list";
    }

    @GetMapping("/tickets/new")
    public String newTicket() {
        return "tickets/new";
    }

    @PostMapping("/tickets")
    public String createTicket(@RequestParam String title,
                               @RequestParam String description,
                               Principal principal) {

        ticketService.createTicket(title, description, principal.getName());

        return "redirect:/tickets";
    }

    @GetMapping("/tickets/{id}")
    public String ticketDetails(@PathVariable Long id,
                                Model model,
                                Principal principal) {

        model.addAttribute("ticket",
                ticketService.getTicket(id, principal.getName()));

        return "tickets/details";
    }
}





