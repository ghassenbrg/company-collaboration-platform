package tn.esprit.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.MailRequest;
import tn.esprit.service.mail.MailService;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@RestController
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private MailService mailService;

	@PostMapping(value = "/send")
	public ResponseEntity<ApiResponse> sendTestReport(@RequestBody MailRequest mailRequest) {
		ApiResponse apiResponse = mailService.sendMail(mailRequest);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);

	}
}
