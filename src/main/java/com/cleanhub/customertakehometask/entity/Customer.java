package com.cleanhub.customertakehometask.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = {"contractList"})
public class Customer {
    @Id
    @SerializedName("uuid")
    private String id;
    @SerializedName("companyName")
    private String companyName;
    @SerializedName("quantity")
    private Double totalQuantity;
    @OneToMany(mappedBy = "customer", orphanRemoval = true,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @SerializedName("contracts")
    @Column(nullable = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Contract> contractList;
}