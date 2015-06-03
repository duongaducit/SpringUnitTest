package com.asiantech.ducdh.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.asiantech.ducdh.dao.AccountDAO;
import com.asiantech.ducdh.entity.TaskJob;

@Service(AccountDAO.NAME_DAO)
public class AccountDAOImpl implements AccountDAO {

	@Autowired
	private DataSource dataSources;
	private JdbcTemplate jdbcTemplate;

	/*
	 * (delete in list)
	 * @see com.asian.spring.two.dao.AccountDAO#delete(java.lang.String, java.util.List)
	 */
	public void delete(String idTask,List<TaskJob> list){
		for(int i = 0;i < list.size();i++){
			if (list.get(i).getIdTask().equals(idTask))
				list.remove(i);
			}
	}
	/*
	 * delete in SQL
	 */
	public void deleteSQL(String idTask) {
		jdbcTemplate = new JdbcTemplate(dataSources);
		String sql = "DELETE FROM TaskJob WHERE idTask=?";
		jdbcTemplate.update(sql, idTask);
	}

	public List<TaskJob> list() {
		jdbcTemplate = new JdbcTemplate(dataSources);
		String sql = "SELECT * FROM TaskJob";
		jdbcTemplate = new JdbcTemplate(dataSources);
		List<TaskJob> listContact = jdbcTemplate.query(sql,
				new RowMapper<TaskJob>() {

					public TaskJob mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						TaskJob taskJob = new TaskJob();
						taskJob.setIdTask(rs.getString(1));
						taskJob.setNameTask(rs.getString(2));
						taskJob.setTimeUpdate(rs.getDate(3));
						taskJob.setStatusTask(rs.getString(4));
						taskJob.setPublicTask(rs.getInt(5) == 1);
						return taskJob;
					}

				});

		return listContact;
	}
	/*
	 * get Task to list
	 * 
	 * @see com.asian.spring.two.dao.AccountDAO#getTask(java.lang.String,
	 * java.util.List)
	 */
	public TaskJob getTask(String idTask, List<TaskJob> list) {
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIdTask().equals(idTask))
				return list.get(i);
		}

		return null;
	}

	/*
	 * save task to list
	 * 
	 * @see
	 * com.asian.spring.two.dao.AccountDAO#saveOrUpdate(com.asian.spring.two
	 * .entity.TaskJob, java.util.List)
	 */
	public void saveOrUpdate(TaskJob task, List<TaskJob> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getIdTask().equals(task.getIdTask()))
				list.remove(i);
		}
		list.add(task);
	}

	/*
	 * search in list
	 * 
	 * @see com.asian.spring.two.dao.AccountDAO#search(java.lang.String,
	 * java.util.List)
	 */
	public List<TaskJob> search(String keyword, List<TaskJob> list) {
		// TODO Auto-generated method stub
		for (int i = list.size() - 1; i >= 0; i--) {
			if (!list.get(i).getNameTask().contains(keyword)) {
				list.remove(i);
			}
		}
		return list;
	}

	/*
	 * save all job
	 * 
	 * @see com.asian.spring.two.dao.AccountDAO#saveJob(java.util.List)
	 */
	public void saveJob(List<TaskJob> listResult) {
		System.out.println("Save");
		jdbcTemplate = new JdbcTemplate(dataSources);
		String sql = "DELETE FROM TaskJob";
		jdbcTemplate.update(sql);
		for (int i = 0;i < listResult.size();i++){
			if (!"".equals(listResult.get(i).getIdTask())){
				TaskJob task = listResult.get(i);
				int publicTask;
				if (task.isPublicTask()){
					publicTask = 1;
				}else{
					publicTask = 0;
				}
		sql = "INSERT INTO TaskJob (idTask, nameTask, timeUpdate, statusTask, publicTask)"
					+ " VALUES (?, ?, ?, ?,?)";
		jdbcTemplate.update(sql, task.getIdTask(),task.getNameTask(),task.getTimeUpdate(),task.getStatusTask(),publicTask);
		}}
		
	}

	/*
	 * check delete
	 */
	public boolean checkDelete(String id, List<TaskJob> list){
		boolean check = false;
		for (int i = 0;i < list.size();i++){
			if (list.get(i).getIdTask().equals(id))
				check = true;
		}
		return check;
	}

	public int getMaxPage(List<TaskJob> list,int recordInPage) {
		// TODO Auto-generated method stub
		int max = 1;
		if (list.size()%recordInPage == 0){
			max = (list.size()/recordInPage);
		}else{
			max = (list.size()/recordInPage) + 1;
		}
		System.out.println(max);
		return max;
	}
	/*
	 * get list paging
	 * @see com.asian.spring.two.dao.AccountDAO#list(java.util.List, int, int)
	 */
	public List<TaskJob> list(List<TaskJob> list,int currentPage, int recordInPage) {
		List<TaskJob> listCurrent = new ArrayList<TaskJob>();
		for (int i = 0;i < recordInPage;i++){
			if ((currentPage*recordInPage + i) < list.size())
			listCurrent.add(list.get(currentPage*recordInPage + i));
		}
		
		return listCurrent;
	}

	public boolean checkTask(String idTask, List<TaskJob> list) {
		boolean check = false;
		for (int i = 0;i < list.size();i++){
			if (idTask.equals(list.get(i).getIdTask())){
				check = true;
			}
		}
		
		return check;
	}
	
	public int getCurrentPage(String idTask, List<TaskJob> list,
			int recordInPage) {
		int post = 0;
		for (int i = 0;i < list.size();i++){
			if (idTask.equals(list.get(i).getIdTask())){
				post = i;
			}
		}
		
		return post/recordInPage;
	}
	
	public void changePublic(String idTask, List<TaskJob> list) {
		for (int i = 0;i < list.size();i++){
			if (idTask.equals(list.get(i).getIdTask())){
				list.get(i).setPublicTask(!list.get(i).isPublicTask());
			}
		}
		System.out.println("change");
	}
	
	public void changeStatus(String idTask,String statusTask, List<TaskJob> list) {
		for (int i = 0;i < list.size();i++){
			if (idTask.equals(list.get(i).getIdTask())){
				list.get(i).setStatusTask(statusTask);
			}
		}
		System.out.println("changeSTT");
	}
	
	public DataSource getDataSources() {
		return dataSources;
	}

	public void setDataSources(DataSource dataSources) {
		this.dataSources = dataSources;
	}
	public void delete(TaskJob task) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
