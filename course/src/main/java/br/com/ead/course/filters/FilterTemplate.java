package br.com.ead.course.filters;

import java.util.UUID;

import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.ead.course.models.Course;
import br.com.ead.course.models.Lesson;
import br.com.ead.course.models.Module;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class FilterTemplate {

	@And({ @Spec(path = "courseStatus", spec = Equal.class), @Spec(path = "courseLevel", spec = Equal.class),
			@Spec(path = "name", spec = LikeIgnoreCase.class) })
	public interface CourseFilter extends Specification<Course> {
	}

	@Spec(path = "title", spec = LikeIgnoreCase.class)
	public interface ModuleFilter extends Specification<Module> {
	}

	@Spec(path = "title", spec = LikeIgnoreCase.class)
	public interface LessonFilter extends Specification<Lesson> {
	}

	public static Specification<Module> moduleCourseId(final UUID courseId) {
		return (root, query, cb) -> {
			query.distinct(true);
			Root<Module> module = root;			
			return cb.and(cb.equal(module.get("course").get("courseId"), courseId));
		};
	}

	
	public static Specification<Lesson> lessonModuleId(final UUID moduleId) {
    	return (root, query, cb) -> {
    		query.distinct(true);
    		Root<Lesson> lesson = root;
    		return cb.and(cb.equal(lesson.get("module").get("moduleId"), moduleId));
    	};
	}
}
