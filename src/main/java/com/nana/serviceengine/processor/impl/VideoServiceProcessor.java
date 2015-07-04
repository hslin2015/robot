package com.nana.serviceengine.processor.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.nana.robot.ui.RobotConsumerListener;
import com.nana.serviceengine.bean.UserDialog;
import com.nana.serviceengine.bean.UserMessage;
import com.nana.serviceengine.cacher.UserTheme;
import com.nana.serviceengine.dao.bean.DomainParam;
import com.nana.serviceengine.dao.bean.impl.VideoParam;
import com.nana.serviceengine.processor.ServiceProcessor;
import com.nana.serviceengine.sentence.impl.VideoSentenceCreator;
import com.nana.serviceengine.statemachine.DialogState;
import com.nana.serviceengine.statemachine.bean.LoadType;
import com.nana.serviceengine.statemachine.bean.ParamState;
import com.nana.serviceengine.webapi.SimpleApiAccessor;
import com.nana.serviceengine.webapi.bean.Movie;
import com.nana.serviceengine.webapi.impl.VideoAPI;

public class VideoServiceProcessor extends ServiceProcessor {

	@Override
	public void run() {
		//ssssss
		UserDialog userDialog = UserTheme.UserDialog.get(mes.getUserid());
		DomainParam domainParam = (DomainParam) userDialog.getParam();
		if (domainParam == null){
			domainParam = new VideoParam();
			userDialog.setParam(domainParam);
		}
		String answer = "好的";
		if(domainParam.getLoadType() == null || LoadType.NOLOAD.equals(domainParam.getLoadType())){
			answer = domainParam.dataCollect(mes);		
		}
		//eeeeee
		if (!LoadType.NOLOAD.equals(domainParam.getLoadType())) {
			
			if (domainParam.getLoadType().equals(LoadType.EXTERNALLOAD)) {
				// 这里需要修改调用方法，正常返回的不需要存储
				domainParam.setCount(1);
				domainParam.setParamState(ParamState.DATAFINISH);
				externalRequest(mes, (VideoParam) domainParam);
				// 保存会话的领域关键词
				
			} else if (domainParam.getLoadType().equals(LoadType.INTERNALLOAD)) {
				internalRequest(mes, (VideoParam) domainParam);
			}
			domainParam.setLoadType(LoadType.NOLOAD);
			// 保存会话的参数
			
			userDialog.setState(DialogState.FINISHED);

		} else {
			RobotConsumerListener.getInstance().sendMsg(answer, mes.getReqMessage());
			domainParam.setParamState(ParamState.DATALACK);
			userDialog.setState(DialogState.WAIT);
		}

	}

	/**
	 * 数据收集完成或关键属性改变后，进行结果请求
	 * 
	 * @param vParam
	 */
	private void externalRequest(UserMessage mes, VideoParam vParam) {
		SimpleApiAccessor apiAccessor = VideoAPI.getInstance();
		String data = apiAccessor.loadData(vParam);
		List<Movie> movies = JSON.parseArray(data, Movie.class);
		vParam.setResMovie(movies);
		RobotConsumerListener.getInstance().sendMsg(VideoSentenceCreator.getInstance().createSentence(vParam), mes.getReqMessage());
		
	}

	private void internalRequest(UserMessage mes, VideoParam vParam) {
	
		RobotConsumerListener.getInstance().sendMsg(VideoSentenceCreator.getInstance().createSentence(vParam), mes.getReqMessage());
		
	}

}
