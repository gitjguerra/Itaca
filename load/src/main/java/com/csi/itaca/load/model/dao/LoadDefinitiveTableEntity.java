package com.csi.itaca.load.model.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.csi.itaca.load.model.LoadDefinitiveTable;
import javax.persistence.*;
/**
 * Created by Robert on 19/06/2018.
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LD_LOAD_DEFINITIVE_TABLE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoadDefinitiveTableEntity implements LoadDefinitiveTable{

    public static final String LOAD_DEFINITIVE_TABLE_ID = "loadDefinitiveTableId";
    public static final String TABLE_NAME = "tableName";

    @Id
    @Column(name="LOAD_DEFINITIVE_TABLE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ld_load_definitive_table_seq")
    @SequenceGenerator(name = "ld_load_definitive_table_seq", sequenceName = "ld_load_definitive_table_seq", allocationSize = 1)
    private Long loadDefinitiveTableId;

    @Column(name = "TABLE_NAME")
    private String tableName;

}
