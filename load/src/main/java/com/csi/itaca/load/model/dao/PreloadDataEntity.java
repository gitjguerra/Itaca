package com.csi.itaca.load.model.dao;

/**
 * Created by Robert on 19/06/2018.
 */

import com.csi.itaca.load.model.PreloadData;
import com.csi.itaca.load.model.PreloadRowType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "LD_PRELOAD_DATA")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class PreloadDataEntity implements PreloadData {

    public static final String PRELOAD_DATA_ID = "preloadDataId";
    public static final String LOAD_FILE_ID = "loadFileId";
    public static final String LOADED_SUCCESSFULLY = "loadedSuccessfully";
    public static final String CREATED_TIMESTAMP = "createTimeStamp";
    public static final String PRELOAD_ROW_TYPE_ID = "PreloadRowTypeId";
    public static final String LINE_NUMBER = "lineNumber";
    public static final String DATA_COL1 = "dataCol1";
    public static final String DATA_COL2 = "dataCol2";
    public static final String DATA_COL3 = "dataCol3";
    public static final String DATA_COL4 = "dataCol4";
    public static final String DATA_COL5 = "dataCol5";
    public static final String DATA_COL6 = "dataCol6";
    public static final String DATA_COL7 = "dataCol7";
    public static final String DATA_COL8 = "dataCol8";
    public static final String DATA_COL9 = "dataCol9";
    public static final String DATA_COL10 = "dataCol10";
    public static final String DATA_COL11 = "dataCol11";
    public static final String DATA_COL12 = "dataCol12";
    public static final String DATA_COL13 = "dataCol13";
    public static final String DATA_COL14 = "dataCol14";
    public static final String DATA_COL15 = "dataCol15";
    public static final String DATA_COL16 = "dataCol16";
    public static final String DATA_COL17 = "dataCol17";
    public static final String DATA_COL18 = "dataCol18";
    public static final String DATA_COL19 = "dataCol19";
    public static final String DATA_COL20 = "dataCol20";
    public static final String DATA_COL21 = "dataCol21";
    public static final String DATA_COL22 = "dataCol22";
    public static final String DATA_COL23 = "dataCol23";
    public static final String DATA_COL24 = "dataCol24";
    public static final String DATA_COL25 = "dataCol25";
    public static final String DATA_COL26 = "dataCol26";
    public static final String DATA_COL27 = "dataCol27";
    public static final String DATA_COL28 = "dataCol28";
    public static final String DATA_COL29 = "dataCol29";
    public static final String DATA_COL30 = "dataCol30";
    public static final String DATA_COL31 = "dataCol31";
    public static final String DATA_COL32 = "dataCol32";
    public static final String DATA_COL33 = "dataCol33";
    public static final String DATA_COL34 = "dataCol34";
    public static final String DATA_COL35 = "dataCol35";
    public static final String DATA_COL36 = "dataCol36";
    public static final String DATA_COL37 = "dataCol37";
    public static final String DATA_COL38 = "dataCol38";
    public static final String DATA_COL39 = "dataCol39";
    public static final String DATA_COL40 = "dataCol40";
    public static final String DATA_COL41 = "dataCol41";
    public static final String DATA_COL42 = "dataCol42";
    public static final String DATA_COL43 = "dataCol43";
    public static final String DATA_COL44 = "dataCol44";
    public static final String DATA_COL45 = "dataCol45";
    public static final String DATA_COL46 = "dataCol46";
    public static final String DATA_COL47 = "dataCol47";
    public static final String DATA_COL48 = "dataCol48";
    public static final String DATA_COL49 = "dataCol49";
    public static final String DATA_COL50 = "dataCol50";
    public static final String DATA_COL51 = "dataCol51";
    public static final String DATA_COL52 = "dataCol52";
    public static final String DATA_COL53 = "dataCol53";
    public static final String DATA_COL54 = "dataCol54";
    public static final String DATA_COL55 = "dataCol55";
    public static final String DATA_COL56 = "dataCol56";
    public static final String DATA_COL57 = "dataCol57";
    public static final String DATA_COL58 = "dataCol58";
    public static final String DATA_COL59 = "dataCol59";
    public static final String DATA_COL60 = "dataCol60";
    public static final String DATA_COL61 = "dataCol61";
    public static final String DATA_COL62 = "dataCol62";
    public static final String DATA_COL63 = "dataCol63";
    public static final String DATA_COL64 = "dataCol64";
    public static final String DATA_COL65 = "dataCol65";
    public static final String DATA_COL66 = "dataCol66";
    public static final String DATA_COL67 = "dataCol67";
    public static final String DATA_COL68 = "dataCol68";
    public static final String DATA_COL69 = "dataCol69";
    public static final String DATA_COL70 = "dataCol70";
    public static final String DATA_COL71 = "dataCol71";
    public static final String DATA_COL72 = "dataCol72";
    public static final String DATA_COL73 = "dataCol73";
    public static final String DATA_COL74 = "dataCol74";
    public static final String DATA_COL75 = "dataCol75";
    public static final String DATA_COL76 = "dataCol76";
    public static final String DATA_COL77 = "dataCol77";
    public static final String DATA_COL78 = "dataCol78";
    public static final String DATA_COL79 = "dataCol79";
    public static final String DATA_COL80 = "dataCol80";
    public static final String DATA_COL81 = "dataCol81";
    public static final String DATA_COL82 = "dataCol82";
    public static final String DATA_COL83 = "dataCol83";
    public static final String DATA_COL84 = "dataCol84";
    public static final String DATA_COL85 = "dataCol85";
    public static final String DATA_COL86 = "dataCol86";
    public static final String DATA_COL87 = "dataCol87";
    public static final String DATA_COL88 = "dataCol88";
    public static final String DATA_COL89 = "dataCol89";
    public static final String DATA_COL90 = "dataCol90";
    public static final String DATA_COL91 = "dataCol91";
    public static final String DATA_COL92 = "dataCol92";
    public static final String DATA_COL93 = "dataCol93";
    public static final String DATA_COL94 = "dataCol94";
    public static final String DATA_COL95 = "dataCol95";
    public static final String DATA_COL96 = "dataCol96";
    public static final String DATA_COL97 = "dataCol97";
    public static final String DATA_COL98 = "dataCol98";
    public static final String DATA_COL99 = "dataCol99";
    public static final String DATA_COL100 = "dataCol100";
    public static final String DATA_COL101 = "dataCol101";
    public static final String DATA_COL102 = "dataCol102";
    public static final String DATA_COL103 = "dataCol103";
    public static final String DATA_COL104 = "dataCol104";
    public static final String DATA_COL105 = "dataCol105";
    public static final String DATA_COL106 = "dataCol106";
    public static final String DATA_COL107 = "dataCol107";
    public static final String DATA_COL108 = "dataCol108";
    public static final String DATA_COL109 = "dataCol109";
    public static final String DATA_COL110 = "dataCol110";
    public static final String DATA_COL111 = "dataCol111";
    public static final String DATA_COL112 = "dataCol112";
    public static final String DATA_COL113 = "dataCol113";
    public static final String DATA_COL114 = "dataCol114";
    public static final String DATA_COL115 = "dataCol115";
    public static final String DATA_COL116 = "dataCol116";
    public static final String DATA_COL117 = "dataCol117";
    public static final String DATA_COL118 = "dataCol118";
    public static final String DATA_COL119 = "dataCol119";
    public static final String DATA_COL120 = "dataCol120";
    public static final String DATA_COL121 = "dataCol121";
    public static final String DATA_COL122 = "dataCol122";
    public static final String DATA_COL123 = "dataCol123";
    public static final String DATA_COL124 = "dataCol124";
    public static final String DATA_COL125 = "dataCol125";
    public static final String DATA_COL126 = "dataCol126";
    public static final String DATA_COL127 = "dataCol127";
    public static final String DATA_COL128 = "dataCol128";
    public static final String DATA_COL129 = "dataCol129";
    public static final String DATA_COL130 = "dataCol130";
    public static final String DATA_COL131 = "dataCol131";
    public static final String DATA_COL132 = "dataCol132";
    public static final String DATA_COL133 = "dataCol133";
    public static final String DATA_COL134 = "dataCol134";
    public static final String DATA_COL135 = "dataCol135";
    public static final String DATA_COL136 = "dataCol136";
    public static final String DATA_COL137 = "dataCol137";
    public static final String DATA_COL138 = "dataCol138";
    public static final String DATA_COL139 = "dataCol139";
    public static final String DATA_COL140 = "dataCol140";
    public static final String DATA_COL141 = "dataCol141";
    public static final String DATA_COL142 = "dataCol142";
    public static final String DATA_COL143 = "dataCol143";
    public static final String DATA_COL144 = "dataCol144";
    public static final String DATA_COL145 = "dataCol145";
    public static final String DATA_COL146 = "dataCol146";
    public static final String DATA_COL147 = "dataCol147";
    public static final String DATA_COL148 = "dataCol148";
    public static final String DATA_COL149 = "dataCol149";
    public static final String DATA_COL150 = "dataCol150";
    public static final String DATA_COL151 = "dataCol151";
    public static final String DATA_COL152 = "dataCol152";
    public static final String DATA_COL153 = "dataCol153";
    public static final String DATA_COL154 = "dataCol154";
    public static final String DATA_COL155 = "dataCol155";
    public static final String DATA_COL156 = "dataCol156";
    public static final String DATA_COL157 = "dataCol157";
    public static final String DATA_COL158 = "dataCol158";
    public static final String DATA_COL159 = "dataCol159";
    public static final String DATA_COL160 = "dataCol160";
    public static final String DATA_COL161 = "dataCol161";
    public static final String DATA_COL162 = "dataCol162";
    public static final String DATA_COL163 = "dataCol163";
    public static final String DATA_COL164 = "dataCol164";
    public static final String DATA_COL165 = "dataCol165";
    public static final String DATA_COL166 = "dataCol166";
    public static final String DATA_COL167 = "dataCol167";
    public static final String DATA_COL168 = "dataCol168";
    public static final String DATA_COL169 = "dataCol169";
    public static final String DATA_COL170 = "dataCol170";
    public static final String DATA_COL171 = "dataCol171";
    public static final String DATA_COL172 = "dataCol172";
    public static final String DATA_COL173 = "dataCol173";
    public static final String DATA_COL174 = "dataCol174";
    public static final String DATA_COL175 = "dataCol175";
    public static final String DATA_COL176 = "dataCol176";
    public static final String DATA_COL177 = "dataCol177";
    public static final String DATA_COL178 = "dataCol178";
    public static final String DATA_COL179 = "dataCol179";
    public static final String DATA_COL180 = "dataCol180";
    public static final String DATA_COL181 = "dataCol181";
    public static final String DATA_COL182 = "dataCol182";
    public static final String DATA_COL183 = "dataCol183";
    public static final String DATA_COL184 = "dataCol184";
    public static final String DATA_COL185 = "dataCol185";
    public static final String DATA_COL186 = "dataCol186";
    public static final String DATA_COL187 = "dataCol187";
    public static final String DATA_COL188 = "dataCol188";
    public static final String DATA_COL189 = "dataCol189";
    public static final String DATA_COL190 = "dataCol190";
    public static final String DATA_COL191 = "dataCol191";
    public static final String DATA_COL192 = "dataCol192";
    public static final String DATA_COL193 = "dataCol193";
    public static final String DATA_COL194 = "dataCol194";
    public static final String DATA_COL195 = "dataCol195";
    public static final String DATA_COL196 = "dataCol196";
    public static final String DATA_COL197 = "dataCol197";
    public static final String DATA_COL198 = "dataCol198";
    public static final String DATA_COL199 = "dataCol199";
    public static final String DATA_COL200 = "dataCol200";

    @Id
    @Column(name="PRELOAD_DATA_ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PRELOAD_DATA_ID")
    @SequenceGenerator(name = "SEQ_PRELOAD_DATA_ID", sequenceName = "SEQ_PRELOAD_DATA_ID", allocationSize = 1)
    private Long preloadDataId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="LOAD_FILE_ID")
    private LoadFileEntity loadFileId;

    @Column(name="LOADED_SUCCESSFULLY")
    private String loadedSuccessfully;

    @Column(name="CREATED_TIMESTAMP")
    private Date createdTimeStamp;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PRELOAD_ROW_TYPE_ID")
    private PreloadRowTypeEntity PreloadRowTypeId;

    @Column(name="LINE_NUMBER")
    private Long lineNumber;

    @Column(name="DATA_COL1")
    private String dataCol1;

    @Column(name="DATA_COL2")
    private String dataCol2;

    @Column(name="DATA_COL3")
    private String dataCol3;

    @Column(name="DATA_COL4")
    private String dataCol4;

    @Column(name="DATA_COL5")
    private String dataCol5;

    @Column(name="DATA_COL6")
    private String dataCol6;

    @Column(name="DATA_COL7")
    private String dataCol7;

    @Column(name="DATA_COL8")
    private String dataCol8;

    @Column(name="DATA_COL9")
    private String dataCol9;

    @Column(name="DATA_COL10")
    private String dataCol10;

    @Column(name="DATA_COL11")
    private String dataCol11;

    @Column(name="DATA_COL12")
    private String dataCol12;

    @Column(name="DATA_COL13")
    private String dataCol13;

    @Column(name="DATA_COL14")
    private String dataCol14;

    @Column(name="DATA_COL15")
    private String dataCol15;

    @Column(name="DATA_COL16")
    private String dataCol16;

    @Column(name="DATA_COL17")
    private String dataCol17;

    @Column(name="DATA_COL18")
    private String dataCol18;

    @Column(name="DATA_COL19")
    private String dataCol19;

    @Column(name="DATA_COL20")
    private String dataCol20;

    @Column(name="DATA_COL21")
    private String dataCol21;

    @Column(name="DATA_COL22")
    private String dataCol22;

    @Column(name="DATA_COL23")
    private String dataCol23;

    @Column(name="DATA_COL24")
    private String dataCol24;

    @Column(name="DATA_COL25")
    private String dataCol25;

    @Column(name="DATA_COL26")
    private String dataCol26;

    @Column(name="DATA_COL27")
    private String dataCol27;

    @Column(name="DATA_COL28")
    private String dataCol28;

    @Column(name="DATA_COL29")
    private String dataCol29;

    @Column(name="DATA_COL30")
    private String dataCol30;

    @Column(name="DATA_COL31")
    private String dataCol31;

    @Column(name="DATA_COL32")
    private String dataCol32;

    @Column(name="DATA_COL33")
    private String dataCol33;

    @Column(name="DATA_COL34")
    private String dataCol34;

    @Column(name="DATA_COL35")
    private String dataCol35;

    @Column(name="DATA_COL36")
    private String dataCol36;

    @Column(name="DATA_COL37")
    private String dataCol37;

    @Column(name="DATA_COL38")
    private String dataCol38;

    @Column(name="DATA_COL39")
    private String dataCol39;

    @Column(name="DATA_COL40")
    private String dataCol40;

    @Column(name="DATA_COL41")
    private String dataCol41;

    @Column(name="DATA_COL42")
    private String dataCol42;

    @Column(name="DATA_COL43")
    private String dataCol43;

    @Column(name="DATA_COL44")
    private String dataCol44;

    @Column(name="DATA_COL45")
    private String dataCol45;

    @Column(name="DATA_COL46")
    private String dataCol46;

    @Column(name="DATA_COL47")
    private String dataCol47;

    @Column(name="DATA_COL48")
    private String dataCol48;

    @Column(name="DATA_COL49")
    private String dataCol49;

    @Column(name="DATA_COL50")
    private String dataCol50;

    @Column(name="DATA_COL51")
    private String dataCol51;

    @Column(name="DATA_COL52")
    private String dataCol52;

    @Column(name="DATA_COL53")
    private String dataCol53;

    @Column(name="DATA_COL54")
    private String dataCol54;

    @Column(name="DATA_COL55")
    private String dataCol55;

    @Column(name="DATA_COL56")
    private String dataCol56;

    @Column(name="DATA_COL57")
    private String dataCol57;

    @Column(name="DATA_COL58")
    private String dataCol58;

    @Column(name="DATA_COL59")
    private String dataCol59;

    @Column(name="DATA_COL60")
    private String dataCol60;

    @Column(name="DATA_COL61")
    private String dataCol61;

    @Column(name="DATA_COL62")
    private String dataCol62;

    @Column(name="DATA_COL63")
    private String dataCol63;

    @Column(name="DATA_COL64")
    private String dataCol64;

    @Column(name="DATA_COL65")
    private String dataCol65;

    @Column(name="DATA_COL66")
    private String dataCol66;

    @Column(name="DATA_COL67")
    private String dataCol67;

    @Column(name="DATA_COL68")
    private String dataCol68;

    @Column(name="DATA_COL69")
    private String dataCol69;

    @Column(name="DATA_COL70")
    private String dataCol70;

    @Column(name="DATA_COL71")
    private String dataCol71;

    @Column(name="DATA_COL72")
    private String dataCol72;

    @Column(name="DATA_COL73")
    private String dataCol73;

    @Column(name="DATA_COL74")
    private String dataCol74;

    @Column(name="DATA_COL75")
    private String dataCol75;

    @Column(name="DATA_COL76")
    private String dataCol76;

    @Column(name="DATA_COL77")
    private String dataCol77;

    @Column(name="DATA_COL78")
    private String dataCol78;

    @Column(name="DATA_COL79")
    private String dataCol79;

    @Column(name="DATA_COL80")
    private String dataCol80;

    @Column(name="DATA_COL81")
    private String dataCol81;

    @Column(name="DATA_COL82")
    private String dataCol82;

    @Column(name="DATA_COL83")
    private String dataCol83;

    @Column(name="DATA_COL84")
    private String dataCol84;

    @Column(name="DATA_COL85")
    private String dataCol85;

    @Column(name="DATA_COL86")
    private String dataCol86;

    @Column(name="DATA_COL87")
    private String dataCol87;

    @Column(name="DATA_COL88")
    private String dataCol88;

    @Column(name="DATA_COL89")
    private String dataCol89;

    @Column(name="DATA_COL90")
    private String dataCol90;

    @Column(name="DATA_COL91")
    private String dataCol91;

    @Column(name="DATA_COL92")
    private String dataCol92;

    @Column(name="DATA_COL93")
    private String dataCol93;

    @Column(name="DATA_COL94")
    private String dataCol94;

    @Column(name="DATA_COL95")
    private String dataCol95;

    @Column(name="DATA_COL96")
    private String dataCol96;

    @Column(name="DATA_COL97")
    private String dataCol97;

    @Column(name="DATA_COL98")
    private String dataCol98;

    @Column(name="DATA_COL99")
    private String dataCol99;

    @Column(name="DATA_COL100")
    private String dataCol100;

    @Column(name="DATA_COL101")
    private String dataCol101;

    @Column(name="DATA_COL102")
    private String dataCol102;

    @Column(name="DATA_COL103")
    private String dataCol103;

    @Column(name="DATA_COL104")
    private String dataCol104;

    @Column(name="DATA_COL105")
    private String dataCol105;

    @Column(name="DATA_COL106")
    private String dataCol106;

    @Column(name="DATA_COL107")
    private String dataCol107;

    @Column(name="DATA_COL108")
    private String dataCol108;

    @Column(name="DATA_COL109")
    private String dataCol109;

    @Column(name="DATA_COL110")
    private String dataCol110;

    @Column(name="DATA_COL111")
    private String dataCol111;

    @Column(name="DATA_COL112")
    private String dataCol112;

    @Column(name="DATA_COL113")
    private String dataCol113;

    @Column(name="DATA_COL114")
    private String dataCol114;

    @Column(name="DATA_COL115")
    private String dataCol115;

    @Column(name="DATA_COL116")
    private String dataCol116;

    @Column(name="DATA_COL117")
    private String dataCol117;

    @Column(name="DATA_COL118")
    private String dataCol118;

    @Column(name="DATA_COL119")
    private String dataCol119;

    @Column(name="DATA_COL120")
    private String dataCol120;

    @Column(name="DATA_COL121")
    private String dataCol121;

    @Column(name="DATA_COL122")
    private String dataCol122;

    @Column(name="DATA_COL123")
    private String dataCol123;

    @Column(name="DATA_COL124")
    private String dataCol124;

    @Column(name="DATA_COL125")
    private String dataCol125;

    @Column(name="DATA_COL126")
    private String dataCol126;

    @Column(name="DATA_COL127")
    private String dataCol127;

    @Column(name="DATA_COL128")
    private String dataCol128;

    @Column(name="DATA_COL129")
    private String dataCol129;

    @Column(name="DATA_COL130")
    private String dataCol130;

    @Column(name="DATA_COL131")
    private String dataCol131;

    @Column(name="DATA_COL132")
    private String dataCol132;

    @Column(name="DATA_COL133")
    private String dataCol133;

    @Column(name="DATA_COL134")
    private String dataCol134;

    @Column(name="DATA_COL135")
    private String dataCol135;

    @Column(name="DATA_COL136")
    private String dataCol136;

    @Column(name="DATA_COL137")
    private String dataCol137;

    @Column(name="DATA_COL138")
    private String dataCol138;

    @Column(name="DATA_COL139")
    private String dataCol139;

    @Column(name="DATA_COL140")
    private String dataCol140;

    @Column(name="DATA_COL141")
    private String dataCol141;

    @Column(name="DATA_COL142")
    private String dataCol142;

    @Column(name="DATA_COL143")
    private String dataCol143;

    @Column(name="DATA_COL144")
    private String dataCol144;

    @Column(name="DATA_COL145")
    private String dataCol145;

    @Column(name="DATA_COL146")
    private String dataCol146;

    @Column(name="DATA_COL147")
    private String dataCol147;

    @Column(name="DATA_COL148")
    private String dataCol148;

    @Column(name="DATA_COL149")
    private String dataCol149;

    @Column(name="DATA_COL150")
    private String dataCol150;

    @Column(name="DATA_COL151")
    private String dataCol151;

    @Column(name="DATA_COL152")
    private String dataCol152;

    @Column(name="DATA_COL153")
    private String dataCol153;

    @Column(name="DATA_COL154")
    private String dataCol154;

    @Column(name="DATA_COL155")
    private String dataCol155;

    @Column(name="DATA_COL156")
    private String dataCol156;

    @Column(name="DATA_COL157")
    private String dataCol157;

    @Column(name="DATA_COL158")
    private String dataCol158;

    @Column(name="DATA_COL159")
    private String dataCol159;

    @Column(name="DATA_COL160")
    private String dataCol160;

    @Column(name="DATA_COL161")
    private String dataCol161;

    @Column(name="DATA_COL162")
    private String dataCol162;

    @Column(name="DATA_COL163")
    private String dataCol163;

    @Column(name="DATA_COL164")
    private String dataCol164;

    @Column(name="DATA_COL165")
    private String dataCol165;

    @Column(name="DATA_COL166")
    private String dataCol166;

    @Column(name="DATA_COL167")
    private String dataCol167;

    @Column(name="DATA_COL168")
    private String dataCol168;

    @Column(name="DATA_COL169")
    private String dataCol169;

    @Column(name="DATA_COL170")
    private String dataCol170;

    @Column(name="DATA_COL171")
    private String dataCol171;

    @Column(name="DATA_COL172")
    private String dataCol172;

    @Column(name="DATA_COL173")
    private String dataCol173;

    @Column(name="DATA_COL174")
    private String dataCol174;

    @Column(name="DATA_COL175")
    private String dataCol175;

    @Column(name="DATA_COL176")
    private String dataCol176;

    @Column(name="DATA_COL177")
    private String dataCol177;

    @Column(name="DATA_COL178")
    private String dataCol178;

    @Column(name="DATA_COL179")
    private String dataCol179;

    @Column(name="DATA_COL180")
    private String dataCol180;

    @Column(name="DATA_COL181")
    private String dataCol181;

    @Column(name="DATA_COL182")
    private String dataCol182;

    @Column(name="DATA_COL183")
    private String dataCol183;

    @Column(name="DATA_COL184")
    private String dataCol184;

    @Column(name="DATA_COL185")
    private String dataCol185;

    @Column(name="DATA_COL186")
    private String dataCol186;

    @Column(name="DATA_COL187")
    private String dataCol187;

    @Column(name="DATA_COL188")
    private String dataCol188;

    @Column(name="DATA_COL189")
    private String dataCol189;

    @Column(name="DATA_COL190")
    private String dataCol190;

    @Column(name="DATA_COL191")
    private String dataCol191;

    @Column(name="DATA_COL192")
    private String dataCol192;

    @Column(name="DATA_COL193")
    private String dataCol193;

    @Column(name="DATA_COL194")
    private String dataCol194;

    @Column(name="DATA_COL195")
    private String dataCol195;

    @Column(name="DATA_COL196")
    private String dataCol196;

    @Column(name="DATA_COL197")
    private String dataCol197;

    @Column(name="DATA_COL198")
    private String dataCol198;

    @Column(name="DATA_COL199")
    private String dataCol199;

    @Column(name="DATA_COL200")
    private String dataCol200;

}
