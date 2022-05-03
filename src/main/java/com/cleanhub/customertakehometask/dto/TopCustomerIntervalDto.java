package com.cleanhub.customertakehometask.dto;

import lombok.*;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TopCustomerIntervalDto {
    private Integer limit;
    private Date from;
    private Date to;
}
