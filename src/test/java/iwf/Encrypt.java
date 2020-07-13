package iwf;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Encrypt {

  public static void main(String[] args) {

    String input = "jdbc:log4jdbc:postgresql://semantics.insilicogen.com/iwf";

    StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
    pbeEnc.setAlgorithm("PBEWithMD5AndDES");
    pbeEnc.setPassword("incodsc"); //암호화 키를 입력

    String enc = pbeEnc.encrypt(input); //암호화 할 내용
    System.out.println("enc = " + enc); //암호화 한 내용을 출력

    //테스트용 복호화
    String des = pbeEnc.decrypt(enc);
    System.out.println("des = " + des);
  }

}
