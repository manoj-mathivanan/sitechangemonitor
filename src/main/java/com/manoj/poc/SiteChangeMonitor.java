package com.manoj.poc;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class SiteChangeMonitor {

    public static void main(String[] args) throws IOException, InterruptedException {

        for(int i=0;i<Integer.MAX_VALUE;i++) {


            System.out.println("Triggering "+ i);
            boolean sent = false;

            if (Jsoup.connect("https://in.bookmyshow.com/buytickets/pvr-brookefields-mall-coimbatore/cinema-coim-BMCL-MT/20231019")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .validateTLSCertificates(false)
                    .followRedirects(true)
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .timeout(10000)
                    .execute()
                    .url().toString().contains("20231019")) {
                Mailer.sendMail("Movie open pvr-brookefields-mall-coimbatore on 19", "https://in.bookmyshow.com/buytickets/pvr-brookefields-mall-coimbatore/cinema-coim-BMCL-MT/20231019");
                sent = true;
            }

            if (Jsoup.connect("https://in.bookmyshow.com/buytickets/inox-prozone-mall-coimbatore/cinema-coim-INPT-MT/20231019")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .validateTLSCertificates(false)
                    .ignoreContentType(true)
                    .followRedirects(true)
                    .ignoreHttpErrors(true)
                    .timeout(10000)
                    .execute()
                    .url().toString().contains("20231019")) {
                Mailer.sendMail("Movie open inox-prozone-mall-coimbatore on 19", "https://in.bookmyshow.com/buytickets/inox-prozone-mall-coimbatore/cinema-coim-INPT-MT/20231019");
                sent = true;
            }

            if(sent){
                System.exit(1);
            }

            Thread.sleep(60000);
        }
    }

}