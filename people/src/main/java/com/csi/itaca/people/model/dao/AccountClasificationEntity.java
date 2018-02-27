package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PER_CLASIFICATION_ACCOUNT")
public class AccountClasificationEntity implements AccountClasification {

    public static final String ID = "id";
    public static final String VALUE = "value";

    @Id
    @Column(name="CLASIFICATION_ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CLASIFICATION_ACCOUNT")
    @SequenceGenerator(name = "SEQ_CLASIFICATION_ACCOUNT", sequenceName = "SEQ_CLASIFICATION_ACCOUNT", allocationSize = 1)
    private Long id;

    @Column(name="VALUE_CLASIFICATION_ACCOUNT")
    private String value;

}

