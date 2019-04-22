package com.hyk.code.common.threadpool;

import org.springframework.core.task.TaskExecutor;

/** 
* @author  霍中曦
* @Description: TODO(spring 线程异步执行任务) 
* @2016-10-19下午3:42:15
*/ 
public class TaskExecutorExample {
	
	private TaskExecutor taskExecutor;
	//通过构造方法注入线程
	public TaskExecutorExample(TaskExecutor taskExecutor) {    
		this.taskExecutor = taskExecutor;  
	}
	

	
/*	public void saveEducategory(EducategoryService educategoryService,Educategory educategory){   
		taskExecutor.execute(new SaveEducategoryTask(educategoryService,educategory));    
	}*/
	
	

	
/*	private class SaveEducategoryTask implements Runnable {    
		private EducategoryService educategoryService ;
		private Educategory educategory;
		public SaveEducategoryTask(EducategoryService educategoryService,Educategory educategory) {      
			this.educategoryService =educategoryService;    
			this.educategory=educategory;
		}    
		public void run() {      
			educategoryService.add(educategory);
		}
	}*/
}
