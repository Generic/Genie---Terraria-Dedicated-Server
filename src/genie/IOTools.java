/*
 * To change this template, choose IOTools | Templates
 * and open the template in the editor.
 */
package genie;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.jboss.netty.buffer.ChannelBuffer;

/**
 *
 * @author Jay
 */
public class IOTools {
    // Some scary stuff.
    //http://typedescriptor.net/System.IO.BinaryReader.Read7BitEncodedInt()

    private static int read7BitEncodedInt(RandomAccessFile raf) throws IOException {
        int i = 0;
        int j = 0;
        while (j != 35) {
            byte b = raf.readByte();
            i |= (int) ((int) (b & 127) << (j & 31));
            j += 7;
            if ((b & 128) != 0) {
                continue;
            } else {
                return i;
            }
        }
        throw new IOException("Improper 7BitEncodedInt Read.");
    }

    //http://typedescriptor.net/System.Resources.ResourceWriter.Write7BitEncodedInt(BinaryWriter,Int32)
    private static void write7BitEncodedInt(RandomAccessFile store, int value) throws IOException {
        int num;
        for (num = (int) value; num >= 128; num >>= 7) {
            store.write(num | 128);
        }
        store.write(num);
    }

    private static int read7BitEncodedInt(ChannelBuffer buffer) {
        int i = 0;
        int j = 0;
        while (j != 35) {
            byte b = buffer.readByte();
            i |= (int) ((int) (b & 127) << (j & 31));
            j += 7;
            if ((b & 128) != 0) {
                continue;
            } else {
                return i;
            }
        }
        return -1;
//        byte num3;
//        int num = 0;
//        int num2 = 0;
//        do {
//            if (num2 == 0x23) {
//                System.out.println("Malformed 7bitencoded int");
//                return -Integer.MAX_VALUE;
//            }
//            num3 = buffer.readByte();
//            num |= (num3 & 0x7f) << num2;
//            num2 += 7;
//        } while ((num3 & 0x80) != 0);
//        return num;
    }

    private static void write7BitEncodedInt(ChannelBuffer store, int value) {
        int num;
        for (num = (int) value; num >= 128; num >>= 7) {
            store.writeByte(num | 128);
        }
        store.writeByte(num);
    }

    public static String readString(RandomAccessFile raf) throws IOException {
        int length = read7BitEncodedInt(raf);
        byte[] buf = new byte[length];
        for (int i = 0; i < length; i++) {
            buf[i] = raf.readByte();
        }
        return new String(buf);
    }

    public static void writeString(RandomAccessFile raf, String str) throws IOException {
        write7BitEncodedInt(raf, str.length());
        for (int i = 0; i < str.length(); i++) {
            raf.writeByte((byte) str.charAt(i));
        }
    }

    public static String readString(ChannelBuffer buffer, int length) {
        byte[] buf = new byte[length];
        for (int i = 0; i < length; i++) {
            buf[i] = buffer.readByte();
        }
        return new String(buf);
    }

    /**
     * Reads a C# Convention string
     * @param buffer The buffer to read from
     * @return The read string
     */
    public static String readFileString(ChannelBuffer buffer) {
        int length = read7BitEncodedInt(buffer);
        byte[] buf = new byte[length];
        for (int i = 0; i < length; i++) {
            buf[i] = buffer.readByte();
        }
        return new String(buf);
    }

    public static void writeFileString(ChannelBuffer buffer, String str) {
        write7BitEncodedInt(buffer, str.length());
        for (int i = 0; i < str.length(); i++) {
            buffer.writeByte((byte) str.charAt(i));
        }
    }

    public static void writeString(ChannelBuffer buffer, String str) {
        for (int i = 0; i < str.length(); i++) {
            buffer.writeByte((byte) str.charAt(i));
        }
    }
}
