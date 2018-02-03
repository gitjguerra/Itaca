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
    public static final String COD_BANK = "COD_BANK";

    @Id
    @Column(name="ID_BANK")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PER_BANK")
    @SequenceGenerator(name = "SEQ_PER_BANK", sequenceName = "SEQ_PER_BANK", allocationSize = 1)
    private Long id;

    @Column(name="BANK_NAME")
    private String bank_name;

    @Column(name="CBIC")
    private String cbic;

    @Column(name="DRAFT_BANK_PORTAL")
    private Long draftbank;

    @Column(name="COD_BANK")
    private String cod_bank;

    @Override
    public Long getDraftBank() {
        return draftbank;
    }
    @Override
    public String getBankName() {
        return bank_name;
    }
    @Override
    public String getBic() {
        return cbic;
    }
    @Override
    public String getCode() {
        return cod_bank;
    }


}
