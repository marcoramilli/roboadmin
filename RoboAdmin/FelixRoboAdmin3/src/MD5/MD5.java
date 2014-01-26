/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MD5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author Luca
 * Classe che implementa la codifica MD5 di una stringa
 */
public class MD5 {



    public MD5(){

    }


    private String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }
    /**
    * Codifica in md5 di una stringa
    *
    * @param text stringa che viene convertita in md5
    * @return stringa in md5
    */

    public String MD5(String text) {
        MessageDigest md=null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (Exception ex) {
            System.out.print("errore 1"+ex.toString());
            Logger.getLogger(MD5.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] md5hash = new byte[32];
        try {
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
        } catch (Exception ex) {
            System.out.print("errore 1"+ex.toString());
            Logger.getLogger(MD5.class.getName()).log(Level.SEVERE, null, ex);
        }
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

}











