package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import workflow.Student;

@Controller
public class StudentController {

    @RequestMapping(value = "admissionForm.html", method = RequestMethod.GET)
    public ModelAndView getAdmissionForm(){
        ModelAndView modelAndView = new ModelAndView("AdmissionForm");
        return modelAndView;
    }

    @RequestMapping(value = "submitAdmissionForm.html", method = RequestMethod.POST)
//    public ModelAndView submitAdmissionForm(@RequestParam(value = "studentName", defaultValue = "Steve Jobs") String name,
//                                            @RequestParam(value = "studentHobby", defaultValue = "Engineering") String hobby){
      public ModelAndView submitAdmissionForm(@ModelAttribute("student1") Student student1){
        ModelAndView modelAndView = new ModelAndView("AdmissionSuccess");
//        modelAndView.addObject("msg", "Details: Name " + name + "; Hobby " + hobby);

        return modelAndView;
    }
}
