package itmo.visits_control.controllers;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import itmo.visits_control.models.FormCommandModel;
import itmo.visits_control.models.ResultModel;
import itmo.visits_control.services.MainService;

@Controller
public class MainController {
	private static final String [] months=new String[]{"январь","февраль","март","апрель","май","июнь","июль","август","сентябрь","октябрь","ноябрь","декабрь"};
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
		String titleString = "Показаны результаты за " + months[form.getMonth()-1] + ", " + form.getYear() + " года";
		form.getJsonStrings().forEach(System.out::println);
		Long t = System.currentTimeMillis();
		ModelAndView mav = new ModelAndView("result");
		List<ResultModel>result=service.computeResults(form.getMonth(), form.getYear(), form.getJsonStrings());
		mav.addObject("results", result);
		mav.addObject("titleString", titleString);
		System.out.println(Duration.ofMillis(System.currentTimeMillis() - t));
		System.out.println(form.getJsonStrings().size());
		return mav;
	}

	
}
