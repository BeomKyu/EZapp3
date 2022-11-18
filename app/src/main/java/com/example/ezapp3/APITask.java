package com.example.ezapp3;

import android.content.res.Resources;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;



public class APITask {
    String key = "Ic5dcu4eL2UZQRsdDj3p55zppwYcrBsdAFGjlD38oyGp0KLkJwTq9aUIVPosX%2BzGgRkkof%2Bro27sFdYebgGQgg%3D%3D";
    String nowZcode = "11";
    String nowZscode = "11200";

    public String getAPIData() throws IOException {
        StringBuffer buffer=new StringBuffer();
        Log.i("Myzcode", nowZcode);
        Log.i("Myzscode", nowZscode);

        //API문서를 통해서 요청하고싶은 항목을 urlBuilder.append()로 추가하면됨
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/EvCharger/getChargerInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + key); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("9999", "UTF-8")); /*한 페이지 결과 수 (최소 10, 최대 9999)*/
        urlBuilder.append("&" + URLEncoder.encode("zcode","UTF-8") + "=" + URLEncoder.encode(nowZcode, "UTF-8")); /*시도 코드 (행정구역코드 앞 2자리)*/
        urlBuilder.append("&" + URLEncoder.encode("zscode", "UTF-8") + "=" + URLEncoder.encode(nowZscode, "UTF-8")); /*시도 상세코드 (행정구역코드 5자리)*/

        try {
            URL url= new URL(urlBuilder.toString());//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;
            xpp.next();
            int eventType= xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//태그 이름 얻어오기

                        if(tag.equals("item")) ;
                        else if(tag.equals("statNm")) {
                            buffer.append("충전소명 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("chgerType")) {
                            buffer.append("충전소타입 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("addr")) {
                            buffer.append("소재지 도로명 주소 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }else if(tag.equals("lat")){
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }else if(tag.equals("lng")){
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("useTime")) {
                            buffer.append("이용가능시간 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("stat")) {
                            buffer.append("충전기상태 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("statUpdDt")) {
                            buffer.append("상태갱신일시 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();
                        if(tag.equals("item")) buffer.append("\n");
                        break;
                }
                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
        return  buffer.toString();
    }

    public void setNowPlace(String[] nowPlace){
//        String Plce[];
//        Plce = nowPlace.split(" ");
//        Log.i("Myaddr", Plce[1] + Plce[2]);
        Log.i("Myaddr", nowPlace[0] + "," + nowPlace[1]);
        if(!(nowPlace[0] == null)){
            nowZcode = nowPlace[0];

        }else{
            nowZcode = null;
        }
        if(!(nowPlace[1] == null)){
            nowZscode = nowPlace[1];
            Log.i("Myset",nowPlace[1]);
        }else{
            nowZscode = null;
        }

    }

}
