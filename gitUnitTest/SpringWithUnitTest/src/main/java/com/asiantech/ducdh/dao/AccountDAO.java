package com.asiantech.ducdh.dao;

import java.util.List;

import com.asiantech.ducdh.entity.TaskJob;


public interface AccountDAO {
	public static String NAME_DAO = "accountDAO";

	public void saveOrUpdate(TaskJob task, List<TaskJob> list);
	public void delete(String idTask,List<TaskJob> list);
	public List<TaskJob> list();
	public TaskJob getTask(String idTask, List<TaskJob> list);
	public List<TaskJob> search(String keyword,List<TaskJob> list);
	public void saveJob(List<TaskJob> listResult);
	public int getMaxPage(List<TaskJob> list,int recordInPage);
	public List<TaskJob> list(List<TaskJob> list,int currentPage, int recordInPage);
	public boolean checkTask(String idTask, List<TaskJob> list);
	public int getCurrentPage(String idTask, List<TaskJob> list,
			int recordInPage);
	public void changePublic(String idTask, List<TaskJob> list);
	public void changeStatus(String idTask, String statusTask,
			List<TaskJob> list);
	public void delete(TaskJob task);
}
