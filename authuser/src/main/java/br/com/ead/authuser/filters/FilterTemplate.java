package br.com.ead.authuser.filters;

import java.util.UUID;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import br.com.ead.authuser.models.UserCourseModel;
import br.com.ead.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class FilterTemplate {
	
	@And({
		@Spec(path = "userType", spec = Equal.class),
		@Spec(path = "userStatus", spec = Equal.class),
		@Spec(path = "fullName", spec = LikeIgnoreCase.class),
		@Spec(path = "email", spec = LikeIgnoreCase.class)
	})
	public interface UserFilter extends Specification<UserModel>{}
	
	public static Specification<UserModel> userCourseId(final UUID courseId) {
    	return (root, query, cb) -> {
    		query.distinct(true);
    		Join<UserModel, UserCourseModel> userProd = root.join("usersCourses");
    		return cb.and(cb.equal(userProd.get("courseId"), courseId));
    	};
	}
}
