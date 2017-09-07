package org.vikas;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import java.io.*;

import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Component
@RequestMapping("")
public class ResumeHandler {
	@Autowired
	SessionFactory sessionFactory;
	
	@RequestMapping(value="")
	public String redirect()
	{   // to redirect to other url
		return "redirect:candidatedataupload";
	}
	
	@RequestMapping(value="candidatedataupload")
	public ModelAndView resumeSubmissionForm()
	{		
		//by default spring form expect an empty element with name command in model 
		return new ModelAndView("CandidateDataUpload","command",new Candidate());
		
	} 
	
	@RequestMapping(value="candidatedatahandler", method= RequestMethod.POST)
	public ModelAndView resumeDataHandle(@RequestParam("resume") CommonsMultipartFile resume,@RequestParam("image") CommonsMultipartFile image,@ModelAttribute("command") Candidate candidate, HttpSession session, ModelMap model)
	{
		String path = session.getServletContext().getRealPath("/");
		String imagename= image.getOriginalFilename();
		String resumename= resume.getOriginalFilename();
		
		byte imagebytes[] = image.getBytes();
		byte resumebytes[] = resume.getBytes();
		candidate.setPicture(imagebytes);
		candidate.setCv(resumebytes);
		
		Session sessionHibernate = sessionFactory.openSession();
		sessionHibernate.beginTransaction();
		sessionHibernate.save(candidate);
		sessionHibernate.getTransaction().commit();
		
		try{
			//use double forward slash in  file path
			File f =new File(path+"images//"+imagename); 
			BufferedOutputStream outImage = new BufferedOutputStream( new FileOutputStream(f));
			BufferedOutputStream outResume = new BufferedOutputStream( new FileOutputStream("c:\\users\\vikas\\desktop\\"+resumename));
			outImage.write(imagebytes);
			outResume.write(resumebytes);
			outImage.flush();
			outResume.flush();

			model.put("imagedisplay", f.getAbsolutePath());
			model.put("candidate", candidate);
			model.put("testlink","hibernatefetch");
		}
		catch(Exception e)
		{
			
		}
		
		
		Session sess = sessionFactory.openSession();
		sess.beginTransaction();
		candidate= (Candidate)sess.get(Candidate.class,1);
		model.put("candidate", candidate);
		sess.getTransaction().commit();
		
		byte[] imageGet= candidate.getPicture();
		File f =new File(path+"\\images\\db"+resumename); 
		try{
			BufferedOutputStream outResume = new BufferedOutputStream( new FileOutputStream(f));
			outResume.write(imageGet);
			model.put("databaseimage",f.getAbsolutePath());
		}
		catch(Exception e)
		{}
	
		
		return new ModelAndView("HibernateFetch");
	}
	
	
	
	@RequestMapping(value="hibernatefetch/{id1}/{id2}/{id3}")
	public void hibernatefetch(ModelMap model, HttpServletResponse response , @PathVariable("id1") int id1, @PathVariable("id2") int id2, @PathVariable("id3") int id3)
	{
		response.setContentType("image/jpeg");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Candidate candidate= (Candidate)session.get(Candidate.class,id1);
		session.getTransaction().commit();
		byte[] image= candidate.getPicture();
		try{
			InputStream is = new ByteArrayInputStream(image);
			IOUtils.copy(is,response.getOutputStream());
			response.flushBuffer();
		}
		catch(Exception e)
		{}
		
	}
	
}
