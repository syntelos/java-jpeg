/*
 * JPEG Block I/O
 * Copyright (C) 2018, John Pritchard, Syntelos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package syntelos.jpeg;

/**
 * 
 */
public enum Marker {
    /*
     * Start Of Frame markers, non-differential, Huffman coding
     */
    SOF_0(0xC0,"Baseline DCT"),
    SOF_1(0xC1,"Extended sequential DCT"),
    SOF_2(0xC2,"Progressive DCT"),
    SOF_3(0xC3,"Lossless (sequential)"),
    /*
     * Start Of Frame markers, differential, Huffman coding
     */
    SOF_5(0xC5,"Differential sequential DCT"),
    SOF_6(0xC6,"Differential progressive DCT"),
    SOF_7(0xC7,"Differential lossless (sequential)"),
    /*
     * Start Of Frame markers, non-differential, arithmetic coding
     */
    JPG_X(0xC8,"Reserved for JPEG extensions"),
    SOF_9(0xC9,"Extended sequential DCT"),
    SOF_10(0xCA,"Progressive DCT"),
    SOF_11(0xCB,"Lossless (sequential)"),
    /*
     * Start Of Frame markers, differential, arithmetic coding
     */
    SOF_13(0xCD,"Differential sequential DCT"),
    SOF_14(0xCE,"Differential progressive DCT"),
    SOF_15(0xCF,"Differential lossless (sequential)"),
    /*
     * Huffman table specification
     */
    DHT(0xC4,"Define Huffman table(s)"),
    /*
     * Arithmetic coding conditioning specification
     */
    DAC(0xCC,"Define arithmetic coding conditioning(s)"),
    /*
     * Restart interval termination
     */
    RST_1(0xD0,"Restart with modulo 8 count “m”",true),
    RST_2(0xD1,"Restart with modulo 8 count “m”",true),
    RST_3(0xD2,"Restart with modulo 8 count “m”",true),
    RST_4(0xD3,"Restart with modulo 8 count “m”",true),
    RST_5(0xD4,"Restart with modulo 8 count “m”",true),
    RST_6(0xD5,"Restart with modulo 8 count “m”",true),
    RST_7(0xD6,"Restart with modulo 8 count “m”",true),
    RST_8(0xD7,"Restart with modulo 8 count “m”",true),
    /*
     * Other markers
     */
    SOI(0xD8,"Start of image",true),
    EOI(0xD9,"End of image",true),
    SOS(0xDA,"Start of scan"),
    DQT(0xDB,"Define quantization table(s)"),
    DNL(0xDC,"Define number of lines"),
    DRI(0xDD,"Define restart interval"),
    DHP(0xDE,"Define hierarchical progression"),
    EXP(0xDF,"Expand reference component(s)"),
    APP_0(0xE0,"Reserved for application segments"),
    APP_1(0xE1,"Reserved for application segments"),
    APP_2(0xE2,"Reserved for application segments"),
    APP_3(0xE3,"Reserved for application segments"),
    APP_4(0xE4,"Reserved for application segments"),
    APP_5(0xE5,"Reserved for application segments"),
    APP_6(0xE6,"Reserved for application segments"),
    APP_7(0xE7,"Reserved for application segments"),
    APP_8(0xE8,"Reserved for application segments"),
    APP_9(0xE9,"Reserved for application segments"),
    APP_A(0xEA,"Reserved for application segments"),
    APP_B(0xEB,"Reserved for application segments"),
    APP_C(0xEC,"Reserved for application segments"),
    APP_D(0xED,"Reserved for application segments"),
    APP_E(0xEE,"Reserved for application segments"),
    APP_F(0xEF,"Reserved for application segments"),

    JPG_1(0xF0,"Reserved for JPEG extensions"),
    JPG_2(0xF1,"Reserved for JPEG extensions"),
    JPG_3(0xF2,"Reserved for JPEG extensions"),
    JPG_4(0xF3,"Reserved for JPEG extensions"),
    JPG_5(0xF4,"Reserved for JPEG extensions"),
    JPG_6(0xF5,"Reserved for JPEG extensions"),
    JPG_7(0xF6,"Reserved for JPEG extensions"),
    JPG_8(0xF7,"Reserved for JPEG extensions"),
    JPG_9(0xF8,"Reserved for JPEG extensions"),
    JPG_A(0xF9,"Reserved for JPEG extensions"),
    JPG_B(0xFA,"Reserved for JPEG extensions"),
    JPG_C(0xFB,"Reserved for JPEG extensions"),
    JPG_D(0xFC,"Reserved for JPEG extensions"),
    JPG_E(0xFD,"Reserved for JPEG extensions"),

    COM(0xFE,"Comment"),
    /*
     * Reserved markers
     */
    TEM(0x01,"For temporary private use in arithmetic coding",true),
    RES_02(0x02,"Reserved"),
    RES_03(0x03,"Reserved"),
    RES_04(0x04,"Reserved"),
    RES_05(0x05,"Reserved"),
    RES_06(0x06,"Reserved"),
    RES_07(0x07,"Reserved"),
    RES_08(0x08,"Reserved"),
    RES_09(0x09,"Reserved"),
    RES_0A(0x0A,"Reserved"),
    RES_0B(0x0B,"Reserved"),
    RES_0C(0x0C,"Reserved"),
    RES_0D(0x0D,"Reserved"),
    RES_0E(0x0E,"Reserved"),
    RES_0F(0x0F,"Reserved"),
    RES_10(0x10,"Reserved"),
    RES_11(0x11,"Reserved"),
    RES_12(0x12,"Reserved"),
    RES_13(0x13,"Reserved"),
    RES_14(0x14,"Reserved"),
    RES_15(0x15,"Reserved"),
    RES_16(0x16,"Reserved"),
    RES_17(0x17,"Reserved"),
    RES_18(0x18,"Reserved"),
    RES_19(0x19,"Reserved"),
    RES_1A(0x1A,"Reserved"),
    RES_1B(0x1B,"Reserved"),
    RES_1C(0x1C,"Reserved"),
    RES_1D(0x1D,"Reserved"),
    RES_1E(0x1E,"Reserved"),
    RES_1F(0x1F,"Reserved"),
    RES_20(0x20,"Reserved"),
    RES_21(0x21,"Reserved"),
    RES_22(0x22,"Reserved"),
    RES_23(0x23,"Reserved"),
    RES_24(0x24,"Reserved"),
    RES_25(0x25,"Reserved"),
    RES_26(0x26,"Reserved"),
    RES_27(0x27,"Reserved"),
    RES_28(0x28,"Reserved"),
    RES_29(0x29,"Reserved"),
    RES_2A(0x2A,"Reserved"),
    RES_2B(0x2B,"Reserved"),
    RES_2C(0x2C,"Reserved"),
    RES_2D(0x2D,"Reserved"),
    RES_2E(0x2E,"Reserved"),
    RES_2F(0x2F,"Reserved"),
    RES_30(0x30,"Reserved"),
    RES_31(0x31,"Reserved"),
    RES_32(0x32,"Reserved"),
    RES_33(0x33,"Reserved"),
    RES_34(0x34,"Reserved"),
    RES_35(0x35,"Reserved"),
    RES_36(0x36,"Reserved"),
    RES_37(0x37,"Reserved"),
    RES_38(0x38,"Reserved"),
    RES_39(0x39,"Reserved"),
    RES_3A(0x3A,"Reserved"),
    RES_3B(0x3B,"Reserved"),
    RES_3C(0x3C,"Reserved"),
    RES_3D(0x3D,"Reserved"),
    RES_3E(0x3E,"Reserved"),
    RES_3F(0x3F,"Reserved"),
    RES_40(0x40,"Reserved"),
    RES_41(0x41,"Reserved"),
    RES_42(0x42,"Reserved"),
    RES_43(0x43,"Reserved"),
    RES_44(0x44,"Reserved"),
    RES_45(0x45,"Reserved"),
    RES_46(0x46,"Reserved"),
    RES_47(0x47,"Reserved"),
    RES_48(0x48,"Reserved"),
    RES_49(0x49,"Reserved"),
    RES_4A(0x4A,"Reserved"),
    RES_4B(0x4B,"Reserved"),
    RES_4C(0x4C,"Reserved"),
    RES_4D(0x4D,"Reserved"),
    RES_4E(0x4E,"Reserved"),
    RES_4F(0x4F,"Reserved"),
    RES_50(0x50,"Reserved"),
    RES_51(0x51,"Reserved"),
    RES_52(0x52,"Reserved"),
    RES_53(0x53,"Reserved"),
    RES_54(0x54,"Reserved"),
    RES_55(0x55,"Reserved"),
    RES_56(0x56,"Reserved"),
    RES_57(0x57,"Reserved"),
    RES_58(0x58,"Reserved"),
    RES_59(0x59,"Reserved"),
    RES_5A(0x5A,"Reserved"),
    RES_5B(0x5B,"Reserved"),
    RES_5C(0x5C,"Reserved"),
    RES_5D(0x5D,"Reserved"),
    RES_5E(0x5E,"Reserved"),
    RES_5F(0x5F,"Reserved"),
    RES_60(0x60,"Reserved"),
    RES_61(0x61,"Reserved"),
    RES_62(0x62,"Reserved"),
    RES_63(0x63,"Reserved"),
    RES_64(0x64,"Reserved"),
    RES_65(0x65,"Reserved"),
    RES_66(0x66,"Reserved"),
    RES_67(0x67,"Reserved"),
    RES_68(0x68,"Reserved"),
    RES_69(0x69,"Reserved"),
    RES_6A(0x6A,"Reserved"),
    RES_6B(0x6B,"Reserved"),
    RES_6C(0x6C,"Reserved"),
    RES_6D(0x6D,"Reserved"),
    RES_6E(0x6E,"Reserved"),
    RES_6F(0x6F,"Reserved"),
    RES_70(0x70,"Reserved"),
    RES_71(0x71,"Reserved"),
    RES_72(0x72,"Reserved"),
    RES_73(0x73,"Reserved"),
    RES_74(0x74,"Reserved"),
    RES_75(0x75,"Reserved"),
    RES_76(0x76,"Reserved"),
    RES_77(0x77,"Reserved"),
    RES_78(0x78,"Reserved"),
    RES_79(0x79,"Reserved"),
    RES_7A(0x7A,"Reserved"),
    RES_7B(0x7B,"Reserved"),
    RES_7C(0x7C,"Reserved"),
    RES_7D(0x7D,"Reserved"),
    RES_7E(0x7E,"Reserved"),
    RES_7F(0x7F,"Reserved"),
    RES_80(0x80,"Reserved"),
    RES_81(0x81,"Reserved"),
    RES_82(0x82,"Reserved"),
    RES_83(0x83,"Reserved"),
    RES_84(0x84,"Reserved"),
    RES_85(0x85,"Reserved"),
    RES_86(0x86,"Reserved"),
    RES_87(0x87,"Reserved"),
    RES_88(0x88,"Reserved"),
    RES_89(0x89,"Reserved"),
    RES_8A(0x8A,"Reserved"),
    RES_8B(0x8B,"Reserved"),
    RES_8C(0x8C,"Reserved"),
    RES_8D(0x8D,"Reserved"),
    RES_8E(0x8E,"Reserved"),
    RES_8F(0x8F,"Reserved"),
    RES_90(0x90,"Reserved"),
    RES_91(0x91,"Reserved"),
    RES_92(0x92,"Reserved"),
    RES_93(0x93,"Reserved"),
    RES_94(0x94,"Reserved"),
    RES_95(0x95,"Reserved"),
    RES_96(0x96,"Reserved"),
    RES_97(0x97,"Reserved"),
    RES_98(0x98,"Reserved"),
    RES_99(0x99,"Reserved"),
    RES_9A(0x9A,"Reserved"),
    RES_9B(0x9B,"Reserved"),
    RES_9C(0x9C,"Reserved"),
    RES_9D(0x9D,"Reserved"),
    RES_9E(0x9E,"Reserved"),
    RES_9F(0x9F,"Reserved"),
    RES_A0(0xA0,"Reserved"),
    RES_A1(0xA1,"Reserved"),
    RES_A2(0xA2,"Reserved"),
    RES_A3(0xA3,"Reserved"),
    RES_A4(0xA4,"Reserved"),
    RES_A5(0xA5,"Reserved"),
    RES_A6(0xA6,"Reserved"),
    RES_A7(0xA7,"Reserved"),
    RES_A8(0xA8,"Reserved"),
    RES_A9(0xA9,"Reserved"),
    RES_AA(0xAA,"Reserved"),
    RES_AB(0xAB,"Reserved"),
    RES_AC(0xAC,"Reserved"),
    RES_AD(0xAD,"Reserved"),
    RES_AE(0xAE,"Reserved"),
    RES_AF(0xAF,"Reserved"),
    RES_B0(0xB0,"Reserved"),
    RES_B1(0xB1,"Reserved"),
    RES_B2(0xB2,"Reserved"),
    RES_B3(0xB3,"Reserved"),
    RES_B4(0xB4,"Reserved"),
    RES_B5(0xB5,"Reserved"),
    RES_B6(0xB6,"Reserved"),
    RES_B7(0xB7,"Reserved"),
    RES_B8(0xB8,"Reserved"),
    RES_B9(0xB9,"Reserved"),
    RES_BA(0xBA,"Reserved"),
    RES_BB(0xBB,"Reserved"),
    RES_BC(0xBC,"Reserved"),
    RES_BD(0xBD,"Reserved"),
    RES_BE(0xBE,"Reserved"),
    RES_BF(0xBF,"Reserved");


    private final static String ToString(Marker m){

	return String.format("%s (%s) {0xFF,0x%02X}",m.name(),m.description,m.code);
    }


    public final int code;

    public final boolean solitary;

    public final String description;

    public final String string;


    Marker(int code, String desc){
	this.code = code;
	this.solitary = false;
	this.description = desc;
	this.string = ToString(this);
    }
    Marker(int code, String desc, boolean solitary){
	this.code = code;
	this.solitary = solitary;
	this.description = desc;
	this.string = ToString(this);
    }


    /**
     * @return Is reserved (<code>RES</code>)
     */
    public boolean is_reserved(){
	switch(this.code){
	case 0x02:
	case 0x03:
	case 0x04:
	case 0x05:
	case 0x06:
	case 0x07:
	case 0x08:
	case 0x09:
	case 0x0A:
	case 0x0B:
	case 0x0C:
	case 0x0D:
	case 0x0E:
	case 0x0F:
	case 0x10:
	case 0x11:
	case 0x12:
	case 0x13:
	case 0x14:
	case 0x15:
	case 0x16:
	case 0x17:
	case 0x18:
	case 0x19:
	case 0x1A:
	case 0x1B:
	case 0x1C:
	case 0x1D:
	case 0x1E:
	case 0x1F:
	case 0x20:
	case 0x21:
	case 0x22:
	case 0x23:
	case 0x24:
	case 0x25:
	case 0x26:
	case 0x27:
	case 0x28:
	case 0x29:
	case 0x2A:
	case 0x2B:
	case 0x2C:
	case 0x2D:
	case 0x2E:
	case 0x2F:
	case 0x30:
	case 0x31:
	case 0x32:
	case 0x33:
	case 0x34:
	case 0x35:
	case 0x36:
	case 0x37:
	case 0x38:
	case 0x39:
	case 0x3A:
	case 0x3B:
	case 0x3C:
	case 0x3D:
	case 0x3E:
	case 0x3F:
	case 0x40:
	case 0x41:
	case 0x42:
	case 0x43:
	case 0x44:
	case 0x45:
	case 0x46:
	case 0x47:
	case 0x48:
	case 0x49:
	case 0x4A:
	case 0x4B:
	case 0x4C:
	case 0x4D:
	case 0x4E:
	case 0x4F:
	case 0x50:
	case 0x51:
	case 0x52:
	case 0x53:
	case 0x54:
	case 0x55:
	case 0x56:
	case 0x57:
	case 0x58:
	case 0x59:
	case 0x5A:
	case 0x5B:
	case 0x5C:
	case 0x5D:
	case 0x5E:
	case 0x5F:
	case 0x60:
	case 0x61:
	case 0x62:
	case 0x63:
	case 0x64:
	case 0x65:
	case 0x66:
	case 0x67:
	case 0x68:
	case 0x69:
	case 0x6A:
	case 0x6B:
	case 0x6C:
	case 0x6D:
	case 0x6E:
	case 0x6F:
	case 0x70:
	case 0x71:
	case 0x72:
	case 0x73:
	case 0x74:
	case 0x75:
	case 0x76:
	case 0x77:
	case 0x78:
	case 0x79:
	case 0x7A:
	case 0x7B:
	case 0x7C:
	case 0x7D:
	case 0x7E:
	case 0x7F:
	case 0x80:
	case 0x81:
	case 0x82:
	case 0x83:
	case 0x84:
	case 0x85:
	case 0x86:
	case 0x87:
	case 0x88:
	case 0x89:
	case 0x8A:
	case 0x8B:
	case 0x8C:
	case 0x8D:
	case 0x8E:
	case 0x8F:
	case 0x90:
	case 0x91:
	case 0x92:
	case 0x93:
	case 0x94:
	case 0x95:
	case 0x96:
	case 0x97:
	case 0x98:
	case 0x99:
	case 0x9A:
	case 0x9B:
	case 0x9C:
	case 0x9D:
	case 0x9E:
	case 0x9F:
	case 0xA0:
	case 0xA1:
	case 0xA2:
	case 0xA3:
	case 0xA4:
	case 0xA5:
	case 0xA6:
	case 0xA7:
	case 0xA8:
	case 0xA9:
	case 0xAA:
	case 0xAB:
	case 0xAC:
	case 0xAD:
	case 0xAE:
	case 0xAF:
	case 0xB0:
	case 0xB1:
	case 0xB2:
	case 0xB3:
	case 0xB4:
	case 0xB5:
	case 0xB6:
	case 0xB7:
	case 0xB8:
	case 0xB9:
	case 0xBA:
	case 0xBB:
	case 0xBC:
	case 0xBD:
	case 0xBE:
	case 0xBF:
	    return true;
	default:
	    return false;
	}
    }


    /**
     * @return Is restart (<code>RST</code>)
     */
    public boolean is_restart(){
	switch(this.code){

	case 0xD0:
	case 0xD1:
	case 0xD2:
	case 0xD3:
	case 0xD4:
	case 0xD5:
	case 0xD6:
	case 0xD7:
	    return true;

	default:
	    return false;
	}
    }
    /**
     * Restart (<code>RST</code>) parameter value
     */
    public int p_restart(){
	switch(this.code){

	case 0xD0:
	    return 1;
	case 0xD1:
	    return 2;
	case 0xD2:
	    return 3;
	case 0xD3:
	    return 4;
	case 0xD4:
	    return 5;
	case 0xD5:
	    return 6;
	case 0xD6:
	    return 7;
	case 0xD7:
	    return 8;

	default:
	    throw new UnsupportedOperationException(this.toString());
	}
    }
    /**
     * @return Is <code>APP</code> 
     */
    public boolean is_app(){
	switch(this.code){

	case 0xE0:
	case 0xE1:
	case 0xE2:
	case 0xE3:
	case 0xE4:
	case 0xE5:
	case 0xE6:
	case 0xE7:
	case 0xE8:
	case 0xE9:
	case 0xEA:
	case 0xEB:
	case 0xEC:
	case 0xED:
	case 0xEE:
	case 0xEF:
	    return true;

	default:
	    return false;
	}
    }
    /**
     * <code>APP</code> parameter value
     */
    public int p_app(){
	switch(this.code){

	case 0xE0:
	    return 0;
	case 0xE1:
	    return 1;
	case 0xE2:
	    return 2;
	case 0xE3:
	    return 3;
	case 0xE4:
	    return 4;
	case 0xE5:
	    return 5;
	case 0xE6:
	    return 6;
	case 0xE7:
	    return 7;
	case 0xE8:
	    return 8;
	case 0xE9:
	    return 9;
	case 0xEA:
	    return 10;
	case 0xEB:
	    return 11;
	case 0xEC:
	    return 12;
	case 0xED:
	    return 13;
	case 0xEE:
	    return 14;
	case 0xEF:
	    return 15;

	default:
	    throw new UnsupportedOperationException(this.toString());
	}
    }
    /**
     * @return Is <code>JPEG</code> 
     */
    public boolean is_jpg(){
	switch(this.code){

	case 0xF0:
	case 0xF1:
	case 0xF2:
	case 0xF3:
	case 0xF4:
	case 0xF5:
	case 0xF6:
	case 0xF7:
	case 0xF8:
	case 0xF9:
	case 0xFA:
	case 0xFB:
	case 0xFC:
	case 0xFD:
	    return true;

	default:
	    return false;
	}
    }
    /**
     * <code>JPEG</code> extension number
     */
    public int p_jpg(){
	switch(this.code){

	case 0xF0:
	    return 1;
	case 0xF1:
	    return 2;
	case 0xF2:
	    return 3;
	case 0xF3:
	    return 4;
	case 0xF4:
	    return 5;
	case 0xF5:
	    return 6;
	case 0xF6:
	    return 7;
	case 0xF7:
	    return 8;
	case 0xF8:
	    return 9;
	case 0xF9:
	    return 10;
	case 0xFA:
	    return 11;
	case 0xFB:
	    return 12;
	case 0xFC:
	    return 13;
	case 0xFD:
	    return 14;

	default:
	    throw new UnsupportedOperationException(this.toString());
	}
    }
    public byte[] toByteArray(){

	return new byte[]{

	    (byte)0xff,
	    (byte)this.code
	};
    }
    public String toString(){
	return this.string;
    }

    public final static Marker valueOf(int m){
	switch(m){

	case 0xC0:
	    return Marker.SOF_0;
	case 0xC1:
	    return Marker.SOF_1;
	case 0xC2:
	    return Marker.SOF_2;
	case 0xC3:
	    return Marker.SOF_3;

	case 0xC4:
	    return Marker.DHT;

	case 0xC5:
	    return Marker.SOF_5;
	case 0xC6:
	    return Marker.SOF_6;
	case 0xC7:
	    return Marker.SOF_7;
	case 0xC8:
	    return Marker.JPG_X;
	case 0xC9:
	    return Marker.SOF_9;
	case 0xCA:
	    return Marker.SOF_10;
	case 0xCB:
	    return Marker.SOF_11;

	case 0xCC:
	    return Marker.DAC;

	case 0xCD:
	    return Marker.SOF_13;
	case 0xCE:
	    return Marker.SOF_14;
	case 0xCF:
	    return Marker.SOF_15;

	case 0xD0:
	    return Marker.RST_1;
	case 0xD1:
	    return Marker.RST_2;
	case 0xD2:
	    return Marker.RST_3;
	case 0xD3:
	    return Marker.RST_4;
	case 0xD4:
	    return Marker.RST_5;
	case 0xD5:
	    return Marker.RST_6;
	case 0xD6:
	    return Marker.RST_7;
	case 0xD7:
	    return Marker.RST_8;
	case 0xD8:
	    return Marker.SOI;
	case 0xD9:
	    return Marker.EOI;
	case 0xDA:
	    return Marker.SOS;
	case 0xDB:
	    return Marker.DQT;
	case 0xDC:
	    return Marker.DNL;
	case 0xDD:
	    return Marker.DRI;
	case 0xDE:
	    return Marker.DHP;
	case 0xDF:
	    return Marker.EXP;
	case 0xE0:
	    return Marker.APP_0;
	case 0xE1:
	    return Marker.APP_1;
	case 0xE2:
	    return Marker.APP_2;
	case 0xE3:
	    return Marker.APP_3;
	case 0xE4:
	    return Marker.APP_4;
	case 0xE5:
	    return Marker.APP_5;
	case 0xE6:
	    return Marker.APP_6;
	case 0xE7:
	    return Marker.APP_7;
	case 0xE8:
	    return Marker.APP_8;
	case 0xE9:
	    return Marker.APP_9;
	case 0xEA:
	    return Marker.APP_A;
	case 0xEB:
	    return Marker.APP_B;
	case 0xEC:
	    return Marker.APP_C;
	case 0xED:
	    return Marker.APP_D;
	case 0xEE:
	    return Marker.APP_E;
	case 0xEF:
	    return Marker.APP_F;
	case 0xF0:
	    return Marker.JPG_1;
	case 0xF1:
	    return Marker.JPG_2;
	case 0xF2:
	    return Marker.JPG_3;
	case 0xF3:
	    return Marker.JPG_4;
	case 0xF4:
	    return Marker.JPG_5;
	case 0xF5:
	    return Marker.JPG_6;
	case 0xF6:
	    return Marker.JPG_7;
	case 0xF7:
	    return Marker.JPG_8;
	case 0xF8:
	    return Marker.JPG_9;
	case 0xF9:
	    return Marker.JPG_A;
	case 0xFA:
	    return Marker.JPG_B;
	case 0xFB:
	    return Marker.JPG_C;
	case 0xFC:
	    return Marker.JPG_D;
	case 0xFD:
	    return Marker.JPG_E;
	case 0xFE:
	    return Marker.COM;
	    /*
	     * (pas en ordre...)
	     */
	case 0x01:
	    return Marker.TEM;
	    /*
	     * RESERVED
	     */
	case 0x02:
	    return Marker.RES_02;
	case 0x03:
	    return Marker.RES_03;
	case 0x04:
	    return Marker.RES_04;
	case 0x05:
	    return Marker.RES_05;
	case 0x06:
	    return Marker.RES_06;
	case 0x07:
	    return Marker.RES_07;
	case 0x08:
	    return Marker.RES_08;
	case 0x09:
	    return Marker.RES_09;
	case 0x0A:
	    return Marker.RES_0A;
	case 0x0B:
	    return Marker.RES_0B;
	case 0x0C:
	    return Marker.RES_0C;
	case 0x0D:
	    return Marker.RES_0D;
	case 0x0E:
	    return Marker.RES_0E;
	case 0x0F:
	    return Marker.RES_0F;
	case 0x10:
	    return Marker.RES_10;
	case 0x11:
	    return Marker.RES_11;
	case 0x12:
	    return Marker.RES_12;
	case 0x13:
	    return Marker.RES_13;
	case 0x14:
	    return Marker.RES_14;
	case 0x15:
	    return Marker.RES_15;
	case 0x16:
	    return Marker.RES_16;
	case 0x17:
	    return Marker.RES_17;
	case 0x18:
	    return Marker.RES_18;
	case 0x19:
	    return Marker.RES_19;
	case 0x1A:
	    return Marker.RES_1A;
	case 0x1B:
	    return Marker.RES_1B;
	case 0x1C:
	    return Marker.RES_1C;
	case 0x1D:
	    return Marker.RES_1D;
	case 0x1E:
	    return Marker.RES_1E;
	case 0x1F:
	    return Marker.RES_1F;
	case 0x20:
	    return Marker.RES_20;
	case 0x21:
	    return Marker.RES_21;
	case 0x22:
	    return Marker.RES_22;
	case 0x23:
	    return Marker.RES_23;
	case 0x24:
	    return Marker.RES_24;
	case 0x25:
	    return Marker.RES_25;
	case 0x26:
	    return Marker.RES_26;
	case 0x27:
	    return Marker.RES_27;
	case 0x28:
	    return Marker.RES_28;
	case 0x29:
	    return Marker.RES_29;
	case 0x2A:
	    return Marker.RES_2A;
	case 0x2B:
	    return Marker.RES_2B;
	case 0x2C:
	    return Marker.RES_2C;
	case 0x2D:
	    return Marker.RES_2D;
	case 0x2E:
	    return Marker.RES_2E;
	case 0x2F:
	    return Marker.RES_2F;
	case 0x30:
	    return Marker.RES_30;
	case 0x31:
	    return Marker.RES_31;
	case 0x32:
	    return Marker.RES_32;
	case 0x33:
	    return Marker.RES_33;
	case 0x34:
	    return Marker.RES_34;
	case 0x35:
	    return Marker.RES_35;
	case 0x36:
	    return Marker.RES_36;
	case 0x37:
	    return Marker.RES_37;
	case 0x38:
	    return Marker.RES_38;
	case 0x39:
	    return Marker.RES_39;
	case 0x3A:
	    return Marker.RES_3A;
	case 0x3B:
	    return Marker.RES_3B;
	case 0x3C:
	    return Marker.RES_3C;
	case 0x3D:
	    return Marker.RES_3D;
	case 0x3E:
	    return Marker.RES_3E;
	case 0x3F:
	    return Marker.RES_3F;
	case 0x40:
	    return Marker.RES_40;
	case 0x41:
	    return Marker.RES_41;
	case 0x42:
	    return Marker.RES_42;
	case 0x43:
	    return Marker.RES_43;
	case 0x44:
	    return Marker.RES_44;
	case 0x45:
	    return Marker.RES_45;
	case 0x46:
	    return Marker.RES_46;
	case 0x47:
	    return Marker.RES_47;
	case 0x48:
	    return Marker.RES_48;
	case 0x49:
	    return Marker.RES_49;
	case 0x4A:
	    return Marker.RES_4A;
	case 0x4B:
	    return Marker.RES_4B;
	case 0x4C:
	    return Marker.RES_4C;
	case 0x4D:
	    return Marker.RES_4D;
	case 0x4E:
	    return Marker.RES_4E;
	case 0x4F:
	    return Marker.RES_4F;
	case 0x50:
	    return Marker.RES_50;
	case 0x51:
	    return Marker.RES_51;
	case 0x52:
	    return Marker.RES_52;
	case 0x53:
	    return Marker.RES_53;
	case 0x54:
	    return Marker.RES_54;
	case 0x55:
	    return Marker.RES_55;
	case 0x56:
	    return Marker.RES_56;
	case 0x57:
	    return Marker.RES_57;
	case 0x58:
	    return Marker.RES_58;
	case 0x59:
	    return Marker.RES_59;
	case 0x5A:
	    return Marker.RES_5A;
	case 0x5B:
	    return Marker.RES_5B;
	case 0x5C:
	    return Marker.RES_5C;
	case 0x5D:
	    return Marker.RES_5D;
	case 0x5E:
	    return Marker.RES_5E;
	case 0x5F:
	    return Marker.RES_5F;
	case 0x60:
	    return Marker.RES_60;
	case 0x61:
	    return Marker.RES_61;
	case 0x62:
	    return Marker.RES_62;
	case 0x63:
	    return Marker.RES_63;
	case 0x64:
	    return Marker.RES_64;
	case 0x65:
	    return Marker.RES_65;
	case 0x66:
	    return Marker.RES_66;
	case 0x67:
	    return Marker.RES_67;
	case 0x68:
	    return Marker.RES_68;
	case 0x69:
	    return Marker.RES_69;
	case 0x6A:
	    return Marker.RES_6A;
	case 0x6B:
	    return Marker.RES_6B;
	case 0x6C:
	    return Marker.RES_6C;
	case 0x6D:
	    return Marker.RES_6D;
	case 0x6E:
	    return Marker.RES_6E;
	case 0x6F:
	    return Marker.RES_6F;
	case 0x70:
	    return Marker.RES_70;
	case 0x71:
	    return Marker.RES_71;
	case 0x72:
	    return Marker.RES_72;
	case 0x73:
	    return Marker.RES_73;
	case 0x74:
	    return Marker.RES_74;
	case 0x75:
	    return Marker.RES_75;
	case 0x76:
	    return Marker.RES_76;
	case 0x77:
	    return Marker.RES_77;
	case 0x78:
	    return Marker.RES_78;
	case 0x79:
	    return Marker.RES_79;
	case 0x7A:
	    return Marker.RES_7A;
	case 0x7B:
	    return Marker.RES_7B;
	case 0x7C:
	    return Marker.RES_7C;
	case 0x7D:
	    return Marker.RES_7D;
	case 0x7E:
	    return Marker.RES_7E;
	case 0x7F:
	    return Marker.RES_7F;
	case 0x80:
	    return Marker.RES_80;
	case 0x81:
	    return Marker.RES_81;
	case 0x82:
	    return Marker.RES_82;
	case 0x83:
	    return Marker.RES_83;
	case 0x84:
	    return Marker.RES_84;
	case 0x85:
	    return Marker.RES_85;
	case 0x86:
	    return Marker.RES_86;
	case 0x87:
	    return Marker.RES_87;
	case 0x88:
	    return Marker.RES_88;
	case 0x89:
	    return Marker.RES_89;
	case 0x8A:
	    return Marker.RES_8A;
	case 0x8B:
	    return Marker.RES_8B;
	case 0x8C:
	    return Marker.RES_8C;
	case 0x8D:
	    return Marker.RES_8D;
	case 0x8E:
	    return Marker.RES_8E;
	case 0x8F:
	    return Marker.RES_8F;
	case 0x90:
	    return Marker.RES_90;
	case 0x91:
	    return Marker.RES_91;
	case 0x92:
	    return Marker.RES_92;
	case 0x93:
	    return Marker.RES_93;
	case 0x94:
	    return Marker.RES_94;
	case 0x95:
	    return Marker.RES_95;
	case 0x96:
	    return Marker.RES_96;
	case 0x97:
	    return Marker.RES_97;
	case 0x98:
	    return Marker.RES_98;
	case 0x99:
	    return Marker.RES_99;
	case 0x9A:
	    return Marker.RES_9A;
	case 0x9B:
	    return Marker.RES_9B;
	case 0x9C:
	    return Marker.RES_9C;
	case 0x9D:
	    return Marker.RES_9D;
	case 0x9E:
	    return Marker.RES_9E;
	case 0x9F:
	    return Marker.RES_9F;
	case 0xA0:
	    return Marker.RES_A0;
	case 0xA1:
	    return Marker.RES_A1;
	case 0xA2:
	    return Marker.RES_A2;
	case 0xA3:
	    return Marker.RES_A3;
	case 0xA4:
	    return Marker.RES_A4;
	case 0xA5:
	    return Marker.RES_A5;
	case 0xA6:
	    return Marker.RES_A6;
	case 0xA7:
	    return Marker.RES_A7;
	case 0xA8:
	    return Marker.RES_A8;
	case 0xA9:
	    return Marker.RES_A9;
	case 0xAA:
	    return Marker.RES_AA;
	case 0xAB:
	    return Marker.RES_AB;
	case 0xAC:
	    return Marker.RES_AC;
	case 0xAD:
	    return Marker.RES_AD;
	case 0xAE:
	    return Marker.RES_AE;
	case 0xAF:
	    return Marker.RES_AF;
	case 0xB0:
	    return Marker.RES_B0;
	case 0xB1:
	    return Marker.RES_B1;
	case 0xB2:
	    return Marker.RES_B2;
	case 0xB3:
	    return Marker.RES_B3;
	case 0xB4:
	    return Marker.RES_B4;
	case 0xB5:
	    return Marker.RES_B5;
	case 0xB6:
	    return Marker.RES_B6;
	case 0xB7:
	    return Marker.RES_B7;
	case 0xB8:
	    return Marker.RES_B8;
	case 0xB9:
	    return Marker.RES_B9;
	case 0xBA:
	    return Marker.RES_BA;
	case 0xBB:
	    return Marker.RES_BB;
	case 0xBC:
	    return Marker.RES_BC;
	case 0xBD:
	    return Marker.RES_BD;
	case 0xBE:
	    return Marker.RES_BE;
	case 0xBF:
	    return Marker.RES_BF;

	default:
	    throw new IllegalArgumentException(String.format("0x%02X",m));
	}
    }
}
