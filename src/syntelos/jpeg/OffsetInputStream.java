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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;

/**
 * A file input stream with length and offset and an unread buffer.
 */
public class OffsetInputStream
    extends FileInputStream
{

    public final File file;

    public final long length;

    protected long offset = 0;

    private byte[] ubu;

    private int ubu_x = 0, ubu_z;


    public OffsetInputStream(File file)
	throws FileNotFoundException
    {
	super(file);

	this.file = file;

	this.length = file.length();
    }


    public void unread(byte[] buf){
	if (null != buf){
	    this.ubu = buf;
	    this.ubu_x = 0;
	    this.ubu_z = buf.length;
	    this.offset -= this.ubu_z;
	}
	else {
	    throw new IllegalArgumentException();
	}
    }
    @Override
    public int read() throws IOException {

	if (null != this.ubu && this.ubu_x < this.ubu_z){

	    this.offset += 1;

	    return this.ubu[this.ubu_x++];
	}
	else {

	    int r = super.read();

	    if (-1 < r){

		this.offset += 1;
	    }
	    return r;
	}
    }
    @Override
    public int read(byte b[], int o, int l) throws IOException {

	if (null != this.ubu){

	    int r = (this.ubu_z-1-this.ubu_x);

	    if (0 < r){

		if (r > l){
		    System.arraycopy(this.ubu,this.ubu_x,b,o,l);

		    this.ubu_x += l;
		    this.offset += l;

		    return l;
		}
		else {
		    System.arraycopy(this.ubu,this.ubu_x,b,o,r);

		    this.ubu_x += r;
		    this.offset += r;

		    return r;
		}
	    }
	}

	int red = super.read(b,o,l);

	this.offset += red;

	return red;
    }
    @Override
    public long skip(long n) throws IOException {

	long skp = 0;
	/*
	 */
	if (0 < n){

	    if (null != this.ubu){

		int re = (this.ubu_z-1-this.ubu_x);
		if (0 < re){

		    if (re > n){

			this.ubu_x += (int)n;
			this.offset += (int)n;

			skp = n;
			n = 0;
		    }
		    else {
			this.ubu_x += re;
			this.offset += re;

			skp = re;
			n -= re;
		    }
		}
	    }
	}
	/*
	 */
	if (0 < n){

	    skp += super.skip(n);

	    this.offset += skp;
	}
	return skp;
    }
    @Override
    public int available() throws IOException {

	if (null != this.ubu){

	    int re = (this.ubu_z-1-this.ubu_x);

	    if (0 < re){
		return (re+super.available());
	    }
	}
	return super.available();
    }
    @Override
    public void close() throws IOException {

	if (null != this.ubu){
	    this.ubu = null;
	    this.ubu_x = 0;
	    this.ubu_z = 0;
	}
	super.close();
    }
    @Override
    public void mark(int r){
    }
    @Override
    public void reset(){
    }
    @Override
    public boolean markSupported(){
	return false;
    }
    @Override
    public String toString(){
	return String.format("%9d/%9d %20s",offset,length,file.getPath());
    }
}
