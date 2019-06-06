package com.dream.common.core.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeTools {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static final String XXOO = " 00:00:000" ;
	
	/**
	 * input end time and a number,this number means how many day start
	 * @param date
	 * @param dayNumber
	 * @return
	 */
    public static TimeB setTimeSE(Date date,int dayNumber){
    	long lo = date.getTime();
    	return new TimeB(new Date(lo-1000*3600*24*dayNumber),date);
    }
    
    /**
	 * input end time and a number,this number means how many day start
	 * @param date
	 * @param dayNumber
	 * @return
	 */
    public static TimeB setTimeSEOne(Date date,int dayNumber){
    	long lo = date.getTime();
    	Date dateL=null;
    	try {
			dateL=sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	long log =dateL.getTime();
    	return new TimeB(new Date(lo-1000*3600*24*dayNumber),new Date(log+1000*3600*24));
    }
    
    /**
     * 返回date到dayNumber后的date对象
     * @param date
     * @param dayNumber
     * @return
     */
    public static TimeB setTimeSEAfterBig(Date date,int dayNumber) {
    	try {
			date=sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	long lo = date.getTime();
    	return new TimeB(date,new Date(lo+1000*3600*24*dayNumber));
    }
    
    public static Date format(String str){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }

	public static Date forma3(String str){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String format1(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			String str = sdf.format(date);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public static String format2(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String str = sdf.format(date);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public static Date startTime(String strs){
    	Date date =format(strs);
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	try {
    		String str = sdf.format(date);
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public static TimeB getOneDay(){
    	String str =sdf.format(new Date());
    	str = str +XXOO;
    	try {
			Date date =sdff.parse(str);
			long log =date.getTime()+(1000*60*60);
			long log2 =log+1000*3600*24;
			new Date(log);
			System.out.println(sdff.format(new Date(log)));
			System.out.println("------------");
			System.out.println(sdff.format(new Date(log2)));
			return new TimeB(new Date(log),new Date(log2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    
    public static Date endTime(String strs){
    	Date date =format(strs);
    	long lo =date.getTime();
    	lo = lo+3600*1000*24;
    	return new Date(lo);
    }
    
    /** 
     * 获取过去第几天的日期 
     * 
     * @param past 
     * @return 
     */  
    public static Date getPastDate(int past) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);  
        Date today = calendar.getTime();  
        /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        String result = format.format(today);  */
        return today;  
    }  
   
    /** 
     * 获取未来 第 past 天的日期 
     * @param past 
     * @return 
     */  
    public static Date getFetureDate(int past) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);  
        Date today = calendar.getTime();  
        /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        String result = format.format(today);  */
        return today;  
    }  
    
    public static Map<String,Date> todayDate(Date date) throws ParseException{
		Map<String,Date> map = new HashMap<>();
		String da1str = sdf.format(date)+" 23:59:59";
		Date da1 = sdff.parse(da1str);
		Date da2 = sdf.parse(sdf.format(date));
		map.put("startTime", da2);
		map.put("endTime", da1);
		return map;
	}

	public static String getHMS(long timetemp) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(timetemp);
		SimpleDateFormat fmat=new SimpleDateFormat("HH:mm:ss");
		String time=fmat.format(calendar.getTime());
		return time;
	}

	public static boolean isNow(Date date) {
		//当前时间
		Date now = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		//获取今天的日期
		String nowDay = sf.format(now);
		//对比的时间
		String day = sf.format(date);

		return day.equals(nowDay);

	}

	public static boolean isMonth(Date date2){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		String date1=df.format(new Date());
		String date3=df.format(date2);
		String MM=date1.substring(4, 6);//截取系统月份
		String mm=date3.substring(4, 6);//要判断的月份，需要自己截取处理
		return (MM.equals(mm));
	}

	public static boolean isYear(Date date2){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		String date1=df.format(new Date());
		String date3=df.format(date2);
		String MM=date1.substring(0, 4);//截取系统月份
		String mm=date3.substring(0, 4);//要判断的月份，需要自己截取处理
		return (MM.equals(mm));
	}


	public static int differentDays(Date date1,Date date2)
	{
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2)   //同一年
		{
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2-day1) ;
		}
		else    //不同年
		{
			//System.out.println("判断day2 - day1 : " + (day2-day1));
			return day2-day1;
		}
	}



	public static void main(String[] args) throws ParseException {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		Long time = new Date().getTime() - sdff.parse("2019-03-22 17:46:50").getTime();
		long hour = time % nd / nh;
		System.out.println(hour);
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static
	class TimeB{
		private Date startTime;
		private Date endTime;
	}
}
