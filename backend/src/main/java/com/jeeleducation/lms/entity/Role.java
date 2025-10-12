package com.jeeleducation.lms.entity;

/**
 * Enum representing user roles in the system.
 * 
 * Role Hierarchy:
 * - SUPER_ADMIN: System-wide administration and management
 * - SCHOOL_ADMIN: School-level management and administration
 * - TEACHER: Curriculum management and student tracking
 * - PARENT: Child progress monitoring
 * - STUDENT: Learning interface access
 */
public enum Role {
    SUPER_ADMIN("Super Admin", "Full system access and management"),
    SCHOOL_ADMIN("School Admin", "School-level administration"),
    TEACHER("Teacher", "Curriculum and student management"),
    PARENT("Parent", "Child progress tracking"),
    STUDENT("Student", "Learning content access");

    private final String displayName;
    private final String description;

    Role(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get role authority string for Spring Security.
     * 
     * @return Authority string in format "ROLE_XXX"
     */
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}

