package com.csi.itaca.people.model.dao;

import com.csi.itaca.people.model.Bank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "per_bank")
public class BankEntity implements Bank {

    public static final String ID = "ID_BANK";
    public static final String BANK_NAME = "BANK_NAME";
    public static final String CBIC = "CBIC";
    public static final String DRAFTBANK = "DRAFT_BANK_PORTAL";
    public static final String BANK_COD = "BANK_COD";

    @Id
    @Column(name="BANK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PER_BANK")
    @SequenceGenerator(name = "SEQ_PER_BANK", sequenceName = "SEQ_PER_BANK", allocationSize = 1)
    private Long id;

    @Column(name="BANK_NAME")
    private String bankName;

    @Column(name="CBIC")
    private String cbic;

    @Column(name="DRAFT_BANK_PORTAL")
    private Long draftbank;

    @Column(name="BANK_COD")
    private String bankCod;

    @Override
    public Long getDraftBank() {
        return draftbank;
    }
    @Override
    public String getBankName() {
        return bankName;
    }
    @Override
    public String getBic() {
        return cbic;
    }
    @Override
    public String getCode() {
        return bankCod;
    }


}
