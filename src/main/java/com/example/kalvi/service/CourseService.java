package com.example.kalvi.service;

import com.example.kalvi.dto.*;
import com.example.kalvi.entity.*;
import com.example.kalvi.entity.Module;
import com.example.kalvi.exception.CourseNotFoundException;
import com.example.kalvi.repository.CourseRepository;
import com.example.kalvi.repository.ModuleRepository;
import com.example.kalvi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private StudentRepository studentRepository;
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOList = new ArrayList<>();
        mapEntityToDTO(courses, courseDTOList);
        return  courseDTOList;
    }

    public void mapEntityToDTO(List<Course> courses, List<CourseDTO> courseDTOList) {
        for (Course course : courses) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseName(course.getCourseName());
            courseDTO.setId(course.getId());
            courseDTO.setPrice(course.getPrice());
            courseDTO.setDescription(course.getDescription());
            courseDTO.setLastUpdated(course.getLastUpdated());
            courseDTO.setLanguages(course.getLanguages());
            for (Rating rating : course.getRatings()) {
                RatingDTO ratingDTO = new RatingDTO();
                ratingDTO.setId(rating.getId());
                ratingDTO.setRating(rating.getRating());
                ratingDTO.setCreatedAt(rating.getCreatedAt());
                courseDTO.getRatings().add(ratingDTO);
            }
            for (Module module : course.getModules()) {
                ModuleDTO moduleDTO = new ModuleDTO();
                moduleDTO.setId(module.getId());
                moduleDTO.setDuration(module.getDuration());
                moduleDTO.setId(module.getId());
                moduleDTO.setName(module.getName());
                for (Topic topic : module.getTopics()) {
                    TopicDTO topicDTO = new TopicDTO();
                    topicDTO.setId(topic.getId());
                    topicDTO.setName(topic.getName());
                    topicDTO.setVideoUrls(topic.getVideoUrls());
                    topicDTO.setData(topic.getData());
                    moduleDTO.getTopics().add(topicDTO);
                }
                courseDTO.getModules().add(moduleDTO);
            }
            for (Quiz quiz : course.getQuizzes()) {
                QuizDTO quizDTO = new QuizDTO();
                quizDTO.setId(quiz.getId());
                quizDTO.setQuestions(quiz.getQuestions());
                quizDTO.setTitle(quiz.getTitle());
                courseDTO.getQuizzes().add(quizDTO);
            }
            for (Assignment assignment : course.getAssignments()) {
                AssignmentDTO assignmentDTO = new AssignmentDTO();
                assignmentDTO.setId(assignment.getId());
                assignmentDTO.setAssignmentTitle(assignment.getAssignmentTitle());
                courseDTO.getAssignments().add(assignmentDTO);
            }
            courseDTOList.add(courseDTO);
        }
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course addModuleToCourse(Long courseId, Module module) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            if(course.getModules() == null ) {
                course.setModules(new ArrayList<>());
            }
            module.setCourse(course);
            // persist topics along with modules
            if(module.getTopics() != null && !module.getTopics().isEmpty()){
                for (Topic topic : module.getTopics()){
                    topic.setModule(module);
                }
            }
            course.getModules().add(module);

            return courseRepository.save(course);
        } else {
            throw new IllegalArgumentException("Course not found with id: " + courseId);
        }
    }

    public Course addRatingToCourse(Long courseId, Rating rating) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            if(course.getRatings() == null){
                course.setRatings(new ArrayList<>());
            }
            course.getRatings().add(rating);
            return courseRepository.save(course);
        } else {
            throw new IllegalArgumentException("Course not found with id: " + courseId);
        }
    }

    public Quiz addQuizToCourse(Long courseId, Quiz quiz) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
        quiz.setCourse(course);
        course.getQuizzes().add(quiz);
        courseRepository.save(course);
        return quiz;
    }

    public Assignment addAssignmentToCourse(Long courseId, Assignment assignment) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
        assignment.setCourse(course);
        course.getAssignments().add(assignment);
        courseRepository.save(course);
        return assignment;
    }

    public boolean isStudentEnrolledForCourse(Long studentId, Long courseId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if(student.isPresent()){
            for(Course course :student.get().getCourses()){
                if (course.getId().equals(courseId)) {
                    return true;
                }
            }
        }
        return false;
    }
}