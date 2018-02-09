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

<<<<<<< Updated upstream
    public static final String ID_BANK_CARD = "idBankCard";
    public static final String ID_PERSON_DETAIL = "idPersonDetail";
    public static final String ID_CARD_TYPE = "idCardType";
    public static final String CARD = "card";
    public static final String PRINCIPAL = "principal";
    public static final String AVAILABLE = "available";
    public static final String EXPIRATION_DATE = "expirationDate";
    public static final String SECURITY_CODE = "securityCode";
    public static final String ID_BANK = "idBank";
=======
    public static final String ID_BANK_CARD = "id_bank_card";
    public static final String ID_PERSON_DETAIL = "id_person_detail";
    public static final String ID_CARD_TYPE = "id_card_type";
    public static final String CARD = "card";
    public static final String PRINCIPAL = "principal";
    public static final String AVAILABLE = "available";
    public static final String EXPIRATION_DATE = "expiration_date";
    public static final String SECURITY_CODE = "security_code";
    public static final String ID_BANK = "id_bank";
>>>>>>> Stashed changes


    @Id
    @Column(name="ID_BANK_CARD")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_BANK_CARD")
    @SequenceGenerator(name = "SEQ_BANK_CARD", sequenceName = "SEQ_BANK_CARD", allocationSize = 1)
    private Long idBankCard;

    @Column(name="ID_PERSON_DETAIL")
    private Long idPersonDetail;


    @Column(name="ID_CARD_TYPE")
    private Long idCardType;

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
    private Long idBank;



}
