package br.com.ead.course.filters;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.criteria.Expression;
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
	
	@And({
		@Spec(path = "courseStatus", spec = Equal.class),
		@Spec(path = "courseLevel", spec = Equal.class),
		@Spec(path = "name", spec = LikeIgnoreCase.class)
	})
	public interface CourseFilter extends Specification<Course>{}
	
	
	@Spec(path = "title", spec = LikeIgnoreCase.class)	
	public interface ModuleFilter extends Specification<Module>{}
	
		
	@Spec(path = "title", spec = LikeIgnoreCase.class)	
	public interface LessonFilter extends Specification<Lesson>{}
	
		
    public static Specification<Module> moduleCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<Module> module = root;
            Root<Course> course = query.from(Course.class);
            Expression<Collection<Module>> courseModules = course.get("modules");
            return cb.and(cb.equal(course.get("courseId"), courseId), cb.isMember(module, courseModules));
        };
    }
    
    
    public static Specification<Lesson> lessonModuleId(final UUID moduleId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<Lesson> lesson = root;
            Root<Module> module = query.from(Module.class);
            Expression<Collection<Lesson>> moduleLessons = module.get("lessons");
            return cb.and(cb.equal(module.get("moduleId"), moduleId), cb.isMember(lesson, moduleLessons));
        };
    }
}
