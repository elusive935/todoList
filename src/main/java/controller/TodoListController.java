package controller;

import entities.DAO;
import entities.FilterState;
import entities.TaskEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import workflow.HibernateSessionFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TodoListController {
    private DAO dao = new DAO(HibernateSessionFactory.getSessionFactory());
    private FilterState filterState = new FilterState();
    private List<String> filterList = new ArrayList<String>();
    private int page = 1;

    {
        filterList.add("All");
        filterList.add("Done");
        filterList.add("Undone");

        filterState.setFilterSelected("All");
    }

    @RequestMapping(value = "todolist.html", method = RequestMethod.GET)
    public ModelAndView todoListShow(){
        ModelAndView modelAndView = new ModelAndView("index");
        List<TaskEntity> tasks = dao.getTaskList(filterState.getFilterSelected(), page);
        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("filterState", filterState);
        modelAndView.addObject("filterList", filterList);
        modelAndView.addObject("count", dao.getTaskCount(filterState.getFilterSelected()));
        modelAndView.addObject("currentPage", page);
        return modelAndView;
    }

    @RequestMapping(value = "filter", method = RequestMethod.POST)
    public String filterTasks(@ModelAttribute FilterState filterState1){
        filterState = filterState1;
        page = 1;
        System.out.println("Filter controller invoked!");
        return "redirect:/todolist.html";
    }

    @RequestMapping(value="add", method = RequestMethod.POST)
    public String addTask(){
        System.out.println("Add method invoked");
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setText("defaultText");
        taskEntity.setStatus(false);
        dao.add(taskEntity);
        return "redirect:/todolist.html";
    }

    @RequestMapping(value="updateTask/{taskId}", method = RequestMethod.POST, params = "edit")
    public String editTask(HttpServletRequest request, @PathVariable("taskId") int taskId){
        System.out.println("Edit method invoked for taskNum = " + taskId + " with new text = " + request.getParameter("edit"));
        int id = dao.getTaskList("All", page).get(taskId).getIdTask();
        TaskEntity updatedTask = dao.getTaskById(id);
        updatedTask.setText(request.getParameter("edit"));
        dao.update(updatedTask, dao.getTaskById(id).isStatus());

        return "redirect:/todolist.html";
    }

    @RequestMapping(value="updateTask/{taskId}", method = RequestMethod.POST, params = "delete")
    public String deleteTask(@PathVariable("taskId") int taskId){
        System.out.println("Delete method invoked for taskNum = " + taskId);
        dao.delete(dao.getTaskList(filterState.getFilterSelected(), page).get(taskId).getIdTask());

        return "redirect:/todolist.html";
    }

    @RequestMapping(value="updateTask/{taskId}", method = RequestMethod.POST, params = "done")
    public String doneTask(@PathVariable("taskId") int taskId){
        System.out.println("Done method invoked for taskNum = " + taskId);
        dao.update(dao.getTaskById(dao.getTaskList(filterState.getFilterSelected(), page).get(taskId).getIdTask()), true);

        return "redirect:/todolist.html";
    }

    @RequestMapping(value="updateTask/{taskId}", method = RequestMethod.POST, params = "undone")
    public String undoneTask(@PathVariable("taskId") int taskId){
        System.out.println("Undone method invoked for taskNum = " + taskId);
        dao.update(dao.getTaskById(dao.getTaskList(filterState.getFilterSelected(), page).get(taskId).getIdTask()), false);

        return "redirect:/todolist.html";
    }

    @RequestMapping(value="paging/{pageNum}", method = RequestMethod.GET)
    public String paging(@PathVariable("pageNum") int pageNum) {
        System.out.println("Paging method invoked for page number " + pageNum);
        page = pageNum;

        return "redirect:/todolist.html";
    }
}
