package com.sinovoice.example;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.sinovoice.example.asrrecorder.MainActivity;
import com.sinovoice.hcicloudsdk.common.asr.AsrConfig;
import com.sinovoice.hcicloudsdk.common.asr.AsrInitParam;

import org.json.JSONObject;

import java.io.File;

public class HciCloudAsrHelper {
	private static final String TAG = HciCloudAsrHelper.class.getSimpleName();

	//private static final String CAPKEY = "asr.cloud.freetalk";

	//private static final String CAPKEY = "asr.local.grammar.v4 ";
	
	private static final String VADTAIL = "400";
	private static final String VADHEAD = "2000"; // 识别前若静音长度超过此设定值，会触发
	private static  String VADTHRESHOLD;
	//private static final String VADTHRESHOLD = "5";




	private static HciCloudAsrHelper mInstance;

	private HciCloudAsrHelper() {
	}


    public static String getAsrInitConfig(Context context) {
        // 构造本地资源路径 DATAPATH . 该参数用于指定本地识别时所需资源的路径
        String datapath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "sinovoice"
                + File.separator + context.getPackageName() + File.separator + "data";

        // 将样例assets/data下的本地资源拷贝到DATAPATH下
		HciCloudHelper.copyAssetsFiles(context,datapath);
		final AsrInitParam asrInitParam = new AsrInitParam();
		asrInitParam.addParam(AsrInitParam.PARAM_KEY_DATA_PATH, datapath);
		Log.d(TAG, "asrInitParam " + asrInitParam.getStringConfig());
		return asrInitParam.getStringConfig();
	}


    public static String getAsrRecogConfigForCloudFreetalk() {
		AsrConfig recogConfig = new AsrConfig();
		//addNluParam(recogConfig);
		recogConfig.addParam(AsrConfig.SessionConfig.PARAM_KEY_CAP_KEY, AccountInfo.getInstance().getCapKey());
		recogConfig.addParam(AsrConfig.VadConfig.PARAM_KEY_VAD_TAIL, VADTAIL);
		recogConfig.addParam(AsrConfig.VadConfig.PARAM_KEY_VAD_HEAD, VADHEAD);

		// 本样例中，云端识别的realtime取值依赖界面上的流式识别（yes）与实时反馈（rt）的选择
		//recogConfig.addParam(AsrConfig.SessionConfig.PARAM_KEY_REALTIME, "yes");
		//addNluParam(recogConfig);
		//addNluParam(recogConfig);
		return recogConfig.getStringConfig();
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	public static String addNluParam(AsrConfig recogConfig){
		recogConfig.addParam("need_nlu","yes");
		JSONObject jsonObject = new JSONObject();
		try{
			jsonObject.put("address","http://10.0.1.45:8080/KBSBroker/queryAction");
			jsonObject.put("robotHashCode","rnFLNB");
			jsonObject.put("appKey","4c5d548a");
			//jsonObject.put("query","你几岁了");
			jsonObject.put("protocolId","5");
			jsonObject.put("talkerId","100921");
			jsonObject.put("receiverId","1002");
			jsonObject.put("type","voice");
			jsonObject.put("isNeedClearHistory","0");
			jsonObject.put("isQuestionQuery","0");
			jsonObject.put("msgId","0");
			jsonObject.put("userId","100031");
			//jsonObject.put("channel","22");
			jsonObject.put("platformConnType","22");
			jsonObject.put("sendTime","1600226643360");
		}catch (Exception e){
			e.printStackTrace();
		}
		String qaParamter = Base64.encodeToString(jsonObject.toString().getBytes(),Base64.NO_WRAP);
		recogConfig.addParam("qa_paramter",qaParamter);
		return recogConfig.getStringConfig();
	}

//	{
//    "protocolId":"5",
//    "query":"你几岁了",
//    "userId":"test_20200916",
//    "receiverId":"1002",
//    "talkerId":"100921",
//    "isQuestionQuery":"0",
//    "msgId":"0",
//    "type":"text",
//    "platformConnType":"22",
//    "appKey":"4c5d548a",
//    "isNeedClearHistory":"0",
//    "robotHashCode":"rnFLNB",
//    "sendTime":"1600226643360"
//	}






	public static String getAsrRecogConfigForLocalFreetalk() {
		AsrConfig recogConfig = new AsrConfig();
		recogConfig.addParam(AsrConfig.SessionConfig.PARAM_KEY_CAP_KEY, AccountInfo.getInstance().getCapKey());
		recogConfig.addParam(AsrConfig.VadConfig.PARAM_KEY_VAD_TAIL, VADTAIL);
		recogConfig.addParam(AsrConfig.VadConfig.PARAM_KEY_VAD_HEAD, VADHEAD);

		//由于DATAPATH下有多种模型，需通过资源前缀参数指定具体的本地资源文件
		recogConfig.addParam(AsrConfig.SessionConfig.PARAM_KEY_RES_PREFIX, "freetalk_");

		return recogConfig.getStringConfig();
	}

	public static String getAsrRecogConfigForLocalGrammar() {
		VADTHRESHOLD = MainActivity.getVADTHRESHOLD();
		AsrConfig recogConfig = new AsrConfig();
		recogConfig.addParam(AsrConfig.SessionConfig.PARAM_KEY_CAP_KEY, AccountInfo.getInstance().getCapKey());
		recogConfig.addParam(AsrConfig.VadConfig.PARAM_KEY_VAD_TAIL, VADTAIL);
		recogConfig.addParam(AsrConfig.VadConfig.PARAM_KEY_VAD_HEAD, VADHEAD);
		recogConfig.addParam(AsrConfig.VadConfig.PARAM_KEY_VAD_THRESHOLD,VADTHRESHOLD);

		//由于DATAPATH下有多种模型，需通过资源前缀参数指定具体的本地资源文件
		recogConfig.addParam(AsrConfig.SessionConfig.PARAM_KEY_RES_PREFIX, "grammar_");
		return recogConfig.getStringConfig();
	}

	public static String getLoadGrammarConfig(){
        AsrConfig loadGrammarConfig = new AsrConfig();
        loadGrammarConfig.addParam(AsrConfig.GrammarConfig.PARAM_KEY_GRAMMAR_TYPE, AsrConfig.GrammarConfig.VALUE_OF_PARAM_GRAMMAR_TYPE_JSGF);
        loadGrammarConfig.addParam(AsrConfig.GrammarConfig.PARAM_KEY_IS_FILE, AsrConfig.VALUE_OF_NO);

        //由于DATAPATH下有多种模型，需通过资源前缀参数指定具体的本地资源文件
        loadGrammarConfig.addParam(AsrConfig.SessionConfig.PARAM_KEY_RES_PREFIX, "grammar_");

        // 必须传入capkey
        loadGrammarConfig.addParam(AsrConfig.SessionConfig.PARAM_KEY_CAP_KEY, "asr.local.grammar.v4");
        return loadGrammarConfig.getStringConfig();
    }

}
