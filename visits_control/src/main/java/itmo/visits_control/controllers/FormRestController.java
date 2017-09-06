package itmo.visits_control.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import itmo.visits_control.models.StartForm;
import itmo.visits_control.services.FormService;

@RestController
public class FormRestController {
	@Autowired
	private FormService service;
	@RequestMapping(value = "/api/getstartform", consumes = "application/json", produces = "application/json; charset=UTF-8")
	public StartForm getStartForm() {
		return service.getStartForm();
	}
}
