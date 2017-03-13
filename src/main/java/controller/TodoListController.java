package controller;

import entities.DAO;
import entities.FilterState;
import entities.Task;
import entities.TaskEntity;
import org.springframework.beans.factory.annotation.Required;
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

    {
        filterList.add("All");
        filterList.add("Done");
        filterList.add("Undone");

        filterState.setFilterSelected("Undone");
    }

    @RequestMapping(value = "todolist.html", method = RequestMethod.GET)
    public ModelAndView todoListShow(){
        ModelAndView modelAndView = new ModelAndView("index");
        List<Task> tasks = dao.getTaskList(filterState.getFilterSelected());
        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("filterState", filterState);
        modelAndView.addObject("filterList", filterList);
        return modelAndView;
    }

    @RequestMapping(value = "filter", method = RequestMethod.POST)
    public String filterTasks(@ModelAttribute FilterState filterState1){
//        ModelAndView modelAndView = new ModelAndView("index");
//        List<Task> tasks = new ArrayList<Task>();
//        modelAndView.addObject("tasks", tasks);
//        filterState = request.get
        filterState = filterState1;
        System.out.println("Filter controller invoked!");
        return "redirect:/todolist.html";
    }

    @RequestMapping("hello/{countryName}/{userName}")
//    public ModelAndView todoListHello(@PathVariable Map<String, String> vars){
    public ModelAndView todoListHello(@PathVariable("countryName") String country,
                                      @PathVariable("userName") String user){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("msg", "Hello, " + user + " from " + country);
        System.out.println("test text");
        return modelAndView;
    }

    @RequestMapping(value="updateTask/add", method = RequestMethod.POST)
    public String addTask(){
        System.out.println("Add method invoked");
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setText("defaultText");
        dao.add(taskEntity);
        return "redirect:/todolist.html";
    }

    @RequestMapping(value="updateTask/{taskId}/{operation}", method = RequestMethod.GET)
    public String undoneTask(@PathVariable("taskId") int taskId,
                             @PathVariable("operation") String op){
//        if (op.equals("edit")) {
//            System.out.println("Edit method invoked for taskNum = " + taskId);
//            int id = dao.getTaskList().get(taskId).getIdTask();
//            TaskEntity updatedTask = dao.getTaskById(id);
//            updatedTask.setText(task.getText());
//            dao.update(updatedTask, dao.getStatusByTaskId(id).getStatus() != 0);
//        } else
            if (op.equals("delete")) {
            System.out.println("Delete method invoked for taskNum = " + taskId);
            dao.delete(dao.getTaskList("All").get(taskId).getIdTask());
        } else if (op.equals("done")) {
            System.out.println("Done method invoked for taskNum = " + taskId);
            dao.update(dao.getTaskById(dao.getTaskList("All").get(taskId).getIdTask()), true);
        } else if (op.equals("undone")) {
            System.out.println("Undone method invoked for taskNum = " + taskId);
            dao.update(dao.getTaskById(dao.getTaskList("All").get(taskId).getIdTask()), false);
        }

        return "redirect:/todolist.html";
    }

    @RequestMapping(value="updateTask/{taskId}/{operation}", method = RequestMethod.POST)
    public String saveTask(@PathVariable("taskId") int taskId,
                             @PathVariable("operation") String op,
                             @RequestParam("text") String newText){
        if (op.equals("edit")) {
            System.out.println("Edit method invoked for taskNum = " + taskId + " with new text = " + newText);
            int id = dao.getTaskList("All").get(taskId).getIdTask();
            TaskEntity updatedTask = dao.getTaskById(id);
            updatedTask.setText(newText);
            dao.update(updatedTask, dao.getStatusByTaskId(id).getStatus() != 0);
        }

        return "redirect:/todolist.html";
    }
}
