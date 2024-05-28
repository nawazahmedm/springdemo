package com.javalearnings.securitydemo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Defines the Business Exception Reasons.
 */
@Getter
@AllArgsConstructor
public enum BusinessExceptionReason {

    LOCATION_NOT_EXISTS("Location not found for the specified location id", HttpStatus.BAD_REQUEST),

    IPADDRESS_NOT_EXISTS("Location not found for the specified IP Address", HttpStatus.BAD_REQUEST),

    EMAIL_ALREADY_EXISTS("Email already exists. Please choose another Email Id", HttpStatus.BAD_REQUEST),

    FIRSTNAME_ALREADY_EXISTS("Person with the same First Name already exists. Please choose another First Name", HttpStatus.BAD_REQUEST),

    EMAIL_TEMPLATE_ALREADY_EXISTS("Email template already exists with this combination. Please try with different combination.", HttpStatus.BAD_REQUEST),

    USER_ALREADY_EXISTS("User already exists. Please choose another Username", HttpStatus.BAD_REQUEST),

    SIGNUP_CODE_NOT_EXISTS("Signup Code does not exists for the given Chapter and Program. First create Signup Code for Volunteer Signup.", HttpStatus.BAD_REQUEST),

    USER_NOT_EXISTS("User does not exists", HttpStatus.BAD_REQUEST),

    USER_NOT_ADMIN("User does not have admin access", HttpStatus.BAD_REQUEST),

    PASSWORD_INVALID("Password is Invalid. Try with correct password", HttpStatus.BAD_REQUEST),

    ACCOUNT_LOCKED("Your account has been locked as you have entered wrong Password 3 times", HttpStatus.BAD_REQUEST),

    PERSON_NOT_PRESENT("Person record not found specific to Person Program Registration. Please correct the data manually in database", HttpStatus.BAD_REQUEST),

    NO_DATA_PRESENT_FOR_INVOICE("No data present for this invoice number", HttpStatus.BAD_REQUEST),

    NO_SIGNUP_CODE_CONFIG("There is no signup code configured for the given Program code and Chapter Code", HttpStatus.BAD_REQUEST),

    NO_PROGRAM_CONFIG("There is no Program configured for the given Program code and Chapter Code", HttpStatus.BAD_REQUEST),

	CERTIFICATION_NOT_EXISTS("Family certification does not exists for this program", HttpStatus.BAD_REQUEST),

    PROGRAM_NOT_AVAILABLE("For the Given Program Code and Chapter Code, there is no Program available", HttpStatus.BAD_REQUEST),

    PERSON_NOT_AVAILABLE("For the Given Home Phone or Mobile Phone, there is no Person available.", HttpStatus.BAD_REQUEST),

    NO_PLEDGE_CODE_SIGNUP_CODE("Pledge Structure Code or Signup code is not configured for the given Program code and Volunteer SignupCode", HttpStatus.BAD_REQUEST),

    NO_PLEDGE_CODE("Pledge Structure Code is not configured for the given Program code and Volunteer SignupCode", HttpStatus.BAD_REQUEST),

    NO_SIGNUP_CODE("There is no signup code configured for the given Program code and Volunteer SignupCode", HttpStatus.BAD_REQUEST),

    NO_EVENTS_AVAILABLE("There are no Slots available for the given ChapterID, Program code and Volunteer SignupCode", HttpStatus.BAD_REQUEST),

    FOUR_EVENTS_NOT_AVAILABLE("Four Events are not available for the given Event Date to book Reoccurring Slots", HttpStatus.BAD_REQUEST),

    NO_PERSONS_AVAILABLE("There is no persons available for the given Family Id, Program code and Chapter Code", HttpStatus.BAD_REQUEST),

    DOCUMENT_NOT_AVAILABLE("For the Given Person Id/Program Id, there is no document available", HttpStatus.BAD_REQUEST),

    FAMILY_SESSION_CONFIG_NOT_AVAILABLE("Family Session Preference is not present for given program code and signupCodeId", HttpStatus.BAD_REQUEST),

    REGISTRATION_NOT_AVAILABLE("Person Program Registration is not present for given RegistrationId", HttpStatus.BAD_REQUEST),

    REGISTRATION_NOT_DELETE("You will not be able to delete Person Program Registration because Payment status is not BalanceDue", HttpStatus.BAD_REQUEST),

    NO_SLOTS_AVAILABLE("All the slots are reserved. No free slots are available for the given event", HttpStatus.BAD_REQUEST),

    ID_NOT_AVAILABLE("Id not exists", HttpStatus.BAD_REQUEST);

    private final String code = BusinessExceptionReason.class.getSimpleName();
    private final String message;
    private final HttpStatus httpStatus;
}
