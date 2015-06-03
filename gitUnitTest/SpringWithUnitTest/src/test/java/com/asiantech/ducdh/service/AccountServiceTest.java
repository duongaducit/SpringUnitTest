package com.asiantech.ducdh.service;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Stub;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito.Then;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.Returns;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.springframework.scheduling.config.Task;

import com.asiantech.ducdh.entity.*;
import com.asiantech.ducdh.dao.AccountDAO;
import com.asiantech.ducdh.service.impl.AccountServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

	@InjectMocks
	private AccountServiceImpl accountService;

	@Mock
	private AccountDAO accountDao;
	private Map<String, TaskJob> taskMap = new HashMap<String, TaskJob>();

	private Map<String, TaskJob> createTaskMap(int length) {
		Map<String, TaskJob> quesMap = new HashMap<String, TaskJob>();
		for (int i = 0; i < length; i++) {
			TaskJob task = createTask(i);
			quesMap.put("Task_" + i, task);
		}
		return quesMap;
	}

	private TaskJob createTask(int i) {
		Date date = new Date(new java.util.Date().getTime());
		TaskJob task = new TaskJob("Task_" + i, "Learn_" + i, date,
				getStatus(i), (i % 3 == 0));
		return task;
	}

	private String getStatus(int i) {
		if (i % 3 == 0) {
			return "Done!";
		} else {
			if (i % 3 == 1) {
				return "Doing...";
			} else {
				return "Do not.";
			}
		}
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		taskMap = createTaskMap(11);
		// gia lap goi ham list()
		Mockito.when(accountDao.list()).thenAnswer(new Answer<List<TaskJob>>() {
			public List<TaskJob> answer(InvocationOnMock invocation)
					throws Throwable {
				List<TaskJob> list = new ArrayList<TaskJob>();
				for (int i = 0; i < taskMap.size(); i++) {
					list.add(taskMap.get("Task_" + i));
				}
				return list;
			}
		});
		// gia lap goi ham getTask()
		Mockito.when(accountDao.getTask(Mockito.anyString(), Mockito.anyList()))
				.then(new Answer<TaskJob>() {

					public TaskJob answer(InvocationOnMock invocation)
							throws Throwable {

						String id = (String) invocation.getArguments()[0];
						List<TaskJob> list = (List<TaskJob>) invocation
								.getArguments()[1];
						for (int i = 0; i < list.size(); i++) {
							if (id.equals(list.get(i).getIdTask())) {
								return list.get(i);
							}
						}
						return null;
					}
				});
		//gia lap method search
		Mockito.when(accountDao.search(Mockito.anyString(), Mockito.anyList())).then(new Answer<List<TaskJob>>(){

			public List<TaskJob> answer(InvocationOnMock invocation)
					throws Throwable {
				String key = (String) invocation.getArguments()[0];
				List<TaskJob> list = (List<TaskJob>) invocation
						.getArguments()[1];
				List<TaskJob> listReturn = new ArrayList<TaskJob>();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getNameTask().contains(key)){
						listReturn.add(list.get(i));
					}
				}
				return listReturn;
			}
			
		});
		//gia lap method getMaxpage
		Mockito.when(accountDao.getMaxPage(Mockito.anyList(), Mockito.anyInt())).then(new Answer<Integer>() {

			public Integer answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub
				int recordInPage = (Integer) invocation.getArguments()[1];
				List<TaskJob> list = (List<TaskJob>) invocation
						.getArguments()[0];
				return (list.size()/recordInPage) + 1;
			}
		});
		//gia lap method checkTask
		Mockito.when(accountDao.checkTask(Mockito.anyString(), Mockito.anyList())).then(new Answer<Boolean>() {

			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub

				String id = (String) invocation.getArguments()[0];
				List<TaskJob> list = (List<TaskJob>) invocation
						.getArguments()[1];
				for (int i = 0; i < list.size(); i++) {
					if (id.equals(list.get(i).getIdTask())) {
						return true;
					}
				}
				return false;
			}
		});
		//--------method delete------------
		Mockito.doAnswer(new Answer<List<TaskJob>>(){

			public List<TaskJob> answer(InvocationOnMock invocation)
					throws Throwable {
				String id = (String) invocation.getArguments()[0];
				List<TaskJob> list = (List<TaskJob>) invocation
						.getArguments()[1];
				for (int i = 0; i < list.size(); i++) {
					if (id.equals(list.get(i).getIdTask())) {
						list.remove(i);
					}
				}

				return list;
			}
			
		}).when(accountDao).delete(Mockito.anyString(), Mockito.anyList());
		//----method getCurrentPage
		Mockito.when(
				accountDao.getCurrentPage(Mockito.anyString(),
						Mockito.anyList(), Mockito.anyInt())).then(
				new Answer<Integer>() {

			public Integer answer(InvocationOnMock invocation) throws Throwable {
				String idTask = (String) invocation.getArguments()[0];
				List<TaskJob> list = (List<TaskJob>) invocation.getArguments()[1];
				int record = (Integer) invocation.getArguments()[2];
				for (int i = 0;i <list.size();i++){
					if (idTask.equals(list.get(i).getIdTask())){
						return i/record + 1;
					}
				}
				return null;
			}
			
		});
		// method changPublic
		Mockito.doAnswer(new Answer<List<TaskJob>>() {

			public List<TaskJob> answer(InvocationOnMock invocation) throws Throwable {
				String idTask = (String) invocation.getArguments()[0];
				List<TaskJob> list = (List<TaskJob>) invocation
						.getArguments()[1];
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getIdTask().equals(idTask)){
						list.get(i).setPublicTask(true);
					}
				}
				return list;
			}
		}).when(accountDao).changePublic(Mockito.anyString(), Mockito.anyList());
		
		//----method getlist(list,currentPage,recordInPage)
		
		Mockito.when(accountDao.list(Mockito.anyList(), Mockito.anyInt(), Mockito.anyInt())).then(
				new  Answer<List<TaskJob>>(){

					public List<TaskJob> answer(InvocationOnMock invocation)
							throws Throwable {
						List<TaskJob> list = (List<TaskJob>) invocation.getArguments()[0];
						int currentPage = (Integer) invocation.getArguments()[1];
						int recordInPage = (Integer) invocation.getArguments()[2];
						List<TaskJob> listReturn = new ArrayList<TaskJob>();
						
						for (int i = 0;i < recordInPage;i++){
							if ((currentPage*recordInPage + i) < list.size())
							listReturn.add(list.get(currentPage*recordInPage + i));
						}
						return listReturn;
					}
				}
				);
		//----method changStatus
		Mockito.doAnswer(new Answer<List<TaskJob>>() {

			public List<TaskJob> answer(InvocationOnMock invocation) throws Throwable {
				String idTask = (String) invocation.getArguments()[0];
				String status = (String) invocation.getArguments()[1];
				List<TaskJob> list = (List<TaskJob>) invocation
						.getArguments()[2];
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getIdTask().equals(idTask)){
						list.get(i).setStatusTask(status);
					}
				}
				return list;
			}
		}).when(accountDao).changeStatus(Mockito.anyString(),Mockito.anyString() ,Mockito.anyList());
		
		//-----save or update
		Mockito.doAnswer(new Answer<List<TaskJob>>() {

			public List<TaskJob> answer(InvocationOnMock invocation) throws Throwable {
				TaskJob task = (TaskJob) invocation.getArguments()[0];
				List<TaskJob> list = (List<TaskJob>) invocation
						.getArguments()[1];
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getIdTask().equals(task.getIdTask()))
						list.remove(i);
				}
				list.add(task);
				return list;
			}
		}).when(accountDao).saveOrUpdate(Mockito.any(TaskJob.class), Mockito.anyList());
		
		//-------------
		
		
	}

	//---------------------------------------------------------
	@Test
	public void testList() {
		List<TaskJob> list = accountService.list();
		Assert.assertEquals(list.size(), 11);
	}

	 @Test
	 public void testSaveOrUpdate() {
	 List<TaskJob> list = accountService.list();
	 TaskJob task = new TaskJob("Task_1","No Learn",new Date(new java.util.Date().getTime()),"Done!",true);
	 accountService.saveOrUpdate(task, list);
	 Assert.assertTrue("No Learn".equals(accountService.getTask("Task_1", list).getNameTask()));
	 }
	
	 @Test
	 public void testDelete() {
		 List<TaskJob> list = accountService.list();
		 accountService.delete("Task_10", list);
		 Assert.assertEquals(list.size(), 10);
	 }

	//
	@Test
	public void testGetTask() {
		List<TaskJob> list = accountService.list();
		String id = "Task_1";
		TaskJob task = accountService.getTask(id, list);
		Assert.assertTrue(id.equals(task.getIdTask()));
	}

	//
	 @Test
	 public void testSearch() {
		 List<TaskJob> list = accountService.list();
		 String key = "Learn_1";
		 List<TaskJob> listReturn = accountService.search(key, list);
		 Assert.assertTrue(listReturn.size() == 2);
	 }
	//
	 @Test
	 public void testSaveJob() {
	 List<TaskJob> list = accountService.list();
	//----------save all job
	Mockito.doAnswer(new Answer() {

		public Object answer(InvocationOnMock invocation) throws Throwable {
			List<TaskJob> list = (List<TaskJob>) invocation
							.getArguments()[0];
			List<TaskJob> listDB = new ArrayList<TaskJob>();
				for (int i = 0; i < list.size(); i++) {
						listDB.add(list.get(i));
					}
					Assert.assertEquals(listDB.size(),11);
					return null;
				}
			}).when(accountDao).saveJob(Mockito.anyList());
	 accountService.saveJob(list);
	 }
	//
	 @Test
	 public void testGetMaxPage() {
	 List<TaskJob> list = accountService.list();
	 int record = 3;
	 int maxPage = accountService.getMaxPage(list, record);
	 Assert.assertTrue(maxPage == 4);
	 }
	// get list with page
	 @Test
	 public void testListListOfTaskJobIntInt() {
	 List<TaskJob> list  = accountService.list();
	 int record = 3;
	 int maxPage = accountService.getMaxPage(list, record);
	 List<TaskJob> listCurrent = accountService.list(list, maxPage - 1, record);
	 Assert.assertTrue(listCurrent.size() == 2);
	 }
	
	 @Test
	 public void testCheckTask() {
	 List<TaskJob> list = accountService.list();
	 Assert.assertTrue(accountService.checkTask("Task_10", list));
	 }
	//
	 @Test
	 public void testGetCurrentPage() {
	 List<TaskJob> list = accountService.list();
	 Assert.assertTrue(accountService.getCurrentPage("Task_4", list, 3) == 2);
	 }
	
	 @Test
	 public void testChangePublic() {
	 List<TaskJob> list = accountService.list();
	 accountService.changePublic("Task_2", list);
	 TaskJob task = accountService.getTask("Task_2", list);
	 Assert.assertTrue(task.isPublicTask());
	 }
	//
	 @Test
	 public void testChangeStatus() {
	 List<TaskJob> list = accountService.list();
	 accountService.changeStatus("Task_1", "Do not.", list);
	 Assert.assertTrue("Do not.".equals(accountService.getTask("Task_1", list).getStatusTask()));
	 }

}
