package pet.lovers.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pet.lovers.entities.AdoptionRequest;
import pet.lovers.entities.Pet;
import pet.lovers.entities.Shelter;

@Service
public class EmailService {

    public static final String SENDER_EMAIL= "petloversplatform@gmail.com";

    private final JavaMailSender emailSender;
    private final Boolean isEmailEnabled = true;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendAcceptShelterMessage(Shelter shelter) {
        String subject = "Congratulations! Your Shelter Submission Has Been Approved";
        String text = """
            Dear %s,
            
            Congratulations! We are thrilled to inform you that your shelter submission has been approved. 
            Your participation in our platform will help connect more pets with loving homes, 
            and we’re excited to have you as part of our community.
            
            If you have any questions or need assistance, feel free to reach out to us at any time.
            
            Thank you for your commitment to animal welfare.
            
            Warm regards,
            The Pet Lovers Team
            """.formatted(shelter.getFullName());

        sendSimpleMessage(shelter.getEmail(), subject, text);
    }

    public void sendRejectShelterMessage(Shelter shelter) {
        String subject = "Your Shelter Submission Decision";
        String text = """
            Dear %s,
            
            Thank you for submitting your shelter to our platform.
            After careful review, we regret to inform you that your submission has not been approved at this time.
            
            We value your interest and encourage you to reach out if you would like further details or guidance
            on how to meet the necessary criteria in the future.
            
            Thank you for the work you do in supporting animals in need.
            
            Best regards,
            The Pet Lovers Team
            """.formatted(shelter.getFullName());

        sendSimpleMessage(shelter.getEmail(), subject, text);
    }

    public void sendApprovePetMessage(Pet pet) {
        String subject = "Great News! Your Pet Submission Has Been Approved";
        String text = """
            Dear %s,
            
            We are delighted to inform you that your pet submission for "%s" has been approved!
            "%s" is now listed on our platform, increasing their chances of finding a loving home.
            
            If you have additional pets to register or require assistance, please do not hesitate to contact us.
            
            Thank you for being an essential part of this journey to help pets find their forever homes.
            
            Warm regards,
            The Pet Lovers Team
            """.formatted(pet.getShelter().getFullName(), pet.getName(), pet.getName());

        sendSimpleMessage(pet.getShelter().getEmail(), subject, text);
    }

    public void sendRejectPetMessage(Pet pet) {
        String subject = "Your Pet Submission Decision";
        String text = """
            Dear %s,
            
            Thank you for submitting the pet "%s" to our platform.
            After a detailed review, we regret to inform you that the submission has not been approved at this time.
            
            We understand this may be disappointing and encourage you to reach out if you would like 
            additional feedback or guidance on our submission criteria.
            
            We deeply appreciate the care and effort you put into supporting animals in need. 
            
            Best regards,
            The Pet Lovers Team
            """.formatted(pet.getShelter().getFullName(), pet.getName());

        sendSimpleMessage(pet.getShelter().getEmail(), subject, text);
    }


    public void sendReminderToShelter(AdoptionRequest adoptionRequest) {
        String subject = "Reminder: Pending Adoption Request for Review";
        String text = """
            Dear %s,
            
            We would like to remind you that there is a pending adoption request awaiting your review. 
            The request is for the pet named "%s", submitted by %s.
            
            Please take a moment to review the request and either approve or reject it at your earliest convenience.
            
            Thank you for your attention and for being a valued part of our adoption process.
            
            Best regards,
            The Pet Lovers Team
            """.formatted(adoptionRequest.getShelter().getFullName(),
                adoptionRequest.getPet().getName(),
                adoptionRequest.getAdopter().getFullName());

        sendSimpleMessage(adoptionRequest.getShelter().getEmail(), subject, text);
    }

    public void sendRegistrationMessageToUser(String email, String name) {
        String subject = "Welcome to Pet Lovers Platform";
        String text = """
            Dear %s,
            
            Welcome to Pet Lovers Platform! We are excited to have you as part of our community.
            Our platform connects pets in need with loving homes, and your participation is invaluable.
            
            If you have any questions or need assistance, please do not hesitate to reach out to us.
            
            Thank you for joining us in our mission to help pets find their forever homes.
            
            Warm regards,
            The Pet Lovers Team
            """.formatted(name);

        sendSimpleMessage(email, subject, text);
    }

    public void sendResetPasswordMessage(String email,String username, String password) {
        String subject = "Password Reset Request";
        String text = """
            Dear User,
            
            You have requested a password reset.
            
            Your username is: %s
            Your new password is: %s
            
            If you did not request this change, please contact us immediately.
            
            Best regards,
            The Pet Lovers Team
            """.formatted(username,password);

        sendSimpleMessage(email, subject, text);
    }



    public void sendSimpleMessage(String recipient, String subject, String text) {
        if (!isEmailEnabled)
            return;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(SENDER_EMAIL);
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(text);

        try {
            emailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
