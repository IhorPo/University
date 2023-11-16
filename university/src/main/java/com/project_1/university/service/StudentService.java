package com.project_1.university.service;

import com.project_1.university.dataAccessLayer.StudentRepository;
import com.project_1.university.exceptions.ServiceException;
import com.project_1.university.models.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class StudentService {

    private static final Logger logger = LogManager.getLogger(StudentService.class);

    private final StudentRepository repository;
    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAll(){
        return repository.findAll();
    }

    public Student findById(Integer id){
        return repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No such student")
        );
    }

    public Student add(Student student){
        try {
            return repository.save(student);
        } catch (Exception e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public Student update(Student student){
        if (repository.existsById(student.getId())) {
            return repository.save(student);
        } else {
            throw new NoSuchElementException("No such student");
        }
    }

    public void delete(Integer id){
        Student student = repository.findById(id).orElseThrow(
                () ->new NoSuchElementException("No such student")
        );
        repository.delete(student);
    }
}
