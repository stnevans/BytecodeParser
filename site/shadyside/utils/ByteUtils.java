package site.shadyside.utils;

public class ByteUtils {
	public static int getInt(byte[] arr, int off) {
		  return arr[off]<<8 &0xFF00 | arr[off+1]&0xFF;
	}
	
	public static double getDouble(byte[] buf, int off) {
		 return (double)( (buf[off] & 0xff) | 
                 ((buf[off] & 0xff) <<  8) |
                 ( buf[off]         << 16));
		}
	
	public static long getLong(byte[] buf, int off) {
	    long result = 0;
	    for (int i = 0; i < 8; i++) {
	        result <<= 8;
	        result |= (buf[off+i] & 0xFF);
	    }
	    return result;
	}
}
