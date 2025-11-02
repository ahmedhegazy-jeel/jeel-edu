package com.jeeleducation.lms.controller;


import org.springframework.web.bind.annotation.*;

import com.jeeleducation.lms.controller.minimax.AchievementDTO;
import com.jeeleducation.lms.controller.minimax.CurriculumDTO;
import com.jeeleducation.lms.controller.minimax.LessonContentDTO;
import com.jeeleducation.lms.controller.minimax.LessonPathDTO;
import com.jeeleducation.lms.controller.minimax.LessonSpotDTO;
import com.jeeleducation.lms.controller.minimax.StudentProfileDTO;
import com.jeeleducation.lms.controller.minimax.UnitDTO;
import com.jeeleducation.lms.controller.minimax.model.ItemType;
import com.jeeleducation.lms.controller.minimax.model.LessonContentBlockDTO;
import com.jeeleducation.lms.controller.minimax.model.LessonType;
import com.jeeleducation.lms.controller.minimax.model.Rarity;
import com.jeeleducation.lms.controller.minimax.model.Subject;

 
import java.util.*;

@RestController
@RequestMapping("/student/minimax")
@CrossOrigin(origins = "*")
public class StudentMiniMaxController {
    
    @GetMapping("/profile/{studentId}")
    public StudentProfileDTO getStudentProfile(@PathVariable Long studentId) {
        StudentProfileDTO profile = new StudentProfileDTO();
        profile.setId(studentId);
        profile.setUsername("ahmed2024");
        profile.setFirstName("Ahmed");
        profile.setLastName("Hassan");
        profile.setAvatarUrl("/images/assets/avatars/bear-explorer.png");
        profile.setSchoolName("Sunshine Elementary");
        profile.setTotalPoints(1250);
        profile.setCurrentLevel(5);
        profile.setCurrentStreak(7);
        profile.setLastActivityDate(java.time.LocalDate.now());
        profile.setTotalLessonsCompleted(45);
        profile.setTotalTimeSpentHours(32);
        profile.setCurrentStreakStatus("Excellent! Keep it up!");
        profile.setLevelProgress("You need 250 more points to reach Level 6");
        
        // Add some achievements
        List<AchievementDTO> achievements = Arrays.asList(
            createAchievement("math-champion", "Math Champion", "Successfully completed 10 math lessons"),
            createAchievement("streak-master", "Streak Master", "Maintained 7-day learning streak"),
            createAchievement("knowledge-gem", "Knowledge Seeker", "Earned your first knowledge gem")
        );
        profile.setRecentAchievements(achievements);
        profile.setOwnedAvatars(new String[]{"bear-explorer", "cat-scholar", "fox-adventurer"});
        
        return profile;
    }
    
    @GetMapping("/curriculums")
    public List<CurriculumDTO> getCurriculums(@RequestParam Long schoolId) {
        return Arrays.asList(
            createCurriculum(1L, "Math Adventures", "MATH", "Grade 3", 6, 75, 450, "/images/assets/subjects/subject-math.png"),
            createCurriculum(2L, "English Explorer", "ENGLISH", "Grade 3", 8, 60, 380, "/images/assets/subjects/subject-english.png"),
            createCurriculum(3L, "Arabic Discovery", "ARABIC", "Grade 3", 5, 90, 420, "/images/assets/subjects/subject-arabic.png")
        );
    }
    
    @GetMapping("/units/{curriculumId}")
    public List<UnitDTO> getUnits(@PathVariable Long curriculumId) {
        return Arrays.asList(
            createUnit(1L, 1, "Number Magic", "Learn about numbers and counting in a magical way", "magical-forest", 8, 6, 400, 300, "/images/assets/backgrounds/adventure-sky.png"),
            createUnit(2L, 2, "Shape Kingdom", "Discover different shapes in the kingdom", "shape-realm", 10, 4, 500, 150, "/images/assets/backgrounds/adventure-sky.png"),
            createUnit(3L, 3, "Addition Quest", "Master addition through exciting quests", "addition-quest", 6, 2, 300, 100, "/images/assets/backgrounds/adventure-sky.png")
        );
    }
    
    @GetMapping("/lesson-path/{unitId}")
    public LessonPathDTO getLessonPath(@PathVariable Long unitId) {
        // Dynamic lesson path based on number of lessons in unit
        LessonPathDTO path = new LessonPathDTO();
        path.setPathType("curved");
        path.setPathColor("#4ecdc4");
        path.setBackgroundTheme("adventure-sky");
        
        // Create lesson spots based on unit
        List<LessonSpotDTO> lessons = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            lessons.add(createLessonSpot(
                (long)i, 
                i, 
                "Lesson " + i + ": " + getLessonTitle(i),
                i <= 2 ? "ACTIVE" : i <= 6 ? "COMPLETED" : "LOCKED",
                i == 8 ? true : false, // Last lesson is boss
                i <= 2 ? 3 : 0, // Stars earned
                i == 1 || i == 8 ? i : i - 1 // Position on path
            ));
        }
        path.setLessons(lessons);
        path.setSvgPath("M50,200 C100,100 200,300 300,200 S500,100 600,200");
        path.setStartIcon("/images/assets/icons/start-flag.png");
        path.setEndIcon("/images/assets/icons/castle-endpoint.png");
        path.setTotalPathLength(550);
        
        return path;
    }
    
    @GetMapping("/lesson-content/{lessonId}")
    public LessonContentDTO getLessonContent(@PathVariable Long lessonId) {
        return createLessonContent(lessonId);
    }
    
    @PostMapping("/progress/update")
    public ProgressUpdateResponse updateProgress(@RequestBody ProgressUpdateRequest request) {
        ProgressUpdateResponse response = new ProgressUpdateResponse();
        response.setSuccess(true);
        response.setNewPoints(request.getPointsEarned());
        response.setNewLevelAchieved(false);
        response.setNewBadgeEarned(null);
        
        if (request.getStarsEarned() == 3) {
            response.setNewBadgeEarned("perfect-score");
        }
        
        return response;
    }
    
    private CurriculumDTO createCurriculum(Long id, String name, String subject, String grade, Integer totalUnits, Integer progress, Integer points, String iconUrl) {
        CurriculumDTO curriculum = new CurriculumDTO();
        curriculum.setId(id);
        curriculum.setName(name);
        curriculum.setSubject(Subject.valueOf(subject));
        curriculum.setGradeLevel(grade);
        curriculum.setTotalUnits(totalUnits);
        curriculum.setProgressPercentage(progress);
        curriculum.setTotalPointsEarned(points);
        curriculum.setSubjectIcon(iconUrl);
        curriculum.setDescription("Fun and engaging " + subject.toLowerCase() + " learning journey");
        return curriculum;
    }
    
    private UnitDTO createUnit(Long id, Integer unitNumber, String title, String description, String theme, Integer totalLessons, Integer completedLessons, Integer totalPoints, Integer pointsEarned, String backgroundImage) {
        UnitDTO unit = new UnitDTO();
        unit.setId(id);
        unit.setUnitNumber(unitNumber);
        unit.setTitle(title);
        unit.setDescription(description);
        unit.setTheme(theme);
        unit.setThemeIcon("/images/assets/icons/unit-" + theme + ".png");
        unit.setTotalLessons(totalLessons);
        unit.setCompletedLessons(completedLessons);
        unit.setTotalPoints(totalPoints);
        unit.setPointsEarned(pointsEarned);
        unit.setEstimatedMinutes(totalLessons * 15);
        unit.setBackgroundImage(backgroundImage);
        unit.setUnlockConditions(new String[]{"Complete previous unit", "Score 70% on assessment"});
        return unit;
    }
    
    private LessonSpotDTO createLessonSpot(Long id, Integer lessonNumber, String title, String status, Boolean isBossLesson, Integer starsEarned, Integer position) {
        LessonSpotDTO spot = new LessonSpotDTO();
        spot.setId(id);
        spot.setLessonNumber(lessonNumber);
        spot.setTitle(title);
        spot.setType(isBossLesson ? LessonType.BOSS : LessonType.VIDEO);
        spot.setEstimatedMinutes(15);
        spot.setMaxStars(3);
        spot.setIsBossLesson(isBossLesson);
        spot.setIsLocked(status.equals("LOCKED"));
        spot.setIsCompleted(status.equals("COMPLETED"));
        spot.setIsActive(status.equals("ACTIVE"));
        spot.setStarsEarned(starsEarned);
        spot.setIconUrl(isBossLesson ? "/images/assets/icons/boss-battle.png" : "/images/assets/icons/lesson-spot.png");
        spot.setStatus(status.toLowerCase());
        spot.setPosition(position);
        spot.setLessonContentPreview("Interactive content ready!");
        return spot;
    }
    
    private AchievementDTO createAchievement(String id, String name, String description) {
        AchievementDTO achievement = new AchievementDTO();
        achievement.setId(System.currentTimeMillis());
        achievement.setName(name);
        achievement.setDescription(description);
        achievement.setIconUrl("/images/assets/badges/" + id + ".png");
        achievement.setRarity(Rarity.RARE);
        achievement.setType(ItemType.BADGE);
        achievement.setIsEarned(true);
        achievement.setEarnedAt(java.time.LocalDateTime.now());
        achievement.setDisplayName(name);
        achievement.setAchievementColor("#ffd700");
        return achievement;
    }
    
    private LessonContentDTO createLessonContent(Long lessonId) {
        LessonContentDTO content = new LessonContentDTO();
        content.setLessonId(lessonId);
        content.setTitle("Amazing Numbers Adventure");
        content.setType("VIDEO");
        content.setEstimatedMinutes(15);
        content.setMaxStars(3);
        
        List<LessonContentBlockDTO> blocks = Arrays.asList(
            createContentBlock(1, "TEXT", "Welcome to your number adventure! Today we'll explore the magical world of numbers.", null, null, null),
            createContentBlock(2, "VIDEO", null, "https://example.com/video/numbers.mp4", "Counting Fun Video", null),
            createContentBlock(3, "INTERACTIVE", "Let's practice counting! Click on the correct number!", "https://example.com/interactive/counting.html", "Counting Practice", null),
            createContentBlock(4, "IMAGE_OVERLAY", "Can you count how many stars are in the sky?", "https://example.com/images/starry-sky.jpg", null, "Count the stars!")
        );
        content.setContentBlocks(blocks);
        content.setAssessmentQuestions(Arrays.asList(
            "What comes after 5?",
            "How many fingers do you have on one hand?",
            "What is the smallest number you know?"
        ));
        return content;
    }
    
    private LessonContentBlockDTO createContentBlock(Integer order, String type, String textContent, String mediaUrl, String caption, String overlayText) {
        LessonContentBlockDTO block = new LessonContentBlockDTO();
        block.setDisplayOrder(order);
        block.setType(com.jeeleducation.lms.controller.minimax.model.ContentType.valueOf(type));
        block.setTextContent(textContent);
        block.setMediaUrl(mediaUrl);
        block.setCaption(caption);
        block.setOverlayText(overlayText);
        return block;
    }
    


    
    // ------------------------------------------------------

    private String getLessonTitle(Integer lessonNumber) {
        String[] titles = {
            "Meet the Numbers", "Counting Fun", "Number Friends", "Number Games",
            "Number Patterns", "Number Magic", "Number Quiz", "Boss Challenge"
        };
        return titles[Math.min(lessonNumber - 1, titles.length - 1)];
    }
    
    // Nested classes for request/response
    public static class ProgressUpdateRequest {
        private Long studentId;
        private Long lessonId;
        private Integer starsEarned;
        private Integer timeSpentSeconds;
        private Integer pointsEarned;
        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }
        public Long getLessonId() { return lessonId; }
        public void setLessonId(Long lessonId) { this.lessonId = lessonId; }
        public Integer getStarsEarned() { return starsEarned; }
        public void setStarsEarned(Integer starsEarned) { this.starsEarned = starsEarned; }
        public Integer getTimeSpentSeconds() { return timeSpentSeconds; }
        public void setTimeSpentSeconds(Integer timeSpentSeconds) { this.timeSpentSeconds = timeSpentSeconds; }
        public Integer getPointsEarned() { return pointsEarned; }
        public void setPointsEarned(Integer pointsEarned) { this.pointsEarned = pointsEarned; }
    }
    
    public static class ProgressUpdateResponse {
        private Boolean success;
        private Integer newPoints;
        private Boolean newLevelAchieved;
        private String newBadgeEarned;
        public Boolean getSuccess() { return success; }
        public void setSuccess(Boolean success) { this.success = success; }
        public Integer getNewPoints() { return newPoints; }
        public void setNewPoints(Integer newPoints) { this.newPoints = newPoints; }
        public Boolean getNewLevelAchieved() { return newLevelAchieved; }
        public void setNewLevelAchieved(Boolean newLevelAchieved) { this.newLevelAchieved = newLevelAchieved; }
        public String getNewBadgeEarned() { return newBadgeEarned; }
        public void setNewBadgeEarned(String newBadgeEarned) { this.newBadgeEarned = newBadgeEarned; }
    }
}