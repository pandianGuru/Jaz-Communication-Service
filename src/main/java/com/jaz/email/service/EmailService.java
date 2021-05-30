package com.jaz.email.service;

import com.jaz.email.dto.MailRequest;
import com.jaz.email.dto.MailResponse;
import com.jaz.email.dto.TicketRequestDto;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration config;

    @Value("${spring.mail.username}")
    private String emailFrom;

    /**
     * @param ticketRequestDto
     */
    public void ticketConfirmationMail(TicketRequestDto ticketRequestDto) {
        MailRequest mailRequest = new MailRequest();
        Map<String, Object> model = new HashMap<>();
        mailRequest.setTo(ticketRequestDto.getEmailId());
        mailRequest.setName(ticketRequestDto.getUserName());
        mailRequest.setSubject("TICKET BOOKED");
        model.put("name", ticketRequestDto.getUserName());
        model.put("ticketIds", ticketRequestDto.getBookedSeats());
        model.put("movieName", ticketRequestDto.getMovieName());
        model.put("movieDate", ticketRequestDto.getMovieBookedDate());
        model.put("movieTime", ticketRequestDto.getMovieTime());
        //TODO Generate QR code and attach the path in model object
        sendEmail(mailRequest, model);
    }

    /**
     * @param request
     * @param model
     * @return
     */
    public MailResponse sendEmail(MailRequest request, Map<String, Object> model) {
        MailResponse response = new MailResponse();
        MimeMessage message = sender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            //TODO add attachment
           // helper.addAttachment("", new ClassPathResource(""));

            Template t = config.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            helper.setFrom(emailFrom);
            sender.send(message);

            response.setMessage("mail send to : " + request.getTo());
            response.setStatus(Boolean.TRUE);

        } catch (MessagingException | IOException | TemplateException e) {
            response.setMessage("Mail Sending failure : " + e.getMessage());
            response.setStatus(Boolean.FALSE);
        }

        return response;
    }


}
