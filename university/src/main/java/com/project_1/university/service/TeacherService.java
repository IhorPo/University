package com.project_1.university.service;

import com.project_1.university.dataAccessLayer.TeacherRepository;
import com.project_1.university.models.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository repository;

    public TeacherService(TeacherRepository repository) {
        this.repository = repository;
    }

    public List<Teacher> getAll(){
        return repository.findAll();
    }

    public Teacher getById(Integer id){
        return repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No such teacher")
        );
    }

    public Teacher add(Teacher teacher){
        return repository.save(teacher);
    }

    public Teacher update(Teacher teacher){
        Optional<Teacher> teacher1 = repository.findById(teacher.getId());
        if(teacher1.isPresent()){
            Teacher teacher2 = teacher1.get();
            teacher2.setName(teacher.getName());
            teacher2.setSurname(teacher.getSurname());
            teacher2.setEmail(teacher.getEmail());
            return repository.save(teacher2);
        } else {
            throw new NoSuchElementException("No such teacher");
        }
    }

    public void delete(Integer id){
        Teacher teacher = repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No such teacher")
        );
        repository.delete(teacher);
    }
}
