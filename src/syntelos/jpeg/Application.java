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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Application segment
 */
public class Application
    extends Segment
{
    private final static String PP = "syntelos.jpeg.app.";

    private final static Map<String,String> MAP = new HashMap();
    static {
	MAP.put("http://ns.adobe.com/xap/1.0/", "XMP");
	MAP.put("Photoshop 3.0","IPTC");
	MAP.put("Adobe_Photoshop2.5:","IPTC");
	MAP.put("ICC_PROFILE","ICC");
	MAP.put("Adobe","ADCT");
    }


    /**
     * @param source A segment with an APP marker and APP tag.
     */
    public final static <T extends Application> T instantiate(Segment source){

	if (null != source && source.is_app()){

	    String tag = source.tag(), cn = null;

	    if (null != tag){
		{
		    String map = MAP.get(tag);
		    if (null != map){

			cn = (PP+map);

			System.err.printf("[%s -> %s = %s]%n",tag,map,cn);
		    }
		    else {
			cn = (PP+tag);

			System.err.printf("[%s = %s]%n",tag,cn);
		    }
		}


		try {
		    Class<T> cla = (Class<T>)Class.forName(cn);

		    Constructor<T> ctor = cla.getConstructor(Segment.class);

		    return (T)ctor.newInstance(source);
		}
		catch (ClassNotFoundException x){
		    return null;
		}
		catch (ClassCastException x){
		    return null;
		}
		catch (NoSuchMethodException x){
		    return null;
		}
		catch (SecurityException x){
		    return null;
		}
		catch (InstantiationException x){
		    return null;
		}
		catch (IllegalAccessException x){
		    return null;
		}
		catch (IllegalArgumentException x){
		    return null;
		}
		catch (InvocationTargetException x){
		    return null;
		}
	    }
	}
	return null;
    }


    /**
     * Constructor of subclasses is public and takes one {@link
     * Segment} argument.
     */
    protected Application(Segment copy){
	super(copy);
	if (!this.marker.is_app()){
	    throw new IllegalStateException();
	}
    }
}