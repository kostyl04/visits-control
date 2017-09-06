package itmo.visits_control.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import itmo.visits_control.exceptions.DataException;

@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler(DataException.class)
	public ModelAndView conflict(HttpServletRequest req, Exception e) {
		ModelAndView mav=new ModelAndView("exception");
		mav.addObject("exception", e);
		return mav;
	}
}