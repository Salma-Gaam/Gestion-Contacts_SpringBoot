package com.devoir.core.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.devoir.core.bo.Contact;
import com.devoir.core.bo.Groupe;
import com.devoir.core.services.IContactService;
import com.devoir.core.services.IGroupeService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.validation.Valid;

@Controller
public class ContactController {

	@Autowired
	private IContactService contactService; // Injection du service metier ici
	
	@Autowired
	private IGroupeService groupeService;

	
	//Construteur
	public ContactController() {
	}

	@PostConstruct
	public void init() {
	
	}

	@RequestMapping("/showForm")
	public String showForm(Model model) {
		
		model.addAttribute("contactModel", new Contact());
		model.addAttribute("contactList", contactService.findAllByOrderByNom());
		return "form";
	}

	/* 
	 * Contact Things
	 * create, manage, search
	 */
	
	@RequestMapping(value = "/updateContactForm/{id}", method = RequestMethod.GET)
	public String updateContactForm(@PathVariable int id, Model model) {
		
		model.addAttribute("contactModel", contactService.getContactById(Long.valueOf(id)));
		return "updateForm";
		
	}


	@RequestMapping("/updateContact")
	public String updateContact(@Valid @ModelAttribute("contactModel") Contact contact, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			return "updateForm";
		}

		contactService.updateContact(contact);
		model.addAttribute("contactList", contactService.findAllByOrderByNom());

		return "redirect:/manageContacts";
	}

	
	@RequestMapping("/addContact")
	public String createContact(@Valid @ModelAttribute("contactModel") Contact contact, BindingResult bindingResult,
			ModelMap model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("errorMsg", "Les données sont invalides.");
		} else {
			contactService.addContact(contact);
			model.addAttribute("infoMsg", "Personne ajouté avec succès");
		}
		model.addAttribute("contactList", contactService.findAllByOrderByNom()); 
		return "form";

	}

	@RequestMapping("/manageContacts")
	public String manageContacts(Model model) {

		model.addAttribute("contactList", contactService.findAllByOrderByNom());

		return "listContacts";
	}
	

	@RequestMapping(value = "/deleteContact/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable int id) {
		contactService.deleteContact(Long.valueOf(id));
		return "redirect:/manageContacts";
		
	}

	@PostMapping(value = "searchContact")
	public String searchContact(@RequestParam String nid, Model model) {

		List<Contact> ct = contactService.getContactByNom(nid);
		
		if (ct != null) {
			model.addAttribute("contactList",ct);
		}

		return "listContacts";
	}
	
	
	@PostMapping(value = "searchContactPer")
	public String searchContactPhonePer(@RequestParam String phone1, Model model) {

		List<Contact> contacts = contactService.getContactByNumeroPer(phone1);
		
		if (contacts != null) {
			model.addAttribute("contactList", contacts);
		}
		return "listContacts";
	}

	
	@PostMapping(value = "searchContactPro")
	public String searchContactPhonePro(@RequestParam String phone2, Model model) {

		List<Contact> contacts = contactService.getContactByNumeroPro(phone2);
		
		if (contacts != null) {
			model.addAttribute("contactList",contacts);			
		}
		return "listContacts";
	}
	
	
	/*
	 * Group things
	 * 
	 */
	@RequestMapping("/showGroup")
	public String showGrp(Model model) {
		model.addAttribute("groupeModel", new Groupe());
		model.addAttribute("groupeList", groupeService.getAllGrp());
		return "groupForm";
		
	}
	
	@RequestMapping("/addGroup")
	public String createGroupe(@Valid @ModelAttribute("groupeModel") Groupe groupe, BindingResult bindingResult,
			ModelMap model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("errorMsg", "Les données sont invalides.");
		} else {
			groupeService.createGroupe(groupe);
			model.addAttribute("infoMsg", "Groupe ajouté avec succès");

		}
		
		model.addAttribute("groupeList", groupeService.getAllGrp());

		return "groupForm";

	}
	
	@RequestMapping("/manageGroups")
	public String manageGroups(Model model) {

		model.addAttribute("groupeList", groupeService.getAllGrp());

		return "listGroups";
	}
	
	
	@RequestMapping(value = "/deleteGroup/{idGrp}", method = RequestMethod.GET)
	public String deleteGroup(@PathVariable int idGrp) {

		groupeService.deleteGroupe(Long.valueOf(idGrp));
		return "redirect:/manageGroups";
		
	}

	@PostMapping(value = "/searchGroup")
	public String searchGroup(@RequestParam String nom, Model model) {

		List<Groupe> gr = groupeService.getGroupByNom(nom);
		
		if (gr != null) {
			model.addAttribute("groupeList", gr);
		}
		
		return "listGroups";
	}
	
	@RequestMapping(value = "/addGroupContacts/{idGroup}")
	public String fillGroupForm(@PathVariable(name = "idGroup") int id, Model model) {
				
		// to keep the group that we want fill it with contact, we put it in the model
		model.addAttribute("groupeModel",  groupeService.getGroupeById(Long.valueOf(id)));
		// we return the contacts that we will display to select the ones that we want to add to the group
		model.addAttribute("contactList", contactService.findAllByOrderByNom());

		return "selectContacts";
	}
	
	
	@PostMapping(value = "/addGroupContacts")
	public String fillGroup( @ModelAttribute("groupeModel") Groupe grp, 
			@RequestParam("selectedContacts") List<Integer> contactIds,
			BindingResult bindingResult, Model model) {
		
		Groupe groupe = groupeService.getGroupeById(grp.getIdGroup());
		
		
//		if (bindingResult.hasErrors()) {
//			return "selectContacts";
//		}
		
		// stock all the selected contacts in a list
		List<Contact> contactList = new ArrayList<>();
		for(Integer id: contactIds) {
			contactList.add(contactService.getContactById(Long.valueOf(id)));
		}
		
		// add those contacts to the selected group
		groupe = groupeService.addContact(groupe, contactList);
		
//        groupeService.updateGrp(groupe); 
        
		model.addAttribute("groupeModel", groupeService.updateGrp(groupe));

		model.addAttribute("groupeList", groupeService.getAllGrp());
		return "redirect:/manageGroups";
	}
	

	

	
}