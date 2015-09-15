package 第九章.实例97;
package test;

public class ISOtoGb2312
{
  public static String convert( String str )
  {
    try
    {
      byte[] bytesStr=str.getBytes( "ISO-8859-1" );
      return new String( bytesStr, "GB2312" );	
    }
    catch( Exception ex)
    {
  	return str;
    }
  }
}
