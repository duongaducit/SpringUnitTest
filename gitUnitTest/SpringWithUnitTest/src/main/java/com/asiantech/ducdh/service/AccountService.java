package com.asiantech.ducdh.service;

import java.util.List;

import com.asiantech.ducdh.entity.TaskJob;


public interface AccountService {
	public static String NAME_SERVICE = "accountService";
	//---------------
	List<TaskJob> list();
	//----------------
	public void saveOrUpdate(TaskJob task,List<TaskJob> list);
	//-----------------
	public void delete(String idTask,List<TaskJob> list);
	//------------------
	TaskJob getTask(String idTask, List<TaskJob> list);
	//------------------
	List<TaskJob> search(String keyword,List<TaskJob> list);
	//-----------------
	void saveJob(List<TaskJob> listResult);
	//-------------------
	int getMaxPage(List<TaskJob> list,int recordInPage);
	//-------------------
	List<TaskJob> list(List<TaskJob> list,int currentPage, int recordInPage);
	//-------------------
	boolean checkTask(String idTask, List<TaskJob> list);
	//-------------------
	int getCurrentPage(String idTask, List<TaskJob> list,int recordInPage);
	//-------------------
	void changePublic(String idTask, List<TaskJob> list);
	//-------------------
	void changeStatus(String idTask, String statusTask, List<TaskJob> list);
	//-------------------
	void delete(TaskJob task);
}
