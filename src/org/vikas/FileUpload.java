package org.vikas;

import java.io.*;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Component
@RequestMapping("fileupload")
public class FileUpload {

	@RequestMapping(value="/form", method=RequestMethod.GET)
	public ModelAndView form()
	{
		return new ModelAndView("FileUpload");
	}

	
	@RequestMapping(value="/savefile",method=RequestMethod.POST)  
	public ModelAndView upload(@RequestParam("file") CommonsMultipartFile file,HttpSession session){  
		String path=session.getServletContext().getRealPath("/");  
		String filename=file.getOriginalFilename();  

		System.out.println(path+""+filename);  
		try{  
			byte barr[]=file.getBytes();  

			/*BufferedOutputStream bout=new BufferedOutputStream(  
	                 new FileOutputStream(path+""+filename));  
			 */
			BufferedOutputStream bout=new BufferedOutputStream(  
					new FileOutputStream("c:\\users\\vikas\\desktop\\"+filename));  
			bout.write(barr);  
			bout.flush();  
			bout.close();  

		}catch(Exception e){System.out.println(e);}  
		return new ModelAndView("FileUpload","filesuccess",path+filename);  
		//return new ModelAndView("View_Name","keyname_in_modelmap","valuename_in_modelmap");  

	} 

}  


