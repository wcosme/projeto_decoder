package br.com.ead.authuser.filters;

import org.springframework.data.jpa.domain.Specification;

import br.com.ead.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

public class FilterTemplate {
	
	@And({
		@Spec(path = "userType", spec = Equal.class),
		@Spec(path = "userStatus", spec = Equal.class),
		@Spec(path = "email", spec = Like.class)
	})
	public interface UserFilter extends Specification<UserModel>{}

}
