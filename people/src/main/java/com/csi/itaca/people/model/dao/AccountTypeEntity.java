package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.AccountType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "per_type_account")
public class AccountTypeEntity implements AccountType {

    public static final String ID = "id";
    public static final String VALOR = "valor";

    @Id
    @Column(name="TYPE_ACCOUNT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_TYPE_ACCOUNT")
    @SequenceGenerator(name = "SEQ_TYPE_ACCOUNT", sequenceName = "SEQ_TYPE_ACCOUNT", allocationSize = 1)
    private Long id;

    @Column(name="VALUE_TYPE_ACCOUNT")
    private String valor;
}