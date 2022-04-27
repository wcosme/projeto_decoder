package br.com.ead.course.filters;

import java.util.UUID;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.ead.course.models.CourseModel;
import br.com.ead.course.models.CourseUserModel;
import br.com.ead.course.models.LessonModel;
import br.com.ead.course.models.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class FilterTemplate {

	@And({ @Spec(path = "courseStatus", spec = Equal.class), @Spec(path = "courseLevel", spec = Equal.class),
			@Spec(path = "name", spec = LikeIgnoreCase.class) })
	public interface CourseFilter extends Specification<CourseModel> {
	}

	@Spec(path = "title", spec = LikeIgnoreCase.class)
	public interface ModuleFilter extends Specification<ModuleModel> {
	}

	@Spec(path = "title", spec = LikeIgnoreCase.class)
	public interface LessonFilter extends Specification<LessonModel> {
	}

	public static Specification<ModuleModel> moduleCourseId(final UUID courseId) {
		return (root, query, cb) -> {
			query.distinct(true);
			Root<ModuleModel> module = root;			
			return cb.and(cb.equal(module.get("course").get("courseId"), courseId));
		};
	}

	
	public static Specification<LessonModel> lessonModuleId(final UUID moduleId) {
    	return (root, query, cb) -> {
    		query.distinct(true);
    		Root<LessonModel> lesson = root;
    		return cb.and(cb.equal(lesson.get("module").get("moduleId"), moduleId));
    	};
	}
	
	public static Specification<CourseModel> courseUserId(final UUID userId) {
    	return (root, query, cb) -> {
    		query.distinct(true);
    		Join<CourseModel, CourseUserModel> courseProd = root.join("coursesUsers");
    		return cb.and(cb.equal(courseProd.get("userId"), userId));
    	};
	}
}
