package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.models.Student;
import habsida.spring.boot_security.demo.services.RoleService;
import habsida.spring.boot_security.demo.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final RoleService roleService;


    @GetMapping(value = "/admin/adminPage")
    public ModelAndView showUsers() {
        ModelAndView mov = new ModelAndView("/adminPage");
        mov.addObject("students", studentService.listStudents());
        return mov;
    }

    @GetMapping("/admin/new")
    public ModelAndView newPerson(@ModelAttribute("student") Student student) {
        ModelAndView mov = new ModelAndView("/new");
        mov.addObject("roles", roleService.listRoles());
        return mov;
    }

    @PostMapping("/admin/adminPage")
    public String create(@ModelAttribute("student") Student student) {
        studentService.add(student);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/admin/edit")
    public ModelAndView edit(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("/edit");
        Student student = studentService.studentById(id).get();
        mav.addObject("student", student);
        mav.addObject("roles", roleService.listRoles());
        return mav;
    }

    @PostMapping("/admin/edit")
    public String update(@ModelAttribute("student") Student student) {
        studentService.update(student);
        return "redirect:/admin/adminPage";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam long id) {
        studentService.remove(id);
        return "redirect:/admin/adminPage";
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    @GetMapping("/student")
    public ModelAndView user(Principal principal) {
        ModelAndView mov = new ModelAndView("/student");
        mov.addObject("student", studentService.findByName(principal.getName()));
        return mov;
    }
}
