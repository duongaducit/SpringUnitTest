package com.asiantech.ducdh.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.asiantech.ducdh.entity.TaskJob;
import com.asiantech.ducdh.service.AccountService;


@Controller
public class AccountController {
	
	@Autowired
	@Qualifier(AccountService.NAME_SERVICE)
	private AccountService accountService;
	private List<TaskJob> list;
	private List<TaskJob> listResult;
	private List<TaskJob> listCurrent;
	
	private int currentPage = 0;
	private int recordInPage = 3;
	private int maxPage;
	private int maxRecord;
	private int currentRecord;
	
	private Date timeUpdate = new Date(new java.util.Date().getTime());
	private boolean SortByName = true;
	private boolean SortByID = true;
	
	@RequestMapping("/")
	public ModelAndView listTask(ModelAndView model) throws IOException{
		if (list == null){
		list = accountService.list();
		listResult = accountService.list();
		}
		maxPage = accountService.getMaxPage(list,recordInPage);
		listCurrent = accountService.list(list,currentPage,recordInPage);
		maxRecord = list.size();
		currentRecord = currentPage*recordInPage + listCurrent.size();
		model.addObject("list", listCurrent);
		model.addObject("maxPage", maxPage);
		model.addObject("maxRecord", maxRecord);
		model.addObject("currentRecord", currentRecord);
		model.addObject("currentPage", currentPage);
		model.addObject("recordInPage", recordInPage);
		model.setViewName("index");
		
		return model;
	}
	/*
	 * add new Task
	 */
	@RequestMapping(value = "/newTask", method = RequestMethod.GET)
	public ModelAndView newTask(ModelAndView model) {
		TaskJob task= new TaskJob();
		task.setTimeUpdate(timeUpdate);
		model.addObject("task", task);
		model.setViewName("Task");
		return model;
	}
	/*
	 * save Task after edit or new
	 */
	@RequestMapping(value = "/saveTask", method = RequestMethod.POST)
	public String saveTask(Model model,@ModelAttribute("task") @Valid TaskJob task, BindingResult bindingResult) {
		task.setTimeUpdate(timeUpdate);
		if (bindingResult.hasErrors()){
			task.setTimeUpdate(timeUpdate);
			model.addAttribute("task", task);
			System.out.println("error");
			return "Task"; 
		}else{
		accountService.saveOrUpdate(task,list);
		Collections.sort(list,TaskJob.ID_TASK);
		currentPage = accountService.getCurrentPage(task.getIdTask(),list,recordInPage);
		copy(listResult, list);
		return "redirect:/";
		}
	}
	/*
	 * delete Task
	 */
	@RequestMapping(value = "/deleteTask", method = RequestMethod.GET)
	public ModelAndView deleteTask(HttpServletRequest request) {
		String idTask = (request.getParameter("idTask"));
		accountService.delete(idTask,list);
		copy(listResult, list);
		return new ModelAndView("redirect:/");
	}
	/*
	 * sort by ID
	 */
	@RequestMapping(value = "/sortById")
	public ModelAndView sortById() {
		if (SortByID){
		Collections.sort(this.list, TaskJob.ID_TASK);
		SortByID = false;
		}else{
			Collections.sort(this.list, TaskJob.TASK_ID);
			SortByID = true;
		}
		
		return new ModelAndView("redirect:/");
	}
	/*
	 * sort by Name
	 */
	@RequestMapping(value = "/sortByName")
	public ModelAndView sortByName() {
		if (SortByName){
		Collections.sort(this.list, TaskJob.TASK_NAME_COMPARETO);
		SortByName = false;
		}else{
			Collections.sort(this.list, TaskJob.COMPARETO_NAME);
			SortByName = true;
		}
		return new ModelAndView("redirect:/");
	}
	/*
	 * edit Task
	 */
	@RequestMapping(value = "/editTask", method = RequestMethod.GET)
	public ModelAndView editTask(HttpServletRequest request) {
		String idTask = (request.getParameter("idTask"));
		TaskJob task = accountService.getTask(idTask,list);
		ModelAndView model = new ModelAndView("Task");
		task.setTimeUpdate(timeUpdate);
		model.addObject("task", task);
		
		return model;
	}
	/*
	 * search in table
	 */
	@RequestMapping(value = "/search")
	public ModelAndView search(HttpServletRequest request,ModelAndView model) {
		String keyword = (request.getParameter("keyword"));
		if (keyword == ""){
			copy(list, listResult);
		}else{
		list = accountService.search(keyword,list);
		}
		return new ModelAndView("redirect:/");
	}
	/*
	 * save all change
	 */
	@RequestMapping(value = "/saveJob")
	public ModelAndView saveContact(){
		accountService.saveJob(listResult);
		list = null;
		return new ModelAndView("redirect:/");
	}
	/*
	 * select page
	 */
	@RequestMapping(value = "/selectPage")
	public ModelAndView selectPage(HttpServletRequest request) {
		int pageInt = Integer.parseInt(request.getParameter("page"));
		currentPage = pageInt;
		return new ModelAndView("redirect:/");
	}
	/*
	 * select Record in page
	 */
	
	@RequestMapping(value = "/selectRecord")
	public ModelAndView selectRecord(HttpServletRequest request) {
		int recordPage = Integer.parseInt(request.getParameter("record"));
		currentPage = 0;
		recordInPage = recordPage; 
		return new ModelAndView("redirect:/");
	}
	/*
	 * change checkbox in table
	 */
	@RequestMapping(value = "/change", method = RequestMethod.GET)
	public @ResponseBody String changePublic(@RequestParam String idTask) {
		accountService.changePublic(idTask, list);
		copy(listResult, list);
		return idTask;
	}
	/*
	 * change select option in table
	 */
	@RequestMapping(value = "/changeSTT", method = RequestMethod.GET)
	public @ResponseBody String changeSTT(@RequestParam String idTask, @RequestParam String statusTask) {
		accountService.changeStatus(idTask,statusTask, list);
		copy(listResult, list);
		return null;
	}
	
	public void copy(List<TaskJob> list1,List<TaskJob> list2){
		list1.removeAll(list1);
		for (int i = 0;i < list2.size();i++)
			list1.add(list2.get(i));
	}
}
