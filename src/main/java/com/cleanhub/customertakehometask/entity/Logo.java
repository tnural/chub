package com.cleanhub.customertakehometask.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Logo {
    @SerializedName("landingPageRoute")
    private String landingPageRoute;
    @SerializedName("featured")
    private Boolean featured;
}
