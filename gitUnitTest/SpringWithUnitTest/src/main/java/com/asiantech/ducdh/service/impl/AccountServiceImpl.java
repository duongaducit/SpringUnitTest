package com.asiantech.ducdh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.asiantech.ducdh.dao.AccountDAO;
import com.asiantech.ducdh.entity.TaskJob;
import com.asiantech.ducdh.service.AccountService;


@Service(AccountService.NAME_SERVICE)
public class AccountServiceImpl implements AccountService{

	@Autowired
	@Qualifier(AccountDAO.NAME_DAO)
	public AccountDAO accountDao;
	

	public List<TaskJob> list() {
		// TODO Auto-generated method stub
		return accountDao.list();
	}


	public void saveOrUpdate(TaskJob task,List<TaskJob> list) {
		accountDao.saveOrUpdate(task,list);
		
	}


	public void delete(String idTask,List<TaskJob> list) {
		accountDao.delete(idTask,list);
		
	}


	public TaskJob getTask(String idTask, List<TaskJob> list) {
		// TODO Auto-generated method stub
		return accountDao.getTask(idTask,list);
	}


	public List<TaskJob> search(String keyword,List<TaskJob> list) {
		// TODO Auto-generated method stub
		return accountDao.search(keyword,list);
	}


	public void saveJob(List<TaskJob> listResult) {
		// TODO Auto-generated method stub
		accountDao.saveJob(listResult);
	}


	public int getMaxPage(List<TaskJob> list,int recordInPage) {
		// TODO Auto-generated method stub
		return accountDao.getMaxPage(list,recordInPage);
	}


	public List<TaskJob> list(List<TaskJob> list,int currentPage, int recordInPage) {
		// TODO Auto-generated method stub
		return accountDao.list(list,currentPage,recordInPage);
	}


	public boolean checkTask(String idTask, List<TaskJob> list) {
		// TODO Auto-generated method stub
		return accountDao.checkTask(idTask,list);
	}


	public int getCurrentPage(String idTask, List<TaskJob> list, int recordInPage) {
		// TODO Auto-generated method stub
		return accountDao.getCurrentPage(idTask,list,recordInPage);
	}


	public void changePublic(String idTask, List<TaskJob> list) {
		accountDao.changePublic(idTask,list);
		
	}


	public void changeStatus(String idTask, String statusTask,
			List<TaskJob> list) {
		accountDao.changeStatus(idTask,statusTask,list);
		
	}


	public void delete(TaskJob task) {
		accountDao.delete(task);
		
	}

}
