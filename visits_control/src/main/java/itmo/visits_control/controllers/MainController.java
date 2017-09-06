package itmo.visits_control.controllers;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import itmo.visits_control.exceptions.DataException;
import itmo.visits_control.models.FormCommandModel;
import itmo.visits_control.services.MainService;

@Controller
public class MainController {
	@Autowired
	private MainService service;

	@RequestMapping("/")
	public ModelAndView main() {
		ModelAndView mav = new ModelAndView("main");
		mav.addObject("formCommand", new FormCommandModel());
		return mav;
	}

	@RequestMapping(value = "/result", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
	public ModelAndView result(@ModelAttribute FormCommandModel form) throws Exception {
		String titleString = "Показаны результаты за " + form.getMonth() + " месяц, " + form.getYear() + " год";
		form.getJsonStrings().forEach(System.out::println);
		Long t = System.currentTimeMillis();
		ModelAndView mav = new ModelAndView("result");
		mav.addObject("results", service.computeResults(form.getMonth(), form.getYear(), form.getJsonStrings()));
		mav.addObject("titleString", titleString);
		System.out.println(Duration.ofMillis(System.currentTimeMillis() - t));
		return mav;
	}

	
}
