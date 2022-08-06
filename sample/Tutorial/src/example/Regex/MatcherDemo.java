package example.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherDemo {

    public static void test1() {
        //String aaa = "default 172.16.65.200 1009";
        String aaa = "1009";
        String[] bbb = aaa.split("\\s");
        String ccc = bbb[bbb.length - 1];

        Pattern pOk = Pattern.compile("\\+OK\\s?(.*)");
        Matcher mOk = pOk.matcher("+OK   asdfew");
        while (mOk.find()) {
            if (1 <= mOk.groupCount()) {
                String replyDesc = mOk.group(1);
                break;
            }
        }
    }

    // reg_user,realm,token,url,expires,network_ip,network_port,network_proto,hostname,metadata,
    // 1010,192.168.5.220,0082F7C1-1B7F-EB11-82FF-ECDE7BBEC29A@192.168.5.2,sofia/internal/sip:1010@192.168.5.2:5060,1615354128,192.168.5.2,5060,udp,debian,
    // 1011,192.168.5.220,486ac96257a944fd80f209a700ec6fb9,sofia/internal/sip:1011@192.168.5.2:51191;ob,1615354072,192.168.5.2,51191,udp,debian,
    public static void test2() {
        String s = "1010,192.168.5.220,0082F7C1-1B7F-EB11-82FF-ECDE7BBEC29A@192.168.5.2,sofia/internal/sip:1010@192.168.5.2:5060,1615354128,192.168.5.2,5060,udp,debian,";
        Pattern p = Pattern.compile("([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),");
        Matcher m = p.matcher(s);
        while (m.find()) {
            int c = m.groupCount();
            String m1 = m.group(1);
            String m2 = m.group(2);
            String m3 = m.group(3);
            String m4 = m.group(4);
        }
    }

    public static void test3() {
        String s = "5661b81e-29a5-4d2b-89ad-5a66f21f19cb,inbound,2020-11-25 16:31:27,1606293087,sofia/internal/1001@172.16.65.200,CS_EXECUTE,1001,1001,172.16.65.200,1000,1001@172.16.65.200,,1001,ACTIVE,Outbound Call,1000,SEND,5661b81e-29a5-4d2b-89ad-5a66f21f19cb,YANGSHANGSHANG,Outbound Call,1000,24ed6fb0-a44f-4ee8-a925-3771a66a7ae5,outbound,2020-11-25 16:31:37,1606293097,sofia/internal/1000@172.16.65.200:5069,CS_EXCHANGE_MEDIA,Extension 1001,1001,172.16.65.200,1000,1000@172.16.65.200,,,ACTIVE,Outbound Call,1000,SEND,Extension 1001,1001,1606293099";
        Pattern p = Pattern.compile("([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),"
                + "([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),"
                + "([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),"
                + "([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),"
                + "([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),"
                + "([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),"
                + "([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),"
                + "([^,]*),([^,]*),([^,]*),([^,]*),([^,]*),"
                + "([^,]*)");
        Matcher m = p.matcher(s);
        while (m.find()) {
            int c = m.groupCount();
            String m1 = m.group(1);
            String m2 = m.group(2);
            String m3 = m.group(3);
            String m4 = m.group(4);
        }
    }

    public static void test4() {
        String s = "3005-172.16.65.200";
        Pattern p = Pattern.compile("([0-9]+).*");
        Matcher m = p.matcher(s);
        while (m.find()) {
            int c = m.groupCount();
            String m1 = m.group(1);
        }
    }

    public static void test5() {
        String s = "192.168.9.187";
        //String s = "192.168.9.187:5091";
        //String s = "sofia/internal/1014@192.168.9.187:5091";
        Pattern p = Pattern.compile("([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+)");
        Matcher m = p.matcher(s);
        while (m.find()) {
            int c = m.groupCount();
            String m1 = m.group(1);
            System.out.println(m1);
        }
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
    }

}
