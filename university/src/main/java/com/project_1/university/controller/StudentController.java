package com.project_1.university.controller;

import com.project_1.university.dataAccessLayer.StudentRepository;
import com.project_1.university.exceptions.StudentNotFoundException;
import com.project_1.university.models.Student;
import com.project_1.university.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("university/students")
@RequiredArgsConstructor
public class StudentController {


    private final StudentService service;

    private final StudentRepository repository;


    @GetMapping
    public String getAll(Model model){
        model.addAttribute("students",service.getAll());
        return "students";
    }

    @GetMapping("/{id}")
    public String studentById(@PathVariable("id") Integer id, Model model){
        model.addAttribute("student",service.findById(id));
        return "student";
    }

    @GetMapping("/new")
    public String form(Model model){
        model.addAttribute("student",new Student());
        return "studentForm";
    }

    @PostMapping
    public String save(Student student){
        service.add(student);
        return "redirect:/university/students";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        if (repository.existsById(id)) {
            Student student = service.findById(id);
            model.addAttribute("student",student);
            return "editStudent";
        }
        throw new StudentNotFoundException(String.format("Student with id: %s not found.", id));
    }

    @PostMapping("/editSave")
    public String editSave(@ModelAttribute("student") Student student){
        service.update(student);
        return "redirect:/university/students";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        service.delete(id);
        return "redirect:/university/students";
    }
}
