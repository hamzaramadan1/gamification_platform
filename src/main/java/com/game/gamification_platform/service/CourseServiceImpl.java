package com.game.gamification_platform.service;

import com.game.gamification_platform.model.Course;
import com.game.gamification_platform.model.User;
import com.game.gamification_platform.repository.CourseRepository;
import com.game.gamification_platform.repository.StorageRepository;
import com.game.gamification_platform.repository.UserRepository;
import com.game.gamification_platform.utils.ImageUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final StorageRepository storageRepository;

    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository, StorageRepository storageRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.storageRepository = storageRepository;
    }

    @Override
    public Course saveCourse(String courseName, String courseDescription, String courseLink, MultipartFile file) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = StringUtils.getFilenameExtension(fileName);
        String newFileName = UUID.randomUUID().toString() + "." + fileExtension;
        String uploadDir = "C:\\Users\\hramadan\\angular-gamification_platform\\src\\assets\\course_images";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(newFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        Course course = Course.builder()
                .courseName(courseName)
                .courseDescription(courseDescription)
                .courseLink(courseLink)
                .file(ImageUtils.compressImage(file.getBytes()))
                .imageName(newFileName)
                .imageType(file.getContentType())
                .user(user)
                .build();
        return courseRepository.save(course);
    }

    @Override
    public Course saveCourseTest(String courseName, String courseDescription, String courseLink) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        Course course = Course.builder()
                .courseName(courseName)
                .courseDescription(courseDescription)
                .courseLink(courseLink)
                .user(user)
                .build();
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findAllCoursesForUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        return courseRepository.findByUser(user);
    }

    @Override
    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }


    @Override
    public int findAllCoursesForUserCount() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElse(null);
        List<Course> courses =  courseRepository.findByUser(user);
        return courses.size();

    }

}
