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
    extends java.lang.Object
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

	Type(int code){
	    this.code = code;
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
    }


    public final Endian endian;
    public final Tag.Table table;
    public final Tag tag;
    public final Field.Type type;

    public int count;
    public byte[] source;
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
	{
	    this.count = i_cnt;
	}
	this.source = r.copy(of,4);
	{
	    this.value = this.source;
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
    public int int16(){
	switch(this.type){
	case SHORT:
	case SSHORT:
	    /*
	     * [TODO] REVIEW: {a,b,0,0}/{0,0,a,b} interpretation of value
	     */
	    return this.endian.uint16(this.source,0);

	default:
	    throw new IllegalStateException(this.type.name());
	}
    }
    public int int32(){
	switch(this.type){
	case LONG:
	case SLONG:
	    return this.endian.sint32(this.source,0);

	default:
	    throw new IllegalStateException(this.type.name());
	}
    }
    public void println(PrintStream out){

	out.printf("%10s %10s %10s %10d ",this.table,this.tag,this.type,this.count);

	if (this.value instanceof byte[]){
	    byte[] bv = (byte[])value;
	    int a = (bv[0] & 0xFF);
	    int b = (bv[1] & 0xFF);
	    int c = (bv[2] & 0xFF);
	    int d = (bv[3] & 0xFF);

	    out.printf("{0x%02X, 0x%02X, 0x%02X, 0x%02X}%n",a,b,c,d);
	}
	else {
	    out.println(this.value);
	}
    }
}
