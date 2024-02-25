package com.kalvi.tests.service;

import com.example.kalvi.dto.*;
import com.example.kalvi.entity.*;
import com.example.kalvi.entity.Module;
import com.example.kalvi.exception.CourseNotFoundException;
import com.example.kalvi.repository.CourseRepository;
import com.example.kalvi.repository.ModuleRepository;
import com.example.kalvi.repository.StudentRepository;
import com.example.kalvi.service.CourseService;
import org.checkerframework.checker.units.qual.C;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

public class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(description = "It test whether all the records are returned when calling getAllCourse method")
    public void testGetAllCourses() {
        List<Course> courses = new ArrayList<>();
        when(courseRepository.findAll()).thenReturn(courses);
        List<CourseDTO> result = courseService.getAllCourses();
        verify(courseRepository).findAll();
        assertEquals(result.size(), courses.size());
    }

    @Test(description = "It tests whether calling create course will save the course information")
    public void testCreateCourse() {
        Course course = new Course();
        when(courseRepository.save(course)).thenReturn(course);
        Course result = courseService.createCourse(course);
        verify(courseRepository).save(course);
        assertEquals(result, course);
    }

    @Test(description = "Test mapping from Course entity to CourseDTO")
    public void testMapEntityToDTO() {
        // Create a sample Course entity
        Course course = createSampleCourseEntity();

        // Call mapEntityToDTO method to map the entity to DTO
        List<CourseDTO> courseDTOList = new ArrayList<>();
        CourseService courseService = new CourseService();
        courseService.mapEntityToDTO(List.of(course), courseDTOList);

        // Assert that the mapped DTO object has the correct properties
        assertEquals(courseDTOList.size(), 1);
        CourseDTO courseDTO = courseDTOList.get(0);
        assertEquals(courseDTO.getCourseName(), "Kalvi Course");
        assertEquals(courseDTO.getId(), 1L);
        assertEquals(courseDTO.getPrice(), 68);
        assertEquals(courseDTO.getDescription(), "Kalvi Description");
        assertEquals(courseDTO.getLanguages().get(0), "English");

        // Assert that ratings are mapped correctly
        assertEquals(courseDTO.getRatings().size(), 1);
        RatingDTO ratingDTO = courseDTO.getRatings().get(0);
        assertEquals(ratingDTO.getId(), 1L);
        assertEquals(ratingDTO.getRating(), 4);

        // Assert that modules are mapped correctly
        assertEquals(courseDTO.getModules().size(), 1);
        ModuleDTO moduleDTO = courseDTO.getModules().get(0);
        assertEquals(moduleDTO.getId(), 1L);
        assertEquals(moduleDTO.getName(), "Sample Module");
        assertEquals(moduleDTO.getDuration(), 30);

        // Assert that topics are mapped correctly
        assertEquals(moduleDTO.getTopics().size(), 1);
        TopicDTO topicDTO = moduleDTO.getTopics().get(0);
        assertEquals(topicDTO.getId(), 1L);
        assertEquals(topicDTO.getName(), "Sample Topic");
        assertEquals(topicDTO.getVideoUrls(), List.of("url1", "url2"));
        assertEquals(topicDTO.getData().get(0), "Sample Data");

        // Assert that quizzes are mapped correctly
        assertEquals(courseDTO.getQuizzes().size(), 1);


        // Assert that assignments are mapped correctly
        assertEquals(courseDTO.getAssignments().size(), 1);

    }

    // Helper method to create a sample Course entity for testing
    private Course createSampleCourseEntity() {
        Course course = new Course();
        course.setId(1L);
        course.setCourseName("Kalvi Course");
        course.setPrice(68);
        course.setDescription("Kalvi Description");
        course.setLanguages(Arrays.asList("English","Tamil"));

        // Add a sample rating
        Rating rating = new Rating();
        rating.setId(1L);
        rating.setRating(4);
        rating.setCreatedAt(LocalDateTime.of(2024, 2, 25, 10, 15, 30));
        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);
        course.setRatings(ratings);

        // Add a sample module
        Module module = new Module();
        module.setId(1L);
        module.setName("Sample Module");
        module.setDuration(30);

        // Add a sample topic to the module
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setName("Sample Topic");
        topic.setVideoUrls(List.of("url1", "url2"));
        topic.setData(Arrays.asList("Sample Data"));
        List<Topic> topics = new ArrayList<>();
        topics.add(topic);
        module.setTopics(topics);
        List<Module> modules = new ArrayList<>();
        modules.add(module);
        course.setModules(modules);

        // Add a sample quiz
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setTitle("Sample Quiz");
        Question question = new Question();
        question.setQuestionText("Who is the father of computer");
        question.setOptions(Arrays.asList("Charles","Venat","Madhan"));
        List<Question> questionList = new ArrayList<>();
        questionList.add(question);
        quiz.setQuestions(questionList);
        course.getQuizzes().add(quiz);

        // Add a sample assignment
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setAssignmentTitle("Sample Assignment");
        course.getAssignments().add(assignment);

        return course;
    }


    @Test
    public void testAddModuleToCourse() {
        Course course = new Course();
        course.setId(1L);
        Module module = new Module();
        ArrayList<Module> modules = new ArrayList<>();
        modules.add(module);
        course.setModules(modules);

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);


        Course result = courseService.addModuleToCourse(1L, module);

        assertTrue(result.getModules().contains(module));
        verify(courseRepository, times(1)).save(course);

        // test course without ratings in it : Scenario for first time
        Course course1 = new Course();
        course1.setId(1L);
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course1));
        when(courseRepository.save(course1)).thenReturn(course1);

        Course result1 = courseService.addModuleToCourse(1L, module);
        assertTrue(result1.getModules().contains(module));
    }
    @Test
    public void testAddModuleToCourseWithTopic() {
        Course course = new Course();
        course.setId(1L);
        Module module = new Module();
        ArrayList<Module> modules = new ArrayList<>();
        modules.add(module);
        course.setModules(modules);
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setModule(module);
        List<Topic> topics = new ArrayList<>();
        topics.add(topic);
        module.setTopics(topics);

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);


        Course result = courseService.addModuleToCourse(1L, module);

        assertTrue(result.getModules().contains(module));
        verify(courseRepository, times(1)).save(course);

        // test course without ratings in it : Scenario for first time
        Course course1 = new Course();
        course1.setId(1L);
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course1));
        when(courseRepository.save(course1)).thenReturn(course1);

        Course result1 = courseService.addModuleToCourse(1L, module);
        assertTrue(result1.getModules().contains(module));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddModuleToCourseNotFound() {
        courseService.addModuleToCourse(1L, new Module());
    }

    @Test
    public void testAddRatingToCourse() {
        Course course = new Course();
        course.setId(1L);
        Rating rating = new Rating();
        ArrayList<Rating> ratings = new ArrayList<>();
        ratings.add(rating);
        course.setRatings(ratings);

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.addRatingToCourse(1L, rating);

        assertTrue(result.getRatings().contains(rating));
        verify(courseRepository, times(1)).save(course);

        Course course1 = new Course();
        course1.setId(1L);
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course1));
        when(courseRepository.save(course1)).thenReturn(course1);

        Course result1 = courseService.addRatingToCourse(1L, rating);
        assertTrue(result1.getRatings().contains(rating));

    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddRatingToCourseNotFound() {
        courseService.addRatingToCourse(1L, new Rating());
    }

    @Test
    public void testAddQuizToCourse() {
        Course course = new Course();
        Quiz quiz = new Quiz();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        Quiz result = courseService.addQuizToCourse(1L, quiz);

        assertNotNull(result.getCourse());
        assertTrue(course.getQuizzes().contains(result));
        verify(courseRepository, times(1)).save(course);
    }

    @Test(expectedExceptions = CourseNotFoundException.class)
    public void testAddQuizToCourseNotFound() {
        courseService.addQuizToCourse(1L, new Quiz());
    }

    @Test
    public void testAddAssignmentToCourse() {
        Course course = new Course();
        Assignment assignment = new Assignment();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        Assignment result = courseService.addAssignmentToCourse(1L, assignment);

        assertNotNull(result.getCourse());
        assertTrue(course.getAssignments().contains(result));
        verify(courseRepository, times(1)).save(course);
    }

    @Test(expectedExceptions = CourseNotFoundException.class)
    public void testAddAssignmentToCourseNotFound() {
        courseService.addAssignmentToCourse(1L, new Assignment());
    }

    @Test
    public void testIsStudentEnrolledForCourse() {
        Student student = new Student();
        student.setId(1L);
        Course course = new Course();
        course.setId(2L);
        student.getCourses().add(course);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        boolean result = courseService.isStudentEnrolledForCourse(1L, 2L);

        assertTrue(result);
    }

    @Test
    public void testIsStudentNotEnrolledForCourse() {
        Student student = new Student();
        student.setId(1L);
        Course course = new Course();
        course.setId(2L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        boolean result = courseService.isStudentEnrolledForCourse(1L, 2L);

        assertFalse(result);
    }

    @Test
    public void testIsStudentEnrolledForNonExistingCourse() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = courseService.isStudentEnrolledForCourse(1L, 2L);

        assertFalse(result);
    }
}
