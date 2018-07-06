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
package syntelos.jpeg.tiff;

import syntelos.rabu.Endian;

import java.io.PrintStream;

/**
 * 
 */
public class Field 
    extends java.lang.Number
    implements syntelos.rabu.Component
{
    /**
     * 
     */
    public static enum Type {
	BYTE       (1),
	ASCII      (2),
	SHORT      (3), 
	LONG       (4),
	RATIONAL   (5),
	SBYTE      (6),
	UNDEFINED  (7),
	SSHORT     (8),
	SLONG      (9),
	SRATIONAL (10),
	FLOAT     (11),
	DOUBLE    (12);

	public final int code;
	public final int length;

	Type(int code){
	    this.code = code;
	    this.length = count(code);
	}

	public final static Type valueOf(int code){
	    switch(code){
	    case 1:
		return BYTE;
	    case 2:
		return ASCII;
	    case 3:
		return SHORT;
	    case 4:
		return LONG;
	    case 5:
		return RATIONAL;
	    case 6:
		return SBYTE;
	    case 7:
		return UNDEFINED;
	    case 8:
		return SSHORT;
	    case 9:
		return SLONG;
	    case 10:
		return SRATIONAL;
	    case 11:
		return FLOAT;
	    case 12:
		return DOUBLE;
	    default:
		throw new IllegalArgumentException(String.format("Unrecognized field type code 0x%02X",code));
	    }
	}
	/**
	 * @return Length of type: greater than zero for static type
	 * formats, negative one for variable ASCII character string.
	 */
	public final static int count(int code){
	    switch(code){
	    case 1:
		return 1;
	    case 2:
		return 1;
	    case 3:
		return 2;
	    case 4:
		return 4;
	    case 5:
		return 8;
	    case 6:
		return 1;
	    case 7:
		return 0;
	    case 8:
		return 2;
	    case 9:
		return 4;
	    case 10:
		return 8;
	    case 11:
		return 4;
	    case 12:
		return 8;
	    default:
		throw new IllegalArgumentException(String.format("Unrecognized field type code 0x%02X",code));
	    }
	}
    }


    public final Endian endian;
    public final Tag.Table table;
    public final Tag tag;
    public final Field.Type type;
    /**
     * Data coordinates
     */
    public int offset, length;
    /**
     * Data value
     */
    public Object value;


    Field(TIFF r, Tag.Table t, int of){
	super();
	this.endian = r.endian;
	this.table = t;

	int i_tag = r.uint16(of); of += 2;
	{
	    this.tag = t.valueOf(i_tag);
	}
	int i_typ = r.uint16(of); of += 2;
	{
	    this.type = Field.Type.valueOf(i_typ);
	}
	int i_cnt = r.uint16(of); of += 2;

	/*
	 * data[] = r.copy(of,4)
	 */
	this.offset = r.uint16(of+2); of += 2;
	this.length = (i_cnt*this.type.length);

	switch(this.type){
	case ASCII:

	    this.value = r.substring(offset,length);
	    break;
	case BYTE:
	    this.value = new Integer(r.uint8(of+3));
	    break;
	case SBYTE:
	    this.value = new Byte((byte)r.uint8(of+3));
	    break;
	case SHORT:
	    this.value = new Integer(r.uint16(of+2));
	    break;
	case SSHORT:
	    this.value = new Short((short)r.uint16(of+2)); /* [TODO] sign extension */
	    break;
	case LONG:
	case SLONG:
	    this.value = new Integer(r.sint32(of)); /* [TODO] sign extension */
	    break;
	case FLOAT:
	    this.value = new Float(Float.intBitsToFloat(r.sint32(of)));
	    break;
	default:
	    break;
	}
    }


    public int indexOf(Tag[] tag){

	for (int cc = 0, cn = tag.length; cc < cn; cc++){

	    if (this.tag == tag[cc]){

		return cc;
	    }
	}
	return -1;
    }
    public int intValue(){
	if (this.value instanceof Number){

	    return ((Number)this.value).intValue();
	}
	else {
	    return 0;
	}
    }
    public long longValue(){
	if (this.value instanceof Number){

	    return ((Number)this.value).longValue();
	}
	else {
	    return 0;
	}
    }
    public float floatValue(){
	if (this.value instanceof Number){

	    return ((Number)this.value).floatValue();
	}
	else {
	    return 0;
	}
    }
    public double doubleValue(){
	if (this.value instanceof Number){

	    return ((Number)this.value).doubleValue();
	}
	else {
	    return 0;
	}
    }
    public void println(PrintStream out){

	out.printf("%10s %10s %10s [0x%04x, 0x%04x] ",this.table,this.tag,this.type,this.offset,this.length);

	if (this.value instanceof byte[]){

	    byte[] bary = (byte[])this.value;
	    int a = (bary[0] & 0xFF);
	    int b = (bary[1] & 0xFF);
	    int c = (bary[2] & 0xFF);
	    int d = (bary[3] & 0xFF);

	    out.printf("{0x%02X, 0x%02X, 0x%02X, 0x%02X}%n",a,b,c,d);
	}
	else if (null != this.value){

	    out.printf("{%s}%n",this.value);
	}
	else {
	    out.println();
	}
    }
}
