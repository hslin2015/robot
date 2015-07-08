package com.nana.serviceengine.domain.weather.domainparam;

import java.util.Date;
import java.util.List;

import com.nana.serviceengine.common.bean.GPS;
import com.nana.serviceengine.domain.itemcollector.CityCollector;
import com.nana.serviceengine.domain.itemcollector.GPSCollector;
import com.nana.serviceengine.domain.itemcollector.TimeCollector;
import com.nana.serviceengine.domain.weather.bean.WeatherFuture;
import com.nana.serviceengine.neuron.domainparam.DomainParam;
import com.nana.serviceengine.neuron.domainparam.bean.ParamCommand;
import com.nana.serviceengine.neuron.domainparam.bean.ParamItem;

public class WeatherParam extends DomainParam<WeatherFuture> {
	private String location;
	private Date date;

	private String gpslon;
	private String gpslat;
	private List<WeatherFuture> senvenWeathers;

	@Override
	protected void setParams() {
		ParamItem location = new ParamItem();
		location.setName("location");
		location.setCollector(CityCollector.getInstance());
		location.setAlertMes("请问您想知道什么地方的天气？");
		location.setCmd(new ParamCommand() {

			@Override
			public Object doProcess(ParamItem item) {
				if (item.getCollectResult() != null)
					return item.getCollectResult();
				return null;
			}

		});

		ParamItem date = new ParamItem();
		date.setName("date");
		date.setCollector(TimeCollector.getInstance());
		date.setCmd(new ParamCommand() {

			@Override
			public Object doProcess(ParamItem item) {
				if (item.getCollectResult() != null) {
					return item.getCollectResult();
				} else {
					return new Date();
				}
			}

		});
		ParamItem gps = new ParamItem();
		gps.setName("gps");
		gps.setCollector(GPSCollector.getInstance());
		gps.setCmd(new ParamCommand() {

			@Override
			public Object doProcess(ParamItem item) {
				if (item.getCollectResult() != null) {
					return item.getCollectResult();
				} else {
					return new GPS();
				}
			}

		});

		params.put(location.getName(), location);
		params.put(date.getName(), date);
		params.put(gps.getName(), gps);
	}

	// @Override
	// protected String dataCollectFinish(UserMessage mes) {
	// String[] locations = CityCollector.getInstance().parseCity(
	// mes.getTerms());
	// String lon = mes.getLon();
	// String lat = mes.getLat();
	// Date[] dates = TimeCollector.getInstance().parseDate(mes);
	// if ((locations != null && locations.length == 1 && !location
	// .equals(locations[0]))
	// ) {
	// count++;
	// location = locations[0];
	// loadType = LoadType.EXTERNALLOAD;
	//
	// }
	// if((dates != null && dates.length == 1 && !date
	// .equals(dates[0]))){
	// date = dates[0];
	// count++;
	// loadType = LoadType.EXTERNALLOAD;
	// }
	// return "好的。";
	// }
	//
	// @Override
	// protected String dataLack(UserMessage mes) {
	// boolean hasLoc = false;
	// boolean hasDate = false;
	// if (location == null) {
	// String[] locations = CityCollector.getInstance().parseCity(
	// mes.getTerms());
	// String lon = mes.getLon();
	// String lat = mes.getLat();
	// if (locations != null && locations.length == 1) {
	// location = locations[0];
	// hasLoc = true;
	// } else if (lon != null && !"".equals(lon) && lat != null
	// && !"".equals(lat)) {
	// gpslon = lon;
	// gpslat = lat;
	// hasLoc = true;
	// }
	// }
	//
	// if(date == null){
	// Date[] dates = TimeCollector.getInstance().parseDate(mes);
	// if(dates == null || dates.length <1){
	// date = new Date();
	// }else{
	// date = dates[0];
	// }
	// hasDate = true;
	// }
	// if(!hasLoc){
	// loadType = LoadType.NOLOAD;
	// return "请问您想知道什么地方的天气？";
	// }else{
	// loadType = LoadType.EXTERNALLOAD;
	// count=1;
	// }
	// return "好的";
	// }

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getGpslon() {
		return gpslon;
	}

	public void setGpslon(String gpslon) {
		this.gpslon = gpslon;
	}

	public String getGpslat() {
		return gpslat;
	}

	public void setGpslat(String gpslat) {
		this.gpslat = gpslat;
	}

	public List<WeatherFuture> getSenvenWeathers() {
		return senvenWeathers;
	}

	public void setSenvenWeathers(List<WeatherFuture> senvenWeathers) {
		this.senvenWeathers = senvenWeathers;
	}

}