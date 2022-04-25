package br.com.ead.course.filters;

import org.springframework.data.jpa.domain.Specification;

import br.com.ead.course.models.Course;
import br.com.ead.course.models.Lesson;
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
	
	
	@And({
		@Spec(path = "courseStatus", spec = Equal.class),
		@Spec(path = "courseLevel", spec = Equal.class),
		@Spec(path = "name", spec = LikeIgnoreCase.class)
	})
	public interface ModuleFilter extends Specification<Module>{}
	
	
	@And({
		@Spec(path = "courseStatus", spec = Equal.class),
		@Spec(path = "courseLevel", spec = Equal.class),
		@Spec(path = "name", spec = LikeIgnoreCase.class)
	})
	public interface LessonFilter extends Specification<Lesson>{}

}
