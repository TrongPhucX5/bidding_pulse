package vn.edu.uth.biddingpulse.security.service;

import vn.edu.uth.biddingpulse.model.User;
import vn.edu.uth.biddingpulse.security.dto.AuthenticatedUserDto;
import vn.edu.uth.biddingpulse.security.dto.RegistrationRequest;
import vn.edu.uth.biddingpulse.security.dto.RegistrationResponse;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
public interface UserService {

	User findByUsername(String username);

	RegistrationResponse registration(RegistrationRequest registrationRequest);

	AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

}
