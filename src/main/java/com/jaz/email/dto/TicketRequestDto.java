package com.jaz.email.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestDto {
    private Long bookingId;
    private Long movieId;
    private Long screenId;
    private Long showId;
    private Long paymentId;
    private BigDecimal amount;
    private String movieName;
    private String emailId;
    private String userName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private java.sql.Date movieBookedDate;
    private Date createdDate;
    private int noOfPersons;
    private String bookedSeats;
    private String paymentStatus;
    private String movieTime;
}