package itmo.visits_control.controllers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import itmo.visits_control.models.FormCommandModel;
import itmo.visits_control.models.PersonInfo;
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
		String rate=form.getRate()==0?"действующую":String.valueOf(form.getRate());
		///String titleString = "Результаты за " + months[form.getMonth()-1] + " " + form.getYear() + " года "+"в пересчете на "+rate+" ставку.";
		String titleString = "Результаты за " + months[form.getMonth()] + " " + form.getYear() + " года "+"в пересчете на "+rate+" ставку.";
		System.out.println(titleString);
		Long t = System.currentTimeMillis();
		List<ResultModel>result=service.computeResults(form.getMonth(), form.getYear(), form.getJsonStrings(),form.getRate());
		ModelAndView mav = new ModelAndView();

		
		mav.addObject("titleString", titleString);
		if(form.getResultFormat()==0){
		mav.setViewName("result");
		mav.addObject("results", result);
		}else{
			final List<PersonInfo> tmp=new ArrayList<>();
			result.forEach(r->{
				r.getPersonsInfo().forEach(p->tmp.add(p));
			});
			List<PersonInfo> result2=tmp.stream().sorted((r,r1)->{
				return r.getFullName().compareTo(r1.getFullName());
			}).collect(Collectors.toList());
			mav.setViewName("result2");
			mav.addObject("results", result2);
		}
		System.out.println(Duration.ofMillis(System.currentTimeMillis() - t));
		System.out.println(form.getJsonStrings().size());
		return mav;
	}

	
}
