package com.cens.tareas.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cens.tareas.appdata.AppData;
import com.cens.tareas.models.entities.Tarea;
import com.cens.tareas.models.services.TareaService;
import com.cens.tareas.models.services.UploadService;
import com.cens.tareas.util.paginator.PageRender;

import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.support.SessionStatus;




@Controller
@SessionAttributes("tarea")
@RequestMapping("/tarea")
public class TareaController {

	private final AppData appData;
	private final TareaService tareaService;
	public static final String OPGEN = "TAREA"; 
	
	public TareaController(UploadService uploadService,							 
	TareaService tareaService,
	AppData applicationData) {
		this.appData = applicationData;
		this.tareaService = tareaService;
	}

		
	
	@GetMapping({ "", "/", "/list", "/list/{page}" })
	public String list(@PathVariable(name = "page", required = false) Integer page, Model model) {
	
		if (page == null)
			page = 0;
		
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Tarea> pageTarea = tareaService.findAll(pageRequest); 
		PageRender<Tarea> paginator = new PageRender<>("/tarea/list",pageTarea,5);
		

		model.addAttribute("numtarea", tareaService.count());
		model.addAttribute("listtarea", pageTarea);
		model.addAttribute("paginator",paginator);
		
		model.addAttribute("actualpage", page);
		
		return "tarea/list";
	}
	
	@GetMapping({ "/formcr", "/formcr/{page}" })
	public String form(@PathVariable(name = "page", required = false) Integer page, Model model) {
		Tarea tarea = new Tarea();		
		model.addAttribute("tarea",tarea);
		
		if (page == null)
			page = 0;
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"CREATE");
		
		return "tarea/form";
	}
	
	@GetMapping({ "/formup/{id}", "/formup/{id}/{page}" })
	public String form(@PathVariable(name = "id") Long id, @PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {
		if (page == null)
			page = 0;
		Tarea tarea = tareaService.findOne(id);
		if(tarea==null) {
			flash.addFlashAttribute("error","Data not found");
			return "redirect:/tarea/list/" + page;
		}
		
		model.addAttribute("tarea", tarea);
		
		model.addAttribute("actualpage", page);
		
		fillApplicationData(model,"UPDATE");
		
		return "tarea/form";
	}
	
	
	@PostMapping("/form/{page}")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Tarea tarea,  
			           BindingResult result, 
					   
					   Model model,
					   @PathVariable(name = "page") int page,
					   RedirectAttributes flash,
					   SessionStatus status) {
		
		boolean creating;
		
		if(tarea.getId()==null) {
			fillApplicationData(model,"CREATE");
			creating = true;
		} else {
			fillApplicationData(model,"UPDATE");
			creating = false;
		}
		
		String msg = (tarea.getId()==null) ? "Creation successful" : "Update successful";
		
		if(result.hasErrors()) {
			model.addAttribute("actualpage", page);
			return "tarea/form";
		}	
		tareaService.save(tarea);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		
		if (creating)
			page = lastPage();
		
		return "redirect:/tarea/list/" + page;
	}

	@Secured("ROLE_ADMIN")
	@GetMapping({ "/delete/{id}", "/delete/{id}/{page}" })
	public String delete(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, RedirectAttributes flash) {
		
		if (page == null)
			page = 0;
		
		if(id>0) { 			
			Tarea tarea = tareaService.findOne(id);
			
			if(tarea != null) {
				tareaService.remove(id);
			} else {
				flash.addFlashAttribute("error","Data not found");
				return "redirect:/tarea/list/" + page;
			}			
			flash.addFlashAttribute("success","Deletion successful");
		}
		
		return "redirect:/tarea/list/" + page;
	}
	
	@GetMapping({ "/view/{id}", "/view/{id}/{page}" })
	public String view(@PathVariable(name = "id") Long id,
			@PathVariable(name = "page", required = false) Integer page, Model model, RedirectAttributes flash) {

		if (page == null)
			page = 0;
		
		if (id > 0) {
			Tarea tarea = tareaService.findOne(id);

			if (tarea == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/tarea/list/" + page;
			}

			model.addAttribute("tarea", tarea);
			model.addAttribute("actualpage", page);
			fillApplicationData(model, "VIEW");
			return "tarea/view";
			
		}

		return "redirect:/tarea/list/" + page;
	}
	
	
	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Tarea tarea = tareaService.findOne(id);

			if (tarea == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/tarea/list";
			}

			model.addAttribute("tarea", tarea);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption",true);
			model.addAttribute("imageField",imageField);
			
			return "tarea/viewimg";
			
		}

		return "redirect:/tarea/list";
	}
	
	
	
	
	private int lastPage() {
		Long nReg = tareaService.count();
		int nPag = (int) (nReg / 10);
		if (nReg % 10 == 0)
			nPag--;
		return nPag;
	}
	
	private void fillApplicationData(Model model, String screen) {
		model.addAttribute("applicationData",appData);
		model.addAttribute("optionCode",OPGEN);
		model.addAttribute("screen",screen);
	}
	
		
}