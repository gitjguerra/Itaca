package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.BankCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PER_BANK_CARD")
public class BankCardEntity implements BankCard {

    public static final String ID_BANK_CARD = "bankCardId";
    public static final String ID_PERSON_DETAIL = "personDetailId";
    public static final String ID_CARD_TYPE = "cardTypeId";
    public static final String CARD = "card";
    public static final String PRINCIPAL = "principal";
    public static final String AVAILABLE = "available";
    public static final String EXPIRATION_DATE = "expirationDate";
    public static final String SECURITY_CODE = "securityCode";
    public static final String ID_BANK = "bankId";

    @Id
    @Column(name="ID_BANK_CARD")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_BANK_CARD")
    @SequenceGenerator(name = "SEQ_BANK_CARD", sequenceName = "SEQ_BANK_CARD", allocationSize = 1)
    private Long bankCardId;

    @Column(name="ID_PERSON_DETAIL")
    private Long personDetailId;


    @Column(name="ID_CARD_TYPE")
    private Long cardTypeId;

    @Column(name="CARD")
    private String card;

    @Column(name="PRINCIPAL")
    private Boolean principal;

    @Column(name="AVAILABLE")
    private Boolean available;

    @Column(name="EXPIRATION_DATE")
    private LocalDate expirationDate;

    @Column(name="SECURITY_CODE")
    private Long securityCode;

    @Column(name="ID_BANK")
    private Long bankId;



}
