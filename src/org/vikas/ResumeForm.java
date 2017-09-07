package org.vikas;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.*;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@RequestMapping("/resumeform")
public class ResumeForm {
	private Resume resume = new Resume();
	public Resume getResume() {
		return resume;
	}
	public void setResume(Resume resume) {
		this.resume = resume;
	}
	@RequestMapping(value="/hello" )
	public String Hello()
	{
		return "Hello";
	}
	@RequestMapping(value="/form")
	public String resumeform(ModelMap modelmap)
	{
		modelmap.put("acc",resume);
		return "ResumeForm";
	}
	@RequestMapping(value="/details", method=RequestMethod.POST)
	public String details(@ModelAttribute("acc") Resume resume, 
			BindingResult result,ModelMap modelmap, //modelmap is used to send data to view 
			HttpSession session, HttpServletRequest request) /* servlet container objects can 
															be accessed by passing as argument */
	{
		//printing the inputed values in form
		String datehtml=request.getParameter("datehtml");
		String filehtml=request.getParameter("filehtml");		
		System.out.println(datehtml);
		System.out.println(filehtml);
		modelmap.put("acc", resume);  
		modelmap.put("datehtml", datehtml);
		return "Hello";
	}
}
