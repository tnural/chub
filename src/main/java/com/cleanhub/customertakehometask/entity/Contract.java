package com.cleanhub.customertakehometask.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import lombok.*;
import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "contract")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Contract {
    @Id
    @SerializedName("uuid")
    private String id;
    @SerializedName("isFulfilled")
    private Boolean isFullfilled;
    @SerializedName("quantity")
    private Double quantity;
    @SerializedName("recoveredQuantity")
    private Double recoveredQuantity;
    @SerializedName("startDate")
    private Date startDateTime;
    @SerializedName("endDate")
    private Date endDateTime;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id",
            nullable = false, updatable = true, insertable = true)
    @JsonIgnore
    private Customer customer;
}
