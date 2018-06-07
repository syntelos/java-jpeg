/*
 * EXIF Block I/O
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
package syntelos.exif;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;

/**
 * 
 */
public class OffsetInputStream
    extends FileInputStream
{

    public final File file;

    public final long length;

    protected long offset = 0;


    public OffsetInputStream(File file)
	throws FileNotFoundException
    {
	super(file);

	this.file = file;

	this.length = file.length();
    }


    @Override
    public int read() throws IOException {

	this.offset += 1;

	return super.read();
    }
    @Override
    public int read(byte b[], int o, int l) throws IOException {

	int red = super.read(b,o,l);

	this.offset += red;

	return red;
    }
    @Override
    public long skip(long n) throws IOException {

	long skp = super.skip(n);

	this.offset += skp;

	return skp;
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
	return String.format("%d/%d",offset,length);
    }
}
