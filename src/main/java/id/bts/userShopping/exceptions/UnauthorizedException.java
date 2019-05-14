package id.bts.userShopping.exceptions;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2496223883223520226L;
	
	protected static MessageSourceAccessor message =
			SpringSecurityMessageSource.getAccessor();
	
	public UnauthorizedException() {
		super(message.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
	}
	
	public UnauthorizedException(String message) {
		super(message);
	}
}
